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

public class FragmentBreeds extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_breeds, container, false);

        OkHttpClient client = new OkHttpClient();

        String BASE_URL = "https://api.thecatapi.com/v1/";
        Request randomCat = new Request.Builder()
                .url(BASE_URL + "breeds")
                .addHeader("x-api-key", "3164f9ed-553c-4273-833b-4134a190c2aa")
                .build();

        List<String> img = new ArrayList<String>();
        List<String> name = new ArrayList<String>();
        List<String> desc = new ArrayList<String>();
        List<String> temperament = new ArrayList<String>();

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

                            if (objectJSON_RESP.has("image")) {
                                JSONObject image = objectJSON_RESP.getJSONObject("image");
                                if (image.has("url")) {
                                    String a = objectJSON_RESP.getJSONObject("image").getString("url");
                                    img.add(a);

                                    name.add(objectJSON_RESP.getString("name"));
                                    desc.add(objectJSON_RESP.getString("description"));
                                    temperament.add(objectJSON_RESP.getString("temperament"));
                                }
                            }

                        }


                        Handler uiHandler = new Handler(Looper.getMainLooper());
                        uiHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                BreedsListAdapter adapter = new BreedsListAdapter(getActivity(), img, name, desc, temperament);
                                ListView list = (ListView) view.findViewById(R.id.listbreeds);
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

    public class BreedsListAdapter extends ArrayAdapter<String> {

        private final Activity context;
        private final List<String> img;
        private final List<String> name;
        private final List<String> desc;
        private final List<String> temperament;


        public BreedsListAdapter(Activity context, List<String> img, List<String> name, List<String> desc, List<String> temperament) {
            super(context, R.layout.card_listview, img);
            // TODO Auto-generated constructor stub

            this.context = context;
            this.name = name;
            this.desc = desc;
            this.temperament = temperament;
            this.img = img;
        }

        @Override
        public View getView(int position, View view, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View rowView = inflater.inflate(R.layout.card_detail, parent, false);

            ImageView imageView = (ImageView) rowView.findViewById(R.id.catimg);
            TextView tname = (TextView) rowView.findViewById(R.id.textname);
            TextView tdesc = (TextView) rowView.findViewById(R.id.textdesc);
            TextView ttemperament = (TextView) rowView.findViewById(R.id.texttemperament);


            Picasso.get().load(img.get(position)).into(imageView);
            tname.setText(name.get(position));
            tdesc.setText(desc.get(position));
            ttemperament.setText(temperament.get(position));

            return rowView;
        }

    }
}