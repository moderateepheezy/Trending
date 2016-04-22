package com.example.simpumind.trends.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.anupcowkur.reservoir.Reservoir;
import com.anupcowkur.reservoir.ReservoirGetCallback;
import com.anupcowkur.reservoir.ReservoirPutCallback;
import com.example.simpumind.trends.R;
import com.example.simpumind.trends.activities.HomeActivity;
import com.example.simpumind.trends.adapter.EventListAdapter;
import com.example.simpumind.trends.api.ApiConstants;
import com.example.simpumind.trends.models.EventsDataList;
import com.example.simpumind.trends.util.OnLoadMoreListener;
import com.example.simpumind.trends.util.RestClient;
import com.facebook.FacebookSdk;
import com.github.ksoichiro.android.observablescrollview.ObservableRecyclerView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by SimpuMind on 4/15/16.
 */
public class EventsFragment extends Fragment{


    public String json;

    public String formatedDate;
    public String country;
    static String dataString;
    /********************************************************************/
    private static final String TAG_DATA = "data";

    private final static String TAG_DESCRIPTION = "description";
    private static final String TAG_END_TIME = "end_time";
    private final static String TAG_EVENT_NAME = "name";
    private final static String TAG_START_TIME = "start_time";
    private final static String TAG_ID = "id";
/******************************************************************/

    /*******************************************************************/

    private final static String TAG_PLACE = "place";
    private final static String TAG_PLACE_NAME = "name";
    private final static String TAG_LOCATION = "location";
    private final static String TAG_CITY = "city";
    private final static String TAG_COUNTRY = "country";
    private final static String TAG_PLACE_ID  = "id";

    /*********************************************************************/



    private ProgressBar infinityLoading;

    protected Handler handler;

    public String fbSession;
    public SharedPreferences settings;

    public static ArrayList<EventsDataList> eventLists;

    ObservableRecyclerView recyclerView;

    public EventListAdapter mAdapter;

    public int pageNumber;

