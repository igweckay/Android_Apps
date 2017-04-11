package com.teamrocket.infotracker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.support.v7.app.AlertDialog;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    ListView listview;
    ArrayAdapter<String> adapter;
    String[] infotype = {"Memory", "CPU", "Battery", "Thread Optimization", "Job Optimization", "Phone Energy Optimization"};
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listview = (ListView) findViewById(R.id.listView1);
        adapter = new ArrayAdapter<String>(this, R.layout.listviewmainlayout, R.id.list_item, infotype);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getBaseContext(), adapterView.getItemIdAtPosition(i)+ " is selected", Toast.LENGTH_LONG).show();
                Toast.makeText(getBaseContext(), " calculating...", Toast.LENGTH_LONG).show();
                Log.i("TAG", "position: " + "" + i);
                if (i == 0){
                    startActivity(new Intent(getApplicationContext(), MemoryData.class));
                } else if (i == 1){
                    startActivity(new Intent(getApplicationContext(), CPUData.class));
                } else if (i == 2){
                    startActivity(new Intent(getApplicationContext(), BatteryData.class));
                } else if (i == 5){
                    startActivity(new Intent(getApplicationContext(), EnergyOptimization.class));
                }else if(i==3){
                    startActivity(new Intent(getApplicationContext(), ThreadOptimization.class));
                }
                else if(i==4){
                    startActivity(new Intent(getApplicationContext(), JobOptimization.class));
                }
            }
        });

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
