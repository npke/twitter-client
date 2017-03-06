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

        ItemTweetBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_tweet, parent, false);

        return new ViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);
        User user = tweet.getUser();

        holder.itemTweetBinding.setTweet(tweet);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ItemTweetBinding itemTweetBinding;

        public ViewHolder(View itemView) {
            super(itemView);

            itemTweetBinding = DataBindingUtil.bind(itemView);
            itemTweetBinding.textTweetBody.setOnClickListener(getOnClickListener());
            itemView.setOnClickListener(getOnClickListener());
        }

        public View.OnClickListener getOnClickListener() {
            return new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (position != RecyclerView.NO_POSITION) {
                        mListener.onClick(mTweets.get(position));
                    }
                }
            };
        }
    }
}
