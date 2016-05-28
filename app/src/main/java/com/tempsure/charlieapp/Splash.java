package com.tempsure.charlieapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class Splash extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String dswd_number = sharedpreferences.getString("dswd_number","");
        String pnp_number = sharedpreferences.getString("pnp_number","");
        String relative_1 = sharedpreferences.getString("relative_1","");
        String relative_2 = sharedpreferences.getString("relative_2","");
        String name = sharedpreferences.getString("name","");
        if(!(dswd_number.equals("")) && (!pnp_number.equals("")) && (!relative_1.equals("")) && (!relative_2.equals(""))
                && (!name.equals(""))){
            Intent intent = new Intent(Splash.this, MainActivity.class);
            startActivity(intent);
        }
        final ImageView imageView = (ImageView)findViewById(R.id.imageView);
        final Animation animation = AnimationUtils.loadAnimation(getBaseContext(), R.anim.rotate);
        imageView.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
                finish();
                Intent intent = new Intent(Splash.this, SetUpPhase.class);
                startActivity(intent);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
