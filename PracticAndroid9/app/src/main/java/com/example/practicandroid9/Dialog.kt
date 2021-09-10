package com.example.practicandroid9

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

object Dialog {
    var dialogOfError = false
    var dialogOfCleanList = false
    var dialogOfDeleteOrEdit = false
    lateinit var holderForDialog : Any
    fun createDialog (activity : Context, messageStringId : Int, titleStringId : Int) {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage(messageStringId)
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss(); dialogOfError = false}
        val alert = dialogBuilder.create()
        alert.setTitle(titleStringId)
        alert.show()
    }

    fun createDialog(holder : RecyclerView.ViewHolder, recyclerView : RecyclerView, activity: Activity): AlertDialog.Builder {
        val dialogBuilder = AlertDialog.Builder(holder.itemView.context)
        dialogBuilder.setCancelable(false)
            .setPositiveButton(R.string.delete) { dialog, _ ->
                dialog.dismiss()
                dialogOfDeleteOrEdit = false
                Elements.delete(ActualList.list[holder.adapterPosition])
                ActualList.list.removeAt(holder.adapterPosition)
                recyclerView.adapter?.notifyItemRemoved(holder.adapterPosition)
                val view = activity.findViewById<TextView>(R.id.resultText)
                if (ActualList.list.count() == 0) {
                    view.visibility = View.VISIBLE
                    view.text = activity.getString(R.string.EmptyList)
                }
                else view.visibility = View.INVISIBLE
            }
                //////TODO нужно дебажить редактирование, что-то не так
            .setNegativeButton(R.string.edit) { dialog, _ ->
                dialog.dismiss()
                dialogOfDeleteOrEdit = false
                val intent =
                    Intent(recyclerView.context, AddEditElementActivity::class.java).apply {
                        putExtra(
                            AddEditElementActivity.edit,
                            ActualList.list[holder.adapterPosition]
                        )
                        putExtra(
                            AddEditElementActivity.editIndex,
                            Elements.find(ActualList.list[holder.adapterPosition])
                        )
                    }
                ContextCompat.startActivity(recyclerView.context, intent, null)
                activity.finish()
            }
            .setNeutralButton(R.string.cancel) { dialog, _ -> dialog.dismiss(); dialogOfDeleteOrEdit = false }
        return dialogBuilder
    }
    fun createConfirmDialog (activity : MainActivity, messageStringId : Int,
                             titleStringId : Int,
                             recyclerView: RecyclerView) {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage(messageStringId)
            .setCancelable(false)
            .setNeutralButton(R.string.No) { dialog, _ -> dialog.dismiss(); dialogOfCleanList = false }
            .setPositiveButton(R.string.Yes) { dialog, _ ->
                dialog.dismiss()
                dialogOfCleanList = false
                Elements.deleteAll()
                ActualList.list = Elements.printAll()
                recyclerView.adapter = CustomRecyclerAdapter(ActualList.list, recyclerView, activity)
            }
        val alert = dialogBuilder.create()
        alert.setTitle(titleStringId)
        alert.show()
    }
}