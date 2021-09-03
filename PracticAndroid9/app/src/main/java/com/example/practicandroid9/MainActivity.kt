package com.example.practicandroid9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_elements)
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = CustomRecyclerAdapter(Elements.printAll(), recyclerView, this)
        //setting toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    //setting menu in action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_for_toolbar,menu)
        return super.onCreateOptionsMenu(menu)
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            //add element
            R.id.addBarButton -> {
                startActivity(Intent(this, AddEditElementActivity::class.java))
                finish()
                true
            }
            //search element by word
            R.id.searchBarButton -> {
                true
            }
            //types of sort
            R.id.sortProduct -> {
                true
            }
            R.id.sortCount -> {
                true
            }
            R.id.sortPrice -> {
                true
            }
            R.id.sortDate -> {
                true
            }
            else -> {
                false
            }
        }
}