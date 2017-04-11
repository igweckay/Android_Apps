package com.kayigwe.testerjungle;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by kayigwe on 10/21/15.
 */
public class Congratulations extends Activity {

    int count = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.congratulationslevel);

        ImageButton exit_level = (ImageButton) findViewById(R.id.exit_tofrontscreen);


        MyOnClickListener myOnClickListener = new MyOnClickListener();
        exit_level.setOnClickListener(myOnClickListener);

    }

    private class MyOnClickListener implements View.OnClickListener {
        final Vibrator vibe = (Vibrator) Congratulations.this.getSystemService(Context.VIBRATOR_SERVICE);

        @Override
        public void onClick(View v) {
            //To do auto-generated method stub
            vibe.vibrate(80);//duration of the vibration for button 1

            count++;

            if (v.getId() == R.id.exit_tofrontscreen){
                //startActivityForResult();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }

        }
    }
}
