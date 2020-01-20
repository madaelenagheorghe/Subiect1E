package com.example.subiect1e;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JsonRead extends AsyncTask<URL, Void, String> {

    @Override
    protected String doInBackground(URL... urls) {
        HttpURLConnection conn=null;
        StringBuilder stringBuilder=new StringBuilder();
        try{
            conn=(HttpURLConnection)urls[0].openConnection();
            conn.setRequestMethod("GET");
            InputStream is=conn.getInputStream();

            InputStreamReader isr=new InputStreamReader(is);
            BufferedReader br=new BufferedReader(isr);
            String line="";
            while((line=br.readLine())!=null){
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("doInBackground", e.getMessage());
        } finally{
            if(conn!=null)
                conn.disconnect();
        }
        return stringBuilder.toString();
    }

    public List<DataPackage> parseJson(String json){
        List<DataPackage> packages=new ArrayList<>();
        if(json!=null){
            DataPackage.packageType p_type= DataPackage.packageType.position;
            Date p_timestamp=new Date();
            try{
                JSONObject jsonObject=new JSONObject(json);
                JSONArray packagesArray=jsonObject.getJSONArray("packages");
                for(int i=0;i<packagesArray.length();i++){
                    JSONObject packageDetail = packagesArray.getJSONObject(i);
                    int id=packageDetail.getInt("packageId");
                    String type=packageDetail.getString("packageType");
                    Double lat=packageDetail.getDouble("latitude");
                    Double longit=packageDetail.getDouble("longitude");
                    String timestamp=packageDetail.getString("timestamp");
                    if(type.toString().equals("POSITION"))
                        p_type= DataPackage.packageType.position;
                    else if(type.toString().equals("STATE"))
                        p_type= DataPackage.packageType.state;
                    SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm");
                    try {
                        p_timestamp = format.parse(timestamp);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    DataPackage dp=new DataPackage(id, p_type, Float.parseFloat(lat.toString()), Float.parseFloat(longit.toString()), p_timestamp);
                    packages.add(dp);
                }
            } catch (JSONException e){
                e.printStackTrace();
            }
        } else {
            Log.e("Parsing Object", "Couldn't get any data from json");
        }
        return packages;
    }
}
