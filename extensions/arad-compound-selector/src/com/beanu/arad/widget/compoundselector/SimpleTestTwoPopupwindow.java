package com.beanu.arad.widget.compoundselector;

import android.content.Context;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 类型选择器(案例)
 * Created by Beanu on 16/2/23.
 */
public class SimpleTestTwoPopupwindow extends SelectorTwoPopUpWindow {

    private List<Map<String, ?>> leftData;
    private List<Map<String, ?>> rightData;

    private List<CategoryItem> _list;

    public SimpleTestTwoPopupwindow(Context ctx) {
        super(ctx);
    }

    @Override
    protected void init() {

        //测试数据初始化
        leftData = new ArrayList<>();
        rightData = new ArrayList<>();
        _list = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            CategoryItem categoryItem = new CategoryItem();
            categoryItem.setId(i + "");
            categoryItem.setName(i + "name");

            List<CategoryItem> next = new ArrayList<>();
            for (int j = 0; j < 5; j++) {
                CategoryItem child = new CategoryItem();
                child.setId(i + "" + j);
                child.setName(i + "name" + j);
                next.add(child);
            }
            categoryItem.setNext(next);
            _list.add(categoryItem);
        }
    }

    @Override
    protected SimpleAdapter initLeftAdapter() {
        for (CategoryItem item : _list) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", item.getName());
            leftData.add(map);
        }
        return new SimpleAdapter(context, leftData,
                R.layout.acs_selector_two_list_left_item,
                new String[]{"name"}, new int[]{R.id.selector_list_left_item_textview});
    }

    @Override
    protected SimpleAdapter initRightAdapter() {

        rightData.addAll(productRightData(0));

        return new SimpleAdapter(context, rightData,
                R.layout.acs_selector_two_list_right_item,
                new String[]{"name"}, new int[]{R.id.selector_list_right_item_textView});
    }

    @Override
    protected void updateRightData(SimpleAdapter rightAdapter, int position) {

        rightData.clear();
        rightData.addAll(productRightData(position));
        rightAdapter.notifyDataSetChanged();
    }


    private List<Map<String, ?>> productRightData(int position) {

        List<Map<String, ?>> mapList = new ArrayList<>();

        CategoryItem rightData = _list.get(position);
        for (CategoryItem child : rightData.getNext()) {
            HashMap<String, String> map = new HashMap<>();
            map.put("name", child.getName());
            mapList.add(map);
        }
        return mapList;
    }


    public class CategoryItem {

        private String name;
        private String id;
        private List<CategoryItem> next;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public List<CategoryItem> getNext() {
            return next;
        }

        public void setNext(List<CategoryItem> next) {
            this.next = next;
        }
    }
}
