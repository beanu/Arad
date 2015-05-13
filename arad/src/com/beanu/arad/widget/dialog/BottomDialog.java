package com.beanu.arad.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;

import com.beanu.arad.R;

/**
 * 底部弹出的Dialog
 * Created by beanu on 15/1/20.
 */
public class BottomDialog {

    Dialog dialog;

    public BottomDialog(Context context, View view) {
        if (view != null) {
            dialog = new Dialog(context, R.style.arad_Theme_Dialog_Bottom);
            dialog.setContentView(view, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            dialog.setCanceledOnTouchOutside(true);

            Window window = dialog.getWindow();
            window.getAttributes().width = context.getResources().getDisplayMetrics().widthPixels;
            window.setGravity(Gravity.BOTTOM);  //此处可以设置dialog显示的位置
            window.setWindowAnimations(R.style.arad_AnimBottomDialog);  //添加动画
        }
    }

    public BottomDialog show() {
        if (dialog != null)
            dialog.show();
        return this;
    }

    public BottomDialog dismiss() {
        if (dialog != null)
            dialog.dismiss();
        return this;
    }

    public Dialog getDialog() {
        return dialog;
    }
}
