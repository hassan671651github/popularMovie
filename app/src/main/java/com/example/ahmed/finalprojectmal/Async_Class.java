package com.example.ahmed.finalprojectmal;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class Async_Class extends AsyncTask<String, String, String> {
    String result;

    @Override
    protected String doInBackground(String... params) {
        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;
        InputStream inputStream = null;
        StringBuffer buffer = null;
        try {
            URL url = new URL(params[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            inputStream = connection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            buffer = new StringBuffer();
            String line = "";
            while ((line = bufferedReader.readLine()) != null) {

                buffer.append(line);
            }

            return buffer.toString();
        } catch (Exception c) {
            // Toast.makeText(getBaseContext(), "not yet", Toast.LENGTH_SHORT).show();
        } finally {
            {
                if (connection != null)
                    connection.disconnect();
                if (bufferedReader != null)
                    try {
                        bufferedReader.close();
                    } catch (Exception e) {
                    }
            }
        }

        return null;
    }


    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        //  result = s;





    }



}

/**
 * Created by Ahmed on 21/09/2016.
 */


