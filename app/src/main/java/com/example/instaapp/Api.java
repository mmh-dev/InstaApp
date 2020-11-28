package com.example.instaapp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @Headers({
            "X-Parse-Application-Id: bgRApEpKt4SPG27UReRxg7GNuLWLLDMVBk6v8K7H",
            "X-Parse-REST-API-Key: EszUbfJvz1dKSGL7Jecm43ZT68Eyeroj2oKAmhtv"
    })

    @POST("/users")
    Call<User> createUser (
            @Header("X-Parse-Revocable-Session") int session,
            @Body User user);

    @GET("/login")
    Call<User> loggingIn (
            @Header("X-Parse-Revocable-Session") int session,
            @Query("username") String username,
            @Query("password") String password);
}
