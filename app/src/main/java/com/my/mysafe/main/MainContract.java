package com.my.mysafe.main;

import com.my.mysafe.BasePresenter;
import com.my.mysafe.BaseView;
import com.my.mysafe.model.LoadResult;
import com.my.mysafe.model.Node;

public interface MainContract {
    interface Presenter extends BasePresenter {
        void loadData(boolean force);
        void subscribe();
        void unsubscribe();

        int getItemCount();
        Node getItem(int pos);
        int getPageCount(int page);
    }

    interface View extends BaseView<Presenter> {
        void showLoading(boolean visible);
        void refresh(LoadResult result);
    }
}