    public EventsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getActivity());
        View view = inflater.inflate(R.layout.events_fragment, container, false);

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
        pageNumber = 1;
        eventLists = new ArrayList<>();

        handler = new Handler();

        recyclerView.setHasFixedSize(true);

        Intent intent = getActivity().getIntent();

        String jsondata = intent.getStringExtra("jsondata");

        setJsonString(jsondata);

        settings = getActivity().getSharedPreferences("KEYS_NAME",
                getActivity().MODE_PRIVATE);
        fbSession = settings.getString("session", "");

        if(fbSession.isEmpty()){
            HomeActivity.checkLogin();
            Intent intentb = new Intent(getActivity(), HomeActivity.class);
            startActivity(intentb);
        }else {
            try {
                boolean objectExists = Reservoir.contains("eventKey");
                if(objectExists){
                    Type resultType = new TypeToken<ArrayList<EventsDataList>>() {}.getType();
                    Reservoir.getAsync("eventKey", resultType, new ReservoirGetCallback<ArrayList<EventsDataList>>() {
                        @Override
                        public void onSuccess(ArrayList<EventsDataList> strings) {
                            //success
                            Log.d("Sssss", String.valueOf(strings.size()));
                            mAdapter= new EventListAdapter(strings, recyclerView);
                            recyclerView.setAdapter(mAdapter);
                            infinityLoading.setVisibility(View.GONE);
                        }

                        @Override
                        public void onFailure(Exception e) {
                            //error
                            Log.d("Sssss", "Letere");
                        }
                    });
                }
                else{

                    getEvents();
                }
            } catch (Exception e) {
                Log.d("ExceptionExceot", e.getMessage());
            }
        }

        mAdapter= new EventListAdapter(eventLists, recyclerView);
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener(){

        });

        return view;
    }





    public void getEvents(){
        GetAllEvent getAllEvent = new GetAllEvent();
        getAllEvent.execute();
    }

    public String getJsonString() {
        return json;
    }

    public void setJsonString(String json) {
        this.json = json;
    }


    public class GetEventData extends AsyncTask<Void, Void, Void> {

        private RestClient connect;
        private String text;

        final String[] afterString = {""};  // will contain the next page cursor
        final Boolean[] noData = {false};   // stop when there is no after cursor

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {

            String apiUrl = "https://graph.facebook.com/search?q=Nigeria&type=event&access_token=" + fbSession;
            if(getJsonString() == null){
                dataString = "";
            }
            dataString = getJsonString();
//            Log.d("OBJECT_JASON", dataString);
            connect = new RestClient(apiUrl);
            try {

                connect.Execute(RestClient.RequestMethod.GET);
                text = connect.getResponse();
                JSONObject jsonObject = new JSONObject(text);
                JSONArray jsonArray =   jsonObject.getJSONArray(TAG_DATA);
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject data = jsonArray.getJSONObject(i);
                    String endTime = getMessageFromServer(data, TAG_END_TIME);//data.optString(TAG_END_TIME, "");
                    String description = getMessageFromServer(data, TAG_DESCRIPTION);//data.optString(TAG_DESCRIPTION, "");
                    String startTime = getMessageFromServer(data, TAG_START_TIME);//data.optString(TAG_START_TIME, "");
                    String eventName = getMessageFromServer(data, TAG_EVENT_NAME);//data.optString(TAG_EVENT_NAME, "");
                    String dataID = getMessageFromServer(data, TAG_ID); //data.optString(TAG_ID, "");
                    //Looping through place object
                    JSONObject place = data.getJSONObject(TAG_PLACE);
                    String  placeName = getMessageFromServer(place, TAG_PLACE_NAME);//place.optString(TAG_PLACE_NAME, "");
                    String placeId = getMessageFromServer(place, TAG_PLACE_ID);//place.optString(TAG_PLACE_ID, "");

                    //Loop through place location
                    /*if(!place.isNull(TAG_LOCATION)) {
                        JSONObject location = place.getJSONObject(TAG_LOCATION);
                        city = getMessageFromServer(location, TAG_CITY);//location.optString(TAG_CITY, "");
                        country = getMessageFromServer(location, TAG_COUNTRY);//location.optString(TAG_COUNTRY, "");
                    }*/

                    //faceBookEventList.save();
                    eventLists.add(new EventsDataList("", "", placeName, parseDate(startTime), parseDate(endTime),
                            eventName, description, dataID, "", "", placeId, R.drawable.facebook_icon, "Facebook"));
                }


                if(!jsonObject.isNull("paging")) {
                    JSONObject paging = jsonObject.getJSONObject("paging");
                    JSONObject cursors = paging.getJSONObject("cursors");
                    if (!cursors.isNull("after"))
                        afterString[0] = cursors.getString("after");
                    else
                        noData[0] = true;
                }
                else
                    noData[0] = true;
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            // Log.d("EventListTag", face.toString());
            // eventLists.addAll(face);
        }
    }

    public String getMessageFromServer(JSONObject response, String data) throws JSONException {
        return ((response.has(data) && !response.isNull(data))) ? response.getString(data) : "";
    }

    @Override
    public void onDestroyView() {
        // swipeLayout.removeAllViews();
        super.onDestroyView();
    }

    public String parseDate(String str) {

        try {

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
            Date date = dateFormat.parse(str);

            dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
            formatedDate = dateFormat.format(date);
        }catch (java.text.ParseException e){
            e.printStackTrace();
        }

        Log.d("Date", formatedDate);
        return  formatedDate;
    }


    public class GetEventBriteData extends AsyncTask<Void, Void, Void> {

        List<EventsDataList> listEvent = new ArrayList<>();
        private RestClient connect;
        private String text;

        @Override
        protected Void doInBackground(Void... params) {

            String apiUrl = "https://www.eventbriteapi.com/v3/events/search/?location.address=Lagos&token=" +
                    ApiConstants.EVENT_BRITE_CUSTOM_TOKEN;
            connect = new RestClient(apiUrl);
            try {
                connect.Execute(RestClient.RequestMethod.GET);
                text = connect.getResponse();
                JSONObject jsonObject = new JSONObject(text);
                JSONArray jsonArray = jsonObject.getJSONArray("events");

                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject data = jsonArray.getJSONObject(i);

                    JSONObject end = data.getJSONObject("end");
                    String endTime =  end.optString("utc");//  getMessageFromServer(end, "utc");

                    JSONObject desc = data.getJSONObject(TAG_DESCRIPTION);
                    String description = desc.optString("text");// getMessageFromServer(desc, "text");

                    JSONObject start = data.getJSONObject("start");
                    String startTime = start.optString("start");//  getMessageFromServer(start, "utc");

                    JSONObject name = data.getJSONObject("name");
                    String eventName = name.optString("text"); // getMessageFromServer(name, "text");
                    String dataID = data.optString("id");
                    String url = data.optString("url");

                    EventsDataList evn = new EventsDataList("", url, "", parseDate(startTime),
                            parseDate(endTime), eventName, description,
                            dataID, "", "", "", R.drawable.eventbrite, "EventBrite");

                    /*tweet.add(new EventsDataList("", url, "", parseDate(startTime),
                            parseDate(endTime), eventName, description,
                            dataID, "", "", "", R.drawable.eventbrite, "EventBrite"));*/
                    eventLists.add(evn);
                    //eventBriteDataList.save();
                }


            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            //Log.d("Atrre", tweet.toString());
            // eventLists.addAll(tweet);
        }
    }

    public class GetAllEvent extends AsyncTask<Void, Void, Void>{

        Context context;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            new GetEventData().execute();
            new GetEventBriteData().execute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            /*eventLists.addAll(tweet);
            eventLists.addAll(face);*/

            Reservoir.putAsync("eventKey", eventLists, new ReservoirPutCallback() {
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

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            Log.d("EventListTag", eventLists.toString());
            infinityLoading.setVisibility(View.GONE);
        }
    }
}
