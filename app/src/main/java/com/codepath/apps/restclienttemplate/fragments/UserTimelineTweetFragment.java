package com.codepath.apps.restclienttemplate.fragments;

import com.codepath.apps.restclienttemplate.models.User;

public class UserTimelineTweetFragment extends TweetFragment {

    private User user;

    public static UserTimelineTweetFragment getInstance(User user) {
        UserTimelineTweetFragment fragment = new UserTimelineTweetFragment();
        fragment.user = user;

        return fragment;
    }

    @Override
    protected void getTweets(int currentPage) {
        twitterClient.getUserTimelineTweet(currentPage, user, tweetsResponseHandler);
    }
}
