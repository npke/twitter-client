package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.codepath.apps.restclienttemplate.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostTweetDialogFragment extends DialogFragment {

    @BindView(R.id.edit_tweet_body)
    EditText etTweetBody;

    @BindView(R.id.button_tweet)
    Button btnTweet;

    public interface PostTweetListener {
        void onPostTweet(String tweetBody);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_tweet, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
    }

    @OnClick(R.id.button_tweet)
    public void onClick(Button button) {
        String tweetBody = etTweetBody.getText().toString();
        PostTweetListener listener = (PostTweetListener) getActivity();
        listener.onPostTweet(tweetBody);
    }
}
