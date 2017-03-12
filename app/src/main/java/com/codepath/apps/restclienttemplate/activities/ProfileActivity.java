package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.RestApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;
import com.codepath.apps.restclienttemplate.databinding.ActivityProfileBinding;
import com.codepath.apps.restclienttemplate.fragments.UserTimelineTweetFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class ProfileActivity extends AppCompatActivity {

    private ActivityProfileBinding binding;
    private User user;
    private TwitterClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile);


        user = Parcels.unwrap(getIntent().getParcelableExtra("USER"));

        if (user != null) {
            binding.setUser(user);
            showUserTimelineTweet();
        } else {
            client = RestApplication.getRestClient();
            client.getUserCredentials(getUserCredentialsResponseHandler());
        }
    }

    private void showUserTimelineTweet() {
        UserTimelineTweetFragment fragment = UserTimelineTweetFragment.getInstance(user);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_user_timeline, fragment)
                .commit();
    }


    public JsonHttpResponseHandler getUserCredentialsResponseHandler() {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                user = new User(response);
                binding.setUser(user);

                showUserTimelineTweet();
            }
        };
    }
}
