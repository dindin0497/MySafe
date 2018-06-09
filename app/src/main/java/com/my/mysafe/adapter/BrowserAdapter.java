package com.my.mysafe.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.my.mysafe.R;
import com.my.mysafe.Utils;
import com.my.mysafe.main.MainContract;
import com.my.mysafe.model.Node;


public class BrowserAdapter extends RecyclerView.Adapter<BrowserAdapter.MemberViewHolder>{

    final static String TAG=BrowserAdapter.class.getSimpleName();

    Context context;
    MainContract.Presenter presenter;

    public interface onClickListener{
        void onClick(int pos);
    }

    onClickListener listener;

    static class MemberViewHolder extends RecyclerView.ViewHolder{
        private ImageView iv;
        private TextView tv;

        public MemberViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_import);
            tv = (TextView) itemView.findViewById(R.id.tv_name);
        }

    }
    public BrowserAdapter(Context context, MainContract.Presenter presenter, onClickListener listener)
    {
        this.context=context;
        this.presenter=presenter;
        this.listener=listener;
    }



    @Override
    public MemberViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_browser, parent, false);
            return new MemberViewHolder(view);

    }


    @Override
    public void onBindViewHolder(MemberViewHolder holder, int position) {
        holder.itemView.setId(position);
            Node node = presenter.getItem( position);
            if (node == null || TextUtils.isEmpty(node.url))
                return;
            holder.tv.setText(node.page+"-"+(node.index+1));
            final int pos=position;
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(pos);

                }
            });
            Log.d(TAG, "load " + node.url);
            Utils.clearBmp(context, holder.iv);
            Utils.loadBmpWithLowPriority(context, holder.iv, node.url);

    }

    @Override
    public int getItemCount() {
        int size=presenter.getItemCount();
        return size==0?0:size;
    }

}
