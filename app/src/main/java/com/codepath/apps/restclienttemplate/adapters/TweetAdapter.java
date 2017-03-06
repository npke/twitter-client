package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.databinding.ItemTweetBinding;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;

import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder> {

    private List<Tweet> mTweets;
    private Context mContext;
    private TweetClickListener mListener;

    public interface TweetClickListener {
        public void onClick(Tweet tweet);
    }

    public TweetAdapter(Context context, List<Tweet> tweets) {
        mContext = context;
        mTweets = tweets;
    }

    public void setTweets(List<Tweet> tweets) {
        mTweets = tweets;
        notifyDataSetChanged();
    }

    public void setListener(TweetClickListener listener) {
        this.mListener = listener;
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
//        View itemView = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.item_tweet, parent, false);

        ItemTweetBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_tweet, parent, false);

        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        User user = tweet.getUser();

        holder.itemTweetBinding.setTweet(tweet);

//        Glide.with(mContext)
//                .load(user.getProfilePhotoUrl())
//                .into(holder.itemTweetBinding.imageUserProfilePhoto);

//        Glide.with(mContext)
//                .load(user.getProfilePhotoUrl())
//                .into(holder.ivUserProfilePhoto);
//
//        holder.tvUserDisplayName.setText(user.getName());
//        holder.tvUserScreenName.setText(user.getUserName());
//        holder.tvTweetBody.setText(tweet.getText());
//        holder.tvCreatedAt.setText(tweet.getCreatedAt());
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
//        @BindView(R.id.image_user_profile_photo)
//        ImageView ivUserProfilePhoto;
//
//        @BindView(R.id.text_user_name)
//        TextView tvUserDisplayName;
//
//        @BindView(R.id.text_user_screen_name)
//        TextView tvUserScreenName;
//
//        @BindView(R.id.text_tweet_body)
//        TextView tvTweetBody;
//
//        @BindView(R.id.text_created_at)
//        TextView tvCreatedAt;

        public ItemTweetBinding itemTweetBinding;

        public ViewHolder(View itemView) {
            super(itemView);

            itemTweetBinding = DataBindingUtil.bind(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onClick(mTweets.get(position));
                    }
                }
            });

//            ButterKnife.bind(this, itemView);
        }
    }
}
