package com.example.practicandroid9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ShowElementsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_elements)
        findViewById<TextView>(R.id.textView123).text = "dfgsdfg"
        val elements = Elements.printAll()
        elements.forEach { findViewById<TextView>(R.id.textView123).text = (it.product + it.count + it.price + it.date.toString()) }
    }
}