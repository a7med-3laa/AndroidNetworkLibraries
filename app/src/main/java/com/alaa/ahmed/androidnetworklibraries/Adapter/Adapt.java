package com.alaa.ahmed.androidnetworklibraries.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alaa.ahmed.androidnetworklibraries.Model.Flower;
import com.alaa.ahmed.androidnetworklibraries.R;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ahmed on 7/28/2016.
 */
public class Adapt extends RecyclerView.Adapter<Adapt.DataHolder> {
   List<Flower> flowers=new ArrayList<>();
private final String IMAGES_BASE_URL="http://services.hanselandpetal.com/photos/";
    Context context;

    public Adapt(List<Flower> flowers, Context context) {
        this.flowers = flowers;
        this.context = context;
    }

    @Override
    public DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

              View v=  LayoutInflater.from(context).inflate(R.layout.item2,parent,false);
  return   new DataHolder(v);
    }

    @Override
    public void onBindViewHolder(DataHolder holder, int position) {
        holder.name.setText(flowers.get(position).getName());
        holder.price.setText(flowers.get(position).getPrice());
//   we will use ION instead of Picasso
//        Picasso.with(context).load(IMAGES_BASE_URL+flowers.get(position).getPhoto()).into(holder.imageView);
        Ion.with(context)
                .load(IMAGES_BASE_URL+flowers.get(position).getPhoto()).withBitmap()
                .placeholder(R.drawable.load_image)
                .error(R.drawable.error_image)
                .intoImageView(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return flowers.size();
    }

    class DataHolder extends RecyclerView.ViewHolder{
        TextView name,price;
        ImageView imageView;
           public DataHolder(View itemView) {
            super(itemView);
        name=(TextView)itemView.findViewById(R.id.name);
               price=(TextView)itemView.findViewById(R.id.price);
          imageView=(ImageView)itemView.findViewById(R.id.image);
           }

    }
}
