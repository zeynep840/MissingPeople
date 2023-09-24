package com.example.missingpeople;

public class EsyaPosts {
    public String esya_postid,esya_postimage,esya_description,profileimage,esya_publisher,esya_date,esya_time,username;
    public EsyaPosts(){

    }
    public EsyaPosts(String esya_postid,String esya_date,String esya_time,String esya_publisher,String username,String esya_postimage,String esya_description,String profileimage){
        this.esya_postid=esya_postid;
        this.username=username;

        this.esya_postimage=esya_postimage;
        this.esya_description=esya_description;
        this.profileimage=profileimage;
        this.esya_date=esya_date;
        this.esya_time=esya_time;

    }
    public String getEsya_date() {
        return esya_date;
    }

    public void setEsya_date(String esya_date) {
        this.esya_date = esya_date;
    }
    public String getEsya_time() {
        return esya_time;
    }

    public void setEsya_time(String esya_time) {
        this.esya_time = esya_time;
    }

    public String getEsya_postid() {
        return esya_postid;
    }

    public void setEsya_postid(String esya_postid) {
        this.esya_postid = esya_postid;
    }
    public  String getUsername(){
        return username;
    }
    public void setUsername2(String username){
        this.username=username;
    }


    public String getEsya_publisher() {
        return esya_publisher;
    }

    public void setEsya_publisher(String esya_publisher) {
        this.esya_publisher = esya_publisher;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEsya_postimage() {
        return esya_postimage;
    }

    public void setEsya_postimage(String esya_postimage) {
        this.esya_postimage = esya_postimage;
    }

    public String getProfileimage() {
        return profileimage;
    }

    public void setProfileimage(String profileimage) {
        this.profileimage = profileimage;
    }


    public String getEsya_description() {
        return esya_description;
    }

    public void setEsya_description(String esya_description) {
        this.esya_description = esya_description;
    }


}

