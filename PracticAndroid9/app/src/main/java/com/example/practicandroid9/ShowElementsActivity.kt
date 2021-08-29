package com.example.practicandroid9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class ShowElementsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_elements)
        //TODO переделать на RecyclerView, то что сейчас нужно для отладки

        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CustomRecyclerAdapter(fillList())

        val list = Elements.printAll()
        if (list.isNotEmpty()) {
            list.forEach {
                val formatDate = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT).format(it.date)
                findViewById<TextView>(R.id.textView123).text =
                    (it.product + " " + it.count.toString() + " " +
                            it.price.toString() + " " + formatDate)
            }
        }
        else findViewById<TextView>(R.id.textView123).text = "Список пуст"
    }
    private fun fillList(): List<String> {
        val data = mutableListOf<String>()
        (0..30).forEach { i -> data.add("$i element") }
        return data
    }
}