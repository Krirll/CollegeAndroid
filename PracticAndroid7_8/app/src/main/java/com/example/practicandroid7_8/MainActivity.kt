package com.example.practicandroid7_8

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.example.practicandroid7_8.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        // ax + b < 0
        button.setOnClickListener {
            val first = findViewById<EditText>(R.id.firstNumber).text.toString()
            val second = findViewById<EditText>(R.id.secondNumber).text.toString()
            val result =
                if (first.toDoubleOrNull() != null && second.toDoubleOrNull() != null) {
                    if (first.toDouble() == 0.0) {
                        if (second.toDouble() < 0) resources.getString(R.string.result1)
                        else resources.getString(R.string.result2)
                    } else resources.getString(
                        R.string.resultOfEquation,
                        String.format("%.3f", (-second.toDouble() / first.toDouble()))
                    )
                } else resources.getString(R.string.error)
            val intent = Intent(this, ResultActivity::class.java)
            intent.putExtra(ResultActivity.resultEquation, result)
            startActivity(intent)
        }
    }
}