package com.example.qrcodeexample.qrcode.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.qrcodeexample.R
import com.example.qrcodeexample.databinding.QrCodeLayoutBinding

/**
 * day4
 * 1、熟悉本文件自定义 view 内容，了解自定义 view 接口
 * 2、新起 demo，撸一个有时分秒三根指针的时钟
 * 3、给时钟加上外围刻度，走起来
 */
// 下面这段是kotlin中创建自定义视图的声明
class QRCodeView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : FrameLayout(context, attrs) {

    companion object{
        /**
         * 中间那条线每次刷新移动的距离
         */
        const val SPEED_DISTANCE = 8
        const val ANIMATION_DELAY = 12L
    }

    // 在这里用到了qr_code_layout
    // 去掉下划线，再转为驼峰，在加上Binding
    // kotlin，很神奇吧
    private val binding: QrCodeLayoutBinding by lazy {
        QrCodeLayoutBinding.inflate(LayoutInflater.from(context))
    }

    private var lineRect = Rect()
    private var framingRect: Rect = Rect()
    private var isFirst = false
    private var paint: Paint? = null
    private var slideTop = 0
    private var scanLineBitmap: Bitmap? = null

    init {
        setWillNotDraw(false)
        this.addView(binding.root)

        paint = Paint()
        scanLineBitmap = BitmapFactory.decodeResource(resources, R.drawable.qrcode_scan_line)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        binding.qrMain.getGlobalVisibleRect(framingRect)
        val top = framingRect.top - getInternalStatusBarHeight(context)
        val bottom = framingRect.bottom - getInternalStatusBarHeight(context)

        //初始化中间线滑动的最上边和最下边
        if (!isFirst) {
            isFirst = true
            slideTop = top
        }

        //绘制中间的线,每次刷新界面，中间的线往下移动SPEED_DISTANCE
        slideTop += SPEED_DISTANCE
        if (slideTop >= bottom) {
            slideTop = framingRect.top - getInternalStatusBarHeight(context)
        }

        lineRect.left = framingRect.left
        lineRect.right = framingRect.right
        lineRect.top = slideTop
        lineRect.bottom = slideTop + 8
        val bitmap = scanLineBitmap!!
        canvas.drawBitmap(bitmap, null, lineRect, paint)

        postInvalidateDelayed(
            ANIMATION_DELAY, framingRect.left, top, framingRect.right, bottom)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        // 整个界面刷新
        postInvalidate()
    }

    private fun getInternalStatusBarHeight(context: Context): Int {
        var result = 0
        val resources = context.resources
        val resourceId: Int = resources.getIdentifier("status_bar_height", "dimen", "android")
        if (resourceId > 0) {
            try {
                result = resources.getDimensionPixelSize(resourceId)
            } catch (e: Resources.NotFoundException) {
                e.printStackTrace()
            }
        }
        return result
    }
}