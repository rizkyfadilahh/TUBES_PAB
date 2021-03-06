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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttp;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FragmentVote extends Fragment {

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vote, container, false);
        Button nextcat = (Button) view.findViewById(R.id.buttonnext);

        nextcat.post(new Runnable() {
            @Override
            public void run() {
                nextcat.performClick();
            }
        });

        nextcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OkHttpClient client = new OkHttpClient();

                String BASE_URL = "https://api.thecatapi.com/v1/";
                Request randomCat = new Request.Builder()
                        .url(BASE_URL + "images/search")
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
                                JSONObject objectJSON_RESP = arrayJSON_RESP.getJSONObject(0);
                                String url = objectJSON_RESP.getString("url");
                                String imgid = objectJSON_RESP.getString("id");

//                              TextView textView = getView().findViewById(R.id.RandomCatTextJSON);
//                              textView.setText(url);

                                ImageView imageView = (ImageView) view.findViewById(R.id.catimage);

                                Handler uiHandler = new Handler(Looper.getMainLooper());
                                uiHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Picasso
                                                .get()
                                                .load(url)
                                                .into(imageView);
                                    }
                                });

                                Button favbtn = (Button) view.findViewById(R.id.buttonfav);

                                favbtn.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        OkHttpClient client = new OkHttpClient();

                                        String bodyPostJSON = "{\"image_id\":\"" + imgid + "\",\"sub_id\":\"asd123zxc\"}";
                                        RequestBody body = RequestBody.create(
                                                MediaType.parse("application/json"), bodyPostJSON);

                                        Request sendfav = new Request.Builder()
                                                .url(BASE_URL + "favourites")
                                                .addHeader("x-api-key", "3164f9ed-553c-4273-833b-4134a190c2aa")
                                                .post(body)
                                                .build();

                                        client.newCall(sendfav).enqueue(new Callback() {
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
                                                        Snackbar.make(view, "My Favorite <3", Snackbar.LENGTH_SHORT)
                                                                .show();

                                                        nextcat.post(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                nextcat.performClick();
                                                            }
                                                        });

                                                    } catch (IOException e) {
                                                        e.printStackTrace();
                                                        Snackbar.make(view, "Ooops! Something Wrong :(", Snackbar.LENGTH_SHORT)
                                                                .show();
                                                    }
                                                }
                                            }
                                        });
                                    }
                                });

                            } catch (JSONException e) {
//                              debug logger
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