package com.junglerumble.junglerumble;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.view.View;
import android.view.View.OnClickListener;


public class MainActivity extends ActionBarActivity {

    private ImageView imgView;
    private ImageButton m_imgBtn, c_imgBtn, e_imgBtn;
    private TextView result_tv, count_tv;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //buttons initializaton
        m_imgBtn = (ImageButton) findViewById(R.id.btnMouse);
        c_imgBtn = (ImageButton) findViewById(R.id.btnCat);
        e_imgBtn = (ImageButton) findViewById(R.id.btnElephant);

        //image view initialization
        imgView = (ImageView) findViewById(R.id.viewCmp);

        //initialization of results and score variables in TextView
        result_tv = (TextView) findViewById(R.id.textScore);
        count_tv = (TextView) findViewById(R.id.textCount);

        MyOnClickListener myOnClickListener = new MyOnClickListener();

        m_imgBtn.setOnClickListener(myOnClickListener);
        c_imgBtn.setOnClickListener(myOnClickListener);
        e_imgBtn.setOnClickListener(myOnClickListener);
    }

    private class MyOnClickListener implements OnClickListener{

        @Override
        public void onClick(View v) {
            //To do auto-generated method stub

            int rand = (int)(Math.random()*3 + 1 );

            count++;
            switch(rand){

                case 1:

                    imgView.setImageResource(R.drawable.rat);

                    switch(v.getId()){

                        case R.id.btnMouse:
                            result_tv.setText("Result:"+"Tied!");

                            count_tv.setText("Round:" + count);
                            break;
                        case R.id.btnCat:
                            result_tv.setText("Result:"+"Win!");
                            count_tv.setText("Round:"+count);
                            break;

                        case R.id.btnElephant:
                            result_tv.setText("Result:"+"Lose!");
                            count_tv.setText("Round:"+count);
                            break;

                    }
                    break;

                case 2:
                    imgView.setImageResource(R.drawable.cat);

                    switch(v.getId()){
                        case R.id.btnMouse:
                            result_tv.setText("Result" + "Lose!");
                            count_tv.setText("Round:" + count);
                            break;
                        case R.id.btnCat:
                            result_tv.setText("Result" + "Tie!");
                            count_tv.setText("Round:" + count);
                            break;
                        case R.id.btnElephant:
                            result_tv.setText("Result" + "Win!");
                            count_tv.setText("Round:" + count);
                            break;



                    }
                    break;


                case 3:
                    imgView.setImageResource(R.drawable.elephant);

                    switch(v.getId()){
                        case R.id.btnMouse:
                            result_tv.setText("Result:"+"Win!");
                            count_tv.setText("Round:"+count);
                            break;
                        case R.id.btnCat:
                            result_tv.setText("Result:"+"Lose!");
                            count_tv.setText("Round:"+count);
                            break;
                        case R.id.btnElephant:
                            result_tv.setText("Result:"+"Tie!");
                            count_tv.setText("Round:"+count);
                            break;
                    }
                    break;

            }

        }
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
