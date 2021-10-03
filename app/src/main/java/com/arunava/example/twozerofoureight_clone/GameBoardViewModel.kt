package com.arunava.example.twozerofoureight_clone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameBoardViewModel : ViewModel() {

    // Triple of rowId, columnId, data as live data
    private val _boardLiveData by lazy { MutableLiveData<Array<IntArray>>() }
    val boardLiveData: LiveData<Array<IntArray>> by lazy { _boardLiveData }

    private val board = arrayOf(
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0),
        intArrayOf(0, 0, 0, 0)
    )

    fun initBoard() {
        val spaces = board.findEmptySpaces()

        // Pick 1st random
        val item1 = spaces.random()

        // Remove that item from spaces array
        spaces.remove(item1)

        // Pick 2nd random
        val item2 = spaces.random()

        // Fill spaces with 2 in board
        board[item1.first][item1.second] = 2
        board[item2.first][item2.second] = 2

        // Update UI
        _boardLiveData.value = board
    }

    fun moveUp() {
        // Rotate anti-clockwise
        board.rotateAntiClockwise()

        // Do left slide
        board.forEachIndexed { index, row ->
            // Filter out non-zero items
            val newRow = row.filter { it != 0 }.toMutableList()

            // Merge items
            var counter = 0
            while (counter < newRow.size - 1) {
                if (newRow[counter] == newRow[counter + 1]) {
                    newRow[counter] = newRow[counter] + newRow[counter + 1]
                    newRow.removeAt(counter + 1)
                }
                counter++
            }

            // Fill remaining spaces with 0 at end
            while (newRow.size < 4) {
                newRow.add(0)
            }
            board[index] = newRow.toIntArray()
        }

        // Rotate back clockwise
        board.rotateClockwise()

        populateRandomSlot()

        notifyUiToUpdate()
    }

    fun moveDown() {
        // Rotate anti-clockwise
        board.rotateClockwise()

        // Do left slide
        board.forEachIndexed { index, row ->
            // Filter out non-zero items
            val newRow = row.filter { it != 0 }.toMutableList()

            // Merge items
            var counter = 0
            while (counter < newRow.size - 1) {
                if (newRow[counter] == newRow[counter + 1]) {
                    newRow[counter] = newRow[counter] + newRow[counter + 1]
                    newRow.removeAt(counter + 1)
                }
                counter++
            }

            // Fill remaining spaces with 0 at end
            while (newRow.size < 4) {
                newRow.add(0)
            }
            board[index] = newRow.toIntArray()
        }

        // Rotate back clockwise
        board.rotateAntiClockwise()

        populateRandomSlot()

        notifyUiToUpdate()
    }

    fun moveLeft() {
        board.forEachIndexed { index, row ->

            // Filter out non-zero items
            val newRow = row.filter { it != 0 }.toMutableList()

            // Merge items
            var counter = 0
            while (counter < newRow.size - 1) {
                if (newRow[counter] == newRow[counter + 1]) {
                    newRow[counter] = newRow[counter] + newRow[counter + 1]
                    newRow.removeAt(counter + 1)
                }
                counter++
            }

            // Fill remaining spaces with 0 at end
            while (newRow.size < 4) {
                newRow.add(0)
            }

            board[index] = newRow.toIntArray()
        }

        populateRandomSlot()

        notifyUiToUpdate()
    }

    fun moveRight() {
        board.forEachIndexed { index, row ->

            // Filter out non-zero items
            val newRow = row.filter { it != 0 }.toMutableList()

            // Merge items
            var counter = newRow.size - 1
            while (counter > 0) {
                if (newRow[counter] == newRow[counter - 1]) {
                    newRow[counter] = newRow[counter] + newRow[counter - 1]
                    newRow.removeAt(counter - 1)
                }
                counter--
            }

            // Fill remaining spaces with 0 at front
            while (newRow.size < 4) {
                newRow.add(0, 0)
            }

            board[index] = newRow.toIntArray()
        }

        populateRandomSlot()

        notifyUiToUpdate()
    }

    private fun notifyUiToUpdate() {
        _boardLiveData.value = board
    }

    private fun populateRandomSlot() {
        val emptySlots = board.findEmptySpaces()

        val randomSlot = emptySlots.random()

        board[randomSlot.first][randomSlot.second] = randomTwoOrFour()
    }

    private fun randomTwoOrFour(): Int {
        return listOf(2, 4).random()
    }

    private fun Array<IntArray>.findEmptySpaces(): ArrayList<Pair<Int, Int>> {
        val emptySpaces = ArrayList<Pair<Int, Int>>()
        forEachIndexed { i, row ->
            row.forEachIndexed { j, item ->
                if (item == 0) {
                    emptySpaces.add(Pair(i, j))
                }
            }
        }
        return emptySpaces
    }

    private fun Array<IntArray>.rotateAntiClockwise() {
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

    private fun Array<IntArray>.rotateClockwise() {
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