package com.my.mysafe.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.my.mysafe.Constant;
import com.my.mysafe.R;
import com.my.mysafe.adapter.BrowserAdapter;
import com.my.mysafe.model.LoadResult;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity implements MainContract.View{
    final static String TAG=MainActivity.class.getSimpleName();

    @BindView(R.id.recycler)
    protected RecyclerView recyclerView;
    private BrowserAdapter adapter=null;

    @BindView(R.id.loading)
    protected ProgressBar progressBar;

    GridLayoutManager layoutManager;

    private int PAGE_SIZE = 20;

    MainContract.Presenter presenter;

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            //Log.d(TAG,"onScrolled visibleItemCount="+visibleItemCount+" firstVisibleItemPosition="+firstVisibleItemPosition+" totalItemCount="+totalItemCount);
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0
                    && totalItemCount >= PAGE_SIZE) {
                        presenter.loadData(true);
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        ButterKnife.bind(this);

        recyclerView.setHasFixedSize(true);

        initPresenter();

        adapter = new BrowserAdapter(this, presenter, new BrowserAdapter.onClickListener() {
            @Override
            public void onClick(int pos) {
                Intent intent = new Intent(MainActivity.this, SingleBrowserActivity.class);
                intent.putExtra(Constant.PIC_POS, pos);
                startActivityForResult(intent, Constant.REQUEST_SINGLE);
            }
        });

        layoutManager=new GridLayoutManager(this, 4);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(onScrollListener);

        progressBar.setIndeterminate(true);

    }

    @Override
    public void showLoading(boolean visible) {
        progressBar.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @Override
    public void refresh(LoadResult result) {
        Log.d(TAG,"onDataChange "+result.success);
        if (result.success)
            adapter.notifyDataSetChanged();
        else
        {
            Toast.makeText(MainActivity.this, result.err,  Toast.LENGTH_LONG).show();
        }
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void initPresenter() {
        presenter=new MainPresenter(this, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode==Constant.REQUEST_SINGLE) {
            int pos=data.getIntExtra(Constant.PIC_POS, 0);
            Log.d(TAG, "onActivityResult " + pos);
            refresh(new LoadResult());
            recyclerView.smoothScrollToPosition(pos);
        }
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
        presenter.loadData(false);

    }

}
