package com.kayigwe.letters;

import android.app.ActionBar;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.util.Log;
import android.widget.ListView;
import android.view.View.OnClickListener;

import com.kayigwe.letters.RssReader;
import com.kayigwe.letters.ListListener;
import com.kayigwe.letters.RssItem;

import java.util.List;

/**
 * Created by kayigwe on 11/14/15.
 */
public class ReaderAppActivity extends Activity {

    private ReaderAppActivity local;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed_layout);

        local = this;

        GetRSSDataTask task = new GetRSSDataTask();

        //start download RSS task
        task.execute("http://rss.cnn.com/rss/cnn_us.rss");

        //Debug the thread name
        Log.d("ITCRssReader", Thread.currentThread().getName());
    }

    private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem>> {
        @Override
        protected List<RssItem> doInBackground(String... urls) {

            // Debug the task thread name
            Log.d("ITCRssReader", Thread.currentThread().getName());

            try {
                // Create RSS reader
                RssReader rssReader = new RssReader(urls[0]);

                // Parse RSS, get items
                return rssReader.getItems();

            } catch (Exception e) {
                Log.e("ITCRssReader", e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<RssItem> result) {

            // Get a ListView from main view
            ListView itcItems = (ListView) findViewById(R.id.feedlistView);

            // Create a list adapter
            ArrayAdapter<RssItem> adapter = new ArrayAdapter<RssItem>(local,android.R.layout.simple_list_item_1, result);
            // Set list adapter for the ListView
            itcItems.setAdapter(adapter);

            // Set list view item click listener
            itcItems.setOnItemClickListener(new ListListener(result, local));
        }
    }
}
