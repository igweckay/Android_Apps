<<<<<<< HEAD
package com.kayigwe.letters;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.R.drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.app.Activity;
import android.content.Intent;

public class MainActivity extends Activity implements View.OnClickListener{

    ImageButton explorebtn;
    ImageButton sharebtn;
    ImageButton compilebtn;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        explorebtn = (ImageButton)findViewById(R.id.explorebtn);
        explorebtn.setOnClickListener((OnClickListener) this);

        sharebtn = (ImageButton)findViewById(R.id.sharebtn);
        sharebtn.setOnClickListener((OnClickListener) this);

        compilebtn = (ImageButton)findViewById(R.id.compilebtn);
        compilebtn.setOnClickListener((OnClickListener) this);

    }

    private void explorebtnClick(){
        Intent intent = new Intent(MainActivity.this, Notifications.class);
        startActivity(intent);
    }

    private void sharebtnClick(){
        Intent intent = new Intent(MainActivity.this, ShareActivity.class);
        startActivity(intent);
    }

    private void compilebtnClick(){
        Intent intent = new Intent(MainActivity.this, Categories.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.explorebtn:
                explorebtnClick();
                break;
            case R.id.sharebtn:
                sharebtnClick();
                break;
            case R.id.compilebtn:
                compilebtnClick();
                break;
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
=======
package com.kayigwe.letters;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.R.drawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.app.Activity;
import android.content.Intent;
import android.app.ActionBar;

public class MainActivity extends Activity implements View.OnClickListener{

    ImageButton explorebtn;
    ImageButton sharebtn;
    ImageButton compilebtn;

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        explorebtn = (ImageButton)findViewById(R.id.explorebtn);
        explorebtn.setOnClickListener((OnClickListener) this);

        sharebtn = (ImageButton)findViewById(R.id.sharebtn);
        sharebtn.setOnClickListener((OnClickListener) this);

        compilebtn = (ImageButton)findViewById(R.id.compilebtn);
        compilebtn.setOnClickListener((OnClickListener) this);

        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        actionBar.hide();

    }

    private void explorebtnClick(){
        Intent intent = new Intent(MainActivity.this, Notifications.class);
        startActivity(intent);
    }

    private void sharebtnClick(){
        Intent intent = new Intent(MainActivity.this, ShareActivity.class);
        startActivity(intent);
    }

    private void compilebtnClick(){
        Intent intent = new Intent(MainActivity.this, Categories.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.explorebtn:
                explorebtnClick();
                break;
            case R.id.sharebtn:
                sharebtnClick();
                break;
            case R.id.compilebtn:
                compilebtnClick();
                break;
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
>>>>>>> be6be32a53a27dfb2dcd40dfdea184c4e69bde08
