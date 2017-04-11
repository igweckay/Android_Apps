package com.example.strengthbuilder;

import android.app.Activity;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
/**
 * Created by kayigwe on 1/17/16.
 */
public class Bluetooth extends Activity {

    Button btn;
    Button display;
    ProgressDialog progressBar;
    private int progressBarStatus = 0;
    private Handler progressBarHandler = new Handler();
    TextView textView;

    private long timeRemaining = 0;
    int x = 0;

    private String NAME = "StregnthBuilder";
    private UUID MY_UUID = UUID.fromString("0c312388-5d09-4f44-b670-5461605f0b1e");

    private Handler serverHandler = new Handler() {
        public void handleMessage(Message msg) {
            TextView txv_status = (TextView) findViewById(R.id.txv_BTStatus);
            txv_status.setText("Connect with a client successfully!");
            Button btn_startgame = (Button)findViewById(R.id.btn_Start);
            btn_startgame.setEnabled(true);
            super.handleMessage(msg);
        }
    };

    private Handler clientHandler = new Handler() {
        public void handleMessage(Message msg) {
            TextView txv_status = (TextView) findViewById(R.id.txv_BTStatus);
            txv_status.setText("Connect to the Server successfully!");
            Button btn_startgame = (Button)findViewById(R.id.btn_Start);
            btn_startgame.setEnabled(true);
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vs_otherplayer_home_);
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if(!mBluetoothAdapter.isEnabled()) {
            //如果蓝牙设备不可用的话,创建一个intent对象,该对象用于启动一个Activity,提示用户启动蓝牙适配器
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivity(intent);
        }

        Button btn_pair=(Button)findViewById(R.id.btn_pair);
        btn_pair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivity(intent);
            }
        });

        Button btn_server=(Button)findViewById(R.id.btn_Server);
        btn_server.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txv_status = (TextView) findViewById(R.id.txv_BTStatus);
                txv_status.setText("Please wait to be connected by a client.");
                ListView listView = (ListView) findViewById(R.id.lsv_Device);
                listView.setVisibility(View.INVISIBLE);
                AcceptThread acceptThread = new AcceptThread();
                acceptThread.start();
            }
        });

        Button btn_client=(Button)findViewById(R.id.btn_Client);
        btn_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView txv_status = (TextView) findViewById(R.id.txv_BTStatus);
                txv_status.setText("Please click the device below to connect.");
                BluetoothAdapter mBluetoothAdapter;
                BluetoothDevice mmDevice = null;
                mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                // If there are paired devices
                final List<String> deviceNameList = new ArrayList<String>();

                if (pairedDevices.size() > 0) {
                    // Loop through paired devices
                    for (BluetoothDevice device : pairedDevices) {
                        // Add the name and address to an array adapter to show in a ListView

                        Log.d("mytag", device.getName());
                        Log.d("mytag", device.getAddress());
                        deviceNameList.add(device.getName());
                    }
                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                        Bluetooth.this, android.R.layout.simple_list_item_1, deviceNameList);
                final ListView listView = (ListView) findViewById(R.id.lsv_Device);
                listView.setAdapter(adapter);
                listView.setVisibility(View.VISIBLE);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String deviceName = deviceNameList.get(position);

                        BluetoothAdapter mBluetoothAdapter;
                        BluetoothDevice mmDevice = null;
                        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                        for (BluetoothDevice device : pairedDevices) {
                            // Add the name and address to an array adapter to show in a ListView
                            // 考虑只有一个的情况
                            //mmDevice = device;
                            if (device.getName().equals(deviceName)) {
                                mmDevice = device;
                                break;
                            }
                        }
                        ConnectThread connectThread = new ConnectThread(mmDevice);
                        connectThread.start();

                    }
                });
            }
        });

        Button btn_startgame = (Button)findViewById(R.id.btn_Start);
        btn_startgame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Bluetooth.this, ToDoActivity.class);//////////!!!!!!!!!!
                startActivity(intent);
            }
        });

    }

    private class AcceptThread extends Thread {
        private final BluetoothServerSocket mmServerSocket;
        BluetoothAdapter mBluetoothAdapter;
        BluetoothSocket socket = null;

        public AcceptThread() {
            // Use a temporary object that is later assigned to mmServerSocket,
            // because mmServerSocket is final
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            BluetoothServerSocket tmp = null;

            try {
                // MY_UUID is the app's UUID string, also used by the client code
                tmp = mBluetoothAdapter.listenUsingRfcommWithServiceRecord(NAME, MY_UUID);
            } catch (IOException e) { }
            mmServerSocket = tmp;
        }

        public void run() {
            // Keep listening until exception occurs or a socket is returned

            mBluetoothAdapter.cancelDiscovery();

            Log.d("mytag", "accept thread running!");
            while (true) {
                try {
                    socket = mmServerSocket.accept();
                } catch (IOException e) {
                    break;
                }
                // If a connection was accepted
                if (socket != null) {
                    // Do work to manage the connection (in a separate thread)
                    //change the static socket in MySocket class
                    MySocket.Socket = socket;
                    serverHandler.sendMessage(serverHandler.obtainMessage());
                    try{
                        mmServerSocket.close();
                    } catch (IOException e){}
                    break;
                }
            }
        }
        /** Will cancel the listening socket, and cause the thread to finish */

        public void cancel() {
            try {
                mmServerSocket.close();
            } catch (IOException e) { }
        }
    }

    private class ConnectThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final BluetoothDevice mmDevice;

        public ConnectThread(BluetoothDevice device) {
            // Use a temporary object that is later assigned to mmSocket,
            // because mmSocket is final
            BluetoothSocket tmp = null;
            mmDevice = device;

            // Get a BluetoothSocket to connect with the given BluetoothDevice
            try {
                // MY_UUID is the app's UUID string, also used by the server code
                tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID);
            } catch (IOException e) { }
            mmSocket = tmp;
        }

        public void run() {
            // Cancel discovery because it will slow down the connection
            BluetoothAdapter mBluetoothAdapter= BluetoothAdapter.getDefaultAdapter();

            mBluetoothAdapter.cancelDiscovery();
            //////////////////////////////////////////


            Log.d("mytag", "connect thread running");

            try {
                // Connect the device through the socket. This will block
                // until it succeeds or throws an exception
                mmSocket.connect();
            } catch (IOException connectException) {
                // Unable to connect; close the socket and get out
                Log.d("mytag", "unable to connect,close the socket and get out");
                try {
                    mmSocket.close();
                } catch (IOException closeException) { }
                return;
            }

            // Do work to manage the connection (in a separate thread)
            MySocket.Socket=mmSocket;
            clientHandler.sendMessage(clientHandler.obtainMessage());

        }

        /** Will cancel an in-progress connection, and close the socket */
        public void cancel() {

            try {
                mmSocket.close();
            } catch (IOException e) { }

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("mytag", "onStart");
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("mytag", "onResume");
    }
    @Override
    protected void onPause() {
        super.onPause();
        Log.d("mytag", "onPause");
    }
    @Override
    protected void onStop() {
        super.onStop();
        Log.d("mytag", "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("mytag", "onDestroy");
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("mytag", "onRestart");
        Button btn_startgame = (Button)findViewById(R.id.btn_Start);
        btn_startgame.setEnabled(false);
        TextView txv_status = (TextView) findViewById(R.id.txv_BTStatus);
        txv_status.setText("Please choose to be server or client again!");
        ListView listView = (ListView) findViewById(R.id.lsv_Device);
        listView.setVisibility(View.INVISIBLE);
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
        startActivity(new Intent(getApplicationContext(), Bluetooth.class));

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
