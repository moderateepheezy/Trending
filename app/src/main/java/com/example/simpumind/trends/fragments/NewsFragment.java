package com.example.simpumind.trends.fragments;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.anupcowkur.reservoir.ReservoirPutCallback;
import com.example.simpumind.trends.R;
import com.example.simpumind.trends.adapter.EventListAdapter;
import com.example.simpumind.trends.adapter.NewsListAdapter;
import com.example.simpumind.trends.models.EventsDataList;
import com.example.simpumind.trends.models.NewsDataList;
import com.example.simpumind.trends.util.RestClient;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by SimpuMind on 4/21/16.
 */
public class NewsFragment extends Fragment {

    private ProgressBar infinityLoading;

    public String formatedDate;

    public ArrayList<NewsDataList> newsList;

    ObservableRecyclerView recyclerView;

    public static final String ARG_SCROLL_Y = "ARG_SCROLL_Y";

    public NewsListAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_event, container, false);

        newsList = new ArrayList<>();
        try {
            Reservoir.init(getActivity(), 10200048); //in bytes
        } catch (Exception e) {
            Log.d("REservoir", String.valueOf(e.getStackTrace()));
        }
        Activity parentActivity = getActivity();
        recyclerView = (ObservableRecyclerView) view.findViewById(R.id.scroll);
        recyclerView.setLayoutManager(new LinearLayoutManager(parentActivity));
        recyclerView.setHasFixedSize(false);
        recyclerView.setTouchInterceptionViewGroup((ViewGroup) parentActivity.findViewById(R.id.container));

        if (parentActivity instanceof ObservableScrollViewCallbacks) {
            recyclerView.setScrollViewCallbacks((ObservableScrollViewCallbacks) parentActivity);
        }

        infinityLoading = (ProgressBar) view.findViewById(R.id.loading);

        try {
            boolean objectExists = Reservoir.contains("newsKey");
            if (objectExists) {
                Type resultType = new TypeToken<ArrayList<NewsDataList>>() {
                }.getType();
                Reservoir.getAsync("newsKey", resultType, new ReservoirGetCallback<ArrayList<NewsDataList>>() {
                    @Override
                    public void onSuccess(ArrayList<NewsDataList> strings) {
                        //success
                        Log.d("Sssss", String.valueOf(strings.size()));
                        mAdapter = new NewsListAdapter();
                        mAdapter.addAll(strings);
                        recyclerView.setAdapter(mAdapter);
                        infinityLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onFailure(Exception e) {
                        //error
                        Log.d("Sssss", "Letere");
                    }
                });
            } else {

                getEvents();
            }
        }catch (Exception e) {
            Log.d("ExceptionExceot", e.getMessage());
        }

        mAdapter= new NewsListAdapter();
        mAdapter.addAll(newsList);
        recyclerView.setAdapter(mAdapter);

        return view;
    }



    public void getEvents(){
        new GetEventDatas().execute();
    }


    public class GetEventDatas extends AsyncTask<Void, Void, Void> {

        private RestClient connect;
        private String text;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            String apiUrl = "http://voice.atp-sevas.com/demo/yql/linda";
            connect = new RestClient(apiUrl);
            try {
                connect.Execute(RestClient.RequestMethod.GET);
                text = connect.getResponse();
                JSONArray jsonArray = new JSONArray(text);
                for(int i = 0; i < jsonArray.length(); i++ ){
                    JSONObject news = jsonArray.getJSONObject(i);
                    String newsName = news.optString("title");
                    String link = news.optString("href");
                    String timeStamp = news.optString("timestamp");
                    String comments = news.optString("comments");

                    newsList.add(new NewsDataList(parseDate(timeStamp),newsName, comments, link, 0, ""));

                    Reservoir.putAsync("newsKey", newsList, new ReservoirPutCallback() {
                        @Override
                        public void onSuccess() {
                            //success
                            Log.d("Success", "Data suc");
                        }

                        @Override
                        public void onFailure(Exception e) {
                            //error
                            Log.d("Not Success", "Data not suc");
                        }
                    });

                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
        }
    }

    public String parseDate(String str) {

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:sssZ");
            Date date = dateFormat.parse(str);

            dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            formatedDate = dateFormat.format(date);
        }catch (java.text.ParseException e){
            e.printStackTrace();
        }

        Log.d("Date", formatedDate);
        return  formatedDate;
    }

}
