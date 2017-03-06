package com.codepath.apps.restclienttemplate.models;

import android.text.format.DateUtils;

import com.codepath.apps.restclienttemplate.MyDatabase;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.ForeignKey;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.sql.language.Select;
import com.raizlabs.android.dbflow.structure.BaseModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
 * This is a temporary, sample model that demonstrates the basic structure
 * of a SQLite persisted Model object. Check out the DBFlow wiki for more details:
 * https://github.com/codepath/android_guides/wiki/DBFlow-Guide
 *
 * Note: All models **must extend from** `BaseModel` as shown below.
 * 
 */
@Table(database = MyDatabase.class)
@Parcel
public class Tweet extends BaseModel {

	@PrimaryKey
	@Column
	public long id;

	@Column
	public String createdAt;

	@Column
	public boolean favorited;

	@Column
	public String text;

	@Column
	public int reTweetCount;

	@Column
	public boolean retweeted;

	@Column
	@ForeignKey(saveForeignKeyModel = false)
	public User user;

//	@Column
//	@ForeignKey(saveForeignKeyModel = false)
	public ExtendedEntity extendedEntity;

	public Tweet() {
		super();
	}

	public Tweet(JSONObject object){
		super();

		try {
			id = object.getLong("id");
			createdAt = getRelativeTimeAgo(object.getString("created_at"));
			favorited = object.getBoolean("favorited");
			text = object.getString("text");
			reTweetCount = object.getInt("retweet_count");
			retweeted = object.getBoolean("retweeted");
			user = new User(object.getJSONObject("user"));
			if (object.getJSONObject("extended_entities") != null)
				extendedEntity = new ExtendedEntity(object.getJSONObject("extended_entities"));
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static ArrayList<Tweet> fromJsonArray(JSONArray array) {
		ArrayList<Tweet> tweets = new ArrayList<>();

		for (int i = 0; i < array.length(); i++) {
			try {
				JSONObject object = array.getJSONObject(i);
				tweets.add(new Tweet(object));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return tweets;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getReTweetCount() {
		return reTweetCount;
	}

	public void setReTweetCount(int reTweetCount) {
		this.reTweetCount = reTweetCount;
	}

	public boolean isRetweeted() {
		return retweeted;
	}

	public void setRetweeted(boolean retweeted) {
		this.retweeted = retweeted;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ExtendedEntity getExtendedEntity() {
		return extendedEntity;
	}

	public void setExtendedEntity(ExtendedEntity extendedEntity) {
		this.extendedEntity = extendedEntity;
	}

	public String getRelativeTimeAgo(String rawJsonDate) {
		String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
		SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
		sf.setLenient(true);

		String relativeDate = "";
		try {
			long dateMillis = sf.parse(rawJsonDate).getTime();
			relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
					System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS).toString();
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return relativeDate;
	}

	// Record Finders
	public static Tweet byId(long id) {
		//return new Select().from(Tweet.class).where(SampleModel_Table.id.eq(id)).querySingle();
		return null;
	}

	public static List<Tweet> recentItems() {
		//return new Select().from(Tweet.class).orderBy(SampleModel_Table.id, false).limit(300).queryList();
		return null;
	}
}
