package com.beanu.arad.utils;

import android.view.View;

/**
 * @author lizhi
 */

public class ViewUtils {
    public static void setVisibility(int visibility, View view) {
        if (view != null && view.getVisibility() != visibility) {
            view.setVisibility(visibility);
        }
    }

    public static void setVisibility(int visibility, View... views) {
        for (View view : views) {
            if (view != null && view.getVisibility() != visibility) {
                view.setVisibility(visibility);
            }
        }
    }
}
