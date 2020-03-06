package com.beanu.arad.base;

import androidx.lifecycle.ViewModel;

/**
 * p层的数据 包裹在了viewmodel中，保证了数据不会被丢失
 *
 * @author Beanu
 */
public final class BaseViewModel<P extends BasePresenter> extends ViewModel {

    private P presenter;

    void setPresenter(P presenter) {
        if (this.presenter == null) {
            this.presenter = presenter;
        }
    }

    P getPresenter() {
        return this.presenter;
    }

    @Override
    protected void onCleared() {
        super.onCleared();

        if (presenter != null) {
            presenter.onCleared();
            presenter = null;
        }
    }
}
