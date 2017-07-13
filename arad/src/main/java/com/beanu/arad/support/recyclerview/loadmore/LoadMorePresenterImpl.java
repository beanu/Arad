package com.beanu.arad.support.recyclerview.loadmore;

import android.support.v4.util.ArrayMap;

import com.beanu.arad.http.IPageModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


/**
 * LoadMore P层的实现
 * Created by lizhihua on 2016/12/14.
 * 通用加载更多 Presenter
 */

/**
 * @param <B> 列表数据类型
 * @param <V> 实现了 ILoadMoreView接口 的类, 通常填写是当前 Activity (需要实现ILoadMoreView接口)
 *            或 Fragment (需要实现ILoadMoreView接口)
 * @param <M> 实现了 ILoadMoreModel 接口 的类
 */
public class LoadMorePresenterImpl<B, V extends ILoadMoreView<B>, M extends ILoadMoreModel<B>>
        extends ABSLoadMorePresenter<V, M> {

    private int mCurPage = 0;
    private IPageModel<B> mPageModel;
    private List<B> mList = new ArrayList<>();
    private List<B> mCurrentPageList = new ArrayList<>();//当前页的数据

    private boolean mHasError = false;
    private boolean mIsLoading = false;

    private ArrayMap<String, Object> mParams = new ArrayMap<>();

    public List<B> getList() {
        return mList;
    }

    @Override
    public boolean hasMoreResults() {
        return (mPageModel != null && (mPageModel.getCurrentPage() < mPageModel.getTotalPage()));
    }

    @Override
    public boolean hasError() {
        return mHasError;
    }

    @Override
    public boolean isLoading() {
        return mIsLoading;
    }

    @Override
    public void initLoadDataParams(ArrayMap<String, Object> params) {
        mParams = params;
    }

    @Override
    public void loadDataFirst() {
        mCurPage = 0;
        loadDataNext();
    }

    @Override
    public void loadDataNext() {
        ++mCurPage;
        mIsLoading = true;
        mModel.loadData(mParams, mCurPage).subscribe(new Observer<IPageModel<B>>() {

            @Override
            public void onError(Throwable e) {
                mIsLoading = false;
                mHasError = true;
                if (mList == null || mList.isEmpty()) {
                    mView.contentLoadingError();
                }
            }

            @Override
            public void onComplete() {
                mIsLoading = false;
                mHasError = false;
                if (mList == null || mList.isEmpty()) {
                    mView.contentLoadingEmpty();
                } else {
                    mView.contentLoadingComplete();
                }
                mView.loadDataComplete(mCurrentPageList);
            }

            @Override
            public void onSubscribe(@NonNull Disposable d) {
                mRxManage.add(d);
            }

            @Override
            public void onNext(IPageModel<B> pageModel) {
                mPageModel = pageModel;
                if (mCurPage == 1) {//第一次加载或者重新加载
                    mList.clear();
                }
                if (pageModel.getDataList() != null && !pageModel.getDataList().isEmpty()) {
                    mList.addAll(pageModel.getDataList());

                    mCurrentPageList.clear();
                    mCurrentPageList.addAll(pageModel.getDataList());
                }
            }
        });
    }
}
