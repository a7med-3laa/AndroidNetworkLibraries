package com.alaa.ahmed.androidnetworklibraries.Network;

import com.alaa.ahmed.androidnetworklibraries.Model.Flower;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Declare endpoints, http and callback type and paramters
 */

public interface FlowersInterface {

     String BASE_URL="http://services.hanselandpetal.com/";
    @GET("feeds/flowers.json")
    Call<List<Flower>> getposts();
}
