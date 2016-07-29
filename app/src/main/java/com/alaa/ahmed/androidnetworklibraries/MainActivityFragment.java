package com.alaa.ahmed.androidnetworklibraries;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alaa.ahmed.androidnetworklibraries.Adapter.Adapt;
import com.alaa.ahmed.androidnetworklibraries.Model.Flower;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
/*
* when we use Third libraries we dont need to Has AsyncTask since ion make it own Thread
* */

public class MainActivityFragment extends Fragment {
    View rootView;
    RecyclerView recycleview;
    Adapt adapt;
    private static final String BASR_URL="http://services.hanselandpetal.com/feeds/flowers.json";

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
        // make Json Array Request
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(BASR_URL, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<Flower> flowers=new ArrayList<>();
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject itema = response.getJSONObject(i);
                        String name = itema.getString("name");
                        String photo = itema.getString("photo");
                        String price = itema.getString("price");
                        flowers.add(new Flower(name,photo,price));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                adapt=new Adapt(flowers,getActivity());
                recycleview.setAdapter(adapt);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Volley ERRROR:",error.getMessage());
            }
        }
        );
        Volley.newRequestQueue(getActivity()).add(jsonArrayRequest);
    }
}

