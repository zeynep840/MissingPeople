package com.example.missingpeople;

public class YetiskinComment {
    public String yorum2,date2,time2,username,profileimage;
    public YetiskinComment(){


    }

    public YetiskinComment(String yorum2, String date2,String profileimage, String time2, String username) {
        this.yorum2 = yorum2;
        this.date2 = date2;
        this.time2 = time2;
        this.username = username;
        this.profileimage=profileimage;

    }

    public String getYorum2() {
        return yorum2;
    }

    public void setYorum2(String yorum2) {
        this.yorum2 = yorum2;
    }

    public String getDate2() {
        return date2;
    }

    public void setDate2(String date2) {
        this.date2 = date2;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getProfileimage(){
        return profileimage;
    }
    public  void setProfileimage(String profileimage){
        this.profileimage=profileimage;
    }
}


