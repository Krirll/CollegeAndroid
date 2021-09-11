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
    private lateinit var alertError : AlertDialog
    private lateinit var alertCleanAll : AlertDialog
    private lateinit var alertDeleteOrEdit : AlertDialog
    lateinit var currentHolder : Any
    fun createDialog (activity : Context, messageStringId : Int, titleStringId : Int) {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage(messageStringId)
            .setCancelable(false)
            .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
        alertError = dialogBuilder.create()
        alertError.setTitle(titleStringId)
        alertError.show()
    }

    fun createDialog(holder : CustomRecyclerAdapter.MyViewHolder, recyclerView : RecyclerView, activity: Activity) {
        val dialogBuilder = AlertDialog.Builder(holder.itemView.context)
        dialogBuilder.setCancelable(false)
            .setPositiveButton(R.string.delete) { dialog, _ ->
                dialog.dismiss()
                Elements.delete(ActualList.list[holder.adapterPosition])
                if (ActualList.list.isNotEmpty()) ActualList.list.removeAt(holder.adapterPosition)
                recyclerView.adapter?.notifyItemRemoved(holder.adapterPosition)
                val view = activity.findViewById<TextView>(R.id.resultText)
                if (ActualList.list.count() == 0) {
                    view.visibility = View.VISIBLE
                    view.text = activity.getString(R.string.EmptyList)
                }
                else view.visibility = View.INVISIBLE
            }
            .setNegativeButton(R.string.edit) { dialog, _ ->
                dialog.dismiss()
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
            .setNeutralButton(R.string.cancel) { dialog, _ -> dialog.dismiss() }
        alertDeleteOrEdit = dialogBuilder.create()
        alertDeleteOrEdit.setTitle(R.string.ChooseAction)
        alertDeleteOrEdit.show()
    }
    fun createConfirmDialog (activity : MainActivity, messageStringId : Int,
                             titleStringId : Int,
                             recyclerView: RecyclerView) {
        val dialogBuilder = AlertDialog.Builder(activity)
        dialogBuilder.setMessage(messageStringId)
            .setCancelable(false)
            .setNeutralButton(R.string.No) { dialog, _ -> dialog.dismiss() }
            .setPositiveButton(R.string.Yes) { dialog, _ ->
                dialog.dismiss()
                Elements.deleteAll()
                ActualList.list = Elements.printAll()
                recyclerView.adapter = CustomRecyclerAdapter(ActualList.list, recyclerView, activity)
            }
        alertCleanAll = dialogBuilder.create()
        alertCleanAll.setTitle(titleStringId)
        alertCleanAll.show()
    }
    fun isShowingAlertError() =
        if (this::alertError.isInitialized) alertError.isShowing
        else false
    fun isShowingAlertCleanAll() =
        if (this::alertCleanAll.isInitialized) alertCleanAll.isShowing
        else false
    fun isShowingAlertDeleteOrEdit() =
        if (this::alertDeleteOrEdit.isInitialized) alertDeleteOrEdit.isShowing
        else false
    fun closeAlertError() = alertError.dismiss()
    fun closeAlertCleanAll() = alertCleanAll.dismiss()
    fun closeAlertDeleteOrEdit() = alertDeleteOrEdit.dismiss()
}