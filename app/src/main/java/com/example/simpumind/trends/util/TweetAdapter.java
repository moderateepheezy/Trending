package com.example.simpumind.trends.util;

import android.content.Context;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.simpumind.trends.R;
import com.example.simpumind.trends.api.TweetList;
import com.squareup.picasso.Picasso;
import com.thefinestartist.finestwebview.FinestWebView;

/**
 * @author Sergii Zhuk
 *         Date: 23.07.2014
 *         Time: 23:50
 */
public class TweetAdapter extends BaseAdapter {

	private Context mContext;
	private TweetList tweetList;

	LayoutInflater inflater;

	public TweetAdapter(Context mContext, TweetList tweetList) {
		this.mContext = mContext;
		this.tweetList = tweetList;
		inflater = LayoutInflater.from(this.mContext.getApplicationContext());
	}

	public void setTweetList(TweetList tweetList) {
		this.tweetList = tweetList;
	}

	@Override
	public int getCount() {
		if (tweetList.tweets != null) {
			return tweetList.tweets.size();
		} else {
			return 0;
		}
	}

	@Override
	public Object getItem(int position) {
		return null; // we don't need it now
	}

	@Override
	public long getItemId(int position) {
		return 0; // we don't need it now
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		final ViewHolder holder;


		if (row == null) {
			row = inflater.inflate(R.layout.row_tweet, parent, false);
			holder = new ViewHolder();
			holder.textTweet = (TextView) row.findViewById(R.id.text_tweet);
			holder.textUser = (TextView) row.findViewById(R.id.text_user);
			holder.imageLogo = (ImageView) row.findViewById(R.id.image_user_logo);
			holder.textImage = (ImageView) row.findViewById(R.id.textImage);
			row.setTag(holder);
		} else {
			holder = (ViewHolder) row.getTag();
		}
		holder.textTweet.setText(tweetList.tweets.get(position).text);
		setTextViewHTML(holder.textTweet, tweetList.tweets.get(position).text);
		/*holder.textTweet.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new FinestWebView.Builder(mContext)
						.theme(R.style.FinestWebViewTheme)
						.titleDefault("Twitter Trend Url...")
						.showUrl(false)
						.statusBarColorRes(R.color.bluePrimaryDark)
						.toolbarColorRes(R.color.bluePrimary)
						.titleColorRes(R.color.finestWhite)
						.urlColorRes(R.color.bluePrimaryLight)
						.iconDefaultColorRes(R.color.finestWhite)
						.progressBarColorRes(R.color.PrimaryDarkColor)
						.stringResCopiedToClipboard(R.string.copied_to_clipboard)
						.stringResCopiedToClipboard(R.string.copied_to_clipboard)
						.stringResCopiedToClipboard(R.string.copied_to_clipboard)
						.showSwipeRefreshLayout(true)
						.swipeRefreshColorRes(R.color.bluePrimaryDark)
						.menuSelector(R.drawable.selector_light_theme)
						.menuTextGravity(Gravity.CENTER)
						.menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
						.dividerHeight(0)
						.gradientDivider(false)
						.setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
						.show(setTextViewHTML(holder.textTweet, holder.textTweet.getText().toString()));
				}
		});*/
		holder.textUser.setText(tweetList.tweets.get(position).user.name);
		Picasso.with(mContext).load(tweetList.tweets.get(position).user.profileImageUrl).into(holder.imageLogo);
		Picasso.with(mContext) .load(tweetList.tweets.get(position).textImage).into(holder.textImage);
		return row;
	}

	static class ViewHolder {
		TextView textTweet;
		TextView textUser;
		ImageView imageLogo;
		ImageView textImage;
	}

	protected void makeLinkClickable(SpannableStringBuilder strBuilder, final URLSpan span)
	{
		int start = strBuilder.getSpanStart(span);
		int end = strBuilder.getSpanEnd(span);
		int flags = strBuilder.getSpanFlags(span);
		ClickableSpan clickable = new ClickableSpan() {
			public void onClick(View view) {
				// Do something with span.getURL() to handle the link click...
				new FinestWebView.Builder(mContext)
						.theme(R.style.FinestWebViewTheme)
						.titleDefault("Twitter Trend Url...")
						.showUrl(false)
						.statusBarColorRes(R.color.bluePrimaryDark)
						.toolbarColorRes(R.color.bluePrimary)
						.titleColorRes(R.color.finestWhite)
						.urlColorRes(R.color.bluePrimaryLight)
						.iconDefaultColorRes(R.color.finestWhite)
						.progressBarColorRes(R.color.PrimaryDarkColor)
						.stringResCopiedToClipboard(R.string.copied_to_clipboard)
						.stringResCopiedToClipboard(R.string.copied_to_clipboard)
						.stringResCopiedToClipboard(R.string.copied_to_clipboard)
						.showSwipeRefreshLayout(true)
						.swipeRefreshColorRes(R.color.bluePrimaryDark)
						.menuSelector(R.drawable.selector_light_theme)
						.menuTextGravity(Gravity.CENTER)
						.menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
						.dividerHeight(0)
						.gradientDivider(false)
						.setCustomAnimations(R.anim.slide_up, R.anim.hold, R.anim.hold, R.anim.slide_down)
						.show(span.getURL());
			}
		};
		strBuilder.setSpan(clickable, start, end, flags);
		strBuilder.removeSpan(span);
	}

	protected String setTextViewHTML(TextView text, String html) {
		CharSequence sequence = Html.fromHtml(html);
		SpannableStringBuilder strBuilder = new SpannableStringBuilder(sequence);
		URLSpan[] urls = strBuilder.getSpans(0, sequence.length(), URLSpan.class);
		for(URLSpan span : urls) {
			makeLinkClickable(strBuilder, span);
		}
		return strBuilder.toString();
	}

}
