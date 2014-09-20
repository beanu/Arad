package com.beanu.arad.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.beanu.arad.widget.dialog.AlertDialogFragment;
import com.beanu.arad.widget.dialog.CommentDialogFragment;
import com.beanu.arad.widget.dialog.ProgressDialogFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 通用UI
 * Created by beanu on 14-8-22.
 */
public class UIUtils {

    public static int convertDpToPixel(Context context, float dp) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        float px = dp * (metrics.densityDpi / 160f);
        return (int) px;
    }

    /**
     * ProgressDialog
     */
    public static void showProgressDialog(FragmentManager fm, String message) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        ft.commit();

        ProgressDialogFragment dialog = ProgressDialogFragment
                .newInstance(message);
        dialog.show(fm, "dialog");

    }

    /**
     * AlertDialog
     */
    public static void showAlertDialog(FragmentManager fm, String title,
                                       String message, String positiveButtonText,
                                       String negativeButtonText, DialogInterface.OnClickListener positiveListener,
                                       DialogInterface.OnClickListener negativeListener) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        AlertDialogFragment dialog = AlertDialogFragment.newInstance(title,
                message, positiveButtonText, negativeButtonText);
        dialog.setNegativeListener(negativeListener);
        dialog.setPositiveListener(positiveListener);
        dialog.show(fm, "dialog");

    }

    /**
     * 评论提交
     */
    public static void showCommentDialog(FragmentManager fm, String title,
                                         String positiveButtonText, String negativeButtonText,
                                         CommentDialogFragment.PositiveClick onclick) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        CommentDialogFragment dialog = CommentDialogFragment.newInstance(title,
                positiveButtonText, negativeButtonText);
        dialog.setPositiveClick(onclick);
        dialog.show(fm, "dialog");

    }


    /**
     * 隐藏一个等待dialog fm=getSupportFragmentManager()
     */
    public static void hideDialog(FragmentManager fm) {
        DialogFragment prev = (DialogFragment) fm.findFragmentByTag("dialog");
        if (prev != null) {
            prev.dismiss();
        }
    }

    /**
     * 打电话拨号
     */
    public static void dial(Context context, String telephone) {
        if (telephone != null && !telephone.equals("")) {
            Uri uri = Uri.parse("tel:" + telephone);
            Intent intent = new Intent(Intent.ACTION_DIAL, uri);
            context.startActivity(intent);
        }
    }

    /**
     * 打开网页
     */
    public static void openWeb(Context context, String url) {
        try {
            Uri uri = Uri.parse(url);
            context.startActivity(new Intent(Intent.ACTION_VIEW, uri));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 分享
     *
     * @param context
     * @param title
     * @param content
     */
    public static void showShareext(Context context, String title, String content) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, content);
        intent.putExtra(Intent.EXTRA_TITLE, title);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(Intent.createChooser(intent, "请选择"));
    }


    /**
     * 分享
     *
     * @param context
     * @param title
     * @param content
     */
    public static void showShare(Context context, String title, String content) {

        String weibo = "com.sina.weibo";
        String qq = "com.tencent.mobileqq";
        String qqweibo = "com.tencent.WBlog";
        String weixin = "com.tencent.mm";
        String renren = "com.renren.xiaonei.android";
        String google = "com.google.android.apps.plus";

        Intent it = new Intent(Intent.ACTION_SEND);
        it.setType("text/plain");
        List<ResolveInfo> resInfo = context.getPackageManager()
                .queryIntentActivities(it, 0);
        if (!resInfo.isEmpty()) {
            List<Intent> targetedShareIntents = new ArrayList<Intent>();
            for (ResolveInfo info : resInfo) {

                String pkgName = info.activityInfo.applicationInfo.packageName;
                if (weibo.equals(pkgName) || qq.equals(pkgName)
                        || qqweibo.equals(pkgName) || weixin.equals(pkgName)
                        || renren.equals(pkgName) || google.equals(pkgName)) {
                    Intent targeted = new Intent(Intent.ACTION_SEND);
                    targeted.setType("text/plain");
                    targeted.putExtra(Intent.EXTRA_TITLE, title);
                    ActivityInfo activityInfo = info.activityInfo;
                    targeted.putExtra(Intent.EXTRA_TEXT, content);
                    targeted.setPackage(activityInfo.packageName);
                    targetedShareIntents.add(targeted);
                }
            }
            if (targetedShareIntents.size() > 0) {
                Intent chooserIntent = Intent.createChooser(
                        targetedShareIntents.remove(0), "Select app to share");
                if (chooserIntent == null) {
                    return;
                }
                // A Parcelable[] of Intent or LabeledIntent objects as set with
                // putExtra(String, Parcelable[]) of additional activities to
                // place
                // a the front of the list of choices, when shown to the user
                // with a
                // ACTION_CHOOSER.
                chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,
                        targetedShareIntents.toArray(new Parcelable[]{}));

                try {
                    context.startActivity(chooserIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(context,
                            "Can't find share component to share",
                            Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Can't find share component to share",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}
