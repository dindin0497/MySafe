package com.my.mysafe.passcode.adapters;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.my.mysafe.R;
import com.my.mysafe.passcode.models.PasscodeItem;
import com.my.mysafe.passcode.models.PasscodeItemClear;
import com.my.mysafe.passcode.models.PasscodeItemRemove;

import java.util.Arrays;

public class PasscodeAdapter extends PasscodeBaseAdapter {

    private LayoutInflater inflater;

    private int height;

    public PasscodeAdapter(Context context) {
        super(Arrays.asList(
                new PasscodeItem("1", PasscodeItem.TYPE_NUMBER),
                new PasscodeItem("2", PasscodeItem.TYPE_NUMBER),
                new PasscodeItem("3", PasscodeItem.TYPE_NUMBER),
                new PasscodeItem("4", PasscodeItem.TYPE_NUMBER),
                new PasscodeItem("5", PasscodeItem.TYPE_NUMBER),
                new PasscodeItem("6", PasscodeItem.TYPE_NUMBER),
                new PasscodeItem("7", PasscodeItem.TYPE_NUMBER),
                new PasscodeItem("8", PasscodeItem.TYPE_NUMBER),
                new PasscodeItem("9", PasscodeItem.TYPE_NUMBER),
                new PasscodeItemClear(),
                new PasscodeItem("0", PasscodeItem.TYPE_NUMBER),
                new PasscodeItemRemove()
        ));
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        height = dm.heightPixels/2;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null || convertView.getTag() != PasscodeItem.class) {
            convertView = inflater.inflate(R.layout.button_passcode_ios, parent, false);
            convertView.setTag(PasscodeItem.class);
        }

        PasscodeItem item = getItem(position);
        int type = item.getType();
        String value = item.getValue();
        final ImageView iv_passcode = (ImageView) convertView.findViewById(R.id.iv_passcode);
        TextView tv_passcode = (TextView) convertView.findViewById(R.id.tv_passcode);
        RelativeLayout layout = (RelativeLayout) convertView.findViewById(R.id.rl_button);

        ViewGroup.LayoutParams params = layout.getLayoutParams();
        params.height = height / 4;
        params.width = height / 4;
        switch (type) {
            case PasscodeItem.TYPE_NUMBER:
                tv_passcode.setText(value);
                break;
            case PasscodeItem.TYPE_CLEAR:
                tv_passcode.setText(null);
                iv_passcode.setImageResource(R.drawable.close_white_button);
                break;
            case PasscodeItem.TYPE_REMOVE:
                tv_passcode.setText(null);
                iv_passcode.setImageResource(R.drawable.backspace_white_icon);
                break;
            default:
                break;
        }

        return convertView;
    }
}
