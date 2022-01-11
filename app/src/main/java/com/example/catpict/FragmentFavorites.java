package com.example.catpict;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;
import java.util.List;

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

        List<String> img = new ArrayList<String>();
        List<Integer> id = new ArrayList<Integer>();

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
//                              debug logger
                        e.printStackTrace();
                    }

                    try {
//                              get url string
                        JSONArray arrayJSON_RESP = new JSONArray(json);

                        for (int x = 0; x < arrayJSON_RESP.length(); x++) {
                            JSONObject objectJSON_RESP = arrayJSON_RESP.getJSONObject(x);
                            int gid = objectJSON_RESP.getInt("id");
                            String a = objectJSON_RESP.getJSONObject("image").getString("url");
                            id.add(gid);
                            img.add(a);
                        }

                        Handler uiHandler = new Handler(Looper.getMainLooper());
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                FavListAdapter adapter = new FavListAdapter(getActivity(), id, img);
                                ListView list = (ListView) view.findViewById(R.id.listfav);
                                list.setAdapter(adapter);
                            }
                        });

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

    public class FavListAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final List<Integer> id;
        private final List<String> img;

        public FavListAdapter(Activity context, List<Integer> id, List<String> img) {
            super(context, R.layout.card_listview, img);
            // TODO Auto-generated constructor stub

            this.context = context;
            this.id = id;
            this.img = img;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.card_listview, parent, false);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.favimage);

            Picasso.get().load(img.get(position)).into(imageView);

            return rowView;
        }

    }
}

