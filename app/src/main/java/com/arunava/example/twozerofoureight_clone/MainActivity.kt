package com.arunava.example.twozerofoureight_clone

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arunava.example.twozerofoureight_clone.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val sharedPrefs by lazy { getSharedPreferences("2048_clone", MODE_PRIVATE) }

    private val gameBoardViewModel by lazy {
        ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return GameBoardViewModel(sharedPrefs) as T
            }
        }).get(GameBoardViewModel::class.java)
    }


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
        gameBoardViewModel.bestScoreLiveData.observe(this) {
            binding.best.text = "Best Score: $it"
        }
        gameBoardViewModel.scoreLiveData.observe(this) {
            binding.score.text = "Your Score: $it"
        }

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