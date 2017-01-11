package com.beanu.arad.http;

import java.util.List;

/**
 * 翻页数据
 * Created by Beanu on 16/9/26.
 */

public interface IPageModel<T> {

    /**
     * @return 总页数
     */
    int getTotalPage();

    /**
     * @return 数据列表
     */
    List<T> getDataList();

    /**
     * @return 当前页码
     */
    int getCurrentPage();
}
