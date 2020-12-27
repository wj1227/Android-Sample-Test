package com.jay.androidallsampletest.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.jay.androidallsampletest.R

class FirstActivity : AppCompatActivity() {
    private val uri by lazy { intent.data }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (uri == null) {
            Toast.makeText(this, "uri does not exist", Toast.LENGTH_SHORT).show()
        } else {
            when (uri?.scheme) {
                WORD -> startActivity(Intent(this, WordActivity::class.java))
                EXCEL -> startActivity(Intent(this, ExcelActivity::class.java))
                POWER_POINT -> startActivity(Intent(this, PowerPointActivity::class.java))
            }

            finish()
        }

    }

    companion object {
        const val WORD = "doc"
        const val EXCEL = "xls"
        const val POWER_POINT = "ppt"
    }
}