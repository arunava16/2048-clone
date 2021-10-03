package com.arunava.example.twozerofoureight_clone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.arunava.example.twozerofoureight_clone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val gameBoardViewModel by lazy { ViewModelProvider(this).get(GameBoardViewModel::class.java) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.upButton.setOnClickListener {
            // Move UP
            gameBoardViewModel.moveUp()
        }

        binding.downButton.setOnClickListener {
            // Move DOWN
            gameBoardViewModel.moveDown()
        }

        binding.leftButton.setOnClickListener {
            // Move LEFT
            gameBoardViewModel.moveLeft()
        }

        binding.rightButton.setOnClickListener {
            // Move RIGHT
            gameBoardViewModel.moveRight()
        }

        gameBoardViewModel.boardLiveData.observe(this) { onBoardDataUpdate(it) }

        gameBoardViewModel.initBoard()
    }

    private fun onBoardDataUpdate(board: Array<IntArray>) {
        board.forEachIndexed { i, row ->
            row.forEachIndexed { j, item ->
                when {
                    i == 0 && j == 0 -> {
                        binding.zeroZero.text = if (item != 0) item.toString() else ""
                    }
                    i == 0 && j == 1 -> {
                        binding.zeroOne.text = if (item != 0) item.toString() else ""
                    }
                    i == 0 && j == 2 -> {
                        binding.zeroTwo.text = if (item != 0) item.toString() else ""
                    }
                    i == 0 && j == 3 -> {
                        binding.zeroThree.text = if (item != 0) item.toString() else ""
                    }

                    i == 1 && j == 0 -> {
                        binding.oneZero.text = if (item != 0) item.toString() else ""
                    }
                    i == 1 && j == 1 -> {
                        binding.oneOne.text = if (item != 0) item.toString() else ""
                    }
                    i == 1 && j == 2 -> {
                        binding.oneTwo.text = if (item != 0) item.toString() else ""
                    }
                    i == 1 && j == 3 -> {
                        binding.oneThree.text = if (item != 0) item.toString() else ""
                    }

                    i == 2 && j == 0 -> {
                        binding.twoZero.text = if (item != 0) item.toString() else ""
                    }
                    i == 2 && j == 1 -> {
                        binding.twoOne.text = if (item != 0) item.toString() else ""
                    }
                    i == 2 && j == 2 -> {
                        binding.twoTwo.text = if (item != 0) item.toString() else ""
                    }
                    i == 2 && j == 3 -> {
                        binding.twoThree.text = if (item != 0) item.toString() else ""
                    }

                    i == 3 && j == 0 -> {
                        binding.threeZero.text = if (item != 0) item.toString() else ""
                    }
                    i == 3 && j == 1 -> {
                        binding.threeOne.text = if (item != 0) item.toString() else ""
                    }
                    i == 3 && j == 2 -> {
                        binding.threeTwo.text = if (item != 0) item.toString() else ""
                    }
                    i == 3 && j == 3 -> {
                        binding.threeThree.text = if (item != 0) item.toString() else ""
                    }
                }
            }
        }
    }
}