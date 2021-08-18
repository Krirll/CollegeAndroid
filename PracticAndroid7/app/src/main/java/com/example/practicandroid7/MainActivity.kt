package com.example.practicandroid7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        // ax + b < 0
        button.setOnClickListener {
            val first = findViewById<EditText>(R.id.firstNumber).text.toString()
            val second = findViewById<EditText>(R.id.secondNumber).text.toString()
            val result = findViewById<TextView>(R.id.textView)
            if (first.toDoubleOrNull() != null && second.toDoubleOrNull() != null) {
                if (first.toInt() == 0) {
                    if (second.toDouble() < 0) result.text = resources.getString(R.string.result1)
                    else result.text = resources.getString(R.string.result2)
                }
                else result.text = resources.getString(R.string.resultOfEquation, String.format("%.3f", (-second.toDouble() / first.toDouble())))
            }
            else result.text = resources.getString(R.string.error)
        }
    }
}