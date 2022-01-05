package com.example.catpict;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FragmentFavorites extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorites, container, false);

        OkHttpClient client = new OkHttpClient();

        String BASE_URL = "https://api.thecatapi.com/v1/";
        Request randomCat = new Request.Builder()
                .url(BASE_URL + "favourites")
                .addHeader("x-api-key", "3164f9ed-553c-4273-833b-4134a190c2aa")
                .build();

        client.newCall(randomCat).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    String json = null;
                    try {
                        json = response.body().string();
                        System.out.println(json);
                    } catch (IOException e) {
//                              debug logger
                        e.printStackTrace();
                    }

                    try {
//                              get url string
                        JSONArray arrayJSON_RESP = new JSONArray(json);

                        int[] id = new int[arrayJSON_RESP.length()];
                        String[] img = new String[arrayJSON_RESP.length()];

                        for (int x=0; x<arrayJSON_RESP.length(); x++) {
                            JSONObject objectJSON_RESP = arrayJSON_RESP.getJSONObject(x);
                            img[x] = objectJSON_RESP.getJSONObject("image").getString("url");
                        }

                        FavListAdapter adapter=new FavListAdapter(getActivity(), img);
                        ListView list = (ListView) view.findViewById(R.id.listfav);
                        list.setAdapter(adapter);

                    } catch (JSONException e) {
//                              debug logger
                        e.printStackTrace();
                    }

                }

            }
        });

        // Inflate the layout for this fragment
        return view;
    }
}