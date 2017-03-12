package com.codepath.apps.restclienttemplate.fragments;

/**
 * Created by kenp on 12/03/2017.
 */

public class MentionTimelineTweetFragment extends TweetFragment {
    @Override
    protected void getTweets(int currentPage) {
        twitterClient.getUserMentionTweet(currentPage, tweetsResponseHandler);
    }
}
