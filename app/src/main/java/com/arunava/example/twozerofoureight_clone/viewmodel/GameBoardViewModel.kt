package com.arunava.example.twozerofoureight_clone.viewmodel

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.arunava.example.twozerofoureight_clone.data.Slot
import com.arunava.example.twozerofoureight_clone.util.AppConstants

class GameBoardViewModel(private val sharedPrefs: SharedPreferences) : ViewModel() {

    // Matrix of slots as live data
    private val _boardLiveData by lazy { MutableLiveData<Array<Array<Slot>>>() }
    val boardLiveData: LiveData<Array<Array<Slot>>> by lazy { _boardLiveData }

    // Best score as live data
    private val _bestScoreLiveData by lazy { MutableLiveData<Int>() }
    val bestScoreLiveData: LiveData<Int> by lazy { _bestScoreLiveData }

    // Current score as live data
    private val _scoreLiveData by lazy { MutableLiveData<Int>() }
    val scoreLiveData: LiveData<Int> by lazy { _scoreLiveData }

    // Game Won flag as live data
    private val _gameWonLiveData by lazy { MutableLiveData<Boolean>() }
    val gameWonLiveData: LiveData<Boolean> by lazy { _gameWonLiveData }

    // Game Over flag as live data
    private val _gameOver by lazy { MutableLiveData<Boolean>() }
    val gameOver: LiveData<Boolean> by lazy { _gameOver }

    // Board matrix
    private val board = Array(4) { Array(4) { Slot() } }

    // Scores
    private var score = 0
    private var bestScore = sharedPrefs.getInt(AppConstants.BEST_SCORE, 0)

    private var gameWon = false

