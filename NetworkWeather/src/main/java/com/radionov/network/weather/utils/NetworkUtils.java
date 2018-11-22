package com.radionov.network.weather.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

/**
 * @author Andrey Radionov
 */
public class NetworkUtils {
    private NetworkUtils() {
    }

    public static boolean isInternetAvailable(@NonNull final Context context) {
        final ConnectivityManager mConMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return mConMgr != null
                && mConMgr.getActiveNetworkInfo() != null
                && mConMgr.getActiveNetworkInfo().isAvailable()
                && mConMgr.getActiveNetworkInfo().isConnected();
    }
}
