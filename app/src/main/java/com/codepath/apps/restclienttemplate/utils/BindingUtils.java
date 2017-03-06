package com.codepath.apps.restclienttemplate.utils;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class BindingUtils {
    @BindingAdapter("bind:imageUrl")
    public static void loadImage(ImageView imageView, String imageUrl) {
        Glide.with(imageView.getContext())
                .load(imageUrl)
                .bitmapTransform(new RoundedCornersTransformation(imageView.getContext(), 5, 0))
                .into(imageView);
    }
}
