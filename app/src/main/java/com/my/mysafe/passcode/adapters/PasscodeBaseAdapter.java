package com.my.mysafe.passcode.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.my.mysafe.passcode.models.PasscodeItem;

import java.util.List;


public abstract class PasscodeBaseAdapter extends BaseAdapter {

    private List<PasscodeItem> items;

    public PasscodeBaseAdapter(List<PasscodeItem> items) {
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public PasscodeItem getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public abstract View getView(int position, View convertView, ViewGroup parent);


}
