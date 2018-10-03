package com.radionov.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * @author Andrey Radionov
 */
class CustomViewGroup : ViewGroup {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) :
            super(context, attrs, defStyleAttr)


    override fun onLayout(p0: Boolean, p1: Int, p2: Int, p3: Int, p4: Int) {

        val containerRight = measuredWidth - paddingRight
        val containerWidth = containerRight - paddingLeft
        val containerHeight = measuredHeight - paddingBottom - paddingTop

        var rowTop = 0
        var rowBottom = 0

        var rowStart = 0
        while (rowStart < childCount) {
            var rowWidth = 0
            var rowEnd = rowStart

            while (rowEnd < childCount) {

                val child = getChildAt(rowEnd)

                child.measure(View.MeasureSpec.makeMeasureSpec(containerWidth, View.MeasureSpec.AT_MOST),
                        View.MeasureSpec.makeMeasureSpec(containerHeight, View.MeasureSpec.AT_MOST))

                rowWidth += child.measuredWidth
                if (rowWidth >= containerWidth) {
                    break
                }
                rowEnd++
            }

            var childRight = containerRight
            for (j in --rowEnd downTo rowStart) {
                val child = getChildAt(j)

                val childWidth = child.measuredWidth
                val childHeight = child.measuredHeight
                rowBottom = rowTop + childHeight

                child.layout(childRight - childWidth, rowTop, childRight, rowBottom)
                childRight -= childWidth
            }

            rowStart = ++rowEnd
            rowTop = rowBottom
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {

        val containerWidth = View.getDefaultSize(0, widthMeasureSpec)
        var height = 0
        var rowWidth = 0
        var childState = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)

            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            val childWidth = child.measuredWidth
            val childHeight = child.measuredHeight
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
