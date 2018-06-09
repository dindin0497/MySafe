package com.my.mysafe.api;

import com.my.mysafe.model.LoadResult;

import java.util.List;

public interface IGetCallback {
    void onResult(LoadResult result, List<String> list);
}
