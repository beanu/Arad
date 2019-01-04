package com.beanu.arad.support.recyclerview.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.beanu.arad.R;
import com.beanu.arad.support.listview.ILoadMoreListener;

import java.util.List;

/**
 * Created by lizhi on 2017/10/9.
 * 加载更多adapter包装器
 */

@SuppressWarnings({"unchecked", "WeakerAccess", "unused"})
public class LoadMoreAdapterWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //底部点击事件
    public interface OnClickRetryListener {
        void onClickRetry();
    }

    private static final int TYPE_LOAD_MORE = Integer.MAX_VALUE - 1;

    @NonNull
    private final RecyclerView.Adapter innerAdapter;
    @Nullable
    private final ILoadMoreListener loadMoreListener;
    private final LayoutInflater inflater;
    @Nullable
    private OnClickRetryListener onClickRetryListener;
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (onClickRetryListener != null){
                onClickRetryListener.onClickRetry();
            }
        }
    };

    public LoadMoreAdapterWrapper(@NonNull Context context, @NonNull RecyclerView.Adapter<?> adapter, @Nullable ILoadMoreListener loadMoreListener) {
        this(context, adapter, loadMoreListener, null);
    }

    public LoadMoreAdapterWrapper(@NonNull Context context, @NonNull RecyclerView.Adapter<?> adapter, @Nullable ILoadMoreListener loadMoreListener, @Nullable OnClickRetryListener onClickRetryListener) {
        this.innerAdapter = adapter;
        this.loadMoreListener = loadMoreListener;
        inflater = LayoutInflater.from(context);
        this.onClickRetryListener = onClickRetryListener;
    }

    @NonNull
    public RecyclerView.Adapter<?> getRawAdapter() {
        return innerAdapter;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_LOAD_MORE){
            return new ViewHolder(inflater.inflate(R.layout.arad_list_item_stream_status, parent, false));
        }
        return innerAdapter.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder){
            ViewHolder h = (ViewHolder) holder;
            if (loadMoreListener != null && loadMoreListener.hasError()) {
                h.textView.setText("发生错误");
                h.progressBar.setVisibility(View.GONE);
                h.itemView.setOnClickListener(onClickListener);
            } else {
                h.textView.setText("正在加载更多.....");
                h.progressBar.setVisibility(View.VISIBLE);
                h.itemView.setOnClickListener(null);
            }
        } else {
            innerAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return innerAdapter.getItemCount() + (loadMoreListener != null && (loadMoreListener.hasError() || loadMoreListener.hasMoreResults()) ? 1 : 0);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List payloads) {
        if (holder instanceof ViewHolder){
            super.onBindViewHolder(holder, position, payloads);
        } else {
            innerAdapter.onBindViewHolder(holder, position, payloads);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position >= innerAdapter.getItemCount()){
            return TYPE_LOAD_MORE;
        }
        return innerAdapter.getItemViewType(position);
    }

    @Override
    public void setHasStableIds(boolean hasStableIds) {
        super.setHasStableIds(hasStableIds);
        innerAdapter.setHasStableIds(hasStableIds);
    }

    @Override
    public long getItemId(int position) {
        if (position >= innerAdapter.getItemCount()){
            return TYPE_LOAD_MORE;
        }
        return innerAdapter.getItemId(position);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof ViewHolder){
            super.onViewRecycled(holder);
        } else {
            innerAdapter.onViewRecycled(holder);
        }
    }

    @Override
    public boolean onFailedToRecycleView(RecyclerView.ViewHolder holder) {
        if (holder instanceof ViewHolder){
            return super.onFailedToRecycleView(holder);
        }
        return innerAdapter.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof ViewHolder){
            super.onViewAttachedToWindow(holder);
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            if (params != null && params instanceof StaggeredGridLayoutManager.LayoutParams){
                ((StaggeredGridLayoutManager.LayoutParams) params).setFullSpan(true);
            }
        } else {
            innerAdapter.onViewAttachedToWindow(holder);
        }
    }

    @Override
    public void onViewDetachedFromWindow(RecyclerView.ViewHolder holder) {
        if (holder instanceof ViewHolder){
            super.onViewDetachedFromWindow(holder);
        } else {
            innerAdapter.onViewDetachedFromWindow(holder);
        }
    }

    @Override
    public void registerAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.registerAdapterDataObserver(observer);
        innerAdapter.registerAdapterDataObserver(observer);
    }

    @Override
    public void unregisterAdapterDataObserver(RecyclerView.AdapterDataObserver observer) {
        super.unregisterAdapterDataObserver(observer);
        innerAdapter.unregisterAdapterDataObserver(observer);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        innerAdapter.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager){
            final GridLayoutManager gridManager = (GridLayoutManager) manager;
            final GridLayoutManager.SpanSizeLookup sizeLookup = gridManager.getSpanSizeLookup();
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (position >= innerAdapter.getItemCount()){
                        return gridManager.getSpanCount();
                    }
                    return sizeLookup == null ? 1 : sizeLookup.getSpanSize(position);
                }
            });
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        innerAdapter.onDetachedFromRecyclerView(recyclerView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(android.R.id.progress);
            textView = itemView.findViewById(android.R.id.text1);
        }
    }
}
