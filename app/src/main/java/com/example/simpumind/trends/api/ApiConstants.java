package com.example.simpumind.trends.api;

/**
 * @author Sergii Zhuk
 *         Date: 24.06.2014
 *         Time: 22:28
 */
public class ApiConstants {

 	public final static String CONSUMER_KEY = "Pxx3sO5o7HXCbW9OHPj46KXoN"; // HIDDEN, please obtain your one on twitter developers

	public	final static String CONSUMER_SECRET = "yrNQwx1RTXv6suOl2PpdAtnl5V2asBlnc81wfUbO5Iu5xNnnTQ";  // HIDDEN, please obtain your one on twitter developers

	public final static String TWITTER_SEARCH_URL = "https://api.twitter.com";

	public static final String BEARER_TOKEN_CREDENTIALS = CONSUMER_KEY + ":" + CONSUMER_SECRET;

	public final static String TWITTER_HASHTAG_SEARCH_CODE = "/1.1/search/tweets.json";

	public final static String TWITTER_TREND_SEARCH_CODE = "https://api.twitter.com/1.1/trends/place.json?id=23424908";

	public final static String EVENT_BRITE_CUSTOM_TOKEN = "RECAY4SFJCYLTRCBNW5C";

}
