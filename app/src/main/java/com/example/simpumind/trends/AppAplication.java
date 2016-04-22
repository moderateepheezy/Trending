package com.example.simpumind.trends;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.anupcowkur.reservoir.Reservoir;
import com.example.simpumind.trends.api.ApiConstants;
import com.example.simpumind.trends.api.TwitterApiService;
import com.example.simpumind.trends.api.TwitterServiceProvider;
import com.example.simpumind.trends.util.BusProvider;
import com.orm.SugarApp;
import com.squareup.otto.Bus;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;
import retrofit.RestAdapter;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by simpumind on 3/15/16.
 */
public class AppAplication extends SugarApp{

    private static final String TWITTER_KEY = "Pxx3sO5o7HXCbW9OHPj46KXoN";
    private static final String TWITTER_SECRET = "yrNQwx1RTXv6suOl2PpdAtnl5V2asBlnc81wfUbO5Iu5xNnnTQ";

    private static Context mAppContext;

    private TwitterServiceProvider mTwitterService;
    private Bus bus = BusProvider.getInstance();

    @Override
    public void onCreate() {
        super.onCreate();



        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/ufonts.com_gotham-medium.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        this.setAppContext(getApplicationContext());
        mTwitterService = new TwitterServiceProvider(buildApi(), bus);
        bus.register(mTwitterService);
        bus.register(this); //listen to "global" events

    }

    private TwitterApiService buildApi() {
        return new RestAdapter.Builder()
                .setEndpoint(ApiConstants.TWITTER_SEARCH_URL)
                .build()
                .create(TwitterApiService.class);
    }

    /*	@Subscribe
	public void onApiError(ApiErrorEvent event) {
		toast("Something went wrong, please try again.");
		Log.e("ReaderApp", event.getErrorMessage());
	}*/

    /*public static TwitterSearchApplication getInstance(){
        return mInstance;
    }*/
    public static Context getAppContext() {
        return mAppContext;
    }
    public void setAppContext(Context mAppContext) {
        this.mAppContext = mAppContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
        MultiDex.install(base);

    }

}
