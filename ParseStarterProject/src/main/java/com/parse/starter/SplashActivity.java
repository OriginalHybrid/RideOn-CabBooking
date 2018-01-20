package com.parse.starter;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends AppCompatActivity {

    Thread splashTread;


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_splash);
            StartAnimations();
        }


        private void StartAnimations() {
            Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
            anim.reset();
            RelativeLayout l=(RelativeLayout) findViewById(R.id.rlss);
            l.clearAnimation();
            l.startAnimation(anim);

            ImageView bg = (ImageView) findViewById(R.id.s_logo);
            bg.clearAnimation();
            bg.startAnimation(anim);

            anim = AnimationUtils.loadAnimation(this, R.anim.translate);
            anim.reset();
            ImageView iv = (ImageView) findViewById(R.id.s_name);
            iv.clearAnimation();
            iv.startAnimation(anim);

            splashTread = new Thread() {
                @Override
                public void run() {
                    try {
                        int waited = 0;
                        // Splash screen pause time
                        while (waited < 3500) {
                            sleep(100);
                            waited += 100;
                        }
                        Intent intent = new Intent(getApplication(),
                                MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                        startActivity(intent);
                        SplashActivity.this.finish();
                    } catch (InterruptedException e) {
                        // do nothing
                    } finally {
                        SplashActivity.this.finish();
                    }

                }
            };
            splashTread.start();

        }





    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }
}



