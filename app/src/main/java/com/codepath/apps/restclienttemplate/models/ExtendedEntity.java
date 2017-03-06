package com.codepath.apps.restclienttemplate.models;

import com.codepath.apps.restclienttemplate.MyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

//@Table(database = MyDatabase.class)
@Parcel
public class ExtendedEntity extends BaseModel {

    //@Column @PrimaryKey
    public int Id;

    //@Column
    public ArrayList<Media> media;

    public ExtendedEntity() {
        super();
    }

    public ExtendedEntity(JSONObject json) {
        media = new ArrayList<Media>();

        try {
            JSONArray array = json.getJSONArray("media");
            for (int i = 0; i < array.length(); i++) {
                media.add(new Media(array.getJSONObject(i)));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public ArrayList<Media> getMedia() {
        return media;
    }

    public void setMedia(ArrayList<Media> media) {
        this.media = media;
    }
}
