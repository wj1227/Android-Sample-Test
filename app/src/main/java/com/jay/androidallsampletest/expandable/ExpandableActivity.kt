package com.jay.androidallsampletest.expandable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jay.androidallsampletest.R

class ExpandableActivity : AppCompatActivity() {
    private var itemsData = ArrayList<DataModel>()
    private var expandedSize =  ArrayList<Int>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ExpandableAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_expandable)

        recyclerView = findViewById(R.id.rv)
        adapter = ExpandableAdapter(itemsData, expandedSize)

        recyclerView.setHasFixedSize(true)
        getData()
        recyclerView.adapter = adapter
    }

    private fun getData() {
        itemsData = ArrayList()
        itemsData = Data.items

        setCellSize()

        adapter.notifyDataSetChanged()
        adapter = ExpandableAdapter(itemsData, expandedSize)
    }

    // Set the expanded view size to 0, because all expanded views are collapsed at the beginning
    private fun setCellSize() {
        expandedSize = ArrayList()
        for (i in 0 until itemsData.count()) {
            expandedSize.add(0)
        }
    }
}