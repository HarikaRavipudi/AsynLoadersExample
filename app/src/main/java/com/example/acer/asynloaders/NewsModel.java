package com.example.acer.asynloaders;



public class NewsModel {
    String title;
    String imgurl;


    public NewsModel(String title, String imgurl) {
        this.title = title;
        this.imgurl = imgurl;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }
}
