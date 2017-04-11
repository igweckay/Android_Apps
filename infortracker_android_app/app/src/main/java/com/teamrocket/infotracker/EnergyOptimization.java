package com.teamrocket.infotracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import android.provider.ContactsContract.Data;

import java.util.Set;

/**
 * Created by kayigwe on 12/8/15.
 */
public class EnergyOptimization extends Activity {

    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int FP = ViewGroup.LayoutParams.FILL_PARENT;
    TableLayout tableLayout;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.energyoptimization_main);

        //create an instance of TableLayout
        tableLayout = (TableLayout)findViewById(R.id.threadtable);
        //
        tableLayout.setStretchAllColumns(true);
        textView = (TextView) findViewById(R.id.textView25);

        gettingThreads();

    }



    public void gettingThreads(){
        Set<Thread> threadSet = Thread.getAllStackTraces().keySet();
        Thread[] threadArray = threadSet.toArray(new Thread[threadSet.size()]);
        int i;
        long ID;
        for( i = 0; i<threadSet.size(); i++){
            Log.i("TagThread", "thread: " + threadArray[i]);
            ID = android.os.Process.getThreadPriority(android.os.Process.myTid());



            Log.i("IDThread","Thread ID: "+ ID);
            TableRow tableRow=new TableRow(this);

            TextView tv1=new TextView(this);
            tv1.setText("" + (i + 1));
            tableRow.addView(tv1);

            TextView tv2=new TextView(this);
            tv2.setText("" + threadArray[i]);
            tableRow.addView(tv2);

            tableLayout.addView(tableRow, new TableLayout.LayoutParams(WC, WC));

        }
        textView.setText("Thread Count: " + (i));
       // startActivity(new Intent(getApplicationContext(), MyTask.class));
    }



}
