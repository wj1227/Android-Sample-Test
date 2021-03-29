package com.jay.androidallsampletest.multiviewtype

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jay.androidallsampletest.R

// 생성자에서 넣어서 하든 함수 만들어서 추가하든 원하는 방식으로 요리
class MultiViewTypeAdapter(
    //private val items: List<Item>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val items = mutableListOf<Item>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = when (viewType) {
        TYPE_LEFT -> {
            LeftHolder.create(parent)
        }
        TYPE_RIGHT -> {
            RightHolder.create(parent)
        }
        TYPE_CENTER -> {
            CenterHolder.create(parent)
        }
        TYPE_IMAGE -> {
            ImageHolder.create(parent)
        }
        else -> {
            throw IllegalStateException("Not Found ViewHolder Type $viewType")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is LeftHolder -> {
                holder.bind(items[position] as ItemLeft)
            }
            is RightHolder -> {
                holder.bind(items[position] as ItemRight)
            }
            is CenterHolder -> {
                holder.bind(items[position] as ItemCenter)
            }
            is ImageHolder -> {
                holder.bind(items[position] as ItemImage)
            }
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int) = when (items[position]) {
        is ItemLeft -> {
            TYPE_LEFT
        }
        is ItemRight -> {
            TYPE_RIGHT
        }
        is ItemCenter -> {
            TYPE_CENTER
        }
        is ItemImage -> {
            TYPE_IMAGE
        }
        else -> {
            throw IllegalStateException("Not Found ViewHolder Type")
        }
    }

    class LeftHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textView = itemView.findViewById<TextView>(R.id.tv_left)

        fun bind(item: ItemLeft) {
            textView.text = item.height
        }

        companion object Factory {
            fun create(parent: ViewGroup): LeftHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_holder_left, parent, false)

                return LeftHolder(view)
            }
        }
    }

    class RightHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvOne = itemView.findViewById<TextView>(R.id.tv_right_one)
        private val tvTwo = itemView.findViewById<TextView>(R.id.tv_right_two)

        fun bind(item: ItemRight) {
            tvOne.text = item.title
            tvTwo.text = item.address
        }

        companion object Factory {
            fun create(parent: ViewGroup): RightHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_holder_right, parent, false)

                return RightHolder(view)
            }
        }
    }

    class CenterHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvOne = itemView.findViewById<TextView>(R.id.tv_center_one)
        private val tvTwo = itemView.findViewById<TextView>(R.id.tv_center_two)
        private val tvThree = itemView.findViewById<TextView>(R.id.tv_center_three)

        fun bind(item: ItemCenter) {
            tvOne.text = item.age
            tvTwo.text = item.company
            tvThree.text = item.position
        }

        companion object Factory {
            fun create(parent: ViewGroup): CenterHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_holder_center, parent, false)

                return CenterHolder(view)
            }
        }
    }

    class ImageHolder(private val itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView = itemView.findViewById<ImageView>(R.id.image_view)
        private val textView = itemView.findViewById<TextView>(R.id.tv_desc)

        fun bind(item: ItemImage) {
            imageView.setImageResource(item.image)
            textView.text = item.desc
        }

        companion object Factory {
            fun create(parent: ViewGroup): ImageHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater.inflate(R.layout.item_holder_image, parent, false)

                return ImageHolder(view)
            }
        }
    }

    fun addItems(item: Item) {
        this.items.add(item)
        this.notifyDataSetChanged()
    }

    companion object {
        private const val TYPE_LEFT = 0
        private const val TYPE_RIGHT = 1
        private const val TYPE_CENTER = 2
        private const val TYPE_IMAGE = 3
    }
}