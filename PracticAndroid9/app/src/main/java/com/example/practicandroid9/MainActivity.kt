package com.example.practicandroid9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.core.view.forEachIndexed
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView : RecyclerView
    private lateinit var menuToolbar : Menu
    private var indexOfSelectedItem = -1
    private var searchText = ""
    private var searchOpened = true
    private var isListRestore = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_elements)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        if (!isListRestore) ActualList.list = Elements.printAll()
        recyclerView.adapter = CustomRecyclerAdapter(ActualList.list, recyclerView, this)
        //setting toolbar
        setSupportActionBar(findViewById(R.id.toolbar))
    }

    //setting menu in action bar
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_for_toolbar, menu)
        if (menu != null) menuToolbar = menu
        if (indexOfSelectedItem != -1) menu?.getItem(indexOfSelectedItem)?.isChecked = true
        val searchButton = menu?.findItem(R.id.searchBarButton)
        val search = searchButton?.actionView as SearchView
        if (searchText != "") search.setQuery(searchText, false)
        if (!searchOpened) search.isIconified = false
        search.queryHint = getString(R.string.search)
        val activity = this
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?) = false
            override fun onQueryTextChange(newText: String?): Boolean {
                val view = findViewById<TextView>(R.id.resultText)
                ActualList.list = Elements.search(newText as String) as MutableList<Objects>
                if (ActualList.list.count() == 0) {
                    view.visibility = View.VISIBLE
                    view.text = getString(R.string.NoProducts)
                }
                if (ActualList.list.count() > 0 || newText == "") view.visibility = View.INVISIBLE
                recyclerView.adapter = CustomRecyclerAdapter(ActualList.list, recyclerView, activity)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    /*TODO
        3. проблема наложения сортировки на поиск (если была строка поиска и сортировка то все вместе)+++
        4. сохранять список +++
       5.диалоговые окна при повороте тоже вызывать по новой если они были вызваны
       7.поправить диалоговое окно, все кнопки на нем должны быть в столбик (независимо от API)
       8.кэширование и восстановление списка
       9.дизайн
    */

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        val search = findViewById<SearchView>(R.id.searchBarButton)
        when (item.itemId) {
            //add element
            R.id.addBarButton -> {
                startActivity(Intent(this, AddEditElementActivity::class.java))
                finish()
            }
            //search element by word
            R.id.searchBarButton -> {  }
            //types of sort
            R.id.sortProduct -> ActualList.createListWithSearchOrSort(1, search.query.toString(), item, recyclerView, this)
            R.id.sortCount -> ActualList.createListWithSearchOrSort(2, search.query.toString(), item, recyclerView, this)
            R.id.sortPrice -> ActualList.createListWithSearchOrSort(3, search.query.toString(), item, recyclerView, this)
            R.id.sortDate -> ActualList.createListWithSearchOrSort(4, search.query.toString(), item, recyclerView, this)
            //очистка спискса
            R.id.delete -> {
                Dialog.createConfirmDialog(
                    this,
                    R.string.ConfirmMessage,
                    R.string.Confirm,
                    recyclerView
                )
            }
            //показать список по умолчанию
            R.id.defaultList -> {
                if (search.query != "") {
                    search.setQuery("", false)
                    onBackPressed()
                }
                ActualList.list = Elements.printAll()
                recyclerView.adapter =
                    CustomRecyclerAdapter(ActualList.list, recyclerView, this)
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //saving current text for TextView
        val textView = findViewById<TextView>(R.id.resultText)
        if (textView.text != "") {
            outState.putBoolean(SAVE_TEXTVIEW_VIS, textView.isVisible)
            outState.putString(SAVE_TEXTVIEW, textView.text.toString())
            Log.d("SAVE TEXTVIEW", "text view was saved")
        }
        //saving query and focus for SearchView
        val search = findViewById<SearchView>(R.id.searchBarButton)
        if (search.query != "") {
            outState.putString(SAVE_SEARCH_QUERY, search.query.toString())
            Log.d("SAVE SEARCH QUERY", "search query was saved")
        }
        outState.putBoolean(SAVE_SEARCH_FOCUS, search.isIconified)
        Log.d("SAVE_SEARCH_FOCUS", "search view was focused")
        //saving index of selected menu item (type of sort)
        menuToolbar.forEachIndexed { index, item ->
            if (item.isChecked) outState.putInt(SAVE_SELECTED_MENU_ITEM, index)
        }
        Log.d("SAVE_SELECTED_INDEX", "save selected type of sort")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        //get text and visibility for TextView
        val textView = findViewById<TextView>(R.id.resultText)
        textView.text = savedInstanceState.getString(SAVE_TEXTVIEW)
        textView.isVisible = savedInstanceState.getBoolean(SAVE_TEXTVIEW_VIS)
        Log.d("RESTORE TEXTVIEW", "text view was restored")
        //get query and focus for SearchView
        searchText = savedInstanceState.getString(SAVE_SEARCH_QUERY).toString()
        Log.d("RESTORE SEARCH TEXT", "search query was restored")
        searchOpened = savedInstanceState.getBoolean(SAVE_SEARCH_FOCUS)
        Log.d("RESTORE SEARCH FOCUS", "search focus was restored")
        //needs for updating list
        if (searchText != "") {
            isListRestore = true
            ActualList.list = Elements.search(searchText) as MutableList<Objects>
            Log.d("RESTORE_LIST_SEARCH", "")
        }
        indexOfSelectedItem = savedInstanceState.getInt(SAVE_SELECTED_MENU_ITEM, -1)
        Log.d("RESTORE_SELECTED_INDEX", "get selected type of sort")
        //get index of selected menu item (type of sort)
        if (indexOfSelectedItem != -1) {
            isListRestore = true
            ActualList.list = Elements.sort(indexOfSelectedItem - 1, ActualList.list)
            Log.d("RESTORE_LIST_SORT", "")
        }
        if (isListRestore)
            recyclerView.adapter = CustomRecyclerAdapter(ActualList.list, recyclerView, this)
    }

    override fun onBackPressed() {
        val search = findViewById<SearchView>(R.id.searchBarButton)
        if (!search.isIconified) {
            search.isIconified = true
            search.isFocusable = false
            searchOpened = false
            findViewById<TextView>(R.id.resultText).visibility = View.INVISIBLE
            Log.d("SEARCH FOCUS", "search close")
        }
        else super.onBackPressed()
    }

    companion object {
        const val SAVE_TEXTVIEW = "SAVE_TEXTVIEW"
        const val SAVE_TEXTVIEW_VIS = "SAVE_VISIBILITY"
        const val SAVE_SEARCH_QUERY = "SAVE_SEARCH_QUERY"
        const val SAVE_SEARCH_FOCUS = "SAVE_SEARCH_FOCUS"
        const val SAVE_SELECTED_MENU_ITEM = "SAVE_SELECTED_MENU_ITEM"
    }
}