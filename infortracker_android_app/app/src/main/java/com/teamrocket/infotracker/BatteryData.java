package com.teamrocket.infotracker;

import android.app.Activity;
import android.os.BatteryManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ProgressBar;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import org.w3c.dom.Text;


public class BatteryData extends Activity{
    private TextView mBatteryLevelText;
    private ProgressBar mBatteryLevelProgress;
    private BroadcastReceiver mReceiver;
    TextView firstView;
    TextView secondView;
    TextView thirdView;
    TextView fourthView;
    TextView fifthView;
    TextView sixthView;
    TextView seventhView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.battery_main);

        firstView = (TextView) findViewById(R.id.textView9);
        secondView = (TextView) findViewById(R.id.textView11);
        thirdView = (TextView) findViewById(R.id.textView13);
        fourthView = (TextView) findViewById(R.id.textView15);
        fifthView = (TextView) findViewById(R.id.textView17);
        sixthView = (TextView) findViewById(R.id.textView19);
        seventhView = (TextView) findViewById(R.id.textView21);

        mBatteryLevelText = (TextView) findViewById(R.id.textView7);
        mBatteryLevelProgress = (ProgressBar) findViewById(R.id.progressBar);
        mReceiver = new BatteryBroadcastReceiver();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    protected void onStart() {
        registerReceiver(mReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        super.onStart();
    }
    @Override
    protected void onStop() {
        unregisterReceiver(mReceiver);
        super.onStop();
    }
    private class BatteryBroadcastReceiver extends BroadcastReceiver {
        // private final static String BATTERY_LEVEL = "level";

        @Override
        public void onReceive(Context context, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            mBatteryLevelText.setText(getString(R.string.battery_level) + " " + level);
            mBatteryLevelProgress.setProgress(level);

            //new from TA code
            String action = intent.getAction();
            int status = intent.getIntExtra("status", 0);
            Log.i("battery", ""+status);
            int health = intent.getIntExtra("health", 0);
            if(health>=2) {
                secondView.setText("good");
            }
            else{
                secondView.setText("bad");
            }
            boolean present = intent.getBooleanExtra("present", false);
            int level1 = intent.getIntExtra("level", 0);
            fifthView.setText(""+level+"%");
            int scale = intent.getIntExtra("scale", 0);
            seventhView.setText(""+scale);
            int icon_small = intent.getIntExtra("icon-small", 0);
            int plugged = intent.getIntExtra("plugged", 0);
            if (plugged == 0){
                firstView.setText("no");
            } else if (plugged == 2){
                firstView.setText("yes");
            }
            int voltage = intent.getIntExtra("voltage", 0);
            thirdView.setText(""+voltage+"mV");
            int temperature = intent.getIntExtra("temperature", 0);
            fourthView.setText(""+(((temperature) - 273))+" Celcius");

            String technology = intent.getStringExtra("technology");
            sixthView.setText(technology);
            String statusString = "";
            if(action.equals(Intent.ACTION_BATTERY_CHANGED)){
                //battery level
                Log.d("Battery", "" + intent.getIntExtra("level", 0));
                //battery scal
                Log.d("Battery", "" + intent.getIntExtra("scale", 0));
                //battery voltage
                Log.d("Battery", "" + intent.getIntExtra("voltage", 0));
                //batterty temperature
                Log.d("Battery", "" + intent.getIntExtra("temperature", 0));

                //电池状态，返回是一个数字
                // BatteryManager.BATTERY_STATUS_CHARGING 表示是充电状态
                // BatteryManager.BATTERY_STATUS_DISCHARGING 放电中
                // BatteryManager.BATTERY_STATUS_NOT_CHARGING 未充电
                // BatteryManager.BATTERY_STATUS_FULL 电池满
            }

            //end of TA Code

        }
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
