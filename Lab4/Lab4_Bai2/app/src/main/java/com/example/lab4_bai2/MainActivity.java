package com.example.lab4_bai2;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    private PowerStateChangeReceiver powerStateChangeReceiver;

    private void initValue() {
        powerStateChangeReceiver = new PowerStateChangeReceiver();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        initValue();

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);

        registerReceiver(
                powerStateChangeReceiver,
                intentFilter
        );
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(powerStateChangeReceiver);
    }
}