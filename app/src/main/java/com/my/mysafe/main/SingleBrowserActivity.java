package com.my.mysafe.main;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.my.mysafe.Constant;
import com.my.mysafe.R;
import com.my.mysafe.Utils;
import com.my.mysafe.adapter.ViewPagerAdapter;
import com.my.mysafe.model.IDataObserver;
import com.my.mysafe.model.IImageData;
import com.my.mysafe.model.LoadResult;
import com.my.mysafe.model.Node;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SingleBrowserActivity extends AppCompatActivity implements MainContract.View
{
    final static String TAG=SingleBrowserActivity.class.getSimpleName();

    @BindView(R.id.viewPager)
    protected ViewPager viewPager;

    ViewPagerAdapter pagerAdapter;

    @BindView(R.id.tv_title)
    protected TextView tvTitle;

    private int position;

    MainContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_single);
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        getSupportActionBar().hide();

        Log.d(TAG,"onCreate");

        ButterKnife.bind(this);

        initPresenter();

        initView();
        initData();
        setListener();
    }


    @Override
    protected void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }


    @Override
    protected void onResume() {
        super.onResume();
        presenter.subscribe();

    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed");
        Intent data=new Intent();
        data.putExtra(Constant.PIC_POS,pagerAdapter.getCurrentPosition());
        setResult(RESULT_OK, data);

        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d(TAG,"onDestroy");


        viewPager.removeAllViews();

    }

    protected void initView()
    {
        viewPager.setPageMargin(25);
        viewPager.setOffscreenPageLimit(1);

        pagerAdapter=new ViewPagerAdapter(this, presenter);

        viewPager.setAdapter(pagerAdapter);

    }

    protected void initData()
    {
        Intent intent = getIntent();
        position = intent.getIntExtra(Constant.PIC_POS, -1);

        refresh();
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "onPageSelected " + position);
            if (viewPager.getAdapter() != null) {
                SingleBrowserActivity.this.position = position;
                setTitle();
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private void setListener() {
        viewPager.addOnPageChangeListener(listener);

    }

    protected void setTitle()
    {
        Node node=presenter.getItem(position);
        if (node!=null) {
            String text = "page "+node.page+" - "+(node.index+1)+"/"+presenter.getPageCount(node.page) ;
            tvTitle.setText(text);
        }
    }

    private void refresh()
    {
        int size= presenter.getItemCount();

        int pos=position;
        if (pos>=size || pos<0)
            pos=0;
        viewPager.setCurrentItem(pos);
        listener.onPageSelected(pos);

        pagerAdapter.notifyDataSetChanged();

        setTitle();

    }

    @Override
    public void showLoading(boolean visible) {

    }

    @Override
    public void refresh(LoadResult result) {
        if (result.success)
            pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void initPresenter() {
        presenter=new MainPresenter(this, this);
    }
}
