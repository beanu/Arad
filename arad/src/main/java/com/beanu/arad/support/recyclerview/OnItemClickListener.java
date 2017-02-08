package com.beanu.arad.support.recyclerview;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by lizhihua on 2017/1/13.
 */

public abstract class OnItemClickListener extends RecyclerView.SimpleOnItemTouchListener {
    private GestureDetectorCompat gestureDetector;

    public OnItemClickListener(final RecyclerView recyclerView) {
        gestureDetector = new GestureDetectorCompat(recyclerView.getContext(),
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onSingleTapUp(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null) {
                            onItemClick(recyclerView, childView, recyclerView.getChildAdapterPosition(childView));
                        }
                        return true;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        View childView = recyclerView.findChildViewUnder(e.getX(), e.getY());
                        if (childView != null) {
                            onItemLongClick(recyclerView, childView, recyclerView.getChildAdapterPosition(childView));
                        }
                    }
                });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    public abstract void onItemClick(RecyclerView recyclerView, View view, int position);

    public void onItemLongClick(RecyclerView recyclerView, View view, int position) {
    }
}
