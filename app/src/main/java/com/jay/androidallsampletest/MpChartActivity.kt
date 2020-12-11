package com.jay.androidallsampletest

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import java.util.*

class MpChartActivity : AppCompatActivity() {
    private val TAG = javaClass.simpleName

    private lateinit var barChart: BarChart
    private lateinit var lineChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mp_chart)

        initView()
        initBarSetting()
        initLineSetting()
        setBarData()
    }

    private fun initView() {
        barChart = findViewById(R.id.bar_chart)
        lineChart = findViewById(R.id.line_chart)
    }

    private fun initBarSetting() {
        barChart.run {
            setDrawBarShadow(true) // 그래프 그림자
            setTouchEnabled(false) // 차트 터치 막기
            setDrawValueAboveBar(true) // 입력?값이 차트 위or아래에 그려질 건지 (true=위, false=아래)
            setPinchZoom(false) // 두손가락으로 줌 설정
            setDrawGridBackground(false) // 격자구조
            setMaxVisibleValueCount(10) // 그래프 최대 갯수
            description.isEnabled = false // 그래프 오른쪽 하단에 라벨 표시
            legend.isEnabled = false // 차트 범례 설정(legend object chart)

            xAxis.run { // 아래 라벨 x축
                isEnabled = true // 라벨 표시 설정
                position = XAxis.XAxisPosition.BOTTOM // 라벨 위치 설정
                valueFormatter = LabelFormatter() // 라벨 값 포멧 설정
                setDrawGridLines(false) // 격자구조
                granularity = 1f // 간격 설정
                setDrawAxisLine(false) // 그림
                textSize = 12f // 라벨 크기
                textColor = Color.BLUE
            }

            axisLeft.run { // 왼쪽 y축
                isEnabled = false
                axisMinimum = 0f // 최소값
                axisMaximum = 100f // 최대값
                granularity = 10f // 값 만큼 라인선 설정
                setDrawLabels(false) // 값 셋팅 설정
                textColor = Color.RED // 색상 설정
                axisLineColor = Color.BLACK // 축 색상 설정
                gridColor = Color.BLUE // 격자 색상 설정
            }
            axisRight.run { // 오른쪽 y축(왼쪽과동일)
                isEnabled = false
                textSize = 15f
            }

            animateY(1500) // y축 애니메이션
            animateX(1000) // x축 애니메이션
        }
    }

    private fun initLineSetting() {
        lineChart.setPinchZoom(false)
        lineChart.setDrawGridBackground(false)
        lineChart.description.isEnabled = false
        lineChart.legend.isEnabled = false

        lineChart.data = setLineData()

        lineChart.animateX(3000)
        lineChart.animateY(300)

        val leftAis = lineChart.axisLeft
        leftAis.axisMaximum = 100f
        leftAis.axisMinimum = 0f

        lineChart.axisRight.isEnabled = false
    }

    private fun setBarData() {
        val values = mutableListOf<BarEntry>()
        val colorList = listOf(
            Color.rgb(192, 255, 140),
            Color.rgb(255, 247, 97),
            Color.rgb(79, 208, 45),
            Color.rgb(140, 234, 255),
            Color.rgb(255, 140, 157)
        )

        for (i in 6..10) {
            val index = i.toFloat()
            val random = Random().nextInt(101).toFloat()

            values.add(BarEntry(index, random))
        }

        val set = BarDataSet(values, "내용없음")
            .apply {
                setDrawIcons(false)
                setDrawValues(true)
                colors = colorList
                valueFormatter = ScoreFormatter()
                valueTextColor = Color.RED
            }
//        /**
//         * 커스텀중..?
//         */
//        for (i in 0 until set.values.size) {
//            val index = set.getEntryForIndex(i)
//            when (index.x.toInt()) {
//                6 -> {
//                    Log.d(TAG, "setBarData: 시발 ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ")
//                    set.valueTextColor = R.color.colorAccent
//                }
//                7 -> set.valueTextColor = R.color.colorPrimary
//                8 -> set.valueTextColor = R.color.color_1_white
//                9 -> set.valueTextColor = R.color.color_20_gray_blue
//                10 -> set.valueTextColor = R.color.color_7_yellow
//                else -> Log.d(TAG, "setBarData: sibal -______-")
//            }
//            Log.d(TAG, "yVals: ${index.yVals}")
//            Log.d(TAG, "y: ${index.y}")
//            Log.d(TAG, "ranges: ${index.ranges}")
//            Log.d(TAG, "positiveSum: ${index.positiveSum}")
//            Log.d(TAG, "negativeSum: ${index.negativeSum}")
//            Log.d(TAG, "isStacked: ${index.isStacked}")
//            Log.d(TAG, "x: ${index.x}")
//            Log.d(TAG, "data: ${index.data}")
//        }

        val dataSets = mutableListOf<IBarDataSet>()
        dataSets.add(set)

        val data = BarData(dataSets)
            .apply {
                setValueTextSize(10f)
                barWidth = 0.5f
            }

        barChart.data = data
    }

    private fun setLineData(): LineData {
        val sets = mutableListOf<ILineDataSet>()

        val test1 = mutableListOf<Entry>()
        val test2 = mutableListOf<Entry>()

        for (i in 0..5) {
            val index = i.toFloat()
            val random = Random()
            val range = random.nextInt(101).toFloat()

            test1.add(Entry(index, range))
        }

        for (i in 5..10) {
            val index = i.toFloat()
            val random = Random().nextInt(101).toFloat()

            test2.add(Entry(index, random))
        }

        val abc = LineDataSet(test1, "라벨1")
        val def = LineDataSet(test2, "라벨1")

        abc.lineWidth = 2f
        def.lineWidth = 2f

        abc.setDrawCircles(false)
        def.setDrawCircles(false)
        abc.setDrawValues(false)
        def.setDrawValues(false)

        abc.color = ColorTemplate.VORDIPLOM_COLORS[0]
        def.color = ColorTemplate.VORDIPLOM_COLORS[1]

        sets.add(abc)
        sets.add(def)

        return LineData(sets)
    }


    class LabelFormatter : ValueFormatter() {
        private var index = 0

        override fun getFormattedValue(value: Float): String {
            index = value.toInt()
            return when (index) {
                6 -> "드라이버"
                7 -> "아이언"
                8 -> "우드"
                9 -> "어프로치"
                10 -> "퍼팅"
                else -> throw IndexOutOfBoundsException("index out")
            }
        }
    }

    class ScoreFormatter : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            val score = value.toString().split(".")
            return score[0]+"점"
        }
    }

    class TestTest(
        yVals: MutableList<BarEntry>?,
        label: String?
    ) : BarDataSet(yVals, label) {

        override fun getEntryIndex(e: BarEntry?): Int {
            return 0
        }
    }


}