package com.my.mysafe;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.my.mysafe.model.IImageData;

import java.util.List;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class Utils {

    final static String TAG=Utils.class.getSimpleName();

    public static IImageData getData(Context context)
    {
       return  ((SafeApplication)context.getApplicationContext()).getData();
    }


    private static RequestListener requestListener = new RequestListener() {
        @Override
        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target target, boolean isFirstResource) {
            Log.e(TAG, "onException: " + e.toString()+"  model:"+model+" isFirstResource: "+isFirstResource);
            return false;
        }

        @Override
        public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
            return false;
        }
    };

    private static RequestOptions requestOptions = new RequestOptions()
            .placeholder(R.mipmap.ic_launcher)
            .error(R.mipmap.ic_launcher);

    public static void loadBmpWithLowPriority(final Context context, final ImageView imageView, final String url)
    {
        GlideApp.with(context/*.getApplicationContext()*/)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .apply(requestOptions)
                .listener(requestListener)
                .priority(Priority.NORMAL)
                .into(imageView);
    }
    public static void loadBmpWithHighPriority(final Context context, final ImageView imageView, final String url)
    {
        GlideApp.with(context/*.getApplicationContext()*/)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .apply(requestOptions)
                .listener(requestListener)
                .priority(Priority.HIGH)
                .into(imageView);
    }

    public static void clearBmp(final Context context, final ImageView imageView)
    {
        Glide.with(context).clear(imageView);
    }

}
