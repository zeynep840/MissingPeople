package com.example.missingpeople;

public class CocukComment {
    public String yorum,date,time,username,profileimage;
    public CocukComment(){


    }

    public CocukComment(String yorum, String date,String profileimage, String time, String username) {
        this.yorum = yorum;
        this.date = date;
        this.time = time;
        this.username = username;
        this.profileimage=profileimage;

    }

    public String getYorum() {
        return yorum;
    }

    public void setYorum(String yorum) {
        this.yorum = yorum;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
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


