package com.example.catpict;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentVote extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vote, container, false);
        ImageView imageView = (ImageView) view.findViewById(R.id.catimage);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OkHttpClient client = new OkHttpClient();

                String BASE_URL = "https://api.thecatapi.com/v1/";
                Request randomCat = new Request.Builder()
                        .url(BASE_URL + "image/search")
                        .addHeader("x-api-key","3164f9ed-553c-4273-833b-4134a190c2aa")
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
                            } catch (IOException e) {
//                                debug logger
                                e.printStackTrace();
                            }
                            try {
//                                get url string
                                JSONArray arrayJSON_RESP = new JSONArray(json);
                                JSONObject objectJSON_RESP = arrayJSON_RESP.getJSONObject(0);
                                String url = objectJSON_RESP.getString("url");

//                                TextView textView = getView().findViewById(R.id.RandomCatTextJSON);
//                                textView.setText(url);

                                ImageView imageView = (ImageView) view.findViewById(R.id.catimage);
                                Picasso.get().load(url).into(imageView);

                            } catch (JSONException e) {
//                                debug logger
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }
        });





        // Inflate the layout for this fragment
        return view;

    }
}