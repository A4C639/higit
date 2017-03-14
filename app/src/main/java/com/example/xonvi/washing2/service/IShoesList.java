package com.example.xonvi.washing2.service;

import com.example.xonvi.washing2.Entity.Thing;

import java.util.List;

import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by xonvi on 2017/2/6.
 */

public interface IShoesList {
    @POST("Wash/ShoesList")
    Observable<List<Thing>> shoesList();
}
