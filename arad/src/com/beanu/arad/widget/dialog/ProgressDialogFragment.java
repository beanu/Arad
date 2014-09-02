package com.beanu.arad.widget.dialog;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

/**
 * 等待dialog
 * Created by beanu on 14-8-22.
 */
public class ProgressDialogFragment extends DialogFragment {

    private static final String Tag = "message";

    public static ProgressDialogFragment newInstance(String message) {
        ProgressDialogFragment f = new ProgressDialogFragment();

        Bundle args = new Bundle();
        args.putString(Tag, message);
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
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(getArguments().getString(Tag));
        dialog.setCancelable(false);
        return dialog;
    }
}
