package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.codepath.apps.restclienttemplate.adapters.TweetPagerFragmentAdapter;
import com.codepath.apps.restclienttemplate.fragments.PostTweetDialogFragment;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.utils.EndlessRecyclerViewScrollListener;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity implements PostTweetDialogFragment.PostTweetListener {

    private TwitterClient twitterClient;
    private TweetAdapter adapter;

//    @BindView(R.id.recycler_tweets)
//    RecyclerView rvTweets;
//
//    @BindView(R.id.swipe_layout)
//    SwipeRefreshLayout swipeRefreshLayout;

    @BindView(R.id.pager_tweet)
    ViewPager pagerTweet;

    @BindView(R.id.tab_tweet)
    TabLayout tabTweet;

    PostTweetDialogFragment dialogFragment;
    FragmentManager fragmentManager;
    LinearLayoutManager layoutManager;

    private int currentPage = 0;
    private JsonHttpResponseHandler homeTimelineResponseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        dialogFragment = new PostTweetDialogFragment();
        fragmentManager = getSupportFragmentManager();

        pagerTweet.setAdapter(new TweetPagerFragmentAdapter(getSupportFragmentManager()));
        tabTweet.setupWithViewPager(pagerTweet);

//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                currentPage = 0;
//                adapter.setTweets(new ArrayList<Tweet>());
//                twitterClient.getHomeTimeline(currentPage, getHomeTimelineResponseHandler());
//            }
//        });

//        adapter = new TweetAdapter(this, new ArrayList<Tweet>());
//        adapter.setListener(this);
//        rvTweets.setAdapter(adapter);
//        layoutManager = new LinearLayoutManager(this);
//        rvTweets.setLayoutManager(layoutManager);
//        rvTweets.addOnScrollListener(getOnScrollListener(layoutManager));

//        twitterClient = RestApplication.getRestClient();
//        homeTimelineResponseHandler = getHomeTimelineResponseHandler();
//        twitterClient.getHomeTimeline(currentPage, homeTimelineResponseHandler);
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

            case R.id.menu_user:
                startActivity(new Intent(this, ProfileActivity.class));
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
                Toast.makeText(MainActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
