package com.jay.androidallsampletest.expandable

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Typeface
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.*
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jay.androidallsampletest.R
import kotlinx.android.synthetic.main.item_holder.view.*
import java.util.*

class ExpandableAdapter(
    private val itemsCells: ArrayList<DataModel>,
    private val expandedSize: ArrayList<Int>
) : RecyclerView.Adapter<ExpandableAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_holder, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemsCells.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // Add data to cells
        holder.itemView.question_textview.text = itemsCells[position].question
        holder.itemView.answer_textview.text = itemsCells[position].answer
        holder.itemView.tv_title.text = itemsCells[position].title
        holder.itemView.tv_name.text = itemsCells[position].name

        // Set the height in answer TextView
        holder.itemView.answer_textview.layoutParams.height = expandedSize[position]
        holder.itemView.tv_title.layoutParams.height = expandedSize[position]
        holder.itemView.tv_name.layoutParams.height = expandedSize[position]

        // Expand/Collapse the answer TextView when you tap on the question TextView
        holder.itemView.question_textview.setOnClickListener {
            if (expandedSize[position] == 0) {
                // Calculate the height of the Answer Text
                val answerTextViewHeight = height(
                    holder.itemView.context,
                    itemsCells[position].answer,
                    Typeface.DEFAULT,
                    16,
                    dp2px(15f, holder.itemView.context)
                )
                val title = height(
                    holder.itemView.context,
                    itemsCells[position].title,
                    Typeface.DEFAULT,
                    16,
                    dp2px(15f, holder.itemView.context)
                )
                val name = height(
                    holder.itemView.context,
                    itemsCells[position].name,
                    Typeface.DEFAULT,
                    16,
                    dp2px(15f, holder.itemView.context)
                )

                changeViewSizeWithAnimation(
                    holder.itemView.answer_textview,
                    answerTextViewHeight,
                    300L
                )
                changeViewSizeWithAnimation(
                    holder.itemView.tv_title,
                    title,
                    300L
                )
                changeViewSizeWithAnimation(
                    holder.itemView.tv_name,
                    name,
                    300L
                )
                expandedSize[position] = answerTextViewHeight
                expandedSize[position] = title
                expandedSize[position] = name
            } else {
                changeViewSizeWithAnimation(holder.itemView.answer_textview, 0, 300L)
                changeViewSizeWithAnimation(holder.itemView.tv_title, 0, 300L)
                changeViewSizeWithAnimation(holder.itemView.tv_name, 0, 300L)
                expandedSize[position] = 0
            }
        }
    }

    private fun changeViewSizeWithAnimation(view: View, viewSize: Int, duration: Long) {
        val startViewSize = view.measuredHeight
        val endViewSize: Int =
            if (viewSize < startViewSize) (viewSize) else (view.measuredHeight + viewSize)
        val valueAnimator =
            ValueAnimator.ofInt(startViewSize, endViewSize)
        valueAnimator.duration = duration
        valueAnimator.addUpdateListener {
            val animatedValue = valueAnimator.animatedValue as Int
            val layoutParams = view.layoutParams
            layoutParams.height = animatedValue
            view.layoutParams = layoutParams
        }
        valueAnimator.start()
    }

    private fun height(
        context: Context,
        text: String,
        typeface: Typeface?,
        textSize: Int,
        padding: Int
    ): Int {
        val textView = TextView(context)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())
        textView.setPadding(padding, padding, padding, padding)
        textView.typeface = typeface
        textView.text = text
        val mMeasureSpecWidth =
            View.MeasureSpec.makeMeasureSpec(getDeviceWidth(context), View.MeasureSpec.AT_MOST)
        val mMeasureSpecHeight = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        textView.measure(mMeasureSpecWidth, mMeasureSpecHeight)
        return textView.measuredHeight
    }

    private fun dp2px(dpValue: Float, context: Context): Int {
        val scale = context.resources.displayMetrics.density
        return (dpValue * scale + 0.5f).toInt()
    }

    private fun getDeviceWidth(context: Context): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val displayMetrics = DisplayMetrics()
            val display: Display? = context.display
            display?.getRealMetrics(displayMetrics)
            displayMetrics.widthPixels
        } else {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val displayMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(displayMetrics)
            displayMetrics.widthPixels
        }
    }
}