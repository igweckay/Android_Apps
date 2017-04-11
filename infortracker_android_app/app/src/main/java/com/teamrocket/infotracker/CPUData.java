package com.teamrocket.infotracker;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;

/**
 * Created by kayigwe on 12/6/15.
 */
public class CPUData extends Activity {

    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    private final int FP = ViewGroup.LayoutParams.FILL_PARENT;
    String named;
    int spot;
    private ActivityManager mActivityManager = null;
    // ProcessInfo Model store the running app process
    private List<ActivityManager.RunningAppProcessInfo> processInfoList = null;

    long appcputime_start;
    long appcputime_end;
    long totalcputime_start;
    long totalcputime_end;
    String cpuusage;

    HashMap<String, Long> appCpuTimeTable_start = new HashMap<String, Long>();
    HashMap<String, Long> appCpuTimeTable_end = new HashMap<String, Long>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cpusize_main);

        //create an instance of TableLayout
        TableLayout tableLayout = (TableLayout) findViewById(R.id.table_cpusize);
        //
        tableLayout.setStretchAllColumns(true);

        mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        processInfoList = mActivityManager.getRunningAppProcesses();

        totalcputime_start = 0;
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : processInfoList) {
            int pid = appProcessInfo.pid;
            String processName = appProcessInfo.processName;
            long tempAppCpuTime = getAppCpuTime(pid);
            appCpuTimeTable_start.put(processName, tempAppCpuTime);
            totalcputime_start = totalcputime_start + tempAppCpuTime;
        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        totalcputime_end = 0;
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : processInfoList) {
            int pid = appProcessInfo.pid;
            String processName = appProcessInfo.processName;
            long tempAppCpuTime = getAppCpuTime(pid);
            appCpuTimeTable_end.put(processName, tempAppCpuTime);
            totalcputime_end = totalcputime_end + tempAppCpuTime;
        }

        int Process = 0;
        for (ActivityManager.RunningAppProcessInfo appProcessInfo : processInfoList) {
            // process id
            // i= i+1;
            int pid = appProcessInfo.pid;
            // user id
            String processName = appProcessInfo.processName;

            appcputime_start = appCpuTimeTable_start.get(processName);
            appcputime_end = appCpuTimeTable_end.get(processName);

            Log.i("pidTAG", " appcputime_start: " + appcputime_start);
            Log.i("pidTAG", " appcputime_end: " + appcputime_end);
            Log.i("pidTAG", " totalcputime_start: " + totalcputime_start);
            Log.i("pidTAG", " totalcputime_end: " + totalcputime_end);

            if (totalcputime_end != totalcputime_start) {
                cpuusage = ("" + ((float) (appcputime_end - appcputime_start) / (float) (totalcputime_end - totalcputime_start)));
            } else {
                cpuusage = "      ";
            }

            Log.i("cpuTAG", " cpuusage: " + cpuusage);
            Log.i("pidTAG", " cpuusage: " + cpuusage);

            TableRow tableRow=new TableRow(this);
            TextView tv1=new TextView(this);
            tv1.setText("" + pid);
            tableRow.addView(tv1);

            if (processName.contains(".")) {
                String delims = "[.]";
                String[] tokens = processName.split(delims);
                spot  = tokens.length - 1;
                named = tokens[spot];
            }
            else {
                named = processName;
            }

            if (named.length() > 15){

                //String substring(int beginIndex, int endIndex)
                named= named.substring(0, 14);
                Log.i("nameTAG", "name: " + named);
            }
            TextView tv2=new TextView(this);
            tv2.setText(named);
            tableRow.addView(tv2);

            TextView tv3=new TextView(this);
            tv3.setText(cpuusage);
            tableRow.addView(tv3);
            tableLayout.addView(tableRow, new TableLayout.LayoutParams(WC, WC));

            Process = Process + 1;
        }
        TextView textView_Process =(TextView)findViewById(R.id.textView3);
        textView_Process.setText(""+Process);
    }

    public static long getAppCpuTime(int logger)
    {
        String[] cpuInfos = null;
        try
        {
            // int pid = android.os.Process.myPid();
            int pid = logger;
            //Log.i("pidTAG"," pid: " + pid);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/" + pid + "/stat")), 1000);
            String load = reader.readLine();
            //Log.i("pidTAG", " load: " + load);
            reader.close();
            cpuInfos = load.split(" ");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        long appCpuTime = Long.parseLong(cpuInfos[13])
                + Long.parseLong(cpuInfos[14]) + Long.parseLong(cpuInfos[15])
                + Long.parseLong(cpuInfos[16]);
        //Log.i("pidTAG"," appCpuTime: " + appCpuTime);

        return appCpuTime;
    }
}