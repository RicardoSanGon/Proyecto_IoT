package com.example.pry_iot.retrofit;

import static com.example.pry_iot.constants.AppConstants.BASE_URL;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitRequest {
    private static Retrofit retrofit;
    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl("https://dodo-loving-likely.ngrok-free.app}")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        } return retrofit;
    }
}
