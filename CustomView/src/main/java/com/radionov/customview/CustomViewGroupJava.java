package com.radionov.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * @author Andrey Radionov
 */
public class CustomViewGroupJava extends FrameLayout {

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
        final int childCount = getChildCount();

        MarginLayoutParams layoutParams;
        if (childCount > 0) {
            layoutParams = (MarginLayoutParams) getChildAt(0).getLayoutParams();
        } else {
            layoutParams = (MarginLayoutParams) getLayoutParams();
        }

        int rowTop = 0;
        int rowBottom = 0;

        for (int rowStart = 0; rowStart < childCount; rowStart++) {
            int rowWidth = 0;
            int rowEnd = rowStart;

            for (; rowEnd < childCount; rowEnd++) {

                View child = getChildAt(rowEnd);

                rowWidth += child.getMeasuredWidth() +
                        layoutParams.leftMargin + layoutParams.rightMargin;
                if (rowWidth >= containerWidth) {
                    break;
                }
            }

            int childRight = containerRight - layoutParams.rightMargin;
            for (int j = --rowEnd; j >= rowStart; j--) {
                View child = getChildAt(j);

                int childWidth = child.getMeasuredWidth();
                int childHeight = child.getMeasuredHeight();

                if (j < rowEnd) {
                    childRight -= layoutParams.leftMargin + layoutParams.rightMargin;
                }

                rowBottom = rowTop + childHeight;

                child.layout(childRight - childWidth, rowTop, childRight, rowBottom);
                childRight -= childWidth;
            }

            rowStart = rowEnd;
            rowTop = rowBottom + layoutParams.topMargin + layoutParams.bottomMargin;
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int containerWidth = getDefaultSize(0, widthMeasureSpec);
        int childCount = getChildCount();
        int height = 0;
        int rowWidth = 0;
        int childState = 0;

        MarginLayoutParams layoutParams;
        if (childCount > 0) {
            layoutParams = (MarginLayoutParams) getChildAt(0).getLayoutParams();
        } else {
            layoutParams = (MarginLayoutParams) getLayoutParams();
        }

        for (int i = 0; i < getChildCount(); i++) {
            final View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);

            int childWidth = child.getMeasuredWidth() + layoutParams.leftMargin + layoutParams.rightMargin;
            int childHeight = child.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            rowWidth += childWidth;

            if (rowWidth > containerWidth) {
                height += childHeight;
                rowWidth = childWidth;
            } else {
                height = Math.max(height, childHeight);
            }
            childState = combineMeasuredStates(childState, child.getMeasuredState());
        }

        height = Math.max(height, getSuggestedMinimumHeight());
        setMeasuredDimension(containerWidth, height);
    }
}
