package com.beanu.arad.compoundselector;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.beanu.arad.compoundselector.adapter.SelectorLeftAdapter;
import com.beanu.arad.compoundselector.adapter.SelectorRightAdapter;

import java.util.ArrayList;
import java.util.List;

public class SelectorPopUpWindow {

    private Context context;
    private PopupWindow popupWindow;
	private Pull_ListView listRight;
	private Pull_ListView listLeft;
    private LinearLayout layout;

	private SelectorRightAdapter rightAdapter;
	private SelectorLeftAdapter leftAdapter;

	String cities[][] = new String[][] {
			new String[] { "全部美食", "本帮江浙菜", "川菜", "粤菜", "湘菜", "东北菜", "台湾菜", "新疆/清真", "素菜", "火锅", "自助餐", "小吃快餐", "日本",
					"韩国料理", "东南亚菜", "西餐", "面包甜点", "其他" },
			new String[] { "全部休闲娱乐", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术",
					"DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐" },
			new String[] { "全部购物", "综合商场", "服饰鞋包", "运动户外", "珠宝饰品", "化妆品", "数码家电", "亲子购物", "家居建材", "书店", "书店", "眼镜店",
					"特色集市", "更多购物场所", "食品茶酒", "超市/便利店", "药店" },
			new String[] { "全部休闲娱乐", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术",
					"DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐" },
			new String[] { "全", "咖啡厅", "酒吧", "茶馆", "KTV", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术", "DIY手工坊", "桌球馆",
					"桌面游戏", "更多休闲娱乐" },
			new String[] { "全部", "咖啡厅", "酒吧", "茶馆", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术", "DIY手工坊",
					"桌球馆", "桌面游戏", "更多休闲娱乐" },
			new String[] { "全部休", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术",
					"DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐" },
			new String[] { "全部休闲", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术",
					"DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐" },
			new String[] { "全部休闲娱", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术",
					"DIY手工坊", "桌球馆", "桌面游戏" },
			new String[] { "全部休闲娱乐", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术",
					"DIY手工坊", "桌球馆", "桌面游戏", "更多休闲娱乐" },
			new String[] { "全部休闲aaa", "咖啡厅", "酒吧", "茶馆", "KTV", "电影院", "游乐游艺", "公园", "景点/郊游", "洗浴", "足浴按摩", "文化艺术",
					"DIY手工坊", "桌球馆", "桌面游戏" }, };
	String food[] = new String[] { "全部频道", "美食", "休闲娱乐", "购物", "酒店", "丽人", "运动健身", "结婚", "亲子", "爱车", "生活服务" };
	List<String> leftData = new ArrayList<String>();

    public SelectorPopUpWindow(Context ctx){
        this.context=ctx;

        // TODO delete TEST
        for (int i = 0; i < food.length; i++) {
            leftData.add(food[i]);
        }

        leftAdapter = new SelectorLeftAdapter(context, leftData);
        rightAdapter = new SelectorRightAdapter(context, cities, 0);
        initView();
        popupWindow=new PopupWindow(context);
        popupWindow.setWidth((int)context.getResources().getDimension(R.dimen.popupWindow_width));
        popupWindow.setHeight((int)context.getResources().getDimension(R.dimen.popupWindow_height));
        popupWindow.setContentView(layout);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setFocusable(true);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
    }

    private void initView(){
        layout= (LinearLayout)LayoutInflater.from(context).inflate(R.layout.acs_selector_list_activity,null);
        listLeft = (Pull_ListView) layout.findViewById(R.id.listLeft);
        listLeft.setAdapter(leftAdapter);
        listLeft.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // leftAdapter.notifyDataSetInvalidated();
                rightAdapter.setLeftPosition(position);
                rightAdapter.notifyDataSetChanged();

            }
        });

        listRight = (Pull_ListView) layout.findViewById(R.id.listRight);
        listRight.setAdapter(rightAdapter);
        listRight.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // TODO Auto-generated method stub

            }
        });


    }

    /**
     * 显示浮动框，筛选器
     */
    public void showPopupwindow(View view){
        if (popupWindow!=null){
            if (popupWindow.isShowing())
                popupWindow.dismiss();
            else{
                popupWindow.showAsDropDown(view);
            }

        }
    }

    public void dismissPopupwindow(){
        if (popupWindow!=null){
            popupWindow.dismiss();
        }

    }
}
