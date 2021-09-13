package com.example.practicandroid9

import java.io.Serializable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class Objects (val product : String,
                    val count : Int,
                    val price : Double,
                    val date: Date) : Serializable

object Elements {
    private var list : MutableList<Objects> = mutableListOf()
    fun add(elem : Objects) = list.add(elem)
    fun delete(elem : Objects) = list.remove(elem)
    fun edit(index : Int, newElem : Objects) { list[index] = newElem }
    fun sort(typeOfSort: Int, currentList: MutableList<Objects>) =
        when (typeOfSort) {
            1 -> currentList.sortedBy { it.product } as MutableList<Objects>
            2 -> currentList.sortedBy { it.count } as MutableList<Objects>
            3 -> currentList.sortedBy { it.price } as MutableList<Objects>
            else -> currentList.sortedBy { it.date } as MutableList<Objects>
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
    fun find(elem: Objects) = list.indexOf(elem)
    fun deleteAll() = list.clear()
    fun printAll() = list
}