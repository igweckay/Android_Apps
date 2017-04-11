<<<<<<< HEAD
package com.kayigwe.letters;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by kayigwe on 11/14/15.
 */
public class Categories extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_activity);
    }
}
=======
package com.kayigwe.letters;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;

import java.net.MalformedURLException;

/**
 * Created by kayigwe on 11/14/15.
 */
public class Categories extends Activity implements View.OnClickListener{

    ImageButton wrldnws;
    private MobileServiceClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.categories_activity);

        wrldnws = (ImageButton)findViewById(R.id.worldnewsbtn);
        wrldnws.setOnClickListener((View.OnClickListener) this);

        try {
            mClient = new MobileServiceClient(
                    "https://hellofakeproject.azure-mobile.net/",
                    "nCaFUKPYzwkzuXfkXrBVpHdnLKHyyx93",
                    this
            );
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


        Item item = new Item();
        item.Text = "Awesome item";
        mClient.getTable(Item.class).insert(item, new TableOperationCallback<Item>() {
            public void onCompleted(Item entity, Exception exception, ServiceFilterResponse response) {
                if (exception == null) {
                    // Insert succeeded
                } else {
                    // Insert failed
                }
            }
        });

        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        actionBar.hide();


    }

    private void worldnewsbtnClick(){
        Intent intent = new Intent(Categories.this, ReaderAppActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.worldnewsbtn:
                worldnewsbtnClick();
                break;
        }
    }
}
>>>>>>> be6be32a53a27dfb2dcd40dfdea184c4e69bde08
