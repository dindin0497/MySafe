package com.my.mysafe.model;

public class LoadResult {
    public boolean success;
    public String err;

    public LoadResult()
    {
        success=true;
    }
    public LoadResult(boolean succ, String error)
    {
        success=succ;
        err=error;
    }
}
