package com.example.catpict;

import android.app.Activity;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class FavListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] img;

    public FavListAdapter(Activity context, String[] img) {
        super(context, R.layout.card_listview, img);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.img = img;
        System.out.println(img);
    }


    public View getView(int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.card_listview, null,true);

        ImageView imageView = (ImageView) view.findViewById(R.id.favimage);

        Handler uiHandler = new Handler(Looper.getMainLooper());
        uiHandler.post(new Runnable() {
            @Override
            public void run() {
                Picasso
                        .get()
                        .load(img[position])
                        .into(imageView);
            }
        });
        return rowView;
    };
}