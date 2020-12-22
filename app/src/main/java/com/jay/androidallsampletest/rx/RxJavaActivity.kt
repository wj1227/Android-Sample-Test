package com.jay.androidallsampletest.rx

import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import com.jay.androidallsampletest.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function3
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.concurrent.TimeUnit

class RxJavaActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()
    private val backButtonSubject: Subject<Unit> = PublishSubject.create()
    private val textSubject: Subject<String> = PublishSubject.create()
    private val toggleButtonSubject: Subject<Unit> = PublishSubject.create()
    private val leftButtonSubject: Subject<Unit> = PublishSubject.create()
    private val rightButtonSubject: Subject<Unit> = PublishSubject.create()
    private val firstTextSubject: Subject<String> = BehaviorSubject.createDefault("")
    private val secondTextSubject: Subject<String> = BehaviorSubject.createDefault("")
    private val firstPasswordSubject: Subject<String> = BehaviorSubject.createDefault("")
    private val secondPasswordSubject: Subject<String> = BehaviorSubject.createDefault("")
    private val firstCheckBoxSubject: Subject<Unit> = PublishSubject.create()
    private val secondCheckBoxSubject: Subject<Boolean> = BehaviorSubject.createDefault(false)
    private val thirdCheckBoxSubject: Subject<Boolean> = BehaviorSubject.createDefault(false)
    private val fourthCheckBoxSubject: Subject<Boolean> = BehaviorSubject.createDefault(false)

    private lateinit var backButton: Button
    private lateinit var text: EditText
    private lateinit var tvResult: TextView
    private lateinit var checkBox: CheckBox
    private lateinit var toggleButton: Button
    private lateinit var leftButton: Button
    private lateinit var rigthButton: Button
    private lateinit var tapResult: TextView
    private lateinit var firstText: EditText
    private lateinit var secondText: EditText
    private lateinit var combineResult: TextView
    private lateinit var firstPassword: EditText
    private lateinit var secondPassword: EditText
    private lateinit var passwordResult: TextView
    private lateinit var cbFirst: CheckBox
    private lateinit var cbSecond: CheckBox
    private lateinit var cbThird: CheckBox
    private lateinit var cbFourth: CheckBox

    private var count = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rx_java)

        backButton = findViewById(R.id.btn_back)
        text = findViewById(R.id.ed_text)
        tvResult = findViewById(R.id.tv_result)
        checkBox = findViewById(R.id.cb)
        toggleButton = findViewById(R.id.btn_toggle)
        leftButton = findViewById(R.id.btn_left_tap)
        rigthButton = findViewById(R.id.btn_right_tap)
        tapResult = findViewById(R.id.tv_merge)
        firstText = findViewById(R.id.et_first)
        secondText = findViewById(R.id.et_second)
        combineResult = findViewById(R.id.tv_combine_result)
        firstPassword = findViewById(R.id.et_first_password)
        secondPassword = findViewById(R.id.et_second_password)
        passwordResult = findViewById(R.id.tv_password_result)
        cbFirst = findViewById(R.id.cb_first)
        cbSecond = findViewById(R.id.cb_second)
        cbThird = findViewById(R.id.cb_third)
        cbFourth = findViewById(R.id.cb_fourth)

        initSetting()
        bindRx()
    }

    private fun initSetting() {
        backButton.setOnClickListener { onBackPressed() }
        text.addTextChangedListener { textSubject.onNext(it.toString()) }
        toggleButton.setOnClickListener { toggleButtonSubject.onNext(Unit) }
        leftButton.setOnClickListener { leftButtonSubject.onNext(Unit) }
        rigthButton.setOnClickListener { rightButtonSubject.onNext(Unit) }
        firstText.addTextChangedListener { firstTextSubject.onNext(it.toString()) }
        secondText.addTextChangedListener { secondTextSubject.onNext(it.toString()) }
        firstPassword.addTextChangedListener { firstPasswordSubject.onNext(it.toString()) }
        secondPassword.addTextChangedListener { secondPasswordSubject.onNext(it.toString()) }
        cbFirst.setOnClickListener { firstCheckBoxSubject.onNext(Unit) }
        cbSecond.setOnClickListener { secondCheckBoxSubject.onNext(cbSecond.isChecked) }
        cbThird.setOnClickListener { thirdCheckBoxSubject.onNext(cbThird.isChecked) }
        cbFourth.setOnClickListener { fourthCheckBoxSubject.onNext(cbFourth.isChecked) }
    }

    private fun bindRx() {
        /**
         * backPressed
         */
        backButtonSubject
            .map { System.currentTimeMillis() }
            .buffer(2, 1)
            .map { val (first, second) = it; first to second }
            .filter { (first, second) -> second - first < 1000 }
            .subscribe { finish() }
            .let(compositeDisposable::add)

        /**
         * debounce
         */
        textSubject.debounce(700, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(tvResult::setText)
            .let(compositeDisposable::add)

        /**
         * throttleFirst
         */
        toggleButtonSubject.throttleFirst(1000, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { checkBox.isChecked = !checkBox.isChecked }
            .let(compositeDisposable::add)

        /**
         * merge
         */
        Observable.merge(leftButtonSubject, rightButtonSubject)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { tapResult.text = (count++).toString() }
            .let(compositeDisposable::add)

        /**
         * combineLatest
         */
        Observable.combineLatest(
            firstTextSubject,
            secondTextSubject,
            BiFunction { first: String, second: String -> first to second }
        )
            .map { (first, second) -> "$first $second" }
            .subscribe(combineResult::setText)
            .let(compositeDisposable::add)

        Observable.combineLatest(
            firstPasswordSubject,
            secondPasswordSubject,
            BiFunction { up: String, down: String -> up to down }
        )
            .map { val (up, down) = it; up == down }
            .subscribe { showPasswordResult(it) }
            .let(compositeDisposable::add)

        Observable.combineLatest(
            secondCheckBoxSubject,
            thirdCheckBoxSubject,
            fourthCheckBoxSubject,
            Function3 { x: Boolean, y: Boolean, z: Boolean -> Triple(x, y, z) }
        )
            .map { (first, second, third) -> first && second && third }
            .subscribe { cbFirst.isChecked = it }
            .let(compositeDisposable::add)
    }

    private fun showPasswordResult(visible: Boolean) {
        passwordResult.text = if (visible) {
            null
        } else {
            "비밀번호가 일치하지 않아요"
        }
    }

    override fun onBackPressed() {
        backButtonSubject.onNext(Unit)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

}