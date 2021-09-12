package com.example.practicandroid9

import android.view.MenuItem
import androidx.recyclerview.widget.RecyclerView

object ActualList {
    lateinit var list : MutableList<Objects>
    fun createListWithSearchOrSort(typeOfSort : Int, search : String,
                                   item : MenuItem, recyclerView : RecyclerView,
                                   activity: MainActivity) {
        item.isChecked = true
        if (search != "") list = Elements.search(search) as MutableList<Objects>
        list = if (Elements.printAll().count() != 0 && list.count() != 0) Elements.sort(typeOfSort, list) else list
        recyclerView.adapter = CustomRecyclerAdapter(list, recyclerView, activity)
    }
}