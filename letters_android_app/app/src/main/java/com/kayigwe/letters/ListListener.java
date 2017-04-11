package com.kayigwe.letters;

import android.view.View;
import android.widget.AdapterView;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;

/**
 * Created by kayigwe on 11/14/15.
 */
public class ListListener implements AdapterView.OnItemClickListener {

    List<RssItem> listItems;
    Activity activity;

    public ListListener(List<RssItem> listItems, Activity activity){
        this.listItems = listItems;
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        //TODO Auto-generated method stub
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(listItems.get(pos).getLink()));

        activity.startActivity(i);
    }
}
