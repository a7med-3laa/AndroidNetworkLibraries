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
    private static final String BASE_URL="http://services.hanselandpetal.com/feeds/flowers.json";
List<Flower> flowers=new ArrayList<>(); // list to store data form ION , and pass it into Adapter
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
// Start Ion
        Ion.with(getActivity())
                .load(BASE_URL) //load json URL
                .asJsonArray(). //retrive response as Json Array (due to json format)
                setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {
                        //if there is any error
                        if(e==null)
                          {
                     JsonObject jsonObject;
                     for (int i = 0; i < result.size(); i++) {
                         jsonObject = result.get(i).getAsJsonObject();
                         String name = jsonObject.get("name").getAsString(); // get name of flower
                         String photo = jsonObject.get("photo").getAsString(); // get photo of flower
                         String price = jsonObject.get("price").getAsString(); //get price of flower
                         flowers.add(new Flower(name, photo, price)); // add flower object to list

                     }
                     adapt = new Adapt(flowers, getActivity());
                     recycleview.setAdapter(adapt);
                 }
                    }

                });


    }


    }

