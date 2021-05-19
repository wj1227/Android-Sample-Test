package com.jay.androidallsampletest.facebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.jay.androidallsampletest.R

class FacebookActivity : AppCompatActivity() {
    private lateinit var button: LoginButton
    private lateinit var callbackManager: CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_facebook)

        callbackManager = CallbackManager.Factory.create()
        button = findViewById(R.id.btn_login)

        button.apply {
            registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    println("onSuccess: ${result?.accessToken},\n${result}")
                }

                override fun onCancel() {
                    println("onCancel")
                }

                override fun onError(error: FacebookException?) {
                    println("onError: ${error?.printStackTrace()}")
                }
            })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        println("result!")
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)
    }
}