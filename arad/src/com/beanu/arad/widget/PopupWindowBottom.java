package com.beanu.arad.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.beanu.arad.R;

/**
 * 底部弹出的PopupWindow
 * Created by beanu on 15/1/17.
 */
public abstract class PopupWindowBottom {

    private PopupWindow popupWindow;

    public abstract void onViewCreated(View view);

    public PopupWindowBottom(Context context, int resource) {

        View popupView = LayoutInflater.from(context).inflate(resource, null);
        popupWindow = new PopupWindow(popupView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0xb0000000));
        popupWindow.setFocusable(true);
        //设置popwindow出现和消失动画
        popupWindow.setAnimationStyle(R.style.AnimBottomDialog);
        onViewCreated(popupView);
    }


    public void show(View view) {
        popupWindow.showAtLocation(view, Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
    }

    public void hide() {
        if (popupWindow.isShowing())
            popupWindow.dismiss();
    }

    public PopupWindow getPopupWindow() {
        return popupWindow;
    }
}
