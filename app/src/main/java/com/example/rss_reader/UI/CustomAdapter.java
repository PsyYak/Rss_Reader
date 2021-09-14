package com.example.rss_reader.UI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rss_reader.Data.Article;
import com.example.rss_reader.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Article> articles;


    public CustomAdapter(Context context, ArrayList<Article> articles) {
        this.context = context;
        this.articles = articles;
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    @Override
    public Object getItem(int position) {
        return articles.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView = LayoutInflater.from(context).inflate(R.layout.rss_model,parent,false);
        }

        // init ui vars
        TextView tvTitle = convertView.findViewById(R.id.rssTitle);
        TextView tvDesc = convertView.findViewById(R.id.rssDescription);
        TextView tvDate = convertView.findViewById(R.id.rssDate);
        ImageView imgView = convertView.findViewById(R.id.rssImage);

        // init vars
        Article article = (Article) this.getItem(position);
        String title = article.getTitle();
        String description = article.getDesc();
        String date = article.getDate();
        tvDesc.setText(description);
          if(!article.getImageUrl().isEmpty() || !article.getImageUrl().equals("")) {
            String imageUrl = article.getImageUrl().replace("localhost", "10.0.2.2");
            PicassoClient.downloadImage(context,imageUrl,imgView);
            tvDesc.setText(description.substring(2));
        }

        // init ui data
        tvTitle.setText(title);

        @SuppressLint("SimpleDateFormat") DateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy");
        try {
            Date dateTest = formatter.parse(date);
            assert dateTest != null;
            String strDate = formatter.format(dateTest);
            tvDate.setText(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
       // tvDate.setText(date);
        notifyDataSetChanged();


       convertView.setOnClickListener(v -> {
           if(article.getLink() == null){
               Toast.makeText(context,"No link available ",Toast.LENGTH_SHORT).show();
           }else {
               Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(article.getLink()));
               context.startActivity(browserIntent);
           }

       });

        return convertView;
    }

}
