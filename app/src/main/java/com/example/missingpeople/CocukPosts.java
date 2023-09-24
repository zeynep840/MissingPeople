package com.example.missingpeople;

public class CocukPosts {
    public String postid,postimage,description,profileimage,publisher,date,time,username;
    public CocukPosts(){

    }
    public CocukPosts(String postid,String date,String time,String publisher,String username,String postimage,String description,String profileimage){
        this.postid=postid;
        this.username=username;

        this.postimage=postimage;
        this.description=description;
        this.profileimage=profileimage;
        this.date=date;
        this.time=time;

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

    public String getPostidid() {
        return postid;
    }

    public void setPostid(String postid) {
        this.postid = postid;
    }
    public  String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }



    public String getPostimage() {
        return postimage;
    }

    public void setPostimage(String postimage) {
        this.postimage = postimage;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


}

