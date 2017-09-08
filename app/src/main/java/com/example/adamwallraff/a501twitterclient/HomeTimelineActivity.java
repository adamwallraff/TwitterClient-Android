package com.example.adamwallraff.a501twitterclient;

import android.nfc.Tag;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import Model.JSON;
import Model.Tweet;

public class HomeTimelineActivity extends AppCompatActivity {

    private static final String TAG = "HomeTimelineActivity";

    private ListView tweetListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_timeline);

        setupTweetListView();

    }

    private void setupTweetListView(){
        tweetListView = (ListView) findViewById(R.id.tweet_list_view);

        ArrayList<Tweet> allTweets = JSON.getTweets(this, true);

        String[] listItems = new String[allTweets.size()];

        for (int index = 0; index < allTweets.size(); index++){
            listItems[index] = allTweets.get(index).text;
        }

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);

        tweetListView.setAdapter(adapter);

    }

}
