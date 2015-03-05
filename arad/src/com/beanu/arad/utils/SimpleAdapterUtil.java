package com.beanu.arad.utils;

import android.view.View;
import android.widget.ImageView;
import android.widget.SimpleAdapter;

import com.beanu.arad.Arad;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * TODO 此方法没有经过检验，暂不使用
 * SimpleAdapter辅助类
 * Created by beanu on 15/1/28.
 */
public class SimpleAdapterUtil {

    public static SimpleAdapter.ViewBinder ImageViewBinder() {

        return new SimpleAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Object data, String textRepresentation) {
                if ((view instanceof ImageView) & (data instanceof String)) {
                    ImageView iv = (ImageView) view;
                    String url = (String) data;
                    Arad.imageLoader.load(url).into(iv);
                    return true;
                }
                return false;
            }
        };
    }

    public static List<? extends Map<String, ?>> beanToMap(List<Object> list) {
        if (list != null) {
            ObjectMapper mapper = new ObjectMapper();
            List data = new ArrayList<>();
            for (Object bean : list) {
                Class<?> pomclass = bean.getClass();
                Map<String, Object> props = mapper.convertValue(bean, Map.class);
                data.add(props);
            }
            return data;
        }
        return null;
    }
}
