package com.codepath.apps.restclienttemplate.models;

import com.codepath.apps.restclienttemplate.MyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONException;
import org.json.JSONObject;

//@Table(database = MyDatabase.class)
public class Media extends BaseModel {

  //  @Column @PrimaryKey
    public int id;

    //@Column
    public String mediaUrlHttps;

    public Media() {
        super();
    }

    public Media(JSONObject jsonObject) {
        super();

        try {
            mediaUrlHttps = jsonObject.getString("media_url_https");
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

    public void setMediaUrlHttps(String mediaUrlHttps) {
        this.mediaUrlHttps = mediaUrlHttps;
    }
}
