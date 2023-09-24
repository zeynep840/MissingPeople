package com.example.missingpeople;

public class Users {
    public String profileimage2,username;
    public Users(){

    }

    public Users(String profileimage2, String username) {
        this.profileimage2 = profileimage2;
        this.username = username;
    }

    public String getProfileimage2() {
        return profileimage2;
    }

    public void setProfileimage2(String profileimage2) {
        this.profileimage2 = profileimage2;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username2) {
        this.username = username;
    }
}


