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
    fun sort(typeOfSort : Int, list : MutableList<Objects> = printAll()) : MutableList<Objects> {
            when(typeOfSort) {
                            1 -> list.sortBy { it.product }
                            2 -> list.sortBy { it.count }
                            3 -> list.sortBy { it.price }
                            else -> list.sortBy { it.date }
        }
        return list
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

