package com.example.simpumind.trends.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.simpumind.trends.R;
import com.example.simpumind.trends.activities.MainerTabActivity;
import com.example.simpumind.trends.activities.Trending;
import com.example.simpumind.trends.adapter.TweetAdapter;
import com.example.simpumind.trends.api.ApiConstants;
import com.example.simpumind.trends.models.TrendListItem;
import com.example.simpumind.trends.models.Trends;
import com.example.simpumind.trends.util.Authenticated;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.google.gson.Gson;
import com.twitter.sdk.android.tweetui.SearchTimeline;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by SimpuMind on 4/18/16.
 */
public class TrendsFragment extends Fragment{

    public static List<TrendListItem> trendListItem;
    ObservableRecyclerView recyclerView;
    public static String textValue;

    private ProgressBar infinityLoading;

     TweetTimelineListAdapter adapter;

    public TrendsFragment() {
        // Required empty public constructor
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.main, container, false);

        Activity parentActivity = getActivity();
        recyclerView = (ObservableRecyclerView) rootView.findViewById(R.id.scroll);
        recyclerView.setLayoutManager(new GridLayoutManager(parentActivity, 2));
        recyclerView.setHasFixedSize(false);
        recyclerView.setTouchInterceptionViewGroup((ViewGroup) parentActivity.findViewById(R.id.container));

        if (parentActivity instanceof ObservableScrollViewCallbacks) {
            recyclerView.setScrollViewCallbacks((ObservableScrollViewCallbacks) parentActivity);
        }
        trendListItem = new ArrayList<>();
        infinityLoading = (ProgressBar) rootView.findViewById(R.id.loading);
        //   new Handler().postDelayed(new Runnable() {
        //     @Override
        //     public void run() {
        downloadTweets();
        //   }
        //  }, SPLASH_TIME_OUT);
        return rootView;
    }

    class MyAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        final static String TwitterTokenURL = "https://api.twitter.com/oauth2/token";
        @Override
        protected Void doInBackground(Void... params) {
            String result = null;
            result = getTwitterStream();
            try
            {
                JSONArray wrapper = new JSONArray(result);
                textValue = result;
                JSONObject trendsObject = wrapper.getJSONObject(0);
                JSONArray trendsArray = trendsObject.getJSONArray("trends");
                for(int i = 0; i < trendsArray.length(); i++){
                    Trends trn = new Trends((JSONObject) trendsArray.get(i));

                    if(trn.getNames()!= null)
                        trendListItem.add(new TrendListItem(trn.getTweetVolume(), trn.getNames(),
                                trn.getQuery(), trn.getUrl()));

                }
            }
            catch (JSONException e)
            {
                // handle the error here ...
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private String getTwitterStream() {
            String results = null;

            // Step 1: Encode consumer key and secret
            try {
                // URL encode the consumer key and secret
                String urlApiKey = URLEncoder.encode(ApiConstants.CONSUMER_KEY, "UTF-8");
                String urlApiSecret = URLEncoder.encode(ApiConstants.CONSUMER_SECRET, "UTF-8");

                // Concatenate the encoded consumer key, a colon character, and the
                // encoded consumer secret
                String combined = urlApiKey + ":" + urlApiSecret;

                // Base64 encode the string
                String base64Encoded = Base64.encodeToString(combined.getBytes(), Base64.NO_WRAP);

                // Step 2: Obtain a bearer token
                HttpPost httpPost = new HttpPost(TwitterTokenURL);
                httpPost.setHeader("Authorization", "Basic " + base64Encoded);
                httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
                httpPost.setEntity(new StringEntity("grant_type=client_credentials"));
                String rawAuthorization = getResponseBody(httpPost);
                Authenticated auth = jsonToAuthenticated(rawAuthorization);

                // Applications should verify that the value associated with the
                // token_type key of the returned object is bearer
                if (auth != null && auth.token_type.equals("bearer")) {

                    // Step 3: Authenticate API requests with bearer token
                    HttpGet httpGet = new HttpGet(ApiConstants.TWITTER_TREND_SEARCH_CODE);

                    // construct a normal HTTPS request and include an Authorization
                    // header with the value of Bearer <>
                    httpGet.setHeader("Authorization", "Bearer " + auth.access_token);
                    httpGet.setHeader("Content-Type", "application/json");
                    // update the results with the body of the response
                    results = getResponseBody(httpGet);
                }
            } catch (UnsupportedEncodingException ex) {
            } catch (IllegalStateException ex1) {
            }
            Log.d("URL_Connect 1", "" + results);
            return results;
        }

        private String getResponseBody(HttpRequestBase request) {
            StringBuilder sb = new StringBuilder();
            try {

                DefaultHttpClient httpClient = new DefaultHttpClient(new BasicHttpParams());
                HttpResponse response = httpClient.execute(request);
                int statusCode = response.getStatusLine().getStatusCode();
                String reason = response.getStatusLine().getReasonPhrase();

                if (statusCode == 200) {

                    HttpEntity entity = response.getEntity();
                    InputStream inputStream = entity.getContent();

                    BufferedReader bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);
                    String line = null;
                    while ((line = bReader.readLine()) != null) {
                        sb.append(line);
                    }
                } else {
                    sb.append(reason);
                }
            } catch (UnsupportedEncodingException ex) {
            } catch (ClientProtocolException ex1) {
            } catch (IOException ex2) {
            }
            return sb.toString();
        }

        private Authenticated jsonToAuthenticated(String rawAuthorization) {
            Authenticated auth = null;
            if (rawAuthorization != null && rawAuthorization.length() > 0) {
                try {
                    Gson gson = new Gson();
                    auth = gson.fromJson(rawAuthorization, Authenticated.class);
                } catch (IllegalStateException ex) {
                    // just eat the exception
                }
            }
            return auth;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("Fileer", trendListItem.toString());
            Task task = new Task();
            String[] tagList = task.getTweetsTag(trendListItem);
            recyclerView.setAdapter(new TweetAdapter(trendListItem, tagList, new TweetAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(TrendListItem item) {
                    Toast.makeText(getActivity(), "Item Clicked", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getActivity(), Trending.class);
                    intent.putExtra("value", item.getQuery());
                    startActivity(intent);
                }
            }));

            infinityLoading.setVisibility(View.GONE);
        }
    }


    public void downloadTweets() {
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            Log.d("Validae", "Text Value" + textValue +  trendListItem.toString());
            new MyAsyncTask().execute();
        } else {
            Log.v("Post", "No network connection available.");
        }
    }

    public class Task{

        public  int ix;

        public  String[] getTweetsTag(List<TrendListItem> trendListItem){

            final String[] tagList = new String[trendListItem.size()];
            for( ix = 0; ix < trendListItem.size(); ix++){
                String request = trendListItem.get(ix).getNames();
                final SearchTimeline timeline = new SearchTimeline.Builder()
                        .query(request)
                        .build();
                adapter = new TweetTimelineListAdapter.Builder(getActivity())
                        .setTimeline(timeline)
                        .build();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run(){
                        tagList[ix] = adapter.getItem(ix).text;
                    }
                }, 5000);
            }

            return tagList;
        }
    }
}
