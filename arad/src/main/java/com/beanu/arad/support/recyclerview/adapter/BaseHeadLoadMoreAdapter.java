//package com.beanu.arad.support.recyclerview.adapter;
//
//import android.content.Context;
//import android.support.v7.widget.RecyclerView;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.beanu.arad.R;
//import com.beanu.arad.support.listview.ILoadMoreListener;
//
//import java.util.List;
//
///**
// * 带有头部和loadmore的recyclerview
// * Created by yunhe on 2015-06-23.
// */
//public abstract class BaseHeadLoadMoreAdapter<E, VH extends RecyclerView.ViewHolder> extends _BaseAdapter<E, RecyclerView.ViewHolder> {
//    public static final int VIEW_TYPE_HEADER = 0;
//    public static final int VIEW_TYPE_ITEM = 1;
//    private static final int VIEW_TYPE_LOADING = 2;
//
//    protected LayoutInflater inflater;
//    protected Context mContext;
//
//    private ILoadMoreListener listener;
//
//    public BaseHeadLoadMoreAdapter(Context context, List<E> list, ILoadMoreListener listener) {
//        this.list = list;
//        this.mContext = context;
//        this.listener = listener;
//        this.inflater = LayoutInflater.from(context);
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//
//        if (position == 0) {
//            return VIEW_TYPE_HEADER;
//        } else if (position > list.size()) {
//            return VIEW_TYPE_LOADING;
//        } else {
//            return VIEW_TYPE_ITEM;
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        if (list == null)
//            return 1;
//        if (listener == null)
//            return list.size() + 1;
//        else {
//            if (list.size() == 0) {
//                return 1;
//            }
//
//            return list.size() + 1 + (
//                    // show the status list row if...
//                    (listener.hasMoreResults() // ...or there's another
//                            // page
//                            || listener.hasError()) // ...or there's an error
//                            ? 1
//                            : 0);
//
//        }
//    }
//
//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        switch (viewType) {
//            case VIEW_TYPE_HEADER:
//                return onCreateHeaderViewHolder(parent);
//            case VIEW_TYPE_ITEM:
//                return onCreateItemViewHolder(parent);
//            case VIEW_TYPE_LOADING:
//                return new LoadMoreViewHolder(inflater.inflate(R.layout.arad_list_item_stream_status, parent, false));
//            default:
//                return onCreateItemViewHolder(parent);
//        }
//    }
//
//    @Override
//    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (getItemViewType(position) == VIEW_TYPE_HEADER) {
//            onBindHeaderViewHolder(holder, position);
//        } else if (getItemViewType(position) == VIEW_TYPE_LOADING) {
//            onBindLoadmoreViewHolder(holder, position);
//        } else {
//            onBindItemViewHolder((VH) holder, position - 1);
//        }
//    }
//
//    /**
//     * 创建header布局
//     *
//     * @param parent
//     * @return 视图Holder
//     */
//    public abstract RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent);
//
//    /**
//     * 创建Item布局
//     *
//     * @param parent
//     * @return 视图Holder
//     */
//    public abstract VH onCreateItemViewHolder(ViewGroup parent);
//
//    public abstract void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position);
//
//    public abstract void onBindItemViewHolder(VH holder, int position);
//
//    //loadmore holder
//    public static class LoadMoreViewHolder extends RecyclerView.ViewHolder {
//        TextView textView;
//
//        public LoadMoreViewHolder(View itemView) {
//            super(itemView);
//            textView = (TextView) itemView.findViewById(android.R.id.text1);
//        }
//    }
//
//    //loadmore 绑定事件
//    private void onBindLoadmoreViewHolder(RecyclerView.ViewHolder holder, int position) {
//        if (listener.hasError()) {
//            ((LoadMoreViewHolder) holder).textView.setText("发生错误");
//        } else {
//            ((LoadMoreViewHolder) holder).textView.setText("正在加载更多.....");
//        }
//    }
//
//}
