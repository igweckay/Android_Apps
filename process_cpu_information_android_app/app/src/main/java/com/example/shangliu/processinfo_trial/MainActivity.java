package com.example.shangliu.processinfo_trial;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.util.ArrayList;


import java.util.List;

public class MainActivity extends Activity {

    private ActivityManager mActivityManager = null;
    // ProcessInfo Model store the running app process
    private List<ActivityManager.RunningAppProcessInfo> processInfoList = null;

    //String[] mobileArray = new String[10];
    private ListView listviewProcess;
    //private TextView tvTotalProcessNo ;
  //  TextView textView;
    ListView listview;
    TextView process;
    Context context;
    List<String> mobileArray;
    ArrayAdapter<String> adapter;
    long appcputime1;
    long appcputime2;
    long totalcputime1;
    long totalcputime2;

    //mobileArray = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        processInfoList = mActivityManager.getRunningAppProcesses();
       // textView = (TextView) findViewById(R.id.memSizes);
        process = (TextView) findViewById(R.id.processtext);
        context = this;
        mobileArray = new ArrayList<String>();

        // second parameter is row layout,
        adapter = new ArrayAdapter<String>(context, R.layout.activity_listview, mobileArray);
        ListView listView = (ListView) findViewById(R.id.newlist);
        listView.setAdapter(adapter);

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

            //get cpu info
           // cputime = readUsage();
            String appstring;
            appcputime1 = getAppCpuTime(pid);
            totalcputime1 = getTotalCpuTime();
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            appcputime2 = getAppCpuTime(pid);
            totalcputime2 = getTotalCpuTime();
            Log.i("pidTAG"," appcputime1: " + appcputime1);
            Log.i("pidTAG"," appcputime2: " + appcputime2);
            Log.i("pidTAG"," totalcputime1: " + totalcputime1);
            Log.i("pidTAG"," totalcputime2: " + totalcputime2);
            // ((appcputime2-appcputime1)/(totalcputime2-totalcputime1))

            Log.i("pidTAG"," appcputime: " +(float) ((appcputime2-appcputime1)/( (appcputime2 + totalcputime2) - (appcputime1+totalcputime1))) );
            appstring = "hello";//("" + ((appcputime2 + totalcputime2) - (appcputime1+totalcputime1)));



                    Log.i("TAG", "processName: " + processName + "  pid: " + pid
                    + " uid:" + uid + " memorySize is -->" + memSize + "kb");
          //  textView.setText("" + memSize + "kb");
            mobileArray.add("processName: " + processName + "  pid: " + pid
                    + " uid:" + uid + " memorySize is -->" + memSize + "kb " + appstring + "cpu");
           /* if (i<10)
            {
                mobileArray[i] = ("processName: " + processName + "  pid: " + pid
                        + " uid:" + uid + " memorySize is -->" + memSize + "kb");
            }*/
          //  i=i+1;
            Process = Process + 1;

        }

     //   ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.activity_listview, mobileArray);
        process.setText("Process Count: "+Process);
        //ListView listView = (ListView) findViewById(R.id.newlist);
        //listView.setAdapter(adapter);

    }

    /*cpu usage stack overflow*/
    public static long getAppCpuTime(int logger)
    { // 获取应用占用的CPU时间
        String[] cpuInfos = null;
        try
        {
           // int pid = android.os.Process.myPid();
            int pid = logger;
            Log.i("pidTAG"," pid: " + pid);
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/" + pid + "/stat")), 1000);
            String load = reader.readLine();
            Log.i("pidTAG", " load: " + load);
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
        Log.i("pidTAG"," appCpuTime: " + appCpuTime);

        return appCpuTime;
    }



    //total cpu time
    public static long getTotalCpuTime()
    { // 获取系统总CPU使用时间
        String[] cpuInfos = null;
        try
        {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/stat")), 1000);
            String load = reader.readLine();
            reader.close();
            cpuInfos = load.split(" ");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
        long totalCpu = Long.parseLong(cpuInfos[2])
                + Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4])
                + Long.parseLong(cpuInfos[6]) + Long.parseLong(cpuInfos[5])
                + Long.parseLong(cpuInfos[7]) + Long.parseLong(cpuInfos[8]);
        return totalCpu;
    }


    //not this one
    private float readUsage() {
        try {
            RandomAccessFile reader = new RandomAccessFile("/proc/stat", "r");
            String load = reader.readLine();

            String[] toks = load.split(" ");

            long idle1 = Long.parseLong(toks[5]);
            long cpu1 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            try {
                Thread.sleep(360);
            } catch (Exception e) {}

            reader.seek(0);
            load = reader.readLine();
            reader.close();

            toks = load.split(" ");

            long idle2 = Long.parseLong(toks[5]);
            long cpu2 = Long.parseLong(toks[2]) + Long.parseLong(toks[3]) + Long.parseLong(toks[4])
                    + Long.parseLong(toks[6]) + Long.parseLong(toks[7]) + Long.parseLong(toks[8]);

            return (float)(cpu2 - cpu1) / ((cpu2 + idle2) - (cpu1 + idle1));

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return 0;
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
