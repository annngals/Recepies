package com.example.recepies;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {

    String API_URL = "https://api.edamam.com/";
    String ID = "7785f9a7";
    String key = "49b6f38bbf46a9addde12dabd41f1971";
    MainActivity mainActivity = this;

    EditText request;
    ListView listView;
    TextView numberOfHits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        request = findViewById(R.id.request);
        listView = findViewById(R.id.listview);
        numberOfHits = findViewById(R.id.hits);
    }

    interface RecepieAPI {
        @GET("search")
        Call<Response> search(@Query("q") String q, @Query("app_id") String ID, @Query("app_key") String key);
        @GET()
        Call<ResponseBody> getImage (@Url String image);
    }

    public void startSearch(String text) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RecepieAPI api = retrofit.create(RecepieAPI.class);

        Retrofit noRetrofit = new Retrofit.Builder().baseUrl(API_URL).build(); //no gson
        final RecepieAPI noApi = noRetrofit.create(RecepieAPI.class);

        Log.d("mytag", "api: "+text+" "+ID+" "+key);
        Call<Response> call = api.search(text, ID, key);
        Callback<Response> callback = new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                Response r = response.body();
                Log.d("mytag", "restonse: "+r);

                numberOfHits.setText("Number of hits: " + r.hits.length);
                MyAdapter adapter = new MyAdapter(mainActivity, r.hits, noApi);
                listView.setAdapter(adapter);
            }
            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast toast = Toast.makeText(getApplicationContext(), "Error loading data", Toast.LENGTH_SHORT);
                toast.show();
                Log.d("mytag", "Error: " + t.getLocalizedMessage());
            }
        };
        call.enqueue(callback);
    }

    public void onSearchClick(View v) {
        startSearch(request.getText().toString());
    }
}