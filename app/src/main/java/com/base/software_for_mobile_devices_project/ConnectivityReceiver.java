package com.base.software_for_mobile_devices_project;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityReceiver extends BroadcastReceiver {
    private static final String TAG = "=== ConnectivityReceiver ===";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: 5/27/2020 : Somehow update 'networkConnected' variable in TransactionListActivity

    }

    boolean checkInternet(Context context) {
        boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork;
        if (cm != null) {
            activeNetwork = cm.getActiveNetworkInfo();
            isConnected = activeNetwork != null && activeNetwork.isConnected();
        }
        return isConnected;
    }
}
