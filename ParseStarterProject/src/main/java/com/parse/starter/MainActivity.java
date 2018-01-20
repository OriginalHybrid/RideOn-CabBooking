/*
 * Copyright (c) 2015-present, Parse, LLC.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */
package com.parse.starter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseAnonymousUtils;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.parse.SignUpCallback;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

  TextView changeSignUP;
  Boolean LoginModeActive = true;
  String userType;
  Switch userTypeSwitch;

  public void redirectActivity() {

    if (ParseUser.getCurrentUser().getString("RiderOrDriver").equals("rider")) {

      Intent intent = new Intent(getApplicationContext(), RiderActivity.class);
      startActivity(intent);

    }
    else {

        Intent intent = new Intent(getApplicationContext(), ViewRequestsActivity.class);
        startActivity(intent);


    }
  }


  @Override
  public void onClick(View view) {

    if (view.getId() == R.id.signup_text) {

      ImageButton loginButton = (ImageButton) findViewById(R.id.loginBT);

      if (LoginModeActive) {

        LoginModeActive = false;
        loginButton.setImageResource(R.drawable.signup);
        changeSignUP.setText("Already have an Account, Login.");

      } else {

        LoginModeActive = true;
        loginButton.setImageResource(R.drawable.login);
        changeSignUP.setText("Don't have an Account !  Sign Up ");

      }

    }

  }

  public void getStarted() {

    userTypeSwitch = (Switch) findViewById(R.id.switch1);

    //Log.i("Switch value", String.valueOf(userTypeSwitch.isChecked()));

    userType = "rider";

    if (userTypeSwitch.isChecked()) {

      userType = "driver";

    }

    //ParseUser.getCurrentUser().put("riderOrDriver", userType);

    //Log.i("Info", "Redirecting as " + userType);

  }

  public void signUp(View view) {



    EditText usernameEditText = (EditText) findViewById(R.id.username);

    EditText passwordEditText = (EditText) findViewById(R.id.pass);

    if (usernameEditText.getText().toString().matches("") || passwordEditText.getText().toString().matches("")) {

      Toast.makeText(this, "A username and password are required.", Toast.LENGTH_SHORT).show();

    } else {

      if (!LoginModeActive) {

        ParseUser user = new ParseUser();

        user.setUsername(usernameEditText.getText().toString());
        user.setPassword(passwordEditText.getText().toString());
        getStarted();
        user.put("RiderOrDriver",userType);

        user.signUpInBackground(new SignUpCallback() {
          @Override
          public void done(ParseException e) {
            if (e == null) {

              Log.i("Signup", "Successful");
              ParseUser.getCurrentUser().put("RiderOrDriver",userType);
              Toast.makeText(MainActivity.this, "Sign Up Successful, Logging In...", Toast.LENGTH_SHORT).show();

              redirectActivity();


            } else {

              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
          }
        });

      }
      else {

        ParseUser.logInInBackground(usernameEditText.getText().toString(), passwordEditText.getText().toString(), new LogInCallback() {
          @Override
          public void done(ParseUser user, ParseException e) {

            if (user != null) {

              Log.i("Signup", "Login successful");

              String currentUserType = ParseUser.getCurrentUser().getString("RiderOrDriver");


              Log.i("Info", "Redirecting as " + currentUserType);

              redirectActivity();

            } else {

              Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }


          }
        });


      }
    }


  }




  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);


    getSupportActionBar().hide();

    ParseUser currentUser = ParseUser.getCurrentUser();
    if (currentUser != null) {
      // do stuff with the user
      redirectActivity();
    }
    changeSignUP = (TextView) findViewById(R.id.signup_text);
    changeSignUP.setOnClickListener(this);
    
    ParseAnalytics.trackAppOpenedInBackground(getIntent());
  }





}