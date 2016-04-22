package com.example.simpumind.trends.api;


import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * @author Sergii Zhuk
 *         Date: 24.06.2014
 *         Time: 11:53
 */
public interface TwitterApiService {
	@GET(ApiConstants.TWITTER_HASHTAG_SEARCH_CODE )
	void getTweetList(
			@Header("Authorization") String authorization,
			@Query("q") String hashtag,
			Callback<TweetList> callback
	);

	@FormUrlEncoded
	@POST("/oauth2/token")
	void getToken(
			@Header("Authorization") String authorization,
			@Field("grant_type") String grantType,
			Callback<TwitterTokenType> response
	);
}

