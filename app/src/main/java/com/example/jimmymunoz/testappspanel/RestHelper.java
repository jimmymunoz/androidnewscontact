package com.example.jimmymunoz.testappspanel;

/**
 * Created by jimmymunoz on 12/8/16.
 */

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;


public class RestHelper {

    // Reads an InputStream and converts it to a String.
    public static String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException
    {
        Reader reader = null;
        reader = new InputStreamReader(stream, "UTF-8");
        char[] buffer = new char[len];
        reader.read(buffer);
        return new String(buffer);
    }

    // REST - GET
    public static String executeGET(String urlString)
    {
        String result = "";
        InputStream is = null;
        int len = 999999;

        // HTTP Get
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(120000 /* milliseconds */);
            conn.setConnectTimeout(120000); //60 * 2 secs
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept-Charset", "UTF-8");
            conn.setDoInput(true);
            // Starts the query
            conn.connect();
            int response = conn.getResponseCode();
            //Log.d(DEBUG_TAG, "The response is: " + response);
            InputStream _is;

            int responseCode=conn.getResponseCode();
            Log.d("GET ", " Response: " + responseCode);
            String line;
            if (responseCode / 100 >= 2 && responseCode / 100 < 3 ) { // 2xx code means success
                _is = conn.getInputStream();
                //result = getStringFromInputStream(_is);

                // Convert the InputStream into a string

                BufferedReader br=new BufferedReader(new InputStreamReader(_is));
                while ((line=br.readLine()) != null) {
                    result+=line;
                }
                //String contentAsString = readIt(_is, len);
                //result = contentAsString;
                result = result.trim();
                Log.i("Response get ok: ", result);
            } else {
                _is = conn.getErrorStream();
                result = getStringFromInputStream(_is);
                result = result.trim();
                Log.i("Response error: ", result);
                BufferedReader br=new BufferedReader(new InputStreamReader(_is));
                while ((line=br.readLine()) != null) {
                    result+=line;
                }
            }


        } catch (Exception e) {
            Log.d("GET ", " Exception: " + e.getMessage());
            System.out.println(e.getMessage());
            return e.getMessage();
        }


        return result;
    }

    // REST - PUT
    public static String executePUT(String urlString, HashMap<String,String> paramspos)
    {
        String result = "";
        InputStream is = null;
        int len = 999999;

        try {
            String urlParameters = getPostDataString(paramspos);

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60000 /* milliseconds */);
            conn.setConnectTimeout(60000); //60 secs
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", "" +
                    Integer.toString(urlParameters.getBytes().length));
            conn.setRequestProperty("Content-Language", "en-US");
            conn.setRequestProperty("Accept", "*/*");
            DataOutputStream wr = new DataOutputStream(
                    conn.getOutputStream ());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            InputStream _is;
            int responseCode=conn.getResponseCode();
            Log.d("PUT ", " Response: " + responseCode);

            if (responseCode / 100 >= 2 && responseCode / 100 < 3 ) { // 2xx code means success
                _is = conn.getInputStream();
            } else {

                _is = conn.getErrorStream();

                result = getStringFromInputStream(_is);
                Log.i("Response error: ", result);
            }

            responseCode=conn.getResponseCode();
            Log.d("GET ", " Response: " + responseCode);

            if (responseCode / 100 >= 2 && responseCode / 100 < 3 ) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    result+=line;
                }
            }

        } catch (Exception e) {
            Log.d("PUT ", " Exception: " + e.getMessage());
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return result;
    }

    // REST - PUT
    public static String executeDELETE(String urlString)
    {
        String result = "";
        InputStream is = null;
        int len = 999999;

        // HTTP Get
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60000 /* milliseconds */);
            conn.setConnectTimeout(60000); //60 secs
            //conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestMethod("DELETE");
            // Starts the query
            conn.connect();
            int responseCode=conn.getResponseCode();
            if (responseCode / 100 >= 2 && responseCode / 100 < 3 ) { // 2xx code means success
                is = conn.getInputStream();
            } else {

                is = conn.getErrorStream();

                result = getStringFromInputStream(is);
                Log.i("Response error: ", result);
            }
            // Convert the InputStream into a string
            String contentAsString = readIt(is, len);
            result = contentAsString;
        } catch (Exception e) {

            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return result;
    }


    public static String executePOST(String urlString, HashMap<String,String> paramspos)
    {
        String result = "";
        InputStream is = null;
        int len = 999999;

        try {
            String urlParameters = getPostDataString(paramspos);

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(60000 /* milliseconds */);
            conn.setConnectTimeout(60000); //60 secs
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setRequestProperty("Content-Length", "" +
                    Integer.toString(urlParameters.getBytes().length));
            conn.setRequestProperty("Content-Language", "en-US");
            conn.setRequestProperty("Accept","*/*");
            DataOutputStream wr = new DataOutputStream(
                    conn.getOutputStream ());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            InputStream _is;
            int responseCode=conn.getResponseCode();
            Log.d("POST ", " Response: " + responseCode);
            if (responseCode / 100 >= 2 && responseCode / 100 < 3 ) { // 2xx code means success
                _is = conn.getInputStream();
            } else {
                _is = conn.getErrorStream();
                result = getStringFromInputStream(_is);
                Log.i("Post Url error: ", urlString);
                Log.i("Response error: ", result);
            }

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    result+=line;
                }
            }

        } catch (Exception e) {
            Log.d("POST ", " Exception: " + e.getMessage());
            System.out.println(e.getMessage());
            return e.getMessage();
        }
        return result;
    }


    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException{
        //StringBuilder result = new StringBuilder();
        boolean first = true;
        String urlParameters = "";
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else {
                urlParameters += "&";
            }
            urlParameters += entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), "UTF-8");
        }
        return urlParameters;
    }

    // convert InputStream to String
    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return sb.toString();

    }

}