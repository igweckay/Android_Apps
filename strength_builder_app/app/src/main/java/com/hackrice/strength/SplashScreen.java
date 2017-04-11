package com.hackrice.strength;

import android.app.Activity;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.view.animation.Animation;
import android.content.Intent;

/**
 * Created by kayigwe on 1/16/16.
 */
public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        final ImageView iv = (ImageView) findViewById(R.id.img_view);
        final Animation an = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_in);
        final Animation an2 = AnimationUtils.loadAnimation(getBaseContext(),R.anim.abc_fade_out);

        an.setDuration(3000);
        iv.startAnimation(an);

        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                an2.setDuration(3000);
                iv.startAnimation(an2);
                finish();
                Intent welcomePage = new Intent(getBaseContext(), MainActivity.class);
                startActivity(welcomePage);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

}
