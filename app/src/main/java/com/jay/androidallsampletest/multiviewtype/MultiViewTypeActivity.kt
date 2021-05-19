package com.jay.androidallsampletest.multiviewtype

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.jay.androidallsampletest.R

// 생성자에서 넣어서 하든 함수 만들어서 추가하든 원하는 방식으로 요리
class MultiViewTypeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MultiViewTypeAdapter
    private lateinit var lists: MutableList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_multiview_type)

        recyclerView = findViewById(R.id.recycler_view)
        lists = mutableListOf()

        initAdapter()

        setSampleData()
    }

    private fun initAdapter() {
        adapter = MultiViewTypeAdapter()
        //adapter = MultiViewTypeAdapter(lists)

        recyclerView.adapter = adapter
    }

    private fun setSampleData() {
//        lists.add(ItemLeft("180"))
//        lists.add(ItemRight("하이", "지구"))
//        lists.add(ItemCenter("123", "18", "여기"))
//        lists.add(ItemImage(R.drawable.sample_icon_1, "설명1"))
//        lists.add(ItemLeft("170"))
//        lists.add(ItemRight("설명", "별"))
//        lists.add(ItemCenter("456", "28", "저기"))
//        lists.add(ItemImage(R.drawable.sample_icon_2, "설명2"))
//        lists.add(ItemLeft("190"))
//        lists.add(ItemRight("안녕", "달"))
//        lists.add(ItemCenter("456", "28", "저기"))
//        lists.add(ItemImage(R.drawable.sample_icon_3, "설명3"))

        adapter.addItems(ItemLeft("180"))
        adapter.addItems(ItemRight("하이", "지구"))
        adapter.addItems(ItemCenter("123", "18", "여기"))
        adapter.addItems(ItemImage(R.drawable.sample_icon_1, "설명1"))
        adapter.addItems(ItemLeft("170"))
        adapter.addItems(ItemRight("설명", "별"))
        adapter.addItems(ItemCenter("456", "28", "저기"))
        adapter.addItems(ItemImage(R.drawable.sample_icon_2, "설명2"))
        adapter.addItems(ItemLeft("190"))
        adapter.addItems(ItemRight("안녕", "달"))
        adapter.addItems(ItemCenter("456", "28", "저기"))
        adapter.addItems(ItemImage(R.drawable.sample_icon_3, "설명3"))
    }

}