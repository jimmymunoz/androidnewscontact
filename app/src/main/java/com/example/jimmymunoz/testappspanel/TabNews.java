package com.example.jimmymunoz.testappspanel;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.LOCATION_SERVICE;

public class TabNews extends ListFragment implements LocationListener  {
    Context myContext;
    static ArrayAdapter<NewsItem> adapter;
    static ArrayList<NewsItem> arrayListData;
    private static final long MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 0; // in Meters
    private static final long MINIMUM_TIME_BETWEEN_UPDATES = 0;
    private double latitude = 0;
    private double longitude = 0;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        arrayListData = new ArrayList<NewsItem>();
        ConnectivityManager connMgr = (ConnectivityManager)  getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            new HttpRequestTask().execute("");
        }
        else {
            Toast.makeText(myContext, "No network connection available.", Toast.LENGTH_LONG).show();
        }

        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);
        final boolean gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!gpsEnabled) {
            Log.d("GPS Enabled", "GPS Enabled");
            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            String provider = locationManager.getBestProvider(criteria, true);
            if (provider != null) {
                /*
                LocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                        10000,          // 10-second interval.
                        10,             // 10 meters.
                        this);
                        */
            }

        }



        View view = inflater.inflate(R.layout.tab_fragment_1, container, false);
        return view;
    }

    public static void refreshArrayListViewData(ArrayList<NewsItem> UpdatedArrayListData){
        arrayListData.clear();
        arrayListData.addAll(UpdatedArrayListData);
        adapter.notifyDataSetChanged();

    }



    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter = new NewsItemAdapter(getActivity(), arrayListData);
        setListAdapter(adapter);
    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        Toast.makeText(getActivity(), "latitude: " + latitude+ " longitude: " + longitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    /*
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getActivity(), "Item: " + position, Toast.LENGTH_SHORT).show();
    }
    */

    private class HttpRequestTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String urladress = "http://lagenda.apnl.ws/index.php?key=N25QS3JnOWg=&deviceuid=MTIzNDU2&action=Z2V0ZWx0cw==&quand=YWpk&lat=NDUuNzQ5MjIy&lng=NC44NTI3MDI=&dist=NTAuMjU3NzMy&gratuit=MQ==&start=MA==&end=NDA=&format=anNvbg==";

            Log.d("Get", "request  :" + urladress);
            String responseServer = RestHelper.executeGET(urladress);
            Log.d("Get", "Response  :" + responseServer);
            return responseServer;
        }

        protected String decodeBase64(String base64){
            String text = "";
            byte[] data = Base64.decode(base64, Base64.DEFAULT);
            try {
                text = new String(data, "ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            return text;
        }

        @Override
        protected void onPostExecute(String responseUrl) {
            ArrayList<NewsItem> updatedListAdapterData = new ArrayList<NewsItem>();
            try {
                JSONArray jsonArray = new JSONArray(responseUrl);

                for(int i=0; i < jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    HashMap<String, String> map = new HashMap<String, String>();

                    NewsItem tmpObj = new NewsItem(
                        decodeBase64(jsonObject.optString("id_element")),
                        decodeBase64(jsonObject.optString("id_appli")),
                        decodeBase64(jsonObject.optString("active")),
                        decodeBase64(jsonObject.optString("date_create")),
                        decodeBase64(jsonObject.optString("type")),
                        decodeBase64(jsonObject.optString("lat")),
                        decodeBase64(jsonObject.optString("lng")),
                        decodeBase64(jsonObject.optString("title")),
                        decodeBase64(jsonObject.optString("description")),
                        decodeBase64(jsonObject.optString("picture")),
                        decodeBase64(jsonObject.optString("data1")),
                        decodeBase64(jsonObject.optString("data2")),
                        decodeBase64(jsonObject.optString("data3")),
                        decodeBase64(jsonObject.optString("data4")),
                        decodeBase64(jsonObject.optString("data5")),
                        decodeBase64(jsonObject.optString("data6")),
                        decodeBase64(jsonObject.optString("data7")),
                        decodeBase64(jsonObject.optString("data8")),
                        decodeBase64(jsonObject.optString("data9")),
                        decodeBase64(jsonObject.optString("data10")),
                        decodeBase64(jsonObject.optString("data11")),
                        decodeBase64(jsonObject.optString("data12")),
                        decodeBase64(jsonObject.optString("data13")),
                        decodeBase64(jsonObject.optString("id_categorie")),
                        decodeBase64(jsonObject.optString("link")),
                        decodeBase64(jsonObject.optString("description_out"))
                    );
                    updatedListAdapterData.add(tmpObj);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            TabNews.refreshArrayListViewData(updatedListAdapterData);
        }
    }
}
