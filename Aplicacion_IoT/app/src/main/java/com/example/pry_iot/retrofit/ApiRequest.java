package com.example.pry_iot.retrofit;

import com.example.pry_iot.model.Reguser;
import com.example.pry_iot.response.ReguserResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiRequest {
    @POST("/api/registry/user")
    Call<ReguserResponse> registerUser(@Body Reguser reguser);
}
