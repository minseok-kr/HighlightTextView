package com.minseok.highlighttextviewsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.widget.Button
import android.widget.SeekBar
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputEditText
import com.minseok.hightlight.HighlightTextView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sentence = findViewById<HighlightTextView>(R.id.text_sample_sentence)

        val input = findViewById<TextInputEditText>(R.id.input)
        input.addTextChangedListener {
            sentence.highlight(it.toString())
        }

        val seek = findViewById<SeekBar>(R.id.seek_width)
        seek.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                val widthValue = TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        progress.toFloat(),
                        resources.displayMetrics
                )

                sentence.setStrokeWidth(widthValue)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit
            override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
        })

        val btnColorBlue = findViewById<Button>(R.id.btn_blue)
        btnColorBlue.setOnClickListener {
            sentence.setColor(R.color.blue)
        }

        val btnColorGreen = findViewById<Button>(R.id.btn_green)
        btnColorGreen.setOnClickListener {
            sentence.setColor(R.color.green)
        }

        val btnColorYellow = findViewById<Button>(R.id.btn_yellow)
        btnColorYellow.setOnClickListener {
            sentence.setColor(R.color.yellow)
        }
    }
}