package com.example.practicandroid9

import java.util.Date

data class Objects (val product : String, val count : Int, val price : Int, val date: Date)

class Elements {
    private val list : MutableList<Objects> = mutableListOf()
    fun Add(elem : Objects) = list.add(elem)
    fun Delete(index : Int) = list.removeAt(index)
    fun Edit(index : Int, newElem : Objects) { list[index] = newElem }
    fun Sort(typeOfSort : Int) = when(typeOfSort) {
        1 -> list.sortBy { it.product }
        2 -> list.sortBy { it.count }
        3 -> list.sortBy { it.price }
        else -> list.sortBy { it.date }
    }
    fun Search(typeOfSearch : Int, word : String) =
        when(typeOfSearch) {
            1 -> list.map { it.product.contains(word) }
            2 -> list.map { it.count == word.toInt() }
            3 -> list.map { it.price == word.toInt() }
            else -> list.map { it.date.toString() == word }
        }
    fun PrintAll() = list
}

