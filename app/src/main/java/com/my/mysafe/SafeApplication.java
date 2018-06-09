package com.my.mysafe;

import android.app.Application;
import android.util.DisplayMetrics;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.my.mysafe.api.GetImage;
import com.my.mysafe.model.IDataObserver;
import com.my.mysafe.api.IGetCallback;
import com.my.mysafe.model.IImageData;
import com.my.mysafe.model.ImageDataImpl;
import com.squareup.leakcanary.LeakCanary;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class SafeApplication extends Application{

    private static final String TAG = SafeApplication.class.getSimpleName();

    IImageData data=new ImageDataImpl();

    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        Log.d(TAG,"onLowMemory");
        Glide.get(this).clearMemory();
    }



    public IImageData getData() {
        return data;
    }
}
