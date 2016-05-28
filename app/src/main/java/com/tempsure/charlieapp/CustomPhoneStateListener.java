package com.tempsure.charlieapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.widget.Toast;


public class CustomPhoneStateListener extends PhoneStateListener {
    SharedPreferences sharedpreferences;

    Context context; //Context to make Toast if required

    public CustomPhoneStateListener(Context context) {
        super();
        this.context = context;
    }

    @Override
    public void onCallStateChanged(int state, String incomingNumber) {
        sharedpreferences = context.getSharedPreferences(SetUpPhase.MyPREFERENCES, Context.MODE_PRIVATE);
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                break;
            case TelephonyManager.CALL_STATE_IDLE:
                boolean ifIdle = sharedpreferences.getBoolean("idle", false);
                if (ifIdle == false) {
                    String relative_2 = sharedpreferences.getString("relative_2", "");
                    Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + relative_2));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                    final SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean("idle",true);
                    editor.commit();
                }
                break;
        }
    }
}