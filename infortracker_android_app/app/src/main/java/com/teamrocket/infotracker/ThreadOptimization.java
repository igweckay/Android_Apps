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

public class ThreadOptimization extends AppCompatActivity implements View.OnClickListener {
    private final static String package_name = "com.teamrocket.infotracker";
    private Context mContext;

    private MatrixMultiplication matrix;
    private MatrixMultiplication_2 matrix_2;
    private MatrixMultiplication_3 matrix_3;
    private double powerUsage;
    long r_time;
    HashMap<String, Long> appCpuTimeBefor;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.thread_main);

        Button button_1 = (Button) findViewById(R.id.button);
        Button button_2 = (Button) findViewById(R.id.button2);
        Button button_3 = (Button) findViewById(R.id.button3);
        Button button_4 = (Button) findViewById(R.id.button4);
        button_1.setOnClickListener(this);
        button_2.setOnClickListener(this);
        button_3.setOnClickListener(this);
        button_4.setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                appCpuTimeBefor = new HashMap<String, Long>(); //app running time
                appCpuTimeBefor = getAppCpuTime(package_name);

                matrix = new MatrixMultiplication();
                r_time = 0;
                try {
                    r_time = matrix.main_serial(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                powerUsage = measurePowerUsage(package_name, appCpuTimeBefor, r_time);
                Log.i("AAAAAAAAA", String.valueOf(powerUsage));
                Log.i("AAAAAAAAA", String.valueOf(r_time));
                textView = (TextView) findViewById(R.id.textView23);
                textView.setText("CPU usage: " + powerUsage*100+"%");
                break;
            case R.id.button2:
                appCpuTimeBefor = new HashMap<String, Long>(); //app running time
                appCpuTimeBefor = getAppCpuTime(package_name);

                matrix = new MatrixMultiplication();
                r_time = 0;
                try {
                    r_time = matrix.main_parallel(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                powerUsage = measurePowerUsage(package_name, appCpuTimeBefor, r_time);
                Log.i("AAAAAAAAA", String.valueOf(powerUsage));
                Log.i("AAAAAAAAA", String.valueOf(r_time));
                textView = (TextView) findViewById(R.id.textView23);
                textView.setText("CPU usage: " + powerUsage*100+"%");
                break;
            case R.id.button3:
                appCpuTimeBefor = new HashMap<String, Long>(); //app running time
                appCpuTimeBefor = getAppCpuTime(package_name);

                matrix_2 = new MatrixMultiplication_2();
                r_time = 0;
                try {
                    r_time = matrix_2.main_parallel(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                powerUsage = measurePowerUsage(package_name, appCpuTimeBefor, r_time);
                Log.i("AAAAAAAAA", String.valueOf(powerUsage));
                Log.i("AAAAAAAAA", String.valueOf(r_time));
                textView = (TextView) findViewById(R.id.textView23);
                textView.setText("CPU usage: " + powerUsage*100+"%");
                break;
            case R.id.button4:
                appCpuTimeBefor = new HashMap<String, Long>(); //app running time
                appCpuTimeBefor = getAppCpuTime(package_name);

                matrix_3 = new MatrixMultiplication_3();
                r_time = 0;
                try {
                    r_time = matrix_3.main_parallel(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                powerUsage = measurePowerUsage(package_name, appCpuTimeBefor, r_time);
                Log.i("AAAAAAAAA", String.valueOf(powerUsage));
                Log.i("AAAAAAAAA", String.valueOf(r_time));
                textView = (TextView) findViewById(R.id.textView23);
                textView.setText("CPU usage: " + powerUsage*100+"%");
                break;

            default:
                break;
        }


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

        return  ratio; //second to hour
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
