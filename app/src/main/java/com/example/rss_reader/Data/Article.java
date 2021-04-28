package com.example.rss_reader.Data;

public class Article {

    String title,desc,date,imageUrl,link;

    // Empty Constructor
    public Article(){}

    // Define Constructor
    public Article(String title, String desc, String date, String imageUrl,String link) {
        this.title = title;
        this.desc = desc;
        this.date = date;
        this.imageUrl = imageUrl;
        this.link = link;
    }

    // Getters / Setters


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
