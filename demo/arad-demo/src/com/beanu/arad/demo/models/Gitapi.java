package com.beanu.arad.demo.models;

import com.beanu.arad.demo.models.data.User;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by Beanu on 15/12/17.
 */
public interface Gitapi {

    @GET("/users/{user}")
    Observable<User> getFeed(@Path("user") String user);


}
