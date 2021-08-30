package com.example.practicandroid9

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*

class CustomRecyclerAdapter(private val list : MutableList<Objects>) : RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var product: TextView = itemView.findViewById(R.id.product)
        var count: TextView = itemView.findViewById(R.id.countOfProduct)
        var price: TextView = itemView.findViewById(R.id.priceOfProduct)
        var date: TextView = itemView.findViewById(R.id.dateOfBuy)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.recyclerview_item, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.product.text = "Покупка: " + list[position].product
        holder.count.text = "Кол-во: " + list[position].count
        holder.price.text = "Цена: " + list[position].price
        holder.date.text = "Дата: " + SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT).format(list[position].date)
        holder.itemView.setOnLongClickListener {
            val dialogBuilder = AlertDialog.Builder(holder.itemView.context)
            dialogBuilder.setCancelable(false)
                .setPositiveButton(R.string.delete) { dialog, _ -> dialog.dismiss(); Elements.delete(holder.adapterPosition) }
                //todo удалить элемент из списка и обновить RecyclerView
                .setNegativeButton(R.string.edit) { dialog, _  -> dialog.dismiss() }
                //todo переходить на экран редактирования
                .setNeutralButton(R.string.cancel) { dialog, _  -> dialog.dismiss() }
            val alert = dialogBuilder.create()
            alert.setTitle(R.string.ChooseAction)
            alert.show()
            true
        }
    }

    override fun getItemCount() = list.size
}