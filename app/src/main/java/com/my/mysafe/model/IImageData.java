package com.my.mysafe.model;

public interface IImageData{
    void addObserver(IDataObserver observer);
    void removeObserver(IDataObserver observer);

    void loadData();
    boolean isLoading();
    void notifyData(LoadResult result);

    int getPicCount();
    int getPageCount(int page);
    Node getPic(int pos);
}
