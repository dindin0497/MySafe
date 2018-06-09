package com.my.mysafe.pin;

import com.my.mysafe.BasePresenter;
import com.my.mysafe.BaseView;

public interface PinContract {
    interface Presenter extends BasePresenter {
        boolean verifyPin(String pin);
    }

    interface View extends BaseView<Presenter> {

    }
}
