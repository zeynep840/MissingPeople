package com.example.missingpeople;

public class EsyaComment {
    public String esya_yorum1,esya_date1,esya_time1,username,profileimage;
    public EsyaComment(){


    }

    public EsyaComment(String esya_yorum1, String esya_date1,String profileimage, String esya_time1, String username) {
        this.esya_yorum1 = esya_yorum1;
        this.esya_date1 = esya_date1;
        this.esya_time1 = esya_time1;
        this.username = username;
        this.profileimage=profileimage;

    }

    public String getEsya_yorum1() {
        return esya_yorum1;
    }

    public void setEsya_yorum1(String esya_yorum1) {
        this.esya_yorum1 = esya_yorum1;
    }

    public String getEsya_date1() {
        return esya_date1;
    }

    public void setEsya_date1(String esya_date1) {
        this.esya_date1 = esya_date1;
    }

    public String getEsya_time1() {
        return esya_time1;
    }

    public void setEsya_time1(String esya_time1) {
        this.esya_time1 = esya_time1;
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


