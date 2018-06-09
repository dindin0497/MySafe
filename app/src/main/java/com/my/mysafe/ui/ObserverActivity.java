package com.my.mysafe.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.my.mysafe.Utils;
import com.my.mysafe.model.IDataObserver;
import com.my.mysafe.model.IImageData;

public abstract class ObserverActivity extends AppCompatActivity  implements IDataObserver {

    protected IImageData data;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        data= Utils.getData(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        data.removeObserver(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        data.addObserver(this);

    }
}
