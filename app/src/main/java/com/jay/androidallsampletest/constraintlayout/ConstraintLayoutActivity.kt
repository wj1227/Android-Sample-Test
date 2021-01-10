package com.jay.androidallsampletest.constraintlayout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jay.androidallsampletest.R

class ConstraintLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_layout)

        /**
         * 0dp 와 wrap_content의 차이는
         * wrap은 위젯의 크기를 계산해서 나타내고
         * 0dp는 해당 width or height를 부모의 속성에 맞게 꽉 차있다(match_constraint)
         */

        /**
         * chaingSytle을 지정할때는 왼쪽(호리젠탈), 위(버티칼)가 헤더가 된다.
         */

        /**
         * bias : 비율
         * circle : 기준잡을 뷰
         * circleRadius : 거리
         * circleAngle : 각도
         */
    }
}