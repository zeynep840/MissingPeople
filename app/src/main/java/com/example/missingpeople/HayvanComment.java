package com.example.missingpeople;

public class HayvanComment {
    public String yorum1,date1,time1,username,profileimage;
    public HayvanComment(){


    }

    public HayvanComment(String yorum1, String date1,String profileimage, String time1, String username) {
        this.yorum1 = yorum1;
        this.date1 = date1;
        this.time1 = time1;
        this.username = username;
        this.profileimage=profileimage;

    }

    public String getYorum1() {
        return yorum1;
    }

    public void setYorum1(String yorum1) {
        this.yorum1 = yorum1;
    }

    public String getDate1() {
        return date1;
    }

    public void setDate1(String date1) {
        this.date1 = date1;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
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


