package com.arunava.example.twozerofoureight_clone

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.arunava.example.twozerofoureight_clone.databinding.ActivityMainBinding

data class Slot(var value: Int = 0) {

    fun update(binding: ActivityMainBinding, i: Int, j: Int) {
        when {
            i == 0 && j == 0 -> {
                binding.apply {
                    zeroZero.background = getBackground(zeroZero.context, value)
                    zeroZero.text = if (value != 0) value.toString() else ""
                }
            }
            i == 0 && j == 1 -> {
                binding.apply {
                    zeroOne.background = getBackground(zeroZero.context, value)
                    zeroOne.text = if (value != 0) value.toString() else ""
                }
            }
            i == 0 && j == 2 -> {
                binding.apply {
                    zeroTwo.background = getBackground(zeroZero.context, value)
                    zeroTwo.text = if (value != 0) value.toString() else ""
                }
            }
            i == 0 && j == 3 -> {
                binding.apply {
                    zeroThree.background = getBackground(zeroZero.context, value)
                    zeroThree.text = if (value != 0) value.toString() else ""
                }
            }

            i == 1 && j == 0 -> {
                binding.apply {
                    oneZero.background = getBackground(zeroZero.context, value)
                    oneZero.text = if (value != 0) value.toString() else ""
                }
            }
            i == 1 && j == 1 -> {
                binding.apply {
                    oneOne.background = getBackground(zeroZero.context, value)
                    oneOne.text = if (value != 0) value.toString() else ""
                }
            }
            i == 1 && j == 2 -> {
                binding.apply {
                    oneTwo.background = getBackground(zeroZero.context, value)
                    oneTwo.text = if (value != 0) value.toString() else ""
                }
            }
            i == 1 && j == 3 -> {
                binding.apply {
                    oneThree.background = getBackground(zeroZero.context, value)
                    oneThree.text = if (value != 0) value.toString() else ""
                }
            }

            i == 2 && j == 0 -> {
                binding.apply {
                    twoZero.background = getBackground(zeroZero.context, value)
                    twoZero.text = if (value != 0) value.toString() else ""
                }
            }
            i == 2 && j == 1 -> {
                binding.apply {
                    twoOne.background = getBackground(zeroZero.context, value)
                    twoOne.text = if (value != 0) value.toString() else ""
                }
            }
            i == 2 && j == 2 -> {
                binding.apply {
                    twoTwo.background = getBackground(zeroZero.context, value)
                    twoTwo.text = if (value != 0) value.toString() else ""
                }
            }
            i == 2 && j == 3 -> {
                binding.apply {
                    twoThree.background = getBackground(zeroZero.context, value)
                    twoThree.text = if (value != 0) value.toString() else ""
                }
            }

            i == 3 && j == 0 -> {
                binding.apply {
                    threeZero.background = getBackground(zeroZero.context, value)
                    threeZero.text = if (value != 0) value.toString() else ""
                }
            }
            i == 3 && j == 1 -> {
                binding.apply {
                    threeOne.background = getBackground(zeroZero.context, value)
                    threeOne.text = if (value != 0) value.toString() else ""
                }
            }
            i == 3 && j == 2 -> {
                binding.apply {
                    threeTwo.background = getBackground(zeroZero.context, value)
                    threeTwo.text = if (value != 0) value.toString() else ""
                }
            }
            i == 3 && j == 3 -> {
                binding.apply {
                    threeThree.background = getBackground(zeroZero.context, value)
                    threeThree.text = if (value != 0) value.toString() else ""
                }
            }
        }
    }

    private fun getBackground(context: Context, value: Int): Drawable? {
        return ContextCompat.getDrawable(
            context, when (value) {
                2 -> R.drawable.slot_background_2
                4 -> R.drawable.slot_background_4
                8 -> R.drawable.slot_background_8
                16 -> R.drawable.slot_background_16
                32 -> R.drawable.slot_background_32
                64 -> R.drawable.slot_background_64
                128 -> R.drawable.slot_background_128
                256 -> R.drawable.slot_background_256
                512 -> R.drawable.slot_background_512
                1024 -> R.drawable.slot_background_1024
                2048 -> R.drawable.slot_background_2048
                else -> R.drawable.slot_background_default
            }
        )
    }
}