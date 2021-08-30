package com.example.practicandroid9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.util.Date

class AddElementActivity : AppCompatActivity() {
    //TODO сделать подходящим и для добавления и для редактирования
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_element)
        findViewById<Button>(R.id.AddElem).setOnClickListener {
            val product = findViewById<EditText>(R.id.productName).text
            val count = findViewById<EditText>(R.id.count).text
            val price = findViewById<EditText>(R.id.price).text
            if (Data.checkData(product.toString(), count.toString().toIntOrNull(), price.toString().toDoubleOrNull())) {
                Elements.add(Objects(product.toString(), count.toString().toInt(), price.toString().toDouble(), Date()))
                finish()
            }
            else {
                Dialog.createDialog(this, R.string.ErrorField, R.string.Error)
                product.clear()
                count.clear()
                price.clear()
            }
        }
    }
}