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
        if (!isListRestore)
            ActualList.list = Elements.printAll()
        checkForTextViewSettings()
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
                ActualList.list = Elements.search(newText as String) as MutableList<Objects>
                if (ActualList.list.count() == 0 && newText != "")
                    textViewSettings(true, getString(R.string.NoProducts))
                else {
                    if (ActualList.list.count() == 0 && newText == "")
                        textViewSettings(true, getString(R.string.EmptyList))
                    else textViewSettings(false)
                }
                recyclerView.adapter = CustomRecyclerAdapter(ActualList.list, recyclerView, activity)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    // actions on click menu items
    override fun onOptionsItemSelected(item: MenuItem) : Boolean {
        val search = findViewById<SearchView>(R.id.searchBarButton)
        when (item.itemId) {
            //add element
            R.id.addBarButton -> {
                startActivity(Intent(this, AddEditElementActivity::class.java))
                finish()
            }
            //types of sort
            R.id.sortProduct -> {
                ActualList.createListWithSearchOrSort(1, search.query.toString(), item, recyclerView, this)
                checkForTextViewSettings(search.query.toString())
            }
            R.id.sortCount -> {
                ActualList.createListWithSearchOrSort(2, search.query.toString(), item, recyclerView, this)
                checkForTextViewSettings(search.query.toString())
            }
            R.id.sortPrice -> {
                ActualList.createListWithSearchOrSort(3, search.query.toString(), item, recyclerView, this)
                checkForTextViewSettings(search.query.toString())
            }
            R.id.sortDate -> {
                ActualList.createListWithSearchOrSort(4, search.query.toString(), item, recyclerView, this)
                checkForTextViewSettings(search.query.toString())
            }
            //очистка спискса
            R.id.delete -> {
                Dialog.createConfirmDialog(
                    this,
                    R.string.ConfirmMessage,
                    R.string.Confirm,
                    recyclerView,
                    findViewById(R.id.resultText),
                    getString(R.string.EmptyList)
                )
                menuToolbar.findItem(R.id.defaultList).isChecked = true
            }
            //показать список по умолчанию
            R.id.defaultList -> {
                item.isChecked = true
                if (search.query.isNotEmpty()) {
                    search.setQuery("", true)
                    onBackPressed()
                }
                ActualList.list = Elements.printAll()
                recyclerView.adapter =
                    CustomRecyclerAdapter(ActualList.list, recyclerView, this)
                checkForTextViewSettings()
            }
            else -> super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //saving current text for TextView
        val textView = findViewById<TextView>(R.id.resultText)
        outState.putBoolean(SAVE_TEXTVIEW_VIS, textView.isVisible)
        outState.putString(SAVE_TEXTVIEW, textView.text.toString())
        Log.d("SAVE TEXTVIEW", "text view was saved")
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
            if (item.isChecked && index != 1) outState.putInt(SAVE_SELECTED_MENU_ITEM, index)
        }
        Log.d("SAVE_SELECTED_INDEX", "save selected type of sort")
        //saving dialog
        if (Dialog.isShowingAlertCleanAll()) {
            outState.putBoolean(SAVE_DIALOG_DELETE_ALL, Dialog.isShowingAlertCleanAll())
            Dialog.closeAlertCleanAll()
            Log.d("SAVE_DIALOG", "save dialog state (clean list)")
        }
        if (Dialog.isShowingAlertDeleteOrEdit()) {
            outState.putBoolean(SAVE_DIALOG_DELETE_EDIT, Dialog.isShowingAlertDeleteOrEdit())
            Dialog.closeAlertDeleteOrEdit()
            Log.d(SAVE_DIALOG_DELETE_EDIT, "save dialog state (delete/edit)")
        }
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
        if (indexOfSelectedItem != -1 && indexOfSelectedItem != 1) {
            isListRestore = true
            ActualList.list =
                if (Elements.printAll().count() != 0 && ActualList.list.count() != 0)
                    Elements.sort(indexOfSelectedItem - 1, ActualList.list)
                else ActualList.list
            checkForTextViewSettings(searchText)
            Log.d("RESTORE_LIST_SORT", "")
        }
        if (isListRestore)
            recyclerView.adapter = CustomRecyclerAdapter(ActualList.list, recyclerView, this)
        //get dialogs
        if (savedInstanceState.getBoolean(SAVE_DIALOG_DELETE_ALL)) {
            Dialog.createConfirmDialog(
                this,
                R.string.ConfirmMessage,
                R.string.Confirm,
                recyclerView,
                findViewById(R.id.resultText),
                getString(R.string.EmptyList)
            )
            Log.d("RESTORE_DIALOG", "restore dialog state (clean list)")
        }
        if (savedInstanceState.getBoolean(SAVE_DIALOG_DELETE_EDIT)) {
            Dialog.createDialog(
                Dialog.currentHolder as CustomRecyclerAdapter.MyViewHolder,
                recyclerView,
                this
            )
            Log.d("RESTORE_DIALOG", "restore dialog state (clean list)")
        }
    }

    private fun checkForTextViewSettings(search : String = "") {
        if (ActualList.list.count() == 0)
            textViewSettings(true, if (search != "") getString(R.string.NoProducts) else getString(R.string.EmptyList))
        else textViewSettings(false)
    }

    private fun textViewSettings(flag : Boolean, text : String = "") {
        val view = findViewById<TextView>(R.id.resultText)
        if (flag) {
            view.visibility = View.VISIBLE
            view.text = text
        }
        else view.visibility = View.INVISIBLE
    }

    override fun onBackPressed() {
        val search = findViewById<SearchView>(R.id.searchBarButton)
        if (!search.isIconified) {
            search.isIconified = true
            search.isFocusable = false
            searchOpened = false
            checkForTextViewSettings(search.query.toString())
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
        const val SAVE_DIALOG_DELETE_ALL = "SAVE_DIALOG_DELETE_ALL"
        const val SAVE_DIALOG_DELETE_EDIT = "SAVE_DIALOG_DELETE_EDIT"
    }
}