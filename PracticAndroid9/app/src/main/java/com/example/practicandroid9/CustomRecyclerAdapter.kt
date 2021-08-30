package com.example.practicandroid9

import android.app.AlertDialog
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Locale

class CustomRecyclerAdapter(private val list : MutableList<Objects>, private val recyclerView: RecyclerView) :
    RecyclerView.Adapter<CustomRecyclerAdapter.MyViewHolder>() {

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
        holder.product.text = holder.itemView.context.getString(R.string.ProductName, list[position].product)
        holder.count.text = holder.itemView.context.getString(R.string.Count, list[position].count)
        holder.price.text = holder.itemView.context.getString(R.string.Price, list[position].price)
        holder.date.text = holder.itemView.context.getString(R.string.DateOfBuy, SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.ROOT).format(list[position].date))
        holder.itemView.setOnLongClickListener {
            val dialogBuilder = AlertDialog.Builder(holder.itemView.context)
            dialogBuilder.setCancelable(false)
                .setPositiveButton(R.string.delete) { dialog, _ -> dialog.dismiss()
                                                                    Elements.delete(holder.adapterPosition)
                                                                    recyclerView.adapter?.notifyItemRemoved(holder.adapterPosition)
                }
                .setNegativeButton(R.string.edit) { dialog, _  -> dialog.dismiss()
                                                                    val intent = Intent(recyclerView.context, AddElementActivity::class.java)
                                                                    
                }
                .setNeutralButton(R.string.cancel) { dialog, _  -> dialog.dismiss() }
            val alert = dialogBuilder.create()
            alert.setTitle(R.string.ChooseAction)
            alert.show()
            true
        }
    }

    override fun getItemCount() = list.size
}