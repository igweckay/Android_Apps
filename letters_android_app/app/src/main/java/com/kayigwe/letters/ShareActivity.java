<<<<<<< HEAD
package com.kayigwe.letters;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;
import android.content.Intent;
import android.text.Html;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import android.net.Uri;
import com.facebook.share.widget.ShareDialog;
import io.fabric.sdk.android.Fabric;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

/**
 * Created by kayigwe on 11/14/15.
 */
public class ShareActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.share_activity);
        TwitterAuthConfig authConfig =  new TwitterAuthConfig("qYoWkoyrCvaUFh9SqcQFtSvAT", "NbRyc4O9vBa6ae5pKeZyeZkYhQnKTYqlwv4opHKzw9wIuV1JjZ");
        Fabric.with(this, new Twitter(authConfig));
    }


    public void onBtn1Clicked(View v)
    {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"recipient@example.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
        i.putExtra(Intent.EXTRA_TEXT   , Html.fromHtml("<html>body of email</html>"));
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ShareActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onBtn2Clicked(View v)
    {
        TweetComposer composer = Twitter.tweetComposer;

    }

    public void onBtn3Clicked(View v)
    {
       ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://developers.facebook.com"))
              .build();

        ShareDialog.show(ShareActivity.this, content);

    }



}
=======
package com.kayigwe.letters;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by kayigwe on 11/14/15.
 */
public class ShareActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.share_activity);

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
