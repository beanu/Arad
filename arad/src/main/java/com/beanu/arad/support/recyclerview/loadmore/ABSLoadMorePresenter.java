package com.beanu.arad.support.recyclerview.loadmore;

import androidx.collection.ArrayMap;

import com.beanu.arad.base.BasePresenter;
import com.beanu.arad.support.listview.ILoadMoreListener;

import java.util.Map;

/**
 * LoadMore P层
 * Created by lizhihua on 2016/12/14.
 */

public abstract class ABSLoadMorePresenter<V extends ILoadMoreView, M extends ILoadMoreModel> extends BasePresenter<V, M>
        implements ILoadMoreListener {

    /**
     * 初始化Model层的数据请求参数
     * <p>
     * {@link com.beanu.arad.support.recyclerview.loadmore.ILoadMoreModel#loadData(Map, int)}
     *
     * @param params loadData 参数
     */
    public abstract void initLoadDataParams(ArrayMap<String, Object> params);

    /**
     * 第一次请求
     */
    public abstract void loadDataFirst();

    /**
     * 请求下一页数据
     */
    public abstract void loadDataNext();
}
