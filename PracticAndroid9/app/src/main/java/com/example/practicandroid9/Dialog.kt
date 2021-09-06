package com.example.practicandroid9

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

object Dialog {
    fun createDialog (activity : Context, messageStringId : Int, titleStringId : Int) {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage(messageStringId)
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
        val alert = dialogBuilder.create()
        alert.setTitle(titleStringId)
        alert.show()
    }

    fun createDialog(holder : RecyclerView.ViewHolder, recyclerView : RecyclerView,
                     list: MutableList<Objects>, activity: Activity): AlertDialog.Builder {
        val dialogBuilder = AlertDialog.Builder(holder.itemView.context)
        dialogBuilder.setCancelable(false)
            .setPositiveButton(R.string.delete) { dialog, _ ->
                dialog.dismiss()
                Elements.delete(holder.adapterPosition)
                ActualList.list = Elements.printAll()
                recyclerView.adapter?.notifyItemRemoved(holder.adapterPosition)
            }
            .setNegativeButton(R.string.edit) { dialog, _ ->
                dialog.dismiss()
                val intent =
                    Intent(recyclerView.context, AddEditElementActivity::class.java).apply {
                        putExtra(
                            AddEditElementActivity.edit,
                            list[holder.adapterPosition]
                        )
                        putExtra(
                            AddEditElementActivity.editIndex,
                            holder.adapterPosition
                        )
                    }
                ContextCompat.startActivity(recyclerView.context, intent, null)
                activity.finish()
            }
            .setNeutralButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
        return dialogBuilder
    }
}