package com.beanu.arad.widget.compoundselector;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 带有一列的选择
 */
public abstract class SelectorOnePopUpWindow {

    protected Context context;
    private PopupWindow popupWindow;
    private LinearLayout layout;

    private SimpleAdapter oneAdapter;
    private OnSelectedListener listener;

    /**
     * 选择事件
     *
     * @author beanu
     */
    public interface OnSelectedListener {
        public void onSelected(int position);

        public void dismiss();
    }


    protected abstract void init();

    protected abstract SimpleAdapter initAdapter();

    public SelectorOnePopUpWindow(Context ctx) {
        this.context = ctx;

        init();
        oneAdapter = initAdapter();

        initView();
        popupWindow = new PopupWindow(context);
//        popupWindow.setWidth((int) context.getResources().getDimension(R.dimen.popupWindow_width));
        popupWindow.setWidth(context.getResources().getDisplayMetrics().widthPixels);
        popupWindow.setHeight((int) context.getResources().getDimension(R.dimen.popupWindow_height));
        popupWindow.setContentView(layout);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setOnDismissListener(new OnDismissListener() {

            @Override
            public void onDismiss() {
                dismissPopupwindow();

            }
        });
    }

    private void initView() {
        if (oneAdapter == null)
            throw new NullPointerException();

        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.acs_selector_one_columns_activity, null);
        ListView listLeft = (ListView) layout.findViewById(R.id.listOne);
        listLeft.setAdapter(oneAdapter);
        listLeft.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (listener != null) {
                    listener.onSelected(position);
                }
            }
        });
    }

    /**
     * 显示浮动框，筛选器
     */
    public void showPopupwindow(View view) {
        if (popupWindow != null) {
            if (popupWindow.isShowing())
                popupWindow.dismiss();
            else {
                popupWindow.showAsDropDown(view);
            }

        }
    }

    public void dismissPopupwindow() {
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        if (listener != null) {
            listener.dismiss();
        }
    }

    public void setOnSelectedListener(OnSelectedListener listener) {
        this.listener = listener;
    }
}
