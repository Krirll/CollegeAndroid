package com.example.practicandroid9

import java.io.Serializable
import java.util.Date

data class Objects (val product : String, val count : Int, val price : Double, val date: Date) : Serializable

object Elements {
    private val list : MutableList<Objects> = mutableListOf()
    fun add(elem : Objects) = list.add(elem)
    fun delete(index : Int) = list.removeAt(index)
    fun edit(index : Int, newElem : Objects) { list[index] = newElem }
    fun sort(typeOfSort : Int) = when(typeOfSort) {
        1 -> list.sortBy { it.product }
        2 -> list.sortBy { it.count }
        3 -> list.sortBy { it.price }
        4 -> list.sortBy { it.date }
        else -> {}
    }
    fun search(typeOfSearch : Int, word : String) =
        when(typeOfSearch) {
            1 -> list.map { it.product.contains(word) }
            2 -> list.map { it.count == word.toInt() }
            3 -> list.map { it.price == word.toDouble() }
            4 -> list.map { it.date.toString().contains(word) }
            else -> null
        }
    fun printAll() = list
}

