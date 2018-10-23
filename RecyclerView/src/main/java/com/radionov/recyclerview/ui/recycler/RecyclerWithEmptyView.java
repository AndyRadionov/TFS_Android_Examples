package com.radionov.recyclerview.ui.recycler;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author Andrey Radionov
 */
public class RecyclerWithEmptyView extends RecyclerView {

    private View emptyView;
    private AdapterDataObserver emptyObserver = new AdapterDataObserver() {

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            setVisibilityOnChange();
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            setVisibilityOnChange();
        }

        private void setVisibilityOnChange() {
            Adapter<?> adapter = getAdapter();
            if (adapter != null && emptyView != null) {
                if (adapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                    RecyclerWithEmptyView.this.setVisibility(View.INVISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                    RecyclerWithEmptyView.this.setVisibility(View.VISIBLE);
                }
            }
        }
    };

    public RecyclerWithEmptyView(Context context) {
        super(context);
    }

    public RecyclerWithEmptyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerWithEmptyView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        super.setAdapter(adapter);

        if (adapter != null) {
            adapter.registerAdapterDataObserver(emptyObserver);
            emptyObserver.onItemRangeRemoved(0, adapter.getItemCount());
            emptyObserver.onItemRangeInserted(0, adapter.getItemCount());
        }
    }

    public void setEmptyView(View emptyView) {
        this.emptyView = emptyView;
    }
}
