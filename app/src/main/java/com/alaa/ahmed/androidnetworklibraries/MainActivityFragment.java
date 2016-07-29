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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
// make okHttp cilent
        OkHttpClient okHttpClient=new OkHttpClient();
        Request request= new Request.Builder()
                .url(BASR_URL)
                .build();
        // make the calling
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final List<Flower> flowers=new ArrayList<>();
                try {
                    // parsing json
                    JSONArray jsonArray=new JSONArray(response.body().string());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject itema=jsonArray.getJSONObject(i);
                        String name=itema.getString("name");
                        String photo=itema.getString("photo");
                        String price=itema.getString("price");
                        flowers.add(new Flower(name,photo,price));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                // run code on the main thread(set the adapter)
             getActivity().runOnUiThread(new Runnable() {
                 @Override
                 public void run() {
                     adapt=new Adapt(flowers,getActivity());
                     recycleview.setAdapter(adapt);

                 }
             });
            }
            }

         );


    }

}

