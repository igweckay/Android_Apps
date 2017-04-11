package com.womenwerk.mycollageapp;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.content.DialogInterface.OnClickListener;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    ImageView imageViewone;
    ImageView imageViewtwo;
    ImageView imageViewthree;
    ImageView imageViewfour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageViewone = (ImageView) findViewById(R.id.topImage);
        imageViewtwo = (ImageView) findViewById(R.id.secondImage);
        imageViewthree = (ImageView) findViewById(R.id.thirdImage);
        imageViewfour = (ImageView) findViewById(R.id.forthImage);

        imageViewone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),"WomenWerk is a movement that brings women and men together for a modern take on International Women's Day, celebrated annually in March.\n" +
                        " \n" +
                        "WomenWerk was founded to change the way women discuss, perceive, and overcome challenges. Our goal is to provide a platform for women to affirm and celebrate individual and collective success", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        imageViewtwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),"WomenWerk's hallmark event is an annual Forum and Gala held in early March. The next Forum and Gala is set for Saturday, March 12, 2016.", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        imageViewthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),"The WomenWerk Blog is curated for women and men with an international perspective. Our goal is to provide access thought leaders from across the globe on issues relevant to you. ", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        imageViewfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(),"Demi Ajayi and Nekpen Osuan are the founders of Womenwerk, a movement that brings women and men together for a modern take on International Women’s Day.", Toast.LENGTH_LONG);
                toast.show();
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



