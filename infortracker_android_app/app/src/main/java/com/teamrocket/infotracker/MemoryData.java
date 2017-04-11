package com.teamrocket.infotracker;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Activity;
import android.os.Debug;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.LinearLayout;

import java.util.List;

/**
 * Created by kayigwe on 12/6/15.
 */
public class MemoryData extends Activity{

    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int FP = ViewGroup.LayoutParams.FILL_PARENT;
    String named, substring;
    int spot;


    private ActivityManager mActivityManager = null;
    // ProcessInfo Model store the running app process
    private List<ActivityManager.RunningAppProcessInfo> processInfoList = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.memsize_main);

        //create an instance of TableLayout
        TableLayout tableLayout = (TableLayout)findViewById(R.id.table_memsize);
        //
        tableLayout.setStretchAllColumns(true);

        mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        processInfoList = mActivityManager.getRunningAppProcesses();
        int Process = 0;
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : processInfoList) {
            // process id
            // i= i+1;
            int pid = appProcessInfo.pid;
            // user id
            int uid = appProcessInfo.uid;
            // process name
            String processName = appProcessInfo.processName;
            // get the process memory using pin
            int[] myMempid = new int[]{pid};
            // get MemoryInfo from "android.os.Debug.MemoryInfo"
            Debug.MemoryInfo[] memoryInfo = mActivityManager
                    .getProcessMemoryInfo(myMempid);
            // get memory info
            int memSize = memoryInfo[0].dalvikPrivateDirty;

            TableRow tableRow=new TableRow(this);
            TextView tv1=new TextView(this);
            tv1.setText("" + pid);
            tableRow.addView(tv1);


            if (processName.contains(".")) {
                String delims = "[.]";
                String[] tokens = processName.split(delims);
                spot  = tokens.length - 1;
                named = tokens[spot];
                if (named.length() > 15){

                    //String substring(int beginIndex, int endIndex)
                    named= named.substring(0, 14);
                    Log.i("nameTAG", "name: " + named);
                }
            }
            else {
                named = processName;
            }
            TextView tv2=new TextView(this);
            tv2.setText(named);
            tableRow.addView(tv2);


            TextView tv3=new TextView(this);
            tv3.setText(memSize+"kB");
            tableRow.addView(tv3);
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(WC, WC));

            Process = Process + 1;


        }

        TextView textView_Process =(TextView)findViewById(R.id.textView3);
        textView_Process.setText("" + Process);

    }
}