package com.osp.web.dao.ElastichSearchDAO;

//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.net.HttpURLConnection;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.List;
//
//import com.osp.model.MyTelCmsLog.SmsMoLog;
//import org.json.JSONArray;
//
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Invocation;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class CallApi {
    public static List<JSONObject>getAllNews(String path,String data) throws IOException, NoSuchFieldException, IllegalAccessException {
        HttpURLConnection conn =null;
        String result ="";

            URL url = new URL(path);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setRequestProperty("Accept-Charset", "UTF-8");


            Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
            writer.write(data);
            writer.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    (conn.getInputStream()), "UTF-8"));

            String output;
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            while ((output = br.readLine()) != null) {
                result = output;
            }

            JSONObject jsonObject=new JSONObject(result);
        JSONArray lts= jsonObject.getJSONObject("hits").getJSONArray("hits");
        List<JSONObject> list=new ArrayList<>();
     for(int i=0;i<lts.length();i++){
         JSONObject tmp= (JSONObject) lts.getJSONObject(i).get("_source");

       list.add(tmp);
     }

        conn.disconnect();

        return list;
    }
    public static int count(String path,String data) throws IOException, NoSuchFieldException, IllegalAccessException {
        HttpURLConnection conn =null;
        String result ="";

        URL url = new URL(path);
        conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        conn.setRequestProperty("Accept-Charset", "UTF-8");


        Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), "UTF-8"));
        writer.write(data);
        writer.close();
        BufferedReader br = new BufferedReader(new InputStreamReader(
                (conn.getInputStream()), "UTF-8"));

        String output;
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        while ((output = br.readLine()) != null) {
            result = output;
        }

        JSONObject jsonObject=new JSONObject(result);
        int count=0;
        count=jsonObject.getInt("count");

        conn.disconnect();
        return count;
    }


}
