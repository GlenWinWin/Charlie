package com.tempsure.charlieapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SetUpPhase extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    SharedPreferences sharedpreferences;
    EditText etPnp,etDSWD,relative_1,relative_2,name,userNameTwitter;
    Button saveContacts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_phase);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        saveContacts = (Button)findViewById(R.id.buttonContacts);
         etPnp = (EditText)findViewById(R.id.editTextNumberPNP);
         etDSWD = (EditText)findViewById(R.id.editTextNumberDSWD);
         relative_1 = (EditText)findViewById(R.id.editTextNumberRelativeOne);
         relative_2 = (EditText)findViewById(R.id.editTextNumberRelativeTwo);
         name = (EditText)findViewById(R.id.editName);
         userNameTwitter = (EditText)findViewById(R.id.editTwitterUsername);
            saveContacts.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(etDSWD.getText().toString().isEmpty() || etPnp.getText().toString().isEmpty() ||
                            relative_1.getText().toString().isEmpty() || relative_2.getText().toString().isEmpty()
                            || name.getText().toString().isEmpty()){
                        Snackbar.make(view,"Fill up the above 5 fields!",Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                    else {
                        final SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("dswd_number", etDSWD.getText().toString());
                        editor.putString("pnp_number", etPnp.getText().toString());
                        editor.putString("relative_1", relative_1.getText().toString());
                        editor.putString("relative_2", relative_2.getText().toString());
                        editor.putString("name", name.getText().toString());
                        editor.putString("twitterUsername", userNameTwitter.getText().toString());
                        editor.commit();
                        Snackbar.make(view,"Configuration Changes",Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                        finish();
                    }
                }
            });


    }

}
