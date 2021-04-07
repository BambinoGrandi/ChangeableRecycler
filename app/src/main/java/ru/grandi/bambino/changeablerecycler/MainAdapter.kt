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

    fun deleteItem(position: Int) {
        PULL_DELETE_ITEM.add(data[position])
        data.removeAt(position)
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
        holder.deleteItem(deleteItemListener, position)
    }

    override fun getItemCount() = data.size

    class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private lateinit var numberItem: TextView
        private lateinit var deleteButton: Button

        fun initField() {
            numberItem = itemView.findViewById(R.id.position_item)
            deleteButton = itemView.findViewById(R.id.delete_item)
        }

        fun bind(changeableItem: ChangeableItem) {
            numberItem.text = changeableItem.numberItem.toString()
        }

        fun deleteItem(deleteItemListener: DeleteItemListener, position: Int) {
            deleteButton.setOnClickListener {
                deleteItemListener.onClick(position)
            }
        }
    }
}

interface DeleteItemListener {
    fun onClick(position: Int)
}