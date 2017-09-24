package com.example.adamwallraff.a501twitterclient;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterApiClient;
import com.twitter.sdk.android.core.TwitterCore;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.FixedTweetTimeline;
import com.twitter.sdk.android.tweetui.Timeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.TwitterListTimeline;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;

public class HomeTimelineActivity extends Fragment {

    private static final String TAG = "HomeTimelineActivity";

    private ListView tweetListView;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;

    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        setupTweetListView();
    }

    private void setupTweetListView() {
        tweetListView = (ListView) view.findViewById(R.id.tweet_list_view);

        TwitterApiClient apiClient = TwitterCore.getInstance().getApiClient();

        Call<List<Tweet>> userTimeline = apiClient.getStatusesService().homeTimeline(null, null, null, null, null, null, null);

        userTimeline.enqueue(new Callback<List<Tweet>>() {
            @Override
            public void success(Result<List<Tweet>> result) {
                for (Tweet tweet : result.data){
                    Log.d(TAG, "Tweet: " + tweet.text);
                    Timeline<Tweet> tweetTimeline = new FixedTweetTimeline.Builder().setTweets(result.data).build();
                    updateListViewWithList(tweetTimeline);

                }
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d(TAG, "failure: " + exception);

            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_home_timeline, null);

        Twitter.initialize(getActivity());

        TwitterSession session = TwitterCore.getInstance().getSessionManager().getActiveSession();

        if (session != null) {
            Log.d(TAG, "onCreate: Auth Token" + session.getAuthToken());
            setupTweetListView();

        } else {
            Log.d(TAG, "onCreate: Twitter SESSION is NULL");
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    void updateListViewWithList(Timeline<Tweet> tweets){
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter(getActivity(), tweets);

        tweetListView.setAdapter(adapter);

        tweetListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.d(TAG, "onItemClick: " + adapter.getItem(i).text);
            }
        });
    }



}
