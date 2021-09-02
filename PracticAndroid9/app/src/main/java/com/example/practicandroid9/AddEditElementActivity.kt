package com.example.practicandroid9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import java.util.Date

class AddEditElementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_element)
        val elem = intent.getSerializableExtra(edit)
        val indexOfElem = intent.getIntExtra(editIndex, -1)
        val product = findViewById<EditText>(R.id.productName)
        val count = findViewById<EditText>(R.id.count)
        val price = findViewById<EditText>(R.id.price)
        if (elem != null) {
            product.setText(getString(R.string.editProductName, (elem as Objects).product))
            count.setText(getString(R.string.editCount, elem.count))
            price.setText(getString(R.string.editPrice, elem.price))
        }
        val button = findViewById<Button>(R.id.AddElem)
        button.text = getString(if (elem == null) R.string.addNew else R.string.edit)
        button.setOnClickListener {
            if (Data.checkData(product.text.toString(),
                                count.text.toString().toIntOrNull(),
                                price.text.toString().toDoubleOrNull())) {
                if (elem == null) {
                    Elements.add(
                        Objects(
                            product.text.toString(),
                            count.text.toString().toInt(),
                            price.text.toString().toDouble(), Date()
                        )
                    )
                }
                else {
                    Elements.edit(indexOfElem,
                        Objects(
                            product.text.toString(),
                            count.text.toString().toInt(),
                            price.text.toString().toDouble(), (elem as Objects).date
                        )
                    )
                    startActivity(Intent(this, ShowElementsActivity::class.java))
                }
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
        const val editIndex = "EDIT_INDEX"
    }
}