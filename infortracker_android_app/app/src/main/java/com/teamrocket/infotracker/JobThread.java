package com.teamrocket.infotracker;

import android.util.Log;

/**
 * Created by ShangLiu on 12/8/15.
 */
public class JobThread extends Thread {
    private int length;
    private long sum;
    long startTime;
    long endTime;

    public JobThread(){
        Log.i("TAG", "JobThread");
    }

    public JobThread(int len){
        Log.i("TAG", "JobThread");
        this.startTime=System.currentTimeMillis();
        this.length=len;
        this.sum=0;
    }

    public void run(){
        int i;
        int j;
        for(i=0;i<length;i++){
            for(j=0;j<length;j++){
                sum=sum+1;
            }
        }
        endTime=System.currentTimeMillis();
        Log.i("running time",""+(endTime-startTime)+"   "+length);
    }

    public long getRunningTime(){
        //Log.i("running time",""+(endTime-startTime)+"   "+length);

        return endTime-startTime;
    }
}
