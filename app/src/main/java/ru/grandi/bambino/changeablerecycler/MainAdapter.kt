package ru.grandi.bambino.changeablerecycler

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MainAdapter(
    private var data: MutableList<ChangeableItem>,
    private val deleteItemListener: DeleteItemListener
) : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    fun updateData(data: MutableList<ChangeableItem>) {
        this.data = data
        for (i in 0..data.size) {
            notifyItemChanged(i)
        }
    }

    fun deleteItem(changeableItem: ChangeableItem) {
        val position = data.indexOf(changeableItem)
        PULL_DELETE_ITEM.add(changeableItem)
        data.remove(changeableItem)
        notifyItemRemoved(position)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.changeable_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.initField()
        holder.bind(data[position])
        holder.deleteItem(deleteItemListener, data[position])
    }

    override fun getItemCount() = data.size

    class MainViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private lateinit var numberItem: TextView
        private lateinit var deleteButton: Button

        fun initField() {
            numberItem = itemView.findViewById(R.id.position_item)
            deleteButton = itemView.findViewById(R.id.delete_item)
        }

        fun bind(changeableItem: ChangeableItem) {
            numberItem.text = changeableItem.numberItem.toString()
        }

        fun deleteItem(deleteItemListener: DeleteItemListener, changeableItem: ChangeableItem) {
            deleteButton.setOnClickListener {
                deleteItemListener.onClick(changeableItem)
            }
        }
    }
}

interface DeleteItemListener {
    fun onClick(changeableItem: ChangeableItem)
}