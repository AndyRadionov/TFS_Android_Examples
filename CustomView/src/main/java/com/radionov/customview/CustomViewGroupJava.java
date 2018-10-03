package com.radionov.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * @author Andrey Radionov
 */
public class CustomViewGroupJava extends ViewGroup {

    public CustomViewGroupJava(Context context) {
        this(context, null, 0);
    }

    public CustomViewGroupJava(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomViewGroupJava(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        final int containerRight = getMeasuredWidth() - getPaddingRight();
        final int containerWidth = containerRight - getPaddingLeft();
        final int containerHeight = getMeasuredHeight() - getPaddingBottom() - getPaddingTop();

        final int childCount = getChildCount();
        int rowTop = 0;
        int rowBottom = 0;

        for (int rowStart = 0; rowStart < childCount; rowStart++) {
            int rowWidth = 0;
            int rowEnd = rowStart;

            for (; rowEnd < childCount; rowEnd++) {

                View child = getChildAt(rowEnd);

                child.measure(MeasureSpec.makeMeasureSpec(containerWidth, MeasureSpec.AT_MOST),
                        MeasureSpec.makeMeasureSpec(containerHeight, MeasureSpec.AT_MOST));

                rowWidth += child.getMeasuredWidth();
                if (rowWidth >= containerWidth) {
                    break;
                }
            }

            int childRight = containerRight;
            for (int j = --rowEnd; j >= rowStart; j--) {
                View child = getChildAt(j);

                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();
                rowBottom = rowTop + childHeight;

                child.layout(childRight - childWidth, rowTop, childRight, rowBottom);
                childRight -= childWidth;
            }

            rowStart = rowEnd;
            rowTop = rowBottom;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int containerWidth = getDefaultSize(0, widthMeasureSpec);
        int height = 0;
        int rowWidth = 0;
        int childState = 0;

        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);

            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            rowWidth += child.getMeasuredWidth();

            if (rowWidth > containerWidth) {
                height += child.getMeasuredHeight();
                rowWidth = child.getMeasuredWidth();
            } else {
                height = Math.max(height, child.getMeasuredHeight());
            }
            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }

        height = Math.max(height, getSuggestedMinimumHeight());
        setMeasuredDimension(containerWidth, height);
    }
}
