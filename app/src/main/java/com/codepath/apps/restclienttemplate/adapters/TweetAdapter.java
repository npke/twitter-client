package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
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

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }
}
