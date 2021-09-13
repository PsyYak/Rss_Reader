package com.example.rss_reader;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.example.rss_reader.RSS.Downloader;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import static com.example.rss_reader.RSS.RSSParser.articles;

import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    // http://www.ynet.co.il/Integration/StoryRss550.xml
    private String rss_url = "";
    Dialog addRssDialog;
    Button submit,cancel;
    EditText etUrl;
    FloatingActionButton floatingActionButton;
    ListView listView;
    ImageView backgroundImage;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.rssList);
        backgroundImage = findViewById(R.id.backgroundImage);
        floatingActionButton = findViewById(R.id.bottomAddIcon);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addRssDialog = new Dialog(MainActivity.this);
                addRssDialog.setContentView(R.layout.add_rss_layout);
                addRssDialog.setTitle("Enter RSS URL");
                submit = addRssDialog.findViewById(R.id.rssSubmit_btn);
                cancel = addRssDialog.findViewById(R.id.rssCancel_btn);
                etUrl = addRssDialog.findViewById(R.id.rssET);



                submit.setOnClickListener(l -> {
                    articles.clear();
                    if(!etUrl.getText().toString().isEmpty()){
                      //  rss_url = etUrl.getText().toString();
                        try {
                            URL url = new URL(etUrl.getText().toString());
                            initRss(url.toString());
                            addRssDialog.dismiss();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                            addRssDialog.dismiss();
                        }


                    }else{
                        Toast.makeText(MainActivity.this,"Please enter link",Toast.LENGTH_SHORT).show();
                        etUrl.requestFocus();
                    }
                });
                cancel.setOnClickListener(l2 -> addRssDialog.dismiss());
                addRssDialog.show();
                // Expand the width of dialog to maximum screen width
                Window window = addRssDialog.getWindow();
                // Make sure the given window is not null
                assert window != null;
                window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            }
        });

    }

    private void initRss(String url){

        new Downloader(MainActivity.this,url,listView).execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(articles.size()> 0){
            backgroundImage.setVisibility(View.GONE);
        }else{
            backgroundImage.setVisibility(View.VISIBLE);
        }

    }
}
