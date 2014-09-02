package com.beanu.arad.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * 只显示信息的dialog
 * Created by beanu on 14-8-22.
 */
public class MessageDialogFragment extends DialogFragment {

    public static MessageDialogFragment newInstance(String message) {
        MessageDialogFragment f = new MessageDialogFragment();
        Bundle args = new Bundle();
        args.putString("message", message);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用Builder类更方便
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(getArguments().getString("message"));

        // .setPositiveButton(R.string.fire, new
        // DialogInterface.OnClickListener() {
        // public void onClick(DialogInterface dialog, int id) {
        // // 相当于确定
        // }
        // }).setNegativeButton(R.string.cancel, new
        // DialogInterface.OnClickListener() {
        // public void onClick(DialogInterface dialog, int id) {
        // // 相当于取消
        // }
        // });
        // 创建AlertDialog对象并返回
        return builder.create();
    }
}