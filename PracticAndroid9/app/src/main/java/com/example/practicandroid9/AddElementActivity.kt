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
        val elem = intent.getSerializableExtra(edit)
        val product = findViewById<EditText>(R.id.productName)
        val count = findViewById<EditText>(R.id.count)
        val price = findViewById<EditText>(R.id.price)
        if (elem != null) {
            product.setText((elem as Objects).product)
            count.setText((elem as Objects).count)
            price.setText((elem as Objects).price.toString())
        }
        val button = findViewById<Button>(R.id.AddElem)
        button.text = getString(if (elem == null) R.string.addNew else R.string.edit)
        button.setOnClickListener {
            if (Data.checkData(product.text.toString(),
                                count.text.toString().toIntOrNull(),
                                price.text.toString().toDoubleOrNull())) {
                Elements.add(Objects(product.text.toString(),
                                        count.text.toString().toInt(),
                                        price.text.toString().toDouble(), Date()))
                finish()
            }
            else {
                Dialog.createDialog(this, R.string.ErrorField, R.string.Error)
                product.text.clear()
                count.text.clear()
                price.text.clear()
            }
        }
    }

    companion object {
        const val edit = "EDIT_ELEM"
    }
}