package com.example.practicandroid9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val add = findViewById<Button>(R.id.AddButton)
        val delete = findViewById<Button>(R.id.DeleteButton)
        val edit = findViewById<Button>(R.id.EditButton)
        val sort = findViewById<Button>(R.id.SortButton)
        val search = findViewById<Button>(R.id.SearchButton)
        val show = findViewById<Button>(R.id.ShowButton)
        add.setOnClickListener {
            val intent = Intent(this, AddElementActivity::class.java)
            startActivity(intent)
        }
        delete.setOnClickListener {
            val intent = Intent(this, DeleteElementActivity::class.java)
            startActivity(intent)
        }
        edit.setOnClickListener {
            val intent = Intent(this, EditElementActivity::class.java)
            startActivity(intent)
        }
        sort.setOnClickListener {
            val intent = Intent(this, SortElementsActivity::class.java)
            startActivity(intent)
        }
        search.setOnClickListener {
            val intent = Intent(this, SearchElementsActivity::class.java)
            startActivity(intent)
        }
        show.setOnClickListener {
            val intent = Intent(this, ShowElementsActivity::class.java)
            startActivity(intent)
        }
    }
}