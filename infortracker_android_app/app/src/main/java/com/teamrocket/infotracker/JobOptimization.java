package com.teamrocket.infotracker;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class JobOptimization extends AppCompatActivity implements View.OnClickListener {
    private final static String package_name = "com.teamrocket.infotracker";
    HashMap<String, Long> appCpuTimeBefor;
    long r_time=0;
    double powerUsage;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.joboptimization_main);
        Button button_1 = (Button) findViewById(R.id.button5);
        Button button_2 = (Button) findViewById(R.id.button6);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button5:
                appCpuTimeBefor = new HashMap<String, Long>(); //app running time
                appCpuTimeBefor = getAppCpuTime(package_name);

                try {
                    r_time=FCFS();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                powerUsage = measurePowerUsage(package_name, appCpuTimeBefor, r_time);
                Log.i("AAAAAAAAA", String.valueOf(powerUsage));
                Log.i("AAAAAAAAA", String.valueOf(r_time));
                textView = (TextView) findViewById(R.id.textView24);
                //textView.setText("CPU usage: " + powerUsage*100+"%");
                textView.setText("Total finish time: " + r_time);

                break;
            case R.id.button6:
                appCpuTimeBefor = new HashMap<String, Long>(); //app running time
                appCpuTimeBefor = getAppCpuTime(package_name);

                try {
                    r_time=SJF();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                powerUsage = measurePowerUsage(package_name, appCpuTimeBefor, r_time);
                Log.i("AAAAAAAAA", String.valueOf(powerUsage));
                Log.i("AAAAAAAAA", String.valueOf(r_time));
                textView = (TextView) findViewById(R.id.textView24);
                //textView.setText("CPU usage: " + powerUsage*100+"%");
                textView.setText("Total finish time: " + r_time);

                break;

            default:
                break;


        }
    }

    public long FCFS() throws InterruptedException {
        long startTime= System.currentTimeMillis();
        JobThread job1= new JobThread(4000);
        JobThread job2= new JobThread(3500);
        JobThread job3= new JobThread(3000);
        JobThread job4= new JobThread(2500);
        JobThread job5= new JobThread(2000);
        JobThread job6= new JobThread(1500);
        JobThread job7= new JobThread(1000);
        JobThread job8= new JobThread(500);
        //JobThread job9= new JobThread(100);
        //JobThread job10= new JobThread(100);

        job1.start();
        job1.join();
        job2.start();
        job2.join();
        job3.start();
        job3.join();
        job4.start();
        job4.join();
        job5.start();
        job5.join();
        job6.start();
        job6.join();
        job7.start();
        job7.join();
        job8.start();
        job8.join();
        //job9.start();
        //job10.start();
/*
        job1.join();
        job2.join();
        job3.join();
        job4.join();
        job5.join();
        job6.join();
        job7.join();
        job8.join();
*/
        //job9.join();
        //job10.join();
        long endTime= System.currentTimeMillis();
        long totalTime=job1.getRunningTime()+job2.getRunningTime()+job3.getRunningTime()+job4.getRunningTime()+job5.getRunningTime()+job6.getRunningTime()+job7.getRunningTime()+job8.getRunningTime();

        return totalTime;
    }

    public long SJF() throws InterruptedException {
        long startTime= System.currentTimeMillis();
        JobThread job1= new JobThread(500);
        JobThread job2= new JobThread(1000);
        JobThread job3= new JobThread(1500);
        JobThread job4= new JobThread(2000);
        JobThread job5= new JobThread(2500);
        JobThread job6= new JobThread(3000);
        JobThread job7= new JobThread(3500);
        JobThread job8= new JobThread(4000);
        //JobThread job9= new JobThread(5000);
        //JobThread job10= new JobThread(5000);

        job1.start();
        job1.join();
        job2.start();
        job2.join();
        job3.start();
        job3.join();
        job4.start();
        job4.join();
        job5.start();
        job5.join();
        job6.start();
        job6.join();
        job7.start();
        job7.join();
        job8.start();
        job8.join();
/*
        job1.start();
        job2.start();
        job3.start();
        job4.start();
        job5.start();
        job6.start();
        job7.start();
        job8.start();
        //job9.start();
        //job10.start();

        job1.join();
        job2.join();
        job3.join();
        job4.join();
        job5.join();
        job6.join();
        job7.join();
        job8.join();
        //job9.join();
        //job10.join();
*/
        long endTime= System.currentTimeMillis();
        long totalTime=job1.getRunningTime()+job2.getRunningTime()+job3.getRunningTime()+job4.getRunningTime()+job5.getRunningTime()+job6.getRunningTime()+job7.getRunningTime()+job8.getRunningTime();

        return totalTime;
    }

    public double measurePowerUsage(String packageNameString, HashMap<String, Long> appCpuTimeBefor, long r_time) {
        //targetAppTime: CPU Running time of app, totalTime: Total CPU Running time
        HashMap<String, Long> appCpuTimeAfter = new HashMap<String, Long>();
        appCpuTimeAfter = getAppCpuTime(packageNameString);

        double totalPower = 0;
        double targetAppTimeAfter = appCpuTimeAfter.get("targetAppTime");
        double targetAppTimeBefor = appCpuTimeBefor.get("targetAppTime");
        Log.i("TAG", "appCpuTimeAfter " + targetAppTimeAfter);
        Log.i("TAG", "appCpuTimeBefor  " + targetAppTimeBefor);

        double totalTimeAfter = appCpuTimeAfter.get("totalTime");
        double totalTimeBefor = appCpuTimeBefor.get("totalTime");
        Log.i("TAG", "totalTimeAfter " + totalTimeAfter);
        Log.i("TAG", "totalTimeBefor " + totalTimeBefor);

        Log.i("AAAAAAAAA", "targetAppTimeAfter-targetAppTimeBefor"+String.valueOf(targetAppTimeAfter-targetAppTimeBefor));
        Log.i("AAAAAAAAA", "totalTimeAfter-totalTimeBefor"+String.valueOf(totalTimeAfter-totalTimeBefor));
        //double ratio = (targetAppTimeAfter - targetAppTimeBefor + r_time) / (totalTimeAfter - totalTimeBefor + r_time);
        //double ratio = (targetAppTimeAfter - targetAppTimeBefor ) / (totalTimeAfter - totalTimeBefor );
        //double ratio = (targetAppTimeAfter - targetAppTimeBefor + r_time/10) / (totalTimeAfter - totalTimeBefor + r_time/10);
        double ratio= (r_time/10)/(totalTimeAfter - totalTimeBefor);

        Log.i("TAG", "ratio" + ratio);

        //return  ratio; //second to hour
        return targetAppTimeAfter - targetAppTimeBefor;
    }

    public HashMap<String, Long> getAppCpuTime(String packageName) {
        HashMap<String, Long> appCpuTime = new HashMap<String, Long>();
        long totalTime = 0;
        long targetProcessTime = 0;

        ActivityManager am = (ActivityManager)getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo info : runningApps) {
            long time = getAppProcessTime(info.pid);
            totalTime += time;
            if (info.processName.contains(packageName)) {
                targetProcessTime += time; //one app may have multiple processes
            }
        }

        appCpuTime.put("totalTime", totalTime);
        appCpuTime.put("targetAppTime", targetProcessTime);

        return appCpuTime;
    }

    //Return CPU running time of some process
    private long getAppProcessTime(int pid) {
        FileInputStream in = null;
        String ret = null;
        try {
            in = new FileInputStream("/proc/" + pid + "/stat");
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            ret = os.toString();
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if (ret == null) {
            return 0;
        }

        String[] s = ret.split(" ");
        if (s == null || s.length < 17) {
            return 0;
        }

        long utime = string2Long(s[13]);
        long stime = string2Long(s[14]);
        long cutime = string2Long(s[15]);
        long cstime = string2Long(s[16]);

        return utime + stime + cutime + cstime;
    }


    private long string2Long(String s) {
        try {
            return Long.parseLong(s);
        } catch (NumberFormatException e) {
        }
        return 0;
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
