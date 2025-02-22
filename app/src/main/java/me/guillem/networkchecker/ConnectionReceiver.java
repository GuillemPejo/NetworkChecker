package me.guillem.networkchecker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * * Created by Guillem on 01/02/21.
 */
public class ConnectionReceiver extends BroadcastReceiver {

    public static ReceiverListener listener;

    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (listener != null){
            boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
            listener.onNetworkChange(isConnected);
        }
    }

    public interface  ReceiverListener{
        void onNetworkChange(boolean isConnected);
    }

}
