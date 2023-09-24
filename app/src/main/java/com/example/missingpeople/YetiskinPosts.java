package com.example.missingpeople;

public class YetiskinPosts {
    public String postid3,postimage3,description3,profileimage,publisher3,date3,time3,username;
    public YetiskinPosts(){

    }
    public YetiskinPosts(String postid3,String date3,String time3,String publisher3,String username,String postimage3,String description2,String profileimage){
        this.postid3=postid3;
        this.username=username;

        this.postimage3=postimage3;
        this.description3=description3;
        this.profileimage=profileimage;
        this.date3=date3;
        this.time3=time3;

    }
    public String getDate3() {
        return date3;
    }

    public void setDate3(String date3) {
        this.date3 = date3;
    }
    public String getTime3() {
        return time3;
    }

    public void setTime3(String time3) {
        this.time3 = time3;
    }

    public String getPostid3() {
        return postid3;
    }

    public void setPostid3(String postid3) {
        this.postid3 = postid3;
    }
    public  String getUsername(){
        return username;
    }
    public void setUsername(String username){
        this.username=username;
    }



    public String getPostimage3() {
        return postimage3;
    }

    public void setPostimage3(String postimage3) {
        this.postimage3 = postimage3;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }


    public String getDescription3() {
        return description3;
    }

    public void setDescription3(String description3) {
        this.description3 = description3;
    }

}
