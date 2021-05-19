package com.jay.androidallsampletest.facebook

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.facebook.*
import com.facebook.login.LoginManager
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


        val token = AccessToken.getCurrentAccessToken()
        if (token != null && !token.isExpired) {
            println("토큰이 유효하고 널이 아님")
        } else {
            println("토큰 없거나 유효하지 않음, 테스트 자동조회")
            //LoginManager.getInstance().logInWithPublishPermissions(this, mutableListOf())
            LoginManager.getInstance().logIn(this, mutableListOf("public_profile"))
        }

        button.apply {
            registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
                override fun onSuccess(result: LoginResult?) {
                    println("onSuccess: ${result?.accessToken},\n${result}")
                    val result = FacebookMapper.mapper(Profile.getCurrentProfile())
                    println("model result: $result")
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

/**
 * id String
 * firstName String,
 * middleName String,
 * lastName String,
 * name String,
 * link Uri
 */
data class Model(
    val id: String? = null,
    val firstName: String? = null,
    val middleName: String? = null,
    val lastName: String? = null,
    val name: String? = null,
    val profileImage: String? = null
)

interface Mapper<in D : Profile, out R : Model> {
    fun mapper(item: D): R
}

object FacebookMapper : Mapper<Profile, Model> {
    override fun mapper(item: Profile): Model {
        return Model(
            id = item.id,
            firstName = item.firstName,
            middleName = item.middleName,
            lastName = item.lastName,
            name = item.name,
            profileImage = item.linkUri.toString()
        )
    }
}