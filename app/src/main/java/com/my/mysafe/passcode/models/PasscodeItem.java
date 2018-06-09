package com.my.mysafe.passcode.models;


public class PasscodeItem {

    public final static int TYPE_EMPTY  =  0,
                            TYPE_NUMBER =  1,
                            TYPE_REMOVE =  2,
                            TYPE_CLEAR  =  3;

    private String value;
    private int type;

    public PasscodeItem(String value, int type) {
        this.value = value;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public int getType() {
        return type;
    }

    @Override
    public String toString() {
        return value;
    }
}
