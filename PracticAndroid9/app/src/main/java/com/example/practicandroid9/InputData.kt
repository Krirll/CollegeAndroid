package com.example.practicandroid9

object InputData {
    fun checkData(product : String, count : Int?, price : Int?  ) =
        product.matches("\\w+".toRegex()) and
                count.toString().matches("^[1-9]+\\d*".toRegex()) and
                price.toString().matches("^[1-9]+\\d*".toRegex())
}