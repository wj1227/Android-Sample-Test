package com.jay.androidallsampletest.coordinator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.jay.androidallsampletest.R

class CoordinatorActivity : AppCompatActivity() {

    private lateinit var adapter: CoordinatorAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)

        recyclerView = findViewById(R.id.recycler_view)
        adapter = CoordinatorAdapter()

        recyclerView.adapter = adapter

        setDatas()
    }

    private fun setDatas() {
        val list = mutableListOf<Item>()
        for (i in 0..50) {
            val item = Item(
                first = "$i 텍스트",
                second = "$i 버튼"
            )
            list.add(item)
        }

        adapter.setItems(list)
    }

}


class CoordinatorAdapter : RecyclerView.Adapter<CoordinatorAdapter.ItemViewHolder>() {
    private val items = mutableListOf<Item>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CoordinatorAdapter.ItemViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_coordinator,
            parent,
            false
        )

        return ItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoordinatorAdapter.ItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tv = itemView.findViewById<TextView>(R.id.tv_text)
        private val btn = itemView.findViewById<Button>(R.id.btn)

        fun bind(item: Item) {
            tv.text = item.first
            btn.text = item.second
        }
    }

    fun setItems(items: List<Item>) {
        this.items.addAll(items)
        notifyDataSetChanged()
    }
}

data class Item(
    val first: String,
    val second: String
)