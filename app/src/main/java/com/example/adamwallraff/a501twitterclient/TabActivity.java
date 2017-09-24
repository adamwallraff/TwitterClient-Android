package com.example.adamwallraff.a501twitterclient;

import android.app.ActivityGroup;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class TabActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab);

        mTabHost = (FragmentTabHost) findViewById(R.id.tab_host);
        mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        TabHost.TabSpec firstTab = (TabHost.TabSpec) mTabHost.newTabSpec("UserTimeline");

        firstTab.setIndicator("User Timeline");

        mTabHost.addTab(firstTab, HomeTimelineActivity.class, null);


    }
}
