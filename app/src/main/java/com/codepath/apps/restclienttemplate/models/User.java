package com.codepath.apps.restclienttemplate.models;

import com.codepath.apps.restclienttemplate.MyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.Database;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

@Table(database = MyDatabase.class)
@Parcel
public class User extends BaseModel {
    @Column
    @PrimaryKey
    public int id;

    @Column
    public String name;

    @Column
    public String profilePhotoUrl;

    @Column
    public String userName;

    @Column
    public int followingCount;

    @Column
    public int followerCount;

    @Column
    public String tagLine;

    @Column
    public String coverPhotoUrl;

    public User() {
    }

    public User(JSONObject json) {
        try {
            id = json.getInt("id");
            name = json.getString("name");
            userName = "@" + json.getString("screen_name");
            profilePhotoUrl = json.getString("profile_image_url_https");
            followerCount = json.getInt("followers_count");
            followingCount = json.getInt("friends_count");
            tagLine = json.getString("description");
            coverPhotoUrl = json.getString("profile_background_image_url_https");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
