package com.my.mysafe.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.github.chrisbanes.photoview.PhotoView;
import com.my.mysafe.R;
import com.my.mysafe.Utils;
import com.my.mysafe.main.MainContract;
import com.my.mysafe.model.Node;

import static android.view.View.inflate;

public class ViewPagerAdapter extends PagerAdapter {

    final static String TAG=ViewPagerAdapter.class.getSimpleName();

    Context context;
    MainContract.Presenter presenter;

    int current_positon;

    public ViewPagerAdapter(Context context, MainContract.Presenter presenter) {
        this.context=context;
        this.presenter=presenter;
    }

    @Override
    public int getCount() {
        return presenter.getItemCount();
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {

        View imageLayout = inflate(context, R.layout.item_album_pager, null);
        viewGroup.addView(imageLayout);
        PhotoView imageView = (PhotoView) imageLayout.findViewById(R.id.image);

        Node node=presenter.getItem(position);
        if (node!=null && !TextUtils.isEmpty(node.url)) {
            Utils.loadBmpWithHighPriority(context, imageView, node.url);
            Log.d(TAG,"instantiateItem "+position+" "+node.url);
            current_positon=position;
        }

        if (getCount()==position+1)
            presenter.loadData(true);
        return imageLayout;
    }


    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(ViewGroup container, int arg1, Object object) {
        View layout=(View)object;
        Log.d(TAG,"destroyItem "+arg1);
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    public int getCurrentPosition() {
        return current_positon;
    }


}
