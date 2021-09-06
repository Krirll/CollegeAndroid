package com.example.practicandroid9

object Data {
    fun checkData(product : String, count : Int?, price : Double?) =
        product.matches("[\\w\\sА-Яа-я]+".toRegex()) and
                count.toString().matches("^[1-9]+\\d*".toRegex()) and
                price.toString().matches("\\d+\\.\\d+".toRegex())
}