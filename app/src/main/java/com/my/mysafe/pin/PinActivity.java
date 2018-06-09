package com.my.mysafe.pin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.my.mysafe.R;
import com.my.mysafe.passcode.PasscodeIndicator;
import com.my.mysafe.passcode.PasscodeView;
import com.my.mysafe.passcode.adapters.PasscodeAdapter;
import com.my.mysafe.main.MainActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PinActivity extends Activity implements PasscodeView.OnItemClickListener, PinContract.View{
    final static String TAG=PinActivity.class.getSimpleName();

    @BindView(R.id.kbd_passcode)
    protected PasscodeView passcodeView;

    @BindView(R.id.indicator_passcode)
    protected PasscodeIndicator indicator;

    private StringBuilder currentPasscode = new StringBuilder();

    PinContract.Presenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_pin);
        super.onCreate(savedInstanceState);

        Log.d(TAG,"onCreate");

        ButterKnife.bind(this);

        PasscodeAdapter adapter;
        adapter = new PasscodeAdapter(getApplicationContext());
        passcodeView.setAdapter(adapter);

        passcodeView.setOnItemClickListener(this);

        initPresenter();
    }

    @Override
    public void initPresenter() {
        presenter=new PinPresenter();
    }


    @Override
    public void onItemClick(PasscodeView view, int position, View item, Object o) {
        if (indicator.isAnimationInProgress())
            return;
        String str=o.toString();
        if (str.equals("clear"))
        {
            currentPasscode.setLength(0);
            indicator.setIndicatorLevel(0);
        }
        else if (str.equals("remove"))
        {
            if (currentPasscode.length() > 0) {
                currentPasscode.setLength(currentPasscode.length() - 1);
                indicator.setIndicatorLevel(currentPasscode.length());
            }
        }
        else {
            currentPasscode.append(str);
            indicator.setIndicatorLevel(currentPasscode.length());
            if (currentPasscode.length() == indicator.getIndicatorLength()) {
                if (presenter.verifyPin(currentPasscode.toString())) {
                    Intent intent = new Intent(this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    currentPasscode=new StringBuilder();
                    indicator.wrongPasscode();
                }

            }
        }
    }
}
