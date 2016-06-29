package com.tempsure.charlieapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.provider.Settings;
import android.telephony.PhoneStateListener;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

public class PhoneCallReceiver extends BroadcastReceiver {

    ReturnHost rhost = new ReturnHost();
    String host = rhost.getHost();
    private String post_url = "http://"+host+"/twitter/twitter.php";
    GPSTracker gps;
    StringBuilder strAddress = new StringBuilder();
    MediaPlayer mp = null;
    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    public PhoneCallReceiver() {
    }

    @Override
    public void onReceive(final Context context, Intent intent) {
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES,Context.MODE_PRIVATE);
        mp = MediaPlayer.create(context, R.raw.fake);
        mp.setLooping(false);
        mp.start();
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        telephonyManager.listen(new CustomPhoneStateListener(context), PhoneStateListener.LISTEN_CALL_STATE);

        gps = new GPSTracker(context);

        // check if GPS enabled
        if(gps.canGetLocation()){

            final double latitude = gps.getLatitude();
            final double longitude = gps.getLongitude();


            Geocoder geocoder= new Geocoder(context, Locale.ENGLISH);
            try {  //Place your latitude and longitude
                List<Address> addresses = geocoder.getFromLocation(latitude,longitude, 1);
                if(addresses != null) {
                    Address fetchedAddress = addresses.get(0);

                    for(int i=0; i<fetchedAddress.getMaxAddressLineIndex(); i++) {
                        strAddress.append(fetchedAddress.getAddressLine(i)).append("\n");
                    }
                }  else Toast.makeText(context,"gggg", Toast.LENGTH_SHORT).show();
                post(longitude,latitude,context);
            }
            catch (IOException e) { //
                //e.printStackTrace();
                new AsyncTask<Double,Double,Double>(){
                    @Override
                    protected Double doInBackground(Double... doubles) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        String ifHasSent = sharedpreferences.getString("ifHasSent","");
                        if(ifHasSent.toString().equals("yes")) {
                            post(longitude, latitude, context);
                            editor.putString("ifHasSent","no");
                            editor.commit();
                        }
                        return null;
                    }
                }.execute();
            }
        }else{
            Toast.makeText(context,"No Connection",Toast.LENGTH_LONG).show();
        }
    }
    private void post(Double longi,Double lati,Context ctx){
            String PNP = sharedpreferences.getString("pnp_number", "");
            String DSWD = sharedpreferences.getString("dswd_number", "");
            String name = sharedpreferences.getString("name", "");
            SmsManager smsManager = SmsManager.getDefault();
            ConnectivityManager connManager = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            if (mWifi.isConnected() && longi != 0 && lati != 0) {
                smsManager.sendTextMessage("63" + PNP.substring(1), null, "To PNP : " + name + " might need some help here's her location http://www.google.com/maps/place/" + longi + "," + lati, null, null);
                smsManager.sendTextMessage("63" + DSWD.substring(1), null, "To DSWD : " + name + " might need some help here's her location http://www.google.com/maps/place/" + longi + "," + lati, null, null);
            } else {
                smsManager.sendTextMessage("63" + PNP.substring(1), null, "To PNP : " + name + " might need some help.", null, null);
                smsManager.sendTextMessage("63" + DSWD.substring(1), null, "To DSWD : " + name + " might need some help.", null, null);
            }
            InputStream isr = null;
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost(post_url + "?postThis=ggg");
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                isr = entity.getContent();
                Log.d("success", "Cool");
             } catch (Exception ed) {
                Log.e("log_tag", "Error in http connection " + ed.toString());
                Log.d("error", "Error");
            }
    }
}
