package com.beanu.arad.support.recyclerview.loadmore;

import com.beanu.arad.base.BaseView;

import java.util.List;

/**
 * LoadMore V层
 * Created by lizhihua on 2016/12/8.
 */

public interface ILoadMoreView<B> extends BaseView {
    void loadDataComplete(List<B> beans);
}
