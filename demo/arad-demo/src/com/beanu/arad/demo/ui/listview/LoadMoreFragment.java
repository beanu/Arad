//package com.beanu.arad.demo.ui.listview;
//
//
//import android.content.Context;
//import android.os.Bundle;
//import android.view.View;
//import android.view.ViewGroup;
//
//import com.beanu.arad.support.loadmore.ABSLoadMoreListFragment;
//import com.beanu.arad.support.loadmore.ILoadMoreAdapter;
//import com.beanu.arad.support.loadmore.LoadMoreBaseAdapter;
//import com.beanu.arad.utils.JsonUtil;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Map;
//
///**
// * 加载更多Fragment
// * 只需要传入接口参数和listview样式就可以完成一个完整的加载更多页面
// * <p/>
// * 注意：
// * 1.此页面适合一个listView样式
// * 2.数据来源于网络接口，返回的数据格式是json
// * <p/>
// * Created by beanu on 14/12/14.
// */
//public abstract class LoadMoreFragment<T> extends ABSLoadMoreListFragment {
//
//    LoadMoreMode loadMoreMode = new LoadMoreMode(this);
//    LoadMoreAdapter loadMoreAdapter;
//    boolean isError = false;
//
//
//    /**
//     * 网络请求数据
//     *
//     * @param page 请求第几页
//     */
//    public abstract Map<String, String> httpRequestParams(int page);
//
//    /**
//     * 接口请求url
//     *
//     * @return url
//     */
//    public abstract String httpRequestUrl();
//
//    /**
//     * json数据 dataList的key值
//     *
//     * @return "dataList"
//     */
//    public abstract String jsonKeyData();
//
//
//    /**
//     * json数据 totalPage的值
//     *
//     * @return "totalPage"
//     */
//    public abstract String jsonKeyTotalPage();
//
//    public abstract View viewItemForLoadMore(final int position, View view, ViewGroup viewGroup);
//
//    public abstract Class pojoClass();
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        loadMoreAdapter = new LoadMoreAdapter(getActivity(), loadMoreMode.getDataList(), this);
//        setListAdapter(loadMoreAdapter);
//
//        loadMoreMode.request();
//        showProgress(true);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        getListView().setOnScrollListener(this);
//
//    }
//
//    @Override
//    protected void loadMore() {
//        loadMoreMode.loadMore();
//    }
//
//    @Override
//    protected boolean hasMore() {
//        return loadMoreMode.isHasMore();
//    }
//
//
//    @Override
//    protected boolean isError() {
//        return isError;
//    }
//
//    @Override
//    public void onRequestSuccess(int requestCode) {
//        isError = false;
//        loadMoreAdapter.notifyDataSetChanged();
//        showProgress(false);
//
//    }
//
//    @Override
//    public void onRequestFaild(String errorNo, String errorMessage) {
//        super.onRequestFaild(errorNo, errorMessage);
//        isError = true;
//    }
//
//    //数据获取
//    private class LoadMoreMode extends IDao {
//
//        private List<T> dataList = new ArrayList<T>();
//        private int currentPage = 1;
//        private boolean hasMore = true;
//
//        public LoadMoreMode(INetResult activity) {
//            super(activity);
//        }
//
//        private void _request(int page) {
//            Map<String, String> params = httpRequestParams(page);
//            getRequestForCode(httpRequestUrl(), params, 0);
//        }
//
//        public void request() {
//            currentPage = 1;
//            _request(1);
//        }
//
//        public void loadMore() {
//            hasMore = false;
//            currentPage++;
//            _request(currentPage);
//        }
//
//        public boolean isHasMore() {
//            return hasMore;
//        }
//
//
//        @Override
//        public void onRequestSuccess(JsonNode result, int requestCode) throws IOException {
//            if (requestCode == 0) {
//                List<T> _list = JsonUtil.node2pojoList(result.get("dataList"), pojoClass());
//                if (_list != null)
//                    dataList.addAll(_list);
//
//                hasMore = !(currentPage >= result.get(jsonKeyTotalPage()).asInt());
//            }
//        }
//
//        public List<T> getDataList() {
//            return dataList;
//        }
//    }
//
//
//    //Adapter适配器
//    private class LoadMoreAdapter extends LoadMoreBaseAdapter<T> {
//
//        public LoadMoreAdapter(Context context, List<T> data, ILoadMoreAdapter listener) {
//            super(context, data, listener);
//        }
//
//        @Override
//        public View getViewForLoadMore(int position, View view, ViewGroup viewGroup) {
//            return viewItemForLoadMore(position, view, viewGroup);
//        }
//
//    }
//}
