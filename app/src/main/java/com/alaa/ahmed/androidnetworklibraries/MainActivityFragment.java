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
// excute AsyncTask
 new FetchData().execute("http://services.hanselandpetal.com/feeds/flowers.json");
  recycleview.setAdapter(adapt);
    }

    class FetchData extends AsyncTask<String,Void,List<Flower>>{
     // get Json Data
        URL url;
        BufferedReader bufferedReader;
StringBuilder stringBuilder=new StringBuilder();
        HttpURLConnection httpURLConnection;
        @Override
        protected List<Flower> doInBackground(String... params) {
            try {

                url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String temp;
                while((temp=bufferedReader.readLine())!=null)
                    stringBuilder.append(temp);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

                // call json parser function ," it's implementation at the end of the class"
            return jsonParser(stringBuilder.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(List<Flower> flowers) {
            super.onPostExecute(flowers);
            adapt=new Adapt(flowers,getActivity());
            recycleview.setAdapter(adapt);
        }

        // PARSING JSON DATA INTO JAVA OBJECTS
        private List<Flower> jsonParser(String url){
       List<Flower> flowers=new ArrayList<>();
            try {
                JSONArray jsonArray=new JSONArray(url);
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


            return flowers;}


    }
}
