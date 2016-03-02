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

/**
 * 带有两列的选择
 */
public abstract class SelectorThreePopUpWindow {

    protected Context context;
    private PopupWindow popupWindow;
    private LinearLayout layout;

    private SimpleAdapter leftAdapter;
    private SimpleAdapter middleAdapter;
    private SimpleAdapter rightAdapter;
    private OnSelectedListener listener;

    private int leftSelectedPoint, middleSelectedPoint;

    /**
     * 选择事件
     *
     * @author beanu
     */
    public interface OnSelectedListener {
        public void onSelected(int leftPosition, int middlePosition, int rightPosition);

        public void dismiss();
    }

    protected abstract void init();

    protected abstract SimpleAdapter initLeftAdapter();

    protected abstract SimpleAdapter initMiddleAdapter();

    protected abstract SimpleAdapter initRightAdapter();

    protected abstract void updateMiddleData(SimpleAdapter middleAdapter, int leftPosition);

    protected abstract void updateRightData(SimpleAdapter rightAdapter, int leftPosition, int middlePosition);


    public SelectorThreePopUpWindow(Context ctx) {
        this.context = ctx;

        init();
        leftAdapter = initLeftAdapter();
        rightAdapter = initRightAdapter();
        middleAdapter = initMiddleAdapter();

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
        if (leftAdapter == null || rightAdapter == null)
            throw new NullPointerException();

        layout = (LinearLayout) LayoutInflater.from(context).inflate(R.layout.acs_selector_two_columns_activity, null);
        ListView listLeft = (ListView) layout.findViewById(R.id.listLeft);
        listLeft.setAdapter(leftAdapter);
        listLeft.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                leftSelectedPoint = position;

                if (listener != null) {
                    listener.onSelected(position, -1, -1);
                }
                updateMiddleData(middleAdapter, leftSelectedPoint);
            }
        });

        ListView listMiddle = (ListView) layout.findViewById(R.id.listMiddle);
        listMiddle.setAdapter(middleAdapter);
        listMiddle.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                middleSelectedPoint = position;
                if (listener != null) {
                    listener.onSelected(leftSelectedPoint, position, -1);
                }
                updateRightData(rightAdapter, leftSelectedPoint, position);
            }
        });

        ListView listRight = (ListView) layout.findViewById(R.id.listRight);
        listRight.setAdapter(rightAdapter);
        listRight.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (listener != null) {
                    listener.onSelected(leftSelectedPoint, middleSelectedPoint, position);
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
