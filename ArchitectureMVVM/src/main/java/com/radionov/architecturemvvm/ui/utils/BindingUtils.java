package com.radionov.architecturemvvm.ui.utils;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.radionov.architecturemvvm.R;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author Andrey Radionov
 */
public class BindingUtils {

    private BindingUtils() {
    }

    @BindingAdapter("app:imageUrl")
    public static void loadImage(ImageView imageView, String symbol) {
        Context context = imageView.getContext();
        String logoUrl = context.getString(R.string.coin_logo_url, symbol.toLowerCase());
        Glide.with(context)
                .load(logoUrl)
                .into(imageView);
    }


    @BindingAdapter("android:textColor")
    public static void setTextColor(TextView textView, double change) {
        Context context = textView.getContext();
        if (change > 0) {
            textView.setTextColor(ContextCompat.getColor(context, R.color.green700));
        } else {
            textView.setTextColor(ContextCompat.getColor(context, R.color.red700));
        }
    }

    @BindingConversion
    public static String formatDate(long lastUpdated) {
        return DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM, Locale.getDefault())
                .format(new Date(lastUpdated * 1000));
    }
}
