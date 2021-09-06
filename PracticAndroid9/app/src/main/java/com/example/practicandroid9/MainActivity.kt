package com.example.practicandroid9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_elements)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        ActualList.list = Elements.printAll()
        recyclerView.adapter = CustomRecyclerAdapter(ActualList.list, recyclerView, this)
        //setting toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    //setting menu in action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_for_toolbar,menu)

        val searchButton = menu?.findItem(R.id.searchBarButton)
        val search = searchButton?.actionView as SearchView
        search.queryHint = getString(R.string.search)
        val activity = this
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                ActualList.list = Elements.search(newText as String) as MutableList<Objects>
                recyclerView.adapter = CustomRecyclerAdapter(ActualList.list, recyclerView, activity)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    /*TODO 1.сделать поиск по всем параметрам одновременно +++
           2.сделать так, чтобы при нажатии на сортировку у нее выделялась точка
            (означает что эта сортировка выбрана) +++
           3.сделать саму сортировку +++
           4.добавить возможность удаления всего списка в том же меню, где и сортировка
           5.добавить два textView, один "ваш список пуст", другой для "не результатов по вашему запросу"
           5.кэширование и восстановление списка
    */

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) =
        when (item.itemId) {
            //add element
            R.id.addBarButton -> {
                startActivity(Intent(this, AddEditElementActivity::class.java))
                finish()
                true
            }
            //search element by word
            R.id.searchBarButton -> { true }
            //types of sort
            R.id.sortProduct -> {
                item.isChecked = true
                ActualList.list = Elements.sort(1)
                recyclerView.adapter = CustomRecyclerAdapter(ActualList.list, recyclerView, this)
                true
            }
            R.id.sortCount -> {
                item.isChecked = true
                ActualList.list = Elements.sort(2)
                recyclerView.adapter = CustomRecyclerAdapter(ActualList.list, recyclerView, this)
                true
            }
            R.id.sortPrice -> {
                item.isChecked = true
                ActualList.list = Elements.sort(3)
                recyclerView.adapter = CustomRecyclerAdapter(ActualList.list, recyclerView, this)
                true
            }
            R.id.sortDate -> {
                item.isChecked = true
                ActualList.list = Elements.sort(4)
                recyclerView.adapter = CustomRecyclerAdapter(ActualList.list, recyclerView, this)
                true
            }
            //очистка спискса
            R.id.delete -> {
                //todo сделать удаление + окно с подтверждением
                true
            }
            //показать список по умолчанию
            R.id.defaultList -> {
                //todo сделать выводить список по умолчанию
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
}