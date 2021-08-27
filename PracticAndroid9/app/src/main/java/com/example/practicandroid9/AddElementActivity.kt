package com.example.practicandroid9

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import java.util.Date

class AddElementActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_element)
        findViewById<Button>(R.id.AddElem).setOnClickListener {
            /*
            TODO
               -добавить regex, если хоть 1 поле заполнено неправильно, то чистить поля и выводить уведомление об ошибке
               -отформатировать дату
            */
            Dialog().createDialog(this, R.string.ErrorField, R.string.Error)
            Elements.add(Objects(findViewById<TextView>(R.id.productName).text.toString(),
                                 findViewById<TextView>(R.id.count).text.toString().toInt(),
                                 findViewById<TextView>(R.id.price).text.toString().toInt(), Date()))
        }
    }
}