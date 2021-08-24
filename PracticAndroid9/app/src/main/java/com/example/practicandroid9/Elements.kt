package com.example.practicandroid9

import java.util.Date

data class Objects (val product : String, val count : Int, val price : Int, val date: Date)

class Elements {
    private lateinit var list : MutableList<Objects>
    fun Add(elem : Objects) = list.add(elem)
    fun Delete(index : Int) {}
    fun Edit(index : Int) {}
    fun Sort(typeOfSort : Int) {}
    fun Search(word : String) {}
    fun PrintAll() {}
}

