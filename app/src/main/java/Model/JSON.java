package Model;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by adamwallraff on 8/30/17.
 */

public class JSON {

    private static final String TAG = "JSON";

    public static String getSampleJSONAsString(Context context){
        StringBuilder stringBuilder = new StringBuilder();

        String jsonString = null;

        try {
            InputStream stream = context.getAssets().open("Tweet.json");
            Integer jsonDataLength = stream.available();

            byte[] buffer = new byte[jsonDataLength];
            stream.read(buffer);
            stream.close();

            jsonString = new String(buffer, "UTF-8");

        } catch (Exception exception){
            Log.d(TAG, "getSampleJSON: ERROR - " + exception);
        }

        return jsonString;

    }

    public static ArrayList<AWTweet> getTweets(Context context, Boolean usingSampleJSON) {

        ArrayList<AWTweet> tweets = new ArrayList<AWTweet>();

        try {
            JSONArray tweetsJSON = new JSONArray(getSampleJSONAsString(context));

            for (Integer i = 0; i < tweetsJSON.length(); i++){
                JSONObject tweetJSON = tweetsJSON.getJSONObject(i);

                Log.d(TAG, "getTweets: TweetText - " + tweetJSON.get("text"));

                tweets.add(new AWTweet(tweetJSON));
            }

        }catch (Exception exception){
            Log.d(TAG, "getTweets: ERROR - " + exception);
        }

        return tweets;

    }

}
