package com.beanu.arad.base;

import androidx.lifecycle.ViewModel;

/**
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
