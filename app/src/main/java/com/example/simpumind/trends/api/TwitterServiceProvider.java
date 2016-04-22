package com.example.simpumind.trends.api;

import android.util.Log;

import com.example.simpumind.trends.AppAplication;
import com.example.simpumind.trends.events.SearchTweetsEvent;
import com.example.simpumind.trends.events.SearchTweetsEventFailed;
import com.example.simpumind.trends.events.SearchTweetsEventOk;
import com.example.simpumind.trends.events.TwitterGetTokenEvent;
import com.example.simpumind.trends.events.TwitterGetTokenEventFailed;
import com.example.simpumind.trends.events.TwitterGetTokenEventOk;
import com.example.simpumind.trends.util.PrefsController;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import static com.example.simpumind.trends.util.Util.getBase64String;

import java.io.UnsupportedEncodingException;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class TwitterServiceProvider {
	private static final String TAG = TwitterServiceProvider.class.getName();

	private TwitterApiService mApi;
	private Bus mBus;

	public TwitterServiceProvider(TwitterApiService api, Bus bus) {
		this.mApi = api;
		this.mBus = bus;
	}

	@Subscribe
	public void onLoadTweets(final SearchTweetsEvent event) {
		mApi.getTweetList("Bearer " + event.twitterToken, event.hashtag, new Callback<TweetList>() {

			@Override
			public void success(TweetList tweetList, Response response) {
				mBus.post(new SearchTweetsEventOk(tweetList));
			}

			@Override
			public void failure(RetrofitError error) {
				Log.e(TAG, error.toString(), error);
				mBus.post(new SearchTweetsEventFailed());
			}
		});
	}

	@Subscribe
	public void onGetToken(TwitterGetTokenEvent event) {
		try {
			mApi.getToken("Basic " + getBase64String(ApiConstants.BEARER_TOKEN_CREDENTIALS), "client_credentials", new Callback<TwitterTokenType>() {
				@Override
				public void success(TwitterTokenType token, Response response) {
					PrefsController.setAccessToken(AppAplication.getAppContext(), token.accessToken);
					PrefsController.setTokenType(AppAplication.getAppContext(), token.tokenType);
					mBus.post(new TwitterGetTokenEventOk());
				}

				@Override
				public void failure(RetrofitError error) {
					Log.e(TAG, error.toString(), error);
					mBus.post(new TwitterGetTokenEventFailed());
				}
			});
		} catch (UnsupportedEncodingException e) {
			Log.e(TAG, e.toString(), e);
		}
	}


	/*private static String getResponseBody(InputStream inputStream) {
		StringBuilder sb = new StringBuilder();
		BufferedReader bReader = null;
		try {
			bReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"), 8);

			String line = null;
			while ((line = bReader.readLine()) != null) {
				sb.append(line);
			}
		} catch (UnsupportedEncodingException ex) {
			Log.e("LOG", "", ex);
		} catch (ClientProtocolException ex1) {
			Log.e("LOG", "", ex1);
		} catch (IOException ex2) {
			Log.e("LOG", "", ex2);
		}
		return sb.toString();
	}*/

	/*// converts a string of JSON data into a Twitter object
	private static TweetList jsonToTweetLost(String result) {
		TweetList twits = null;
		if (result != null && result.length() > 0) {
			try {
				Gson gson = new Gson();
				twits = gson.fromJson(result, TweetList.class);
			} catch (IllegalStateException ex) {
				Log.e("LOG", "",ex);
			}
		}
		return twits;
	}*/


}
