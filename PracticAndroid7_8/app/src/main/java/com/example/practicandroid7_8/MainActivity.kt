package com.example.practicandroid7_8

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class Equation private constructor() {
    companion object {
        fun getResultOfEquation(firstNumber : String, secondNumber : String) : Double =
            if (firstNumber.toDoubleOrNull() != null && secondNumber.toDoubleOrNull() != null) {
                if (firstNumber.toDouble() == 0.0) {
                    if (secondNumber.toDouble() < 0) 1.0 //@string/result1
                    else 2.0 //@string/result2
                } else -secondNumber.toDouble() / firstNumber.toDouble() //@string/resultOfEquation
            } else 4.0 //@string/error
    }
}

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button = findViewById<Button>(R.id.button)
        // ax + b < 0
        button.setOnClickListener {
            val intent = Intent(this, ResultActivity::class.java)
            val resultString =
                when(val result = Equation.getResultOfEquation(findViewById<EditText>(R.id.firstNumber).text.toString(),
                                                               findViewById<EditText>(R.id.secondNumber).text.toString())) {
                    1.0 -> getString(R.string.result1)
                    2.0 -> getString(R.string.result2)
                    4.0 -> getString(R.string.error)
                    else -> getString(R.string.resultOfEquation, String.format("%.3f", result))
            }
            intent.putExtra(ResultActivity.resultEquation, resultString)
            startActivity(intent)
        }
    }
}