package com.example.practicandroid9

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.*

data class Objects (val product : String, val count : Int, val price : Double, val date: Date) : Serializable

object Elements {
    private val list : MutableList<Objects> = mutableListOf()
    fun add(elem : Objects) = list.add(elem)
    fun delete(index : Int) = list.removeAt(index)
    fun edit(index : Int, newElem : Objects) { list[index] = newElem }
    fun sort(typeOfSort : Int) : MutableList<Objects> {
        val sortedList = printAll()
            when(typeOfSort) {
                            1 -> sortedList.sortBy { it.product }
                            2 -> sortedList.sortBy { it.count }
                            3 -> sortedList.sortBy { it.price }
                            else -> sortedList.sortBy { it.date }
        }
        return sortedList
    }
    fun search(word : String) : List<Objects> {
        var foundElements = list.filter { it.product.contains(word) }
        if (foundElements.count() == 0) {
            foundElements = list.filter { it.count.toString().contains(word) }
            if (foundElements.count() == 0) {
                foundElements = list.filter { it.price.toString().contains(word) }
                if (foundElements.count() == 0) {
                    foundElements = list.filter {
                        SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT).format(it.date).contains(word)
                    }
                }
            }
        }
        return foundElements
    }
    fun deleteAll() = list.clear()
    fun printAll() = list
}

