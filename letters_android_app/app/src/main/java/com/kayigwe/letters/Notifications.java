<<<<<<< HEAD
package com.kayigwe.letters;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by kayigwe on 11/14/15.
 */
public class Notifications extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);
    }
}
=======
package com.kayigwe.letters;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

/**
 * Created by kayigwe on 11/14/15.
 */
public class Notifications extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications);

        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        ActionBar actionBar = getActionBar();
        actionBar.hide();
    }
}
>>>>>>> be6be32a53a27dfb2dcd40dfdea184c4e69bde08
