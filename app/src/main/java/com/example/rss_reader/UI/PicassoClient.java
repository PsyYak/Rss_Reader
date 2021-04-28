package com.example.rss_reader.UI;

import android.content.Context;
import android.widget.ImageView;

import com.example.rss_reader.R;
import com.squareup.picasso.Picasso;

public class PicassoClient {

    public static void downloadImage(Context context, String imageUrl, ImageView img){

        if(imageUrl!=null && imageUrl.length() > 0){
            Picasso.with(context).load(imageUrl).placeholder(R.drawable.no_image_icon).into(img);
        }else{
            Picasso.with(context).load(R.drawable.no_image_icon).into(img);
        }

    }
}
