package com.example.qrcodeexample.qrcode.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import java.util.Calendar
import java.util.TimeZone

class ClockView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    companion object {
        private const val ANIMATION_DELAY = 16L // 大约每秒60帧
    }

    private var centerX = width / 2f
    private var centerY = height / 2f
    private var radius = 200f
    private var hourHandLength = 100f
    private var minuteHandLength = 140f
    private var secondHandLength = 180f

    // 初始化画笔，只执行一次
    private val hourHandPaint = Paint().apply {
        color = Color.BLACK // 线的颜色
        strokeWidth = 15f // 线的粗细
        isAntiAlias = true // 抗锯齿
        style = Paint.Style.STROKE // 绘制线条
    }
    private val minuteHandPaint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 10f
        isAntiAlias = true
        style = Paint.Style.STROKE
    }
    private val secondHandPaint = Paint().apply {
        color = Color.RED
        strokeWidth = 5f
        isAntiAlias = true
        style = Paint.Style.STROKE
    }

    val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 60f
        isAntiAlias = true
        textAlign = Paint.Align.CENTER   // 横向居中
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 根据View大小初始化中心点和半径
        centerX = w / 2f
        centerY = h / 2f
        radius = minOf(w, h) / 2f * 0.9f // 预留边距

        // 根据半径设置指针长度
        hourHandLength = radius * 0.45f
        minuteHandLength = radius * 0.6f
        secondHandLength = radius * 0.8f
    }

    // 绘制圆形的画笔
    private val circlePaint = Paint().apply {
        color = ContextCompat.getColor(context, android.R.color.holo_purple)
        strokeWidth = 8f
        isAntiAlias = true
        style = Paint.Style.STROKE
    }
    // 一种通过极坐标方式来绘制线段的方法
    private fun drawRotatedLine(canvas: Canvas, angle: Float, length: Float, paint: Paint) {
        // 因为一会要对canvas做一些平移旋转，所以这里先把最初canvas的状态保存在栈中，绘制完毕再出栈
        canvas.save()

        canvas.translate(centerX, centerY)
        canvas.rotate(angle - 90)
        canvas.drawLine(0f, 0f, length, 0f, paint)

        // 这里是恢复到保存的状态
        canvas.restore()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawCircle(centerX, centerY, radius, circlePaint)

        val timeZone = TimeZone.getTimeZone("Asia/Shanghai")
        val calendar = Calendar.getInstance(timeZone)
        val hour = calendar.get(Calendar.HOUR_OF_DAY) // 0~23
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        val milli = calendar.get(Calendar.MILLISECOND)

        val secondAngle = (second + milli / 1000f) * 6f             // 每秒6°
        val minuteAngle = (minute + (second + milli / 1000f) / 60f) * 6f  // 每分6°
        val hourAngle = (hour + (minute + (second + milli / 1000f) / 60f) / 60f) * 30f // 每小时30°

        drawRotatedLine(canvas, hourAngle, hourHandLength, hourHandPaint)
        drawRotatedLine(canvas, minuteAngle, minuteHandLength, minuteHandPaint)
        drawRotatedLine(canvas, secondAngle, secondHandLength, secondHandPaint)

        val fm = textPaint.fontMetrics

        for (i in 1..12) {
            val angle = Math.toRadians((i * 30 - 90).toDouble()) // -90° 保证12点朝上
            val x = (centerX + (radius - 40) * Math.cos(angle)).toFloat()
            val y = (centerY + (radius - 40) * Math.sin(angle)).toFloat()

            // baseline 修正（保证文字垂直居中）
            val baseline = y - (fm.ascent + fm.descent) / 2

            canvas.drawText(i.toString(), x, baseline, textPaint)
        }


        postInvalidateDelayed(ANIMATION_DELAY)

    }
}