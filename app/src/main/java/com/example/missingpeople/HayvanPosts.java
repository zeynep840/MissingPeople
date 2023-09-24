package com.example.missingpeople;

public class HayvanPosts {
    public String postid2,postimage2,description2,profileimage,publisher2,date2,time2,username;
    public HayvanPosts(){

    }
    public HayvanPosts(String postid2,String date2,String time2,String publisher2,String username,String postimage2,String description2,String profileimage){
        this.postid2=postid2;
        this.username=username;

        this.postimage2=postimage2;
        this.description2=description2;
        this.profileimage=profileimage;
        this.date2=date2;
        this.time2=time2;

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

    public String getPostid2() {
        return postid2;
    }

    public void setPostid2(String postid2) {
        this.postid2 = postid2;
    }
    public  String getUsername(){
        return username;
    }
    public void setUsername2(String username){
        this.username=username;
    }


    public String getPublisher2() {
        return publisher2;
    }

    public void setPublisher2(String publisher2) {
        this.publisher2 = publisher2;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPostimage2() {
        return postimage2;
    }

    public void setPostimage2(String postimage2) {
        this.postimage2 = postimage2;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }


    public String getDescription2() {
        return description2;
    }

    public void setDescription2(String description2) {
        this.description2 = description2;
    }


}

