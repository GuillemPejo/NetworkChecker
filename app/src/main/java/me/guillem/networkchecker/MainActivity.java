package me.guillem.networkchecker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements ConnectionReceiver.ReceiverListener {

    Button btchecker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btchecker = findViewById(R.id.btChecker);
        checkConnection();

        btchecker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
            }
        });
    }

    private void checkConnection(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(new ConnectionReceiver(),intentFilter);

        ConnectionReceiver.listener = this;

        ConnectivityManager manager = (ConnectivityManager)
                getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();
        
        showSnack(isConnected);
    }

    private void showSnack(boolean isConnected) {
        int color;
        int icon;
        String msg;
        if (isConnected){
            msg = "Connected to internet";
            color = Color.WHITE;
            icon = R.drawable.conected;
        }else {
            msg = "Disconnected to internet";
            color = Color.RED;
            icon = R.drawable.disconected;
        }

        Snackbar snackbar = Snackbar.make(findViewById(R.id.btChecker),msg, Snackbar.LENGTH_LONG);
        View v = snackbar.getView();
        TextView sntv = v.findViewById(R.id.snackbar_text);
        sntv.setCompoundDrawablesWithIntrinsicBounds(icon, 0, 0, 0);
        sntv.setCompoundDrawablePadding(40);
        sntv.setTextColor(color);
        snackbar.show();
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        showSnack(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkConnection();
    }

    @Override
    protected void onPause() {
        super.onPause();
        checkConnection();

    }
}