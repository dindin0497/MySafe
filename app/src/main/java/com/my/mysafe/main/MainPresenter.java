package com.my.mysafe.main;


import android.content.Context;
import android.util.Log;

import com.my.mysafe.Utils;
import com.my.mysafe.model.IDataObserver;
import com.my.mysafe.model.IImageData;
import com.my.mysafe.model.LoadResult;
import com.my.mysafe.model.Node;

public class MainPresenter implements MainContract.Presenter, IDataObserver {

    final static String TAG=MainPresenter.class.getSimpleName();

    protected IImageData data;

    protected MainContract.View view;

    public MainPresenter(Context context, MainContract.View view)
    {
        data= Utils.getData(context);
        this.view=view;
    }
    @Override
    public void loadData(boolean force) {
        if (force || data.getPicCount()==0) {
            Log.d(TAG,"loadData");
            data.loadData();
        }
        view.showLoading(data.isLoading());
    }

    @Override
    public void subscribe() {
        Log.d(TAG,"subscribe");
        data.addObserver(this);
    }

    @Override
    public void unsubscribe() {
        Log.d(TAG,"unsubscribe");
        data.removeObserver(this);
    }

    @Override
    public void onDataChange(LoadResult result) {
        Log.d(TAG,"onDataChange");
        view.refresh(result);
    }

    @Override
    public int getItemCount() {
        return data.getPicCount();
    }

    @Override
    public Node getItem(int pos) {
        return data.getPic(pos);
    }

    @Override
    public int getPageCount(int page) {
        return data.getPageCount(page);
    }
}
