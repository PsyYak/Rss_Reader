package com.example.rss_reader.RSS;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Html;
import android.widget.ListView;
import android.widget.Toast;

import com.example.rss_reader.Data.Article;
import com.example.rss_reader.UI.CustomAdapter;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

@SuppressLint("StaticFieldLeak")
public class RSSParser extends AsyncTask<Void,Void,Boolean> {


    Context context;
    InputStream inputStream;
    ListView listView;

    ProgressDialog progressDialog;
    public static ArrayList<Article> articles = new ArrayList<>();

    public RSSParser(Context context, InputStream inputStream, ListView listView) {
        this.context = context;
        this.inputStream = inputStream;
        this.listView = listView;

    }

    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog = new ProgressDialog(context);
        progressDialog.setTitle("Parse Data");
        progressDialog.setMessage("Parsing.. please wait");
        progressDialog.show();

    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return this.parseRSS();
    }

    @Override
    protected void onPostExecute(Boolean isParsed) {
        super.onPostExecute(isParsed);
        progressDialog.dismiss();
        if(isParsed){
            // BIND
            listView.setAdapter(new CustomAdapter(context,articles));
        }else{
            Toast.makeText(context,"Unable to Parse",Toast.LENGTH_SHORT).show();
        }
    }


    private Boolean parseRSS(){

        try{
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();

            parser.setInput(inputStream,null);
            int event=parser.getEventType();

            String tagValue = null;
            boolean isSiteMeta=true;

            articles.clear();
            Article article = new Article();

            do{

                String tagName = parser.getName();
                switch(event){

                    case XmlPullParser.START_TAG:
                        if(tagName.equalsIgnoreCase("item")){
                            article=new Article();
                            isSiteMeta=false;
                        }
                        break;
                    case XmlPullParser.TEXT:
                        tagValue = parser.getText();

                        break;
                    case XmlPullParser.END_TAG:
                        if(!isSiteMeta){
                            if(tagName.equalsIgnoreCase("title")){
                                article.setTitle(tagValue);
                            }else if(tagName.equalsIgnoreCase("description")){
                                String desc = tagValue;
                                // Extract Image from description
                                String srcExtract = desc.substring(desc.indexOf("src=")+5);
                                String imageUrl = srcExtract.substring(0,srcExtract.indexOf("jpg")+3);
                                article.setImageUrl(imageUrl);
                                // Remove any HTML tags from feed
                                  article.setDesc(String.valueOf(Html.fromHtml(Html.fromHtml(desc).toString())));




                            }else if(tagName.equalsIgnoreCase("pubDate")){
                                article.setDate(tagValue);
                            }else if(tagName.equalsIgnoreCase("link"))
                                article.setLink(tagValue);
                        }
                        if(tagName.equalsIgnoreCase("item")){
                            // Get only this year RSS feed ( you can also change it to full date as to only show last month RSS )
                            Date date = new Date();
                            int year = date.getYear()+1900; // ***To get current year add 1900 to the value of year obtained from this date object***
                            if(article.getDate().contains(String.valueOf(year)))
                            articles.add(article);
                            isSiteMeta=true;
                        }
                        break;


                }
                event=parser.next();


            }while(event!= XmlPullParser.END_DOCUMENT);

        return  true;

        }catch (XmlPullParserException e) {
            e.printStackTrace();
        }catch(IOException e){
            e.printStackTrace();
        }

        return false;
    }
}
