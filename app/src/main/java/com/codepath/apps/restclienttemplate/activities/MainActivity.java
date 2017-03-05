package com.codepath.apps.restclienttemplate.activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.RestApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;
import com.codepath.apps.restclienttemplate.fragments.PostTweetDialogFragment;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.utils.EndlessRecyclerViewScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements PostTweetDialogFragment.PostTweetListener {

    private TwitterClient twitterClient;
    private TweetAdapter adapter;

    @BindView(R.id.recycler_tweets)
    RecyclerView rvTweets;

    PostTweetDialogFragment dialogFragment;
    FragmentManager fragmentManager;
    LinearLayoutManager layoutManager;

    private int page = 0;
    private JsonHttpResponseHandler homeTimelineResponseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        dialogFragment = new PostTweetDialogFragment();
        fragmentManager = getSupportFragmentManager();

        adapter = new TweetAdapter(this, new ArrayList<Tweet>());
        rvTweets.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(layoutManager);
        rvTweets.addOnScrollListener(getOnScrollListener(layoutManager));

        twitterClient = RestApplication.getRestClient();
        homeTimelineResponseHandler = getHomeTimelineResponseHandler();
        twitterClient.getHomeTimeline(page, homeTimelineResponseHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_tweet:

                dialogFragment.show(fragmentManager, "Post tweet fragment");
                return true;
        }

        return false;
    }

    @Override
    public void onPostTweet(final String tweetBody) {
        twitterClient.postTweet(tweetBody, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                Tweet postedTweet = new Tweet(response);
                adapter.addTweet(postedTweet, false);
                dialogFragment.dismiss();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public JsonHttpResponseHandler getHomeTimelineResponseHandler() {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                ArrayList<Tweet> tweets = Tweet.fromJsonArray(json);
                adapter.addTweets(tweets);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        };
    }

    public EndlessRecyclerViewScrollListener getOnScrollListener(LinearLayoutManager layoutManager) {
        return new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                Toast.makeText(MainActivity.this, "" + page + 1, Toast.LENGTH_SHORT).show();
                twitterClient.getHomeTimeline(++page, homeTimelineResponseHandler);
            }
        };
    }
}
