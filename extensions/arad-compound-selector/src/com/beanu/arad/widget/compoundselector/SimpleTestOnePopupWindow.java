package com.beanu.arad.widget.compoundselector;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 排序 选择器
 * Created by Beanu on 16/2/24.
 */
public class SimpleTestOnePopupWindow extends SelectorOnePopUpWindow {

    private List<Map<String, ?>> leftData;

    public SimpleTestOnePopupWindow(Context ctx) {
        super(ctx);
    }

    @Override
    protected void init() {
        leftData = new ArrayList<>();
    }

    @Override
    protected SimpleAdapter initAdapter() {

        for (int i = 0; i < 10; i++) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", i + "name");
            leftData.add(map);
        }
        return new SimpleAdapter(context, leftData,
                R.layout.acs_selector_two_list_left_item,
                new String[]{"name"}, new int[]{R.id.selector_list_left_item_textview});
    }
}
