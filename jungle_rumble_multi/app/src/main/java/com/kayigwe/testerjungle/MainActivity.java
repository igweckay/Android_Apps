package com.kayigwe.testerjungle;

import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.os.Vibrator;
import android.widget.Button;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MediaPlayer sound = MediaPlayer.create(MainActivity.this,R.raw.goodsleep);
        sound.start();

        final Vibrator vibe = (Vibrator) MainActivity.this.getSystemService(Context.VIBRATOR_SERVICE);
        //create vibrate event for a button pressed in the main page
        ImageButton vibrateButton0 = (ImageButton) findViewById(R.id.btn_vs_computer);
        ImageButton vibrateButton1 = (ImageButton) findViewById(R.id.btn_vs_opponent);
        ImageButton vibrateButton2 = (ImageButton) findViewById(R.id.btn_instructions);

        vibrateButton0.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                vibe.vibrate(80);//duration of the vibration for button 1
                startActivity(new Intent(getApplicationContext(), LevelOne.class));
            }
        });
        vibrateButton1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                vibe.vibrate(80);//duration of the vibration for button 2
                startActivity(new Intent(getApplicationContext(), vs_otherplayer_home_Activity.class));
            }
        });
        vibrateButton2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                vibe.vibrate(80);//duration of the vibration for button 3
                startActivity(new Intent(getApplicationContext(), Instructions.class));
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
