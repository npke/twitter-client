package com.codepath.apps.restclienttemplate.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.databinding.FragmentPostTweetBinding;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PostTweetDialogFragment extends DialogFragment {

    @BindView(R.id.edit_tweet_body)
    EditText etTweetBody;

    @BindView(R.id.button_tweet)
    Button btnTweet;

    @BindView(R.id.text_counter)
    TextView tvCounter;

    public static final int MAX_TWEET_CHARACTER = 150;

    public interface PostTweetListener {
        void onPostTweet(String tweetBody);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_post_tweet, container);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        setStyle(STYLE_NO_TITLE, 0);
        return super.onCreateDialog(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        etTweetBody.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvCounter.setText(MAX_TWEET_CHARACTER - charSequence.length() + "");
                if (charSequence.length() == 0)
                    btnTweet.setEnabled(false);
                else btnTweet.setEnabled(true);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @OnClick(R.id.button_tweet)
    public void onClick(Button button) {
        String tweetBody = etTweetBody.getText().toString();
        etTweetBody.setText("");
        PostTweetListener listener = (PostTweetListener) getActivity();
        listener.onPostTweet(tweetBody);
    }
}
