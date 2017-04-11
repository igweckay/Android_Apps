package com.example.strengthbuilder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kayigwe on 1/17/16.
 */
public class Start extends Activity {
    Button btn;
    Button display;
    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    TextView textView;

    private long timeRemaining = 0;
    int x = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        textView = (TextView) findViewById(R.id.txt_done);

        addButtonListener();
    }

    public void addButtonListener(){

        btn = (Button) findViewById(R.id.btn_start);
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                //create and display a new progress bar
                progressBar = new ProgressDialog(view.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("Continue Lifting...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressBar.setProgress(0);
                progressBar.setMax(60);
                progressBar.show();

                progressBarStatus = 0;

                timeRemaining = 0;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (progressBarStatus < 60) {
                            //progress some tasks
                            progressBarStatus = downloadFile();
                            //sleep 1 second

                            try {
                                Thread.sleep(1000);

                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            //Updatse the progress bar
                            progressBarHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    progressBar.setProgress(progressBarStatus);
                                }
                            });
                        }
                        //If the file is downloaded
                        if (progressBarStatus >= 60) {
                            //sleep 2 seconds, so that you can see the 100%
                            try {
                                Thread.sleep(2000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            // and then close the progressBar dialog
                            progressBar.dismiss();

                        }
                    }
                }).start();
                textView.setText("Calibration Complete!");
            }
        });
        startActivity(new Intent(getApplicationContext(), Bluetooth.class));

    }

    //file download simulator
    public int downloadFile(){
        while(timeRemaining <= 1000000){
            timeRemaining++;

            if (timeRemaining == 100000){
                return 10;
            }else if (timeRemaining == 200000){
                return 20;
            }else if (timeRemaining == 300000){
                return 30;
            }else if (timeRemaining == 400000){
                return 40;
            }else if (timeRemaining == 500000){
                return 50;
            }else if (timeRemaining == 600000){
                return 60;
            }

        }


        return 60;
    }

}
