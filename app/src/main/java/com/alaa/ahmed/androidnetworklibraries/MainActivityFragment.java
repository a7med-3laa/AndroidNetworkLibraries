package com.alaa.ahmed.androidnetworklibraries;

import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alaa.ahmed.androidnetworklibraries.Adapter.Adapt;
import com.alaa.ahmed.androidnetworklibraries.Model.Flower;
import com.alaa.ahmed.androidnetworklibraries.Network.FlowersInterface;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
/*
* when we use ION we dont need to Has AsyncTask since ion make it own Thread
* */

public class MainActivityFragment extends Fragment {
    View rootView;
    RecyclerView recycleview;
    Adapt adapt;
    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fragment_main, container, false);
    return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    recycleview=(RecyclerView)rootView.findViewById(R.id.list);
        recycleview.setLayoutManager(new LinearLayoutManager(getActivity()));
            // make Retrofit Adapter
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(FlowersInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) // use Gson Adapter
                .build();
        FlowersInterface flowersInterface=retrofit.create(FlowersInterface.class);
            // make callback mechanism
        final Call<List<Flower>> flowers=flowersInterface.getposts();

        flowers.enqueue(new Callback<List<Flower>>() {
            @Override
            public void onResponse(Call<List<Flower>> call, Response<List<Flower>> response) {
                      List<Flower> flo =response.body();// get Flower Data

                           adapt=new Adapt(flo,getActivity());
                            recycleview.setAdapter(adapt);
            }

            @Override
            public void onFailure(Call<List<Flower>> call, Throwable t) {

            }
        });
    }


    }

