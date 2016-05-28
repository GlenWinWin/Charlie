package com.tempsure.charlieapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedpreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(SetUpPhase.MyPREFERENCES, Context.MODE_PRIVATE);
        call();
    }

    public void call() {
        final SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putBoolean("idle", false);
        editor.commit();
        finish();
        String relative_1 = sharedpreferences.getString("relative_1", "");
        Intent in = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + relative_1));
        startActivity(in);


    }
}
