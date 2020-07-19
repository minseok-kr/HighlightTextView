package com.minseok.highlighttextviewsample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
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