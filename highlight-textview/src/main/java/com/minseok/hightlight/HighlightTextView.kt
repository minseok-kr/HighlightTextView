package com.minseok.hightlight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.TypedValue
import androidx.annotation.ColorRes
import java.lang.reflect.TypeVariable


class HighlightTextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = android.R.attr.textViewStyle) : androidx.appcompat.widget.AppCompatTextView(context, attributeSet, defStyleAttr) {
    private val isHighlighting
        get() = targetText.isNotBlank() && text.contains(targetText)

    private val mPaint = Paint()

    private var mRectF = RectF()

    private var targetText = ""

    private var textHighLightColor = context.getColor(R.color.highlight_yellow)

    private var highlightWidth = NO_STROKE_WIDTH

    private var highlightRadius = 0F

    fun highlight(text: String) {
        targetText = text
        requestLayout()
    }

    fun setStrokeWidth(width: Float) {
        highlightWidth = width
        requestLayout()
    }

    fun setColor(@ColorRes color: Int) {
        textHighLightColor = context.getColor(color)

        mPaint.color = textHighLightColor
        invalidate()
    }

    fun setRadius(dp: Float) {
        highlightRadius = dp
        invalidate()
    }

    init {
        setupAttributes(context, attributeSet, defStyleAttr)

        with(mPaint) {
            this.style = Paint.Style.FILL
            this.color = textHighLightColor
        }
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.HighlightTextView, defStyleAttr, 0)

        textHighLightColor =
                typedArray.getInt(R.styleable.HighlightTextView_highlightColor, textHighLightColor)

        targetText =
                typedArray.getString(R.styleable.HighlightTextView_highlightText) ?: targetText

        highlightWidth =
                typedArray.getDimension(R.styleable.HighlightTextView_highlightWidth, NO_STROKE_WIDTH)

        highlightRadius =
                typedArray.getDimension(R.styleable.HighlightTextView_highlightRadius, highlightRadius)

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (isHighlighting) {
            mRectF = measureTargetTextRect(targetText)
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun measureTargetTextRect(target: String): RectF {
        val bounds = Rect()
        val startIndex = text.indexOf(target)
        paint.getTextBounds(text.toString(), startIndex, startIndex + target.length, bounds)

        if (highlightWidth == NO_STROKE_WIDTH) {
            highlightWidth = bounds.height().toFloat()
        }

        val preWord = text.substring(0, startIndex)
        val preWidth = measureTextWidth(preWord).toInt()
        val targetWidth = measureTextWidth(target)

        // TODO: support multiline.
        //  val positionOfLine = measureLineOfText(startIndex)
        val positionOfLine = 1

        val heightUntilUnderline = positionOfLine * lineHeight

        return RectF(
                preWidth.toFloat(),
                heightUntilUnderline - (highlightWidth * (positionOfLine - 1)),
                preWidth + targetWidth,
                heightUntilUnderline - (highlightWidth * positionOfLine)
        )
    }

    private fun measureTextWidth(text: String): Float {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = this@HighlightTextView.textSize
            typeface = this@HighlightTextView.typeface
        }

        return paint.measureText(text)
    }

    private fun measureLineOfText(targetIndex: Int): Int {
        val texts = text.split(' ')
        val builder = StringBuilder()
        for (text in texts) {
            builder.append("$text ")

            if (builder.length > targetIndex) {
                return ((measureTextWidth(builder.toString()) / width).toInt() + 1)
            }
        }

        // Not found.
        return 0
    }

    override fun onDraw(canvas: Canvas?) {
        if (isHighlighting) {
            canvas?.drawRoundRect(mRectF, highlightRadius, highlightRadius, mPaint)
        }

        super.onDraw(canvas)
    }

    companion object {
        const val NO_STROKE_WIDTH = -1F
    }
}