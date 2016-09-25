package com.example.ahmed.finalprojectmal;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by Ahmed on 24/09/2016.
 */
public class CheckinternetConnection{
private Context context;
        public CheckinternetConnection(Context context) {
            this.context = context;
        }
        public boolean isConnectingToInternet() {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager != null) {
                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                if (info != null && info.isConnected()) {
                    return true;
                }
            }
            return false;
        }
}



