package com.arad.support.viewpager.transforms;

import android.view.View;

/**
 *
 * Created by yunhe on 2015/5/26.
 */
public class HalfTransformer extends ABaseTransformer {

    @Override
    protected void onTransform(View view, float position) {
        view.setTranslationX(position < 0 ? 0f : -view.getWidth()/2 * position);
    }
}
