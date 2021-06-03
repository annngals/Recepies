package com.example.recepies;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;

public class MyAdapter extends BaseAdapter {

    Context context;
    Hit[] hits;
    MainActivity.RecepieAPI api;

    public MyAdapter(Context context, Hit[] hits, MainActivity.RecepieAPI api) {
        this.context = context;
        this.hits = hits;
        this.api = api;
    }

    @Override
    public int getCount() {
        return hits.length;
    }

    @Override
    public Object getItem(int i) {
        return hits[i];
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        v = LayoutInflater.from(context).inflate(R.layout.item, viewGroup, false);
        TextView textView = v.findViewById(R.id.img_text);

        Hit hit = hits[i];
        textView.setText("ID: " + hit.id + "\nLabel:" + hit.label);

        return v;
    }

    public void downloadImage(MainActivity.RecepieAPI api, Hit hit) {
        Call<ResponseBody> getImage = api.getImage(hit.previewURL);

        Callback<ResponseBody> imageCallback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("mytag", "Response error: " + t.getLocalizedMessage());
            }
        };
        getImage.enqueue(imageCallback);
    }
}