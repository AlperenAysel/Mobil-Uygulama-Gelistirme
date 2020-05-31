package com.example.mug_proje;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class ChargerBroadcaster extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(intent.ACTION_POWER_CONNECTED)) {
            Toast.makeText(context, "Şarj Oluyor", Toast.LENGTH_SHORT).show();
        }
        else if (intent.getAction().equals(intent.ACTION_POWER_DISCONNECTED)) {
            Toast.makeText(context, "Şarj Olmuyor", Toast.LENGTH_SHORT).show();
        }
    }
}
