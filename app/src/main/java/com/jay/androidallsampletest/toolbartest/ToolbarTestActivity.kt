package com.jay.androidallsampletest.toolbartest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.jay.androidallsampletest.R
import kotlinx.android.synthetic.main.include_toolbar.view.*

class ToolbarTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toolbar_test)

        val toolbar = findViewById<View>(R.id.include_toolbar)
        toolbar.run {
            iv_left.setBackgroundColor(ContextCompat.getColor(this.context, R.color.color_transle))
            iv_right.setBackgroundColor(ContextCompat.getColor(this.context, R.color.color_transle))
        }
    }
}