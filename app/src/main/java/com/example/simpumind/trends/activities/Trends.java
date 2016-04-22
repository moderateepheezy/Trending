package com.example.simpumind.trends.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.simpumind.trends.R;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class Trends extends AppCompatActivity {

    private String request;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trends);

        ListView listView = (ListView) findViewById(R.id.list);

        Intent intent = getIntent();
        if (intent.hasExtra("value")) {
            request = intent.getStringExtra("value");
            //loadTweet();
        }

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(request)
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .build();

        listView.setAdapter(adapter);
    }
}
