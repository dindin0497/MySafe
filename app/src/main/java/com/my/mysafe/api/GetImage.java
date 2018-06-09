package com.my.mysafe.api;

import android.util.Log;

import com.my.mysafe.model.LoadResult;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class GetImage {
    final static String TAG=GetImage.class.getSimpleName();

    public static void getImage(int id, final IGetCallback callback)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://lit-earth-91645.herokuapp.com/")
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        Log.d(TAG,"load page "+id);

        ImageService request = retrofit.create(ImageService.class);

        request.getImage(id)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    Disposable d;

                    @Override
                    public void onSubscribe(Disposable d) {
                        this.d = d;
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete" );
                        d.dispose();
                    }


                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.getMessage());
                        e.printStackTrace();
                        d.dispose();
                        callback.onResult(new LoadResult(false, e.getMessage()), null);
                    }

                    @Override
                    public void onNext(List<String> list) {
                        callback.onResult(new LoadResult(true, null), list);
                    }
                });


    }
}
