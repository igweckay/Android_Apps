package com.hackrice.strength;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TextView;
import java.util.List;
import java.util.ArrayList;
import android.widget.ArrayAdapter;

/**
 * Created by kayigwe on 1/16/16.
 */
public class Start extends Activity{
    Button btn;
    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    TextView textView;

    private long timeRemaining = 0;
    int x = 0;

    //Log tab
    List<LogList> LogLists = new ArrayList<LogList>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        textView = (TextView) findViewById(R.id.txt_done);
        addButtonListener();

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);

        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("Start");
        tabSpec.setContent(R.id.start);
        tabSpec.setIndicator("Start");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Track");
        tabSpec.setContent(R.id.track);
        tabSpec.setIndicator("Track");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("Log");
        tabSpec.setContent(R.id.log);
        tabSpec.setIndicator("Log");
        tabHost.addTab(tabSpec);

    }

    private class LogListAdapter extends ArrayAdapter<LogList>{
        public LogListAdapter(){
            super(Start.this,R.layout.activity_log, LogLists);
        }
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