    /**
     * Initialises the board
     * Empty board with two random slots filled with 2
     */
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
        _scoreLiveData.value = score
        _bestScoreLiveData.value = bestScore
    }

    /**
     * Slide items to left action.
     * Performs left slide of items if items can be slided left.
     * Then updates board, scores and checks for win and game over situation.
     */
    fun moveLeft() {
        if (!board.canSlideLeft()) {
            return
        }

        val currentMoveScore = board.slideLeft()

        populateRandomSlot()

        _boardLiveData.value = board

        updateScore(currentMoveScore)
        if (!gameWon) {
            checkWinSituation()
        }
        checkGameOver()
    }

    /**
     * Slide items to right action.
     * Performs right slide of items if items can be slided right.
     * Then updates board, scores and checks for win and game over situation.
     */
    fun moveRight() {
        if (!canSlideRight()) {
            return
        }

        // Swap columns
        board.reverseColumns()

        // Do left slide
        val currentMoveScore = board.slideLeft()

        // Swap columns again
        board.reverseColumns()

        populateRandomSlot()

        _boardLiveData.value = board

        updateScore(currentMoveScore)
        if (!gameWon) {
            checkWinSituation()
        }
        checkGameOver()
    }

    /**
     * Slide items to up action.
     * Performs up slide of items if items can be slided up.
     * Then updates board, scores and checks for win and game over situation.
     */
    fun moveUp() {
        if (!canSlideUp()) {
            return
        }

        // Rotate anti-clockwise
        board.rotateAntiClockwise()

        // Do left slide
        val currentMoveScore = board.slideLeft()

        // Rotate back clockwise
        board.rotateClockwise()

        populateRandomSlot()

        _boardLiveData.value = board

        updateScore(currentMoveScore)
        if (!gameWon) {
            checkWinSituation()
        }
        checkGameOver()
    }

    /**
     * Slide items to down action.
     * Performs down slide of items if items can be slided down.
     * Then updates board, scores and checks for win and game over situation.
     */
    fun moveDown() {
        if (!canSlideDown()) {
            return
        }

        // Rotate anti-clockwise
        board.rotateClockwise()

        // Do left slide
        val currentMoveScore = board.slideLeft()

        // Rotate back clockwise
        board.rotateAntiClockwise()

        populateRandomSlot()

        _boardLiveData.value = board

        updateScore(currentMoveScore)
        if (!gameWon) {
            checkWinSituation()
        }
        checkGameOver()
    }

    /**
     * Checks if items can be slide right
     */
    private fun canSlideRight(): Boolean {
        board.reverseColumns()
        return if (board.canSlideLeft()) {
            board.reverseColumns()
            true
        } else {
            board.reverseColumns()
            false
        }
    }

    /**
     * Checks if items can be slide up
     */
    private fun canSlideUp(): Boolean {
        board.rotateAntiClockwise()
        return if (board.canSlideLeft()) {
            board.rotateClockwise()
            true
        } else {
            board.rotateClockwise()
            false
        }
    }

    /**
     * Checks if items can be slide down
     */
    private fun canSlideDown(): Boolean {
        board.rotateClockwise()
        return if (board.canSlideLeft()) {
            board.rotateAntiClockwise()
            true
        } else {
            board.rotateAntiClockwise()
            false
        }
    }

    /**
     * Calculates the scores and notifies the UI
     */
    private fun updateScore(currentMoveScore: Int) {
        score += currentMoveScore
        _scoreLiveData.value = score
        if (score > bestScore) {
            bestScore = score
            _bestScoreLiveData.value = bestScore
            sharedPrefs.edit {
                putInt(AppConstants.BEST_SCORE, bestScore)
            }
        }
    }

    /**
     * Checks for game won situation and notifies observers when condition is satisfied
     */
    private fun checkWinSituation() {
        if (board.flatten().stream().anyMatch { it.value == 2048 }) {
            gameWon = true
            _gameWonLiveData.value = true
        }
    }

    /**
     * Checks for game over and notifies observers when condition is satisfied
     */
    private fun checkGameOver() {
        if (!board.canSlideLeft() && !canSlideRight() && !canSlideUp() && !canSlideDown()) {
            _gameOver.value = true
        }
    }

    /**
     * Finds a random empty slot and assigns a value of 2 or 4 to it.
     */
    private fun populateRandomSlot() {
        val emptySlots = board.findEmptySlots()

        val randomSlot = emptySlots.random()

        randomSlot.value = randomTwoOrFour()
    }

    /**
     * Generates a random 2 or 4 and returns that
     */
    private fun randomTwoOrFour(): Int {
        return listOf(2, 4).random()
    }

    /**
     * Traverses matrix and returns an array containing all the empty slots
     */
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

    /**
     * Slides items to the left.
     * Merges the slots of same value.
     * Returns the score in this slide.
     */
    private fun Array<Array<Slot>>.slideLeft(): Int {
        var score = 0
        forEachIndexed { index, row ->

            // Filter out non-zero items
            val newRow = row.filter { it.value != 0 }.toMutableList()

            // Merge items
            var counter = 0
            while (counter < newRow.size - 1) {
                if (newRow[counter] == newRow[counter + 1]) {
                    newRow[counter].value = newRow[counter].value + newRow[counter + 1].value
                    score += newRow[counter].value
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
        return score
    }

    /**
     * To check if a left slide operation will change the board or not.
     * Creates a copy of the board and performs left slide operation on it.
     * Then checks it's contents matches with the original board or not.
     */
    private fun Array<Array<Slot>>.canSlideLeft(): Boolean {
        val duplicateBoard = Array(4) { Array(4) { Slot() } }
        forEachIndexed { i, row ->
            row.forEachIndexed { j, slot ->
                duplicateBoard[i][j].value = slot.value
            }
        }

        duplicateBoard.slideLeft()

        return !this.contentDeepEquals(duplicateBoard)
    }

    /**
     * Rotates the matrix anti-clockwise
     */
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

    /**
     * Rotates the matrix clockwise
     */
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
        reverseColumns()
    }

    /**
     * Reverses the matrix column wise
     */
    private fun Array<Array<Slot>>.reverseColumns() {
        forEach { it.reverse() }
    }
}