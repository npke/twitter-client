package com.codepath.apps.restclienttemplate.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.RestApplication;
import com.codepath.apps.restclienttemplate.TwitterClient;
import com.codepath.apps.restclienttemplate.activities.TweetDetailActivity;
import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;
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

public abstract class TweetFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, TweetAdapter.TweetClickListener {

    protected TwitterClient twitterClient;
    protected TweetAdapter adapter;
    protected JsonHttpResponseHandler tweetsResponseHandler;
    protected EndlessRecyclerViewScrollListener scrollListener;
    protected LinearLayoutManager layoutManager;

    int currentPage = 0;

    @BindView(R.id.recycler_tweets)
    RecyclerView rvTweets;

    @BindView(R.id.swipe_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tweet, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ButterKnife.bind(this, view);

        swipeRefreshLayout.setOnRefreshListener(this);

        adapter = new TweetAdapter(getContext(), new ArrayList<Tweet>());
        adapter.setListener(this);
        rvTweets.setAdapter(adapter);
        layoutManager = new LinearLayoutManager(getContext());
        rvTweets.setLayoutManager(layoutManager);
//        rvTweets.setNestedScrollingEnabled(false);

        scrollListener = getOnScrollListener();
        rvTweets.addOnScrollListener(scrollListener);

        tweetsResponseHandler = getTweetsResponseHandler();

        twitterClient = RestApplication.getRestClient();
        getTweets(currentPage);
    }

    protected abstract void getTweets(int currentPage);

    public JsonHttpResponseHandler getTweetsResponseHandler() {
        return new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                ArrayList<Tweet> tweets = Tweet.fromJsonArray(json);
                if (currentPage == 0)
                    adapter.setTweets(tweets);
                else adapter.addTweets(tweets);

                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                swipeRefreshLayout.setRefreshing(false);
                Toast.makeText(getContext(), throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };
    }

    public EndlessRecyclerViewScrollListener getOnScrollListener() {
        return new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                getTweets(++currentPage);
            }
        };
    }

    @Override
    public void onRefresh() {
        currentPage = 0;
        adapter.setTweets(new ArrayList<Tweet>());
        getTweets(currentPage);
    }

    @Override
    public void onClick(Tweet tweet) {
        Intent intent = new Intent(getContext(), TweetDetailActivity.class);
        intent.putExtra("TWEET", Parcels.wrap(tweet));
        startActivity(intent);
    }
}
