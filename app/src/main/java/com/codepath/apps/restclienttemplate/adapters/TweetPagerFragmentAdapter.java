package com.codepath.apps.restclienttemplate.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.codepath.apps.restclienttemplate.fragments.HomeTimelineTweetFragment;
import com.codepath.apps.restclienttemplate.fragments.MentionTimelineTweetFragment;

public class TweetPagerFragmentAdapter extends FragmentPagerAdapter {

    public static final int HOME_TIMLINE = 1;
    public static final int MENTION_TIMELINE = 2;

    private String[] titles = {"HOME", "MENTION"};

    public TweetPagerFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
            return new HomeTimelineTweetFragment();
        return new MentionTimelineTweetFragment();
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }


}
