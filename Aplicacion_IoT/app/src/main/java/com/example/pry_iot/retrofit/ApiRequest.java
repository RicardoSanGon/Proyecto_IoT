package com.example.pry_iot.retrofit;

import com.example.pry_iot.model.Historial_paquete;
import com.example.pry_iot.model.Regpaq;
import com.example.pry_iot.model.Reguser;
import com.example.pry_iot.response.FocoResponse;
import com.example.pry_iot.response.HistorialResponse;
import com.example.pry_iot.response.LogoutResponse;
import com.example.pry_iot.response.PaqueteResponse;
import com.example.pry_iot.response.RegpaqResponse;
import com.example.pry_iot.response.ReguserResponse;
import com.example.pry_iot.response.UpdatePackResponse;
import com.example.pry_iot.response.UserResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiRequest {
    //Info sensores
    @GET("info/pack/sensor")
    Call<PaqueteResponse> getPaquetes(@Header("Authorization") String token);
    //Info User
    @FormUrlEncoded
    @POST("login/user")
    Call<UserResponse> login(
            @Field("email") String email,
            @Field("password") String password
    );
    @GET("info/pack/historial/{id}")
    Call<HistorialResponse> getHistorial(@Header("Authorization") String token, @Path("id") int id);
    @POST("registry/user")
    Call<ReguserResponse> registerUser(@Body Reguser reguser);
    //CREAR PAQUETES
    @POST("create/pack")
    Call<RegpaqResponse> registrarPaquete(@Header("Authorization") String token, @Body Regpaq regpaq);

    @PUT("update/pack/{id}")
    Call<UpdatePackResponse> getUpdatePack(@Header("Authorization") String token, @Path("id") int id);

    @POST("logout/user")
    Call<LogoutResponse> cerrarSesion(@Header("Authorization") String token,@Query("email") String correo);


    @FormUrlEncoded
    @POST("led/update")
    Call<FocoResponse> encenderFoco(@Header("Authorization") String token, @Field("estado") int foco);

}

