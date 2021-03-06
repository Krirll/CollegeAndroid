package com.example.practicandroid9

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import java.util.Date

class AddEditElementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_element)
        val elem = intent.getSerializableExtra(edit)
        val indexOfElem = intent.getIntExtra(editIndex, -1)
        val productName = findViewById<EditText>(R.id.productName)
        val count = findViewById<EditText>(R.id.count)
        val price = findViewById<EditText>(R.id.price)
        val addElem = findViewById<Button>(R.id.AddElem)
        if (elem != null) {
            productName.setText((elem as Objects).product)
            count.setText(elem.count.toString())
            price.setText(elem.price.toString())
        }
        addElem.text = getString(if (elem == null) R.string.addNew else R.string.edit)
        addElem.setOnClickListener {
            if (Data.checkData(productName.text.toString(),
                                count.text.toString().toIntOrNull(),
                                price.text.toString().toDoubleOrNull())) {
                if (elem == null) {
                    Elements.add(
                        Objects(
                            productName.text.toString(),
                            count.text.toString().toInt(),
                            price.text.toString().toDouble(), Date()
                        )
                    )
                }
                else {
                    Elements.edit(indexOfElem,
                        Objects(
                            productName.text.toString(),
                            count.text.toString().toInt(),
                            price.text.toString().toDouble(), (elem as Objects).date
                        )
                    )
                }
                ActualList.list = Elements.printAll()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
            else {
                Dialog.createDialog(this, R.string.ErrorField, R.string.Error)
                productName.text.clear()
                count.text.clear()
                price.text.clear()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (Dialog.isShowingAlertError()) {
            outState.putBoolean(SAVING_DIALOG_STATE, true)
            Log.d("SAVE_DIALOG", "save dialog state (error dialog)")
            Dialog.closeAlertError()
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        if (savedInstanceState.getBoolean(SAVING_DIALOG_STATE)) {
            Dialog.createDialog(this, R.string.ErrorField, R.string.Error)
            Log.d("SAVE_DIALOG", "restore dialog state (error dialog)")
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
        super.onBackPressed()
    }
    companion object {
        const val edit = "EDIT_ELEM"
        const val editIndex = "EDIT_INDEX"
        const val SAVING_DIALOG_STATE = "SAVING_DIALOG_STATE"
    }
}