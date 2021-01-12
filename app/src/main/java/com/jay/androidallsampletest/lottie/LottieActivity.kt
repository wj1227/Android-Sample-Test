package com.jay.androidallsampletest.lottie

import android.animation.Animator
import android.app.ProgressDialog.show
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.airbnb.lottie.LottieAnimationView
import com.jay.androidallsampletest.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import java.util.concurrent.TimeUnit

class LottieActivity : AppCompatActivity() {

    private lateinit var lottieView: LottieAnimationView
    private lateinit var button: Button
    private var isFlag = false
    private val compositeDisposable = CompositeDisposable()
    private val dialog by lazy { LoadingFragment() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie)
        button = findViewById(R.id.button)
        button.setOnClickListener { onDialog() }

        lottieView = findViewById(R.id.lottie_view)
        lottieView.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {}
            override fun onAnimationCancel(p0: Animator?) { isFlag = true }
            override fun onAnimationRepeat(p0: Animator?) {}
        })

        Observable.timer(3, TimeUnit.SECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { onLottie() }
            .addTo(compositeDisposable)
    }

    private fun onLottie() = lottieView.cancelAnimation()

    private fun onDialog() {
        if (isFlag) {
            dialog.show(supportFragmentManager, "loader")
//        if (!dialog.isAdded){
//            dialog.show(supportFragmentManager, "loader")
//        }

            Observable.timer(3, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { dialog.dismissAllowingStateLoss() }
                .addTo(compositeDisposable)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }
}