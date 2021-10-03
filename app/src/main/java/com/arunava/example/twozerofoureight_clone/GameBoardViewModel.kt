package com.arunava.example.twozerofoureight_clone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameBoardViewModel : ViewModel() {

    // Triple of rowId, columnId, data as live data
    private val _boardLiveData by lazy { MutableLiveData<Array<Array<Slot>>>() }
    val boardLiveData: LiveData<Array<Array<Slot>>> by lazy { _boardLiveData }

    private val board = Array(4) { Array(4) { Slot() } }

    fun initBoard() {
        val emptySlots = board.findEmptySlots()

        // Pick 1st random
        val item1 = emptySlots.random()

        // Remove that item from spaces array
        emptySlots.remove(item1)

        // Pick 2nd random
        val item2 = emptySlots.random()

        // Fill spaces with 2 in board
        item1.value = 2
        item2.value = 2

        // Update UI
        _boardLiveData.value = board
    }

    fun moveUp() {
        // Rotate anti-clockwise
        board.rotateAntiClockwise()

        // Do left slide
        board.slideItemsLeft()

        // Rotate back clockwise
        board.rotateClockwise()

        populateRandomSlot()

        _boardLiveData.value = board
    }

    fun moveDown() {
        // Rotate anti-clockwise
        board.rotateClockwise()

        // Do left slide
        board.slideItemsLeft()

        // Rotate back clockwise
        board.rotateAntiClockwise()

        populateRandomSlot()

        _boardLiveData.value = board
    }

    fun moveLeft() {
        board.slideItemsLeft()

        populateRandomSlot()

        _boardLiveData.value = board
    }

    fun moveRight() {
        // Swap columns
        board.forEach { it.reverse() }

        // Do left slide
        board.slideItemsLeft()

        // Swap columns again
        board.forEach { it.reverse() }

        populateRandomSlot()

        _boardLiveData.value = board
    }

    private fun populateRandomSlot() {
        val emptySlots = board.findEmptySlots()

        val randomSlot = emptySlots.random()

        randomSlot.value = randomTwoOrFour()
    }

    private fun randomTwoOrFour(): Int {
        return listOf(2, 4).random()
    }

    private fun Array<Array<Slot>>.findEmptySlots(): ArrayList<Slot> {
        val emptySpaces = ArrayList<Slot>()
        forEach { row ->
            row.forEach { item ->
                if (item.value == 0) {
                    emptySpaces.add(item)
                }
            }
        }
        return emptySpaces
    }

    private fun Array<Array<Slot>>.slideItemsLeft() {
        forEachIndexed { index, row ->

            // Filter out non-zero items
            val newRow = row.filter { it.value != 0 }.toMutableList()

            // Merge items
            var counter = 0
            while (counter < newRow.size - 1) {
                if (newRow[counter] == newRow[counter + 1]) {
                    newRow[counter].value = newRow[counter].value + newRow[counter + 1].value
                    newRow.removeAt(counter + 1)
                }
                counter++
            }

            // Fill remaining spaces with 0 at end
            while (newRow.size < 4) {
                newRow.add(Slot())
            }

            // Update it in matrix
            this[index] = newRow.toTypedArray()
        }
    }

    private fun Array<Array<Slot>>.rotateAntiClockwise() {
        // Transpose
        for (i in indices) {
            for (j in 0..i) {
                if (i != j) {
                    val temp = this[i][j]
                    this[i][j] = this[j][i]
                    this[j][i] = temp
                }
            }
        }

        // Swap the rows
        reverse()
    }

    private fun Array<Array<Slot>>.rotateClockwise() {
        // Transpose
        for (i in indices) {
            for (j in 0..i) {
                if (i != j) {
                    val temp = this[i][j]
                    this[i][j] = this[j][i]
                    this[j][i] = temp
                }
            }
        }

        // Swap the columns
        forEach { it.reverse() }
    }
}