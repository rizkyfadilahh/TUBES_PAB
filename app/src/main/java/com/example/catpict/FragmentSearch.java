package com.example.catpict;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FragmentSearch extends Fragment {

    String[] order = {"Rand", "Desc", "Asc"};
    String[] breed = {"", "abys", "aege", "abob", "acur", "asho", "awir", "amau", "amis", "bali", "bamb"};
    String[] type = {"", "jpg,png", "gif"};
    String[] category = {"", "5", "15", "1", "14", "2", "4", "7"};


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Spinner spinnerbreed = (Spinner) view.findViewById(R.id.breedspinner);
        Spinner spinnercategory = (Spinner) view.findViewById(R.id.categoryspinner);
        Spinner spinnertype = (Spinner) view.findViewById(R.id.typespinner);

        spinnertype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                OkHttpClient client = new OkHttpClient();

                HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.thecatapi.com/v1/images/search").newBuilder();
                urlBuilder.addQueryParameter("limit", "20");
                urlBuilder.addQueryParameter("order", "Rand");
                urlBuilder.addQueryParameter("mime_types", type[position]);
                String url = urlBuilder.build().toString();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                System.out.println(url);
                List<String> img = new ArrayList<String>();

                client.newCall(request).enqueue(new Callback() {
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
                                    String a = objectJSON_RESP.getString("url");
                                    img.add(a);
                                }
                                System.out.println(img);

                                Handler uiHandler = new Handler(Looper.getMainLooper());
                                uiHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SearchListAdapterGif adapter = new SearchListAdapterGif(getActivity(), img);
                                        ListView list = (ListView) view.findViewById(R.id.searchlist);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinnercategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                OkHttpClient client = new OkHttpClient();

                HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.thecatapi.com/v1/images/search").newBuilder();
                urlBuilder.addQueryParameter("limit", "20");
                urlBuilder.addQueryParameter("order", "Rand");
                urlBuilder.addQueryParameter("category_ids", category[position]);
                String url = urlBuilder.build().toString();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                System.out.println(url);
                List<String> img = new ArrayList<String>();

                client.newCall(request).enqueue(new Callback() {
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
                                    String a = objectJSON_RESP.getString("url");
                                    img.add(a);
                                }
                                System.out.println(img);

                                Handler uiHandler = new Handler(Looper.getMainLooper());
                                uiHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SearchListAdapter adapter = new SearchListAdapter(getActivity(), img);
                                        ListView list = (ListView) view.findViewById(R.id.searchlist);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinnerbreed.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                OkHttpClient client = new OkHttpClient();

                HttpUrl.Builder urlBuilder = HttpUrl.parse("https://api.thecatapi.com/v1/images/search").newBuilder();
                urlBuilder.addQueryParameter("limit", "20");
                urlBuilder.addQueryParameter("order", "Rand");
                urlBuilder.addQueryParameter("breed", breed[position]);
                String url = urlBuilder.build().toString();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                System.out.println(url);
                List<String> img = new ArrayList<String>();

                client.newCall(request).enqueue(new Callback() {
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
                                    String a = objectJSON_RESP.getString("url");
                                    img.add(a);
                                }
                                System.out.println(img);

                                Handler uiHandler = new Handler(Looper.getMainLooper());
                                uiHandler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        SearchListAdapter adapter = new SearchListAdapter(getActivity(), img);
                                        ListView list = (ListView) view.findViewById(R.id.searchlist);
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
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return view;
    }

    public class SearchListAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final List<String> img;

        public SearchListAdapter(Activity context, List<String> img) {
            super(context, R.layout.card_listview, img);
            // TODO Auto-generated constructor stub

            this.context = context;
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

    public class SearchListAdapterGif extends ArrayAdapter<String> {

        private final Activity context;
        private final List<String> img;

        public SearchListAdapterGif(Activity context, List<String> img) {
            super(context, R.layout.card_listview, img);
            // TODO Auto-generated constructor stub

            this.context = context;
            this.img = img;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.card_listview, parent, false);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.favimage);

            Glide.with(context)
                    .load(img.get(position)) // or url
                    .into(imageView);

            return rowView;
        }

    }
}