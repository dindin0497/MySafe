package com.my.mysafe.pin;

import android.text.TextUtils;


public class PinPresenter implements PinContract.Presenter {
    private String passcode="1111";

    @Override
    public boolean verifyPin(String pin) {
        return !TextUtils.isEmpty(pin) && pin.equals(passcode);

    }
}
