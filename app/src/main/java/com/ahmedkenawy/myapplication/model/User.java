package com.ahmedkenawy.myapplication.model;

public class User {
    private String userid;
    private String Name;
    private String ImageProfile;
    private String status;
    private String search;
    public User(String userid, String name, String ImageProfile,String status,String search) {
        this.userid = userid;
        this.Name = name;
        this.ImageProfile = ImageProfile;
        this.status=status;
        this.search=search;
    }

    public User() {
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getImageProfile() {
        return ImageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.ImageProfile = imageProfile;
    }
}
