package com.radionov.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout

/**
 * @author Andrey Radionov
 */
class CustomViewGroup : FrameLayout {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)


    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {

        val containerRight = measuredWidth - paddingRight
        val containerWidth = containerRight - paddingLeft

        val layoutParams = if (childCount > 0) {
            getChildAt(0).layoutParams as MarginLayoutParams
        } else {
            layoutParams as MarginLayoutParams
        }

        var rowTop = layoutParams.topMargin
        var rowBottom = 0

        var rowStart = 0
        while (rowStart < childCount) {
            var rowWidth = 0
            var rowEnd = rowStart

            while (rowEnd < childCount) {

                val child = getChildAt(rowEnd)

                rowWidth += child.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin
                if (rowWidth >= containerWidth) {
                    break
                }
                rowEnd++
            }

            var childRight = containerRight - layoutParams.rightMargin
            for (j in --rowEnd downTo rowStart) {
                val child = getChildAt(j)

                val childWidth = child.measuredWidth
                val childHeight = child.measuredHeight

                if (j < rowEnd) {
                    childRight -= layoutParams.leftMargin + layoutParams.rightMargin
                }

                rowBottom = rowTop + childHeight

                child.layout(childRight - childWidth, rowTop, childRight, rowBottom)
                childRight -= childWidth
            }

            rowStart = ++rowEnd
            rowTop = rowBottom + layoutParams.topMargin + layoutParams.bottomMargin
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val containerWidth = View.getDefaultSize(0, widthMeasureSpec)
        var height = 0
        var rowWidth = 0
        var childState = 0

        val layoutParams = if (childCount > 0) {
            getChildAt(0).layoutParams as MarginLayoutParams
        } else {
            layoutParams as MarginLayoutParams
        }

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            val childWidth = child.measuredWidth + layoutParams.leftMargin + layoutParams.rightMargin
            val childHeight = child.measuredHeight + layoutParams.topMargin + layoutParams.bottomMargin
            rowWidth += childWidth

            if (rowWidth > containerWidth) {
                height += childHeight
                rowWidth = childWidth
            } else {
                height = Math.max(height, childHeight)
            }
            childState = View.combineMeasuredStates(childState, child.measuredState)
        }

        height = Math.max(height, suggestedMinimumHeight)
        setMeasuredDimension(containerWidth, height)
    }
}
