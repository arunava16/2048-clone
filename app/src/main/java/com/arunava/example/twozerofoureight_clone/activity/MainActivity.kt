package com.arunava.example.twozerofoureight_clone.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.arunava.example.twozerofoureight_clone.data.Slot
import com.arunava.example.twozerofoureight_clone.databinding.ActivityMainBinding
import com.arunava.example.twozerofoureight_clone.util.AppConstants
import com.arunava.example.twozerofoureight_clone.viewmodel.GameBoardViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    private val sharedPrefs by lazy {
        getSharedPreferences(
            AppConstants.PREFS_FILE_NAME,
            MODE_PRIVATE
        )
    }

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
        gameBoardViewModel.gameWonLiveData.observe(this) {
            if (it) showGameWon()
        }
        gameBoardViewModel.gameOver.observe(this) {
            if (it) showGameOver()
        }

        gameBoardViewModel.initBoard()
    }

    /**
     * Show game won dialog
     */
    private fun showGameWon() {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("Hurray! You've won")
            .setMessage("You can continue playing, or quit the game")
            .setPositiveButton("Continue Playing") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    /**
     * Show game over dialog
     */
    private fun showGameOver() {
        AlertDialog.Builder(this)
            .setCancelable(false)
            .setTitle("Game Over! No more moves possible")
            .setMessage(
                "Your score is ${gameBoardViewModel.scoreLiveData.value}" +
                        "\nWould you like to try again or quit the game ?"
            )
            .setPositiveButton("Try again") { dialog, _ ->
                dialog.dismiss()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            .setNegativeButton("Close Game") { dialog, _ ->
                dialog.dismiss()
                finish()
            }
            .show()
    }

    private fun onBoardDataUpdate(board: Array<Array<Slot>>) {
        board.forEachIndexed { i, row ->
            row.forEachIndexed { j, slot ->
                slot.update(binding, i, j)
            }
        }
    }
}