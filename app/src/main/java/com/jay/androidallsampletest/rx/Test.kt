package com.jay.androidallsampletest.rx

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.Function3

fun main() {
    val first = Observable.just(1, 2, 3, 4, 5)
    val second = Observable.just("A", "B", "C", "D")

    Observable.concat(first, second)
        .subscribe(::println)

}