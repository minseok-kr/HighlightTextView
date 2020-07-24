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

        val seekWidth = findViewById<SeekBar>(R.id.seek_width)
        seekWidth.setOnSeekBarChangeListener(SeekChangeListener(
                onProgressChanged = { _, progress, _ -> sentence.setStrokeWidth(progress.dpToPx()) }
        ))

        val seekRadius = findViewById<SeekBar>(R.id.seek_radius)
        seekRadius.setOnSeekBarChangeListener(SeekChangeListener(
                onProgressChanged = { _, progress, _ -> sentence.setRadius(progress.dpToPx()) }
        ))

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

    class SeekChangeListener(
            private var onProgressChanged: ((SeekBar?, Int, Boolean) -> Unit)? = null,
            private var onStartTrackingTouch: ((SeekBar?) -> Unit)? = null,
            private var onStopTrackingTouch: ((SeekBar?) -> Unit)? = null
    ) : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            onProgressChanged?.invoke(seekBar, progress, fromUser)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
            onStartTrackingTouch?.invoke(seekBar)
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            onStopTrackingTouch?.invoke(seekBar)
        }
    }

    private fun Int.dpToPx(): Float = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            resources.displayMetrics
    )
}