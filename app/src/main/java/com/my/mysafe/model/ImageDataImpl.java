package com.my.mysafe.model;

import android.util.Log;

import com.my.mysafe.api.GetImage;
import com.my.mysafe.api.IGetCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageDataImpl implements IImageData {

    private static final String TAG = ImageDataImpl.class.getSimpleName();

    private List<Node> picList=new ArrayList<>();

    private int page=1;

    private boolean isLastPage = false;
    private boolean isLoading = false;

    private List<WeakReference<IDataObserver>> observerList=new ArrayList<>();

    private Map<Integer, Integer> pageMap=new HashMap<>();

    @Override
    public void addObserver(IDataObserver observer) {
        observerList.add(new WeakReference<IDataObserver>(observer));
    }

    @Override
    public void removeObserver(IDataObserver observer) {
        for (int i=0; i<observerList.size(); i++)
        {
            if (observerList.get(i).get()!=null && observerList.get(i).get()==observer)
            {
                observerList.remove(i);
                break;
            }
        }
        observerList.add(new WeakReference<IDataObserver>(observer));
    }

    @Override
    public boolean isLoading() {
        return isLoading;
    }

    @Override
    public void loadData() {
        if (isLastPage || isLoading)
            return ;

        Log.d(TAG,"Load page "+page);
        isLoading = true;

        GetImage.getImage(page, new IGetCallback() {
            @Override
            public void onResult(LoadResult result, List<String> list) {
                isLoading = false;
                if (result.success) {
                    if (list.size()>0) {
                        addPic(list);
                        page++;

                    }
                    else
                        isLastPage=true;
                }
                notifyData(result);
            }
        });
    }

    @Override
    public void notifyData(LoadResult result) {
        for (WeakReference<IDataObserver> observer: observerList)
        {
            if (observer.get()!=null)
                observer.get().onDataChange(result);
        }
    }

    @Override
    public int getPageCount(int page) {
       if (pageMap.containsKey(page))
           return pageMap.get(page);
        return 0;
    }

    @Override
    public int getPicCount() {
        return picList.size();
    }

    @Override
    public Node getPic(int pos) {
        return pos<picList.size()?picList.get(pos):null;
    }

    protected synchronized void addPic(List<String> list)
    {
        int i=0;
        pageMap.put(page,list.size());
        for (String url: list)
        {
            picList.add(new Node(url, page, i++));
        }
    }
}
