package com.minseok.hightlight

import android.content.Context
import android.graphics.*
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.AttributeSet
import androidx.annotation.ColorRes


class HighlightTextView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, defStyleAttr: Int = android.R.attr.textViewStyle) : androidx.appcompat.widget.AppCompatTextView(context, attributeSet, defStyleAttr) {
    private val isHighlighting
        get() = targetText.isNotEmpty()

    private val mPaint = Paint()

    private var mRectF: List<RectF> = emptyList()

    private var targetText: List<String> = emptyList()

    private var highLightColor = context.getColor(R.color.highlight_yellow)

    private var highlightWidth = NO_STROKE_WIDTH

    private var highlightRadius = 0F

    private var highlightBoldFlag = false

    private var highlightTextColor = currentTextColor

    private val isChangeSelectedTextColor: Boolean
        get() = highlightTextColor != currentTextColor

    private val highlightTextStyles: List<Any>
        get() = mutableListOf<Any>().apply {
            if (highlightBoldFlag) {
                add(StyleSpan(Typeface.BOLD))
            }

            if (isChangeSelectedTextColor) {
                add(ForegroundColorSpan(highlightTextColor))
            }
        }

    private val stringBuilder: SpannableStringBuilder by lazy {
        SpannableStringBuilder(text.toString())
    }

    fun highlight(text: String) {
        highlight(listOf(text))
    }

    fun highlight(texts: List<String>) {
        targetText = texts
        requestLayout()
    }

    fun setStrokeWidth(width: Float) {
        highlightWidth = width
        requestLayout()
    }

    fun setHighlightBold(bold: Boolean) {
        highlightBoldFlag = bold
        requestLayout()
    }

    fun setColor(@ColorRes color: Int) {
        highLightColor = context.getColor(color)

        mPaint.color = highLightColor
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
            this.color = highLightColor
        }
    }

    private fun setupAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray =
                context.obtainStyledAttributes(attrs, R.styleable.HighlightTextView, defStyleAttr, 0)

        highLightColor =
                typedArray.getInt(R.styleable.HighlightTextView_highlightColor, highLightColor)

        val inputTargets =
                typedArray.getString(R.styleable.HighlightTextView_highlightText)

        targetText = inputTargets?.split(",") ?: emptyList()

        highlightWidth =
                typedArray.getDimension(R.styleable.HighlightTextView_highlightWidth, NO_STROKE_WIDTH)

        highlightRadius =
                typedArray.getDimension(R.styleable.HighlightTextView_highlightRadius, highlightRadius)

        highlightBoldFlag =
                typedArray.getBoolean(R.styleable.HighlightTextView_highlightBold, highlightBoldFlag)

        highlightTextColor =
                typedArray.getInt(R.styleable.HighlightTextView_highlightTextColor, highlightTextColor)

        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (isHighlighting) {
            mRectF = targetText.mapNotNull(::measureTargetTextRect)
        }

        text = when {
            highlightTextStyles.isNotEmpty() -> {
                getSortedTargetTexts(targetText).forEach {
                    val start = it.first
                    val end = it.first + it.second

                    stringBuilder.setSpan(
                            highlightTextStyles,
                            start,
                            end
                    )
                }

                stringBuilder
            }
            else -> text.toString()
        }

        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    private fun SpannableStringBuilder.setSpan(styles: List<Any>, position: Int, length: Int) {
        styles.forEach { style ->
            setSpan(
                    style,
                    position,
                    length,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    private fun measureTargetTextRect(target: String): RectF? {
        val bounds = Rect()
        val startIndex = text.indexOf(target)
        if (startIndex == -1) {
            return null
        }

        paint.getTextBounds(text.toString(), startIndex, startIndex + target.length, bounds)

        if (highlightWidth == NO_STROKE_WIDTH) {
            highlightWidth = bounds.height().toFloat()
        }

        val preWord = text.substring(0, startIndex)
        val preWidth = measureTextWidth(preWord).toInt()
        val targetWidth = measureTextWidth(target, highlightBoldFlag)

        // TODO: support multiline.
        //  val positionOfLine = measureLineOfText(startIndex)
        val positionOfLine = 1

        val heightUntilUnderline = positionOfLine * lineHeight

        // TODO: check gravity

        return RectF(
                preWidth.toFloat() + paddingStart,
                heightUntilUnderline - (highlightWidth * (positionOfLine - 1)),
                preWidth + targetWidth + paddingStart,
                heightUntilUnderline - (highlightWidth * positionOfLine)
        )
    }

    private fun measureTextWidth(text: String, bold: Boolean = false): Float {
        val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            textSize = this@HighlightTextView.textSize
            typeface = if (bold) Typeface.DEFAULT_BOLD else this@HighlightTextView.typeface
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

    private fun getSortedTargetTexts(targets: List<String>) = targets
            .filter { text.indexOf(it) != -1 }
            .map {
                val length = it.length
                val position = text.indexOf(it)

                position to length
            }
            .sortedBy { it.second }

    override fun onDraw(canvas: Canvas?) {
        if (isHighlighting) {
            mRectF.forEach {
                canvas?.drawRoundRect(it, highlightRadius, highlightRadius, mPaint)
            }
        }

        super.onDraw(canvas)
    }

    companion object {
        const val NO_STROKE_WIDTH = -1F
    }
}