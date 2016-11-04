package com.beanu.arad.widget.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.widget.EditText;

/**
 * 文本框Dialog
 * Created by beanu on 14-8-22.
 */
public class CommentDialogFragment extends DialogFragment {

    public interface PositiveClick {
        public void onclick(String comment);
    }

    private PositiveClick positiveClick;

    public static CommentDialogFragment newInstance(String title, String positiveButtonText,
                                                    String negativeButtonText) {
        CommentDialogFragment f = new CommentDialogFragment();

        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("positiveButtonText", positiveButtonText);
        args.putString("negativeButtonText", negativeButtonText);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, 0);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用Builder类更方便
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Set up the input
        final EditText input = new EditText(getActivity());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);
        builder.setTitle(getArguments().getString("title")).setPositiveButton(getArguments().getString("positiveButtonText"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String m_Text = input.getText().toString();
                if (positiveClick != null)
                    positiveClick.onclick(m_Text);
            }
        }).setNegativeButton(getArguments().getString("negativeButtonText"), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dismiss();

            }
        });

        return builder.create();
    }

    public void setPositiveClick(PositiveClick positiveClick) {
        this.positiveClick = positiveClick;
    }
}
