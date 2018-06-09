package com.my.mysafe.api;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ImageService {
    @GET("images/{id}")
    Observable<List<String>> getImage(@Path("id") int id);
}
