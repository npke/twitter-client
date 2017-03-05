package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    private Context mContext;

    public TweetAdapter(Context context, List<Tweet> tweets) {
        mContext = context;
        mTweets = tweets;
    }

    public void addTweets(List<Tweet> tweets) {
        mTweets.addAll(tweets);
        notifyDataSetChanged();
    }

    public void addTweet(Tweet tweet, boolean addToLast) {
        if (addToLast)
            mTweets.add(tweet);
        else mTweets.add(0, tweet);

        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tweet, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        User user = tweet.getUser();

        Glide.with(mContext)
                .load(user.getProfilePhotoUrl())
                .into(holder.ivUserProfilePhoto);

        holder.tvUserDisplayName.setText(user.getName());
        holder.tvUserScreenName.setText(user.getUserName());
        holder.tvTweetBody.setText(tweet.getText());
        holder.tvCreatedAt.setText(tweet.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_user_profile_photo)
        ImageView ivUserProfilePhoto;

        @BindView(R.id.text_user_name)
        TextView tvUserDisplayName;

        @BindView(R.id.text_user_screen_name)
        TextView tvUserScreenName;

        @BindView(R.id.text_tweet_body)
        TextView tvTweetBody;

        @BindView(R.id.text_created_at)
        TextView tvCreatedAt;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
