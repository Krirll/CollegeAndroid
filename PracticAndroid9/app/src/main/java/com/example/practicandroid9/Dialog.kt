package com.example.practicandroid9

import android.app.AlertDialog
import android.content.Context

class Dialog {
    fun createDialog (activity : Context, messageStringId : Int, titleStringId : Int) {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage(messageStringId)
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
        val alert = dialogBuilder.create()
        alert.setTitle(titleStringId)
        alert.show()
    }
}