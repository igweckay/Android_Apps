package com.kayigwe.testerjungle;

import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


public class vs_otherplayer_level1_Activity extends ActionBarActivity {

    private BluetoothSocket socket = MySocket.Socket;
    InputStream mmInStream;
    OutputStream mmOutStream;


    byte[] buffer = new byte[1024];  // buffer store for the stream
    int bytes; // bytes returned from read()

    int thisUserChoice=-1;
    int otherUserChoice=-1;
    int vs_other_yourlife=5;
    int vs_other_otherlife=5;
    int gameOverFlag=0;

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Log.d("mytag", "mHandler start");
            ImageView othershow = (ImageView)findViewById(R.id.vs_other_otherShow);
            ImageView yourlifeshow = (ImageView)findViewById(R.id.vs_other_yourlife);
            ImageView otherlifeshow = (ImageView)findViewById(R.id.vs_other_otherlife);
            ImageView winlose=(ImageView)findViewById(R.id.vs_other_winlose);


            switch (otherUserChoice) {
                case 0:
                    othershow.setImageResource(R.drawable.mouse);
                    switch (thisUserChoice) {
                        case 0:
                            break;
                        case 1:
                            vs_other_otherlife--;
                            break;
                        case 2:
                            vs_other_yourlife--;
                            break;
                        default:
                            break;
                    }
                    break;
                case 1:
                    othershow.setImageResource(R.drawable.cat);
                    switch (thisUserChoice) {
                        case 0:
                            vs_other_yourlife--;
                            break;
                        case 1:
                            break;
                        case 2:
                            vs_other_otherlife--;
                            break;
                        default:
                            break;
                    }
                    break;
                case 2:
                    othershow.setImageResource(R.drawable.elephant);
                    switch (thisUserChoice) {
                        case 0:
                            vs_other_otherlife--;
                            break;
                        case 1:
                            vs_other_yourlife--;
                            break;
                        case 2:
                            break;
                        default:
                            break;
                    }
                    break;
                default:
                    break;
            }
            switch(vs_other_yourlife){
                case 5:
                    yourlifeshow.setImageResource(R.drawable.five_five);
                    break;
                case 4:
                    yourlifeshow.setImageResource(R.drawable.five_four);
                    break;
                case 3:
                    yourlifeshow.setImageResource(R.drawable.five_three);
                    break;
                case 2:
                    yourlifeshow.setImageResource(R.drawable.five_two);
                    break;
                case 1:
                    yourlifeshow.setImageResource(R.drawable.five_one);
                    break;
                case 0:
                    yourlifeshow.setImageResource(R.drawable.five_zero);
                    winlose.setImageResource(R.drawable.lose);
                    gameOverFlag=1;
                    break;
            }
            switch(vs_other_otherlife){
                case 5:
                    otherlifeshow.setImageResource(R.drawable.five_five);
                    break;
                case 4:
                    otherlifeshow.setImageResource(R.drawable.five_four);
                    break;
                case 3:
                    otherlifeshow.setImageResource(R.drawable.five_three);
                    break;
                case 2:
                    otherlifeshow.setImageResource(R.drawable.five_two);
                    break;
                case 1:
                    otherlifeshow.setImageResource(R.drawable.five_one);
                    break;
                case 0:
                    otherlifeshow.setImageResource(R.drawable.five_zero);
                    winlose.setImageResource(R.drawable.win);
                    gameOverFlag=1;
                    break;
            }
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vs_otherplayer_level1_);

        InputStream tmpIn = null;
        OutputStream tmpOut = null;

        // Get the input and output streams, using temp objects because
        // member streams are final

        try {
            tmpIn = socket.getInputStream();
            tmpOut = socket.getOutputStream();
        } catch (IOException e) {
            Log.d("mytag", "getInput/getOutput wrong!");
        }


        mmInStream = tmpIn;
        mmOutStream = tmpOut;

        Log.d("mytag", "getInput/getOutput success");

        Thread Readingthread =	new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d("mytag", "reading thread running!");
                while (true) {
                    try {
                        // Read from the InputStream
                        bytes = mmInStream.read(buffer);
                        switch(buffer[0]){
                            case 0:
                                otherUserChoice = 0;
                                break;
                            case 1:
                                otherUserChoice = 1;
                                break;
                            case 2:
                                otherUserChoice = 2;
                                break;
                            default:
                                break;
                        }
                        // Send the obtained bytes to the UI activity
                        TimeThread timeThread =new TimeThread(1000);
                        timeThread.start();

                    } catch (IOException e) {
                        break;
                    }
                }
            }
        });
        Readingthread.start();

        ImageButton btn_mouse = (ImageButton)findViewById(R.id.vs_other_mouse);
        btn_mouse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameOverFlag==0) {
                    thisUserChoice = 0;
//                thisUserChooseFlag=1;
                    byte[] sendBuffer = new byte[10];
                    sendBuffer[0] = 0;
                    try {
                        mmOutStream.write(sendBuffer);
                    } catch (IOException e) {
                        Log.d("mytag", "write wrong!");
                    }
                    ImageView youshow = (ImageView) findViewById(R.id.vs_other_youShow);
                    youshow.setImageResource(R.drawable.mouse);
                    ImageView othershow = (ImageView) findViewById(R.id.vs_other_otherShow);
                    othershow.setImageResource(R.drawable.questionmark);////////////////////////////////////////////////////
                }
            }
        });

        ImageButton btn_cat = (ImageButton)findViewById(R.id.vs_other_cat);
        btn_cat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameOverFlag==0) {
                    thisUserChoice = 1;
//                thisUserChooseFlag=1;
                    byte[] sendBuffer = new byte[10];
                    sendBuffer[0] = 1;
                    try {
                        mmOutStream.write(sendBuffer);
                    } catch (IOException e) {
                        Log.d("mytag", "write wrong!");
                    }
                    ImageView youshow = (ImageView) findViewById(R.id.vs_other_youShow);
                    youshow.setImageResource(R.drawable.cat);
                    ImageView othershow = (ImageView) findViewById(R.id.vs_other_otherShow);
                    othershow.setImageResource(R.drawable.questionmark);///
                }
            }
        });

        ImageButton btn_elephant = (ImageButton)findViewById(R.id.vs_other_elephant);
        btn_elephant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(gameOverFlag==0) {
                    thisUserChoice = 2;
//                thisUserChooseFlag=1;
                    byte[] sendBuffer = new byte[10];
                    sendBuffer[0] = 2;
                    try {
                        mmOutStream.write(sendBuffer);
                    } catch (IOException e) {
                        Log.d("mytag", "write wrong!");
                    }
                    ImageView youshow = (ImageView) findViewById(R.id.vs_other_youShow);
                    youshow.setImageResource(R.drawable.elephant);
                    ImageView othershow = (ImageView) findViewById(R.id.vs_other_otherShow);
                    othershow.setImageResource(R.drawable.questionmark);//
                }
            }
        });

    }

    private class TimeThread extends Thread {
        int Time;
        public TimeThread(int time) {
            Time=time;
        }
        public void run() {
            Log.d("mytag", "Timmer start");
            try {
                Thread.sleep(Time);
                Log.d("mytag", "sleep");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mHandler.sendMessage(mHandler.obtainMessage());
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vs_otherplayer_level1_, menu);
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

    public void onBackPressed(){
        try {
            MySocket.Socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onBackPressed();
    }
}
