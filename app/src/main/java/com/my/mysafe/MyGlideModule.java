package com.my.mysafe;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.DiskCache;
import com.bumptech.glide.load.engine.cache.DiskLruCacheWrapper;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;

@GlideModule
public class MyGlideModule extends AppGlideModule {
    public static final String TAG=MyGlideModule.class.getSimpleName();
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
         long maxBitmapMemoryCache = Runtime.getRuntime().maxMemory() / 8;
         long totalMemory = Runtime.getRuntime().totalMemory();
         long freeMemory = Runtime.getRuntime().freeMemory();

        Log.d(TAG,"max mem "+maxBitmapMemoryCache);

        builder.setMemoryCache(new LruResourceCache(maxBitmapMemoryCache));
        builder.setBitmapPool(new LruBitmapPool(maxBitmapMemoryCache));

    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }
}
