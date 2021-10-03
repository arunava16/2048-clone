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

    private fun onBoardDataUpdate(board: Array<Array<Slot>>) {
        board.forEachIndexed { i, row ->
            row.forEachIndexed { j, slot ->
                slot.update(binding, i, j)
            }
        }
    }
}