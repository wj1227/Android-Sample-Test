package com.jay.androidallsampletest.progressbar

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.jay.androidallsampletest.R

class ProgressbarActivity : AppCompatActivity() {

    private val progressBar: ProgressBar by lazy {
        findViewById(R.id.progress_bar)
    }

    private val button: Button by lazy {
        findViewById(R.id.btn_apply)
    }

    private val et: EditText by lazy {
        findViewById(R.id.et_count)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progressbar)

        progressBar.run {
            max = 10
            background
        }

        button.setOnClickListener {
            val count = et.text.toString()

            if (count.toInt() > 10) {
                Toast.makeText(this, "max is 10", Toast.LENGTH_SHORT).show()
            } else {
                progressBar.progress = count.toInt()
            }
        }
    }
}