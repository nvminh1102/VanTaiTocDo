package com.osp.common;

import com.google.gson.Gson;
import com.osp.model.CodeToken;
import com.osp.model.Items;
import com.osp.model.LtsItem;
import com.osp.model.view.JwtToken;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthencationAPI {
    public static HttpClient httpClient = null;
    private static String tokenString = null;
    private static long timeTokenAlive = 0;
    public static String token(HttpSession session) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(Constants.PATH_CALL_API+"/validateToken_1.jsp?userName=admin&password=1");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        rd.close();
        Gson g = new Gson();
        CodeToken p = g.fromJson(result.toString(), CodeToken.class);
        if(p.getResCode().equals("0")){
        session.setAttribute("token",p.getStringToken());
        session.setAttribute("timeStart",new Date().getTime());}
        return result.toString();
}
    public PagingResult GetList(HttpSession session,String Link,PagingResult page) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(Constants.PATH_CALL_API+Link);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("token",session.getAttribute("token").toString());
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        Gson g = new Gson();
         LtsItem ltsItem = g.fromJson(result.toString(),LtsItem.class);

        page.setItems(ltsItem.getData());
        page.setRowCount(ltsItem.getTotal());
        rd.close();
        return page;
    }
    public LtsItem GetListNoPaging(HttpSession session, String Link) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(Constants.PATH_CALL_API+Link);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("token",session.getAttribute("token").toString());
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        Gson g = new Gson();
        LtsItem ltsItem = g.fromJson(result.toString(),LtsItem.class);
        rd.close();
        return ltsItem;
    }
    public Items GetObject(HttpSession session,String Link,PagingResult page) throws IOException {
        StringBuilder result = new StringBuilder();
        URL url = new URL(Constants.PATH_CALL_API+Link);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("token",session.getAttribute("token").toString());
        BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        while ((line = rd.readLine()) != null) {
            result.append(line);
        }
        Gson g = new Gson();
        Items items = g.fromJson(result.toString(),Items.class);
        rd.close();
        return items;
    }
public static Long deCodeToken(String token){
    System.out.println("------------ Decode JWT ------------");
    String[] split_string = token.split("\\.");
    String base64EncodedBody = split_string[1];
    System.out.println("~~~~~~~~~ JWT Body ~~~~~~~");
    Base64 base64Url=new Base64();
    String body = new String(base64Url.decode(base64EncodedBody));
    System.out.println(body);
    Gson gson=new Gson();
    JwtToken token2=gson.fromJson(body,JwtToken.class);
    return Long.parseLong(token2.getExp());
}
public static Boolean check(String token,HttpSession session,HttpServletRequest request){
        Boolean check=false;
        if(token!=null && !token.equals("")){
        long result=deCodeToken(token);
        long end=new Date().getTime();
        long start=Long.parseLong(session.getAttribute("timeStart").toString());
        if(end-start<result){
            check=true;
        }}
        return check;
}
public static  void callCheckToken(HttpSession session, HttpServletRequest request){
    session=request.getSession();
    if(session.getAttribute("token")!=null){
    String token=session.getAttribute("token").toString();
    if(!check(token,session,request)){
        try {
            token(session);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }}else {
        try {
            token(session);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
    public String callNoTokenAPI(String functionName, String jsonInput) {
        HashMap<String, String> headerParam = new HashMap<String, String>();
        String response = null;
        PostMethod method = new PostMethod(Constants.PATH_CALL_API+functionName);
        headerParam.put("Accept", "application/json");
        headerParam.put("Content-Type", "application/json");
        for (Map.Entry<String, String> hdParam : headerParam.entrySet()) {
            method.addRequestHeader(hdParam.getKey(), hdParam.getValue());
        }
        method.getParams().setContentCharset("utf-8");
        StringRequestEntity requestEntity;
        try {
            requestEntity = new StringRequestEntity(jsonInput, "application/json", "UTF-8");
            method.setRequestEntity(requestEntity);
            int status = httpClient.executeMethod(method);
            if (status == 200) {
                response = method.getResponseBodyAsString();
                response = (response != null) ? response.trim() : response;
                return response;
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Boolean callAPI(String functionName, String jsonInput,HttpServletRequest request) {
        String response = null;
        String token= (String) request.getSession().getAttribute("token");
        PostMethod method = new PostMethod(Constants.PATH_CALL_API+functionName);
        HashMap<String, String> headerParam = new HashMap<String, String>();
        headerParam.put("token",token);
        headerParam.put("Accept", "application/json");
        headerParam.put("Content-Type", "application/json");
        for (Map.Entry<String, String> hdParam : headerParam.entrySet()) {
            method.addRequestHeader(hdParam.getKey(), hdParam.getValue());
        }
        method.getParams().setContentCharset("utf-8");
        StringRequestEntity requestEntity;
        try {
            requestEntity = new StringRequestEntity(jsonInput, "application/json", "UTF-8");
            method.setRequestEntity(requestEntity);
            int status = httpClient.executeMethod(method);
            if (status == 200) {
                response = method.getResponseBodyAsString();
                response = (response != null) ? response.trim() : response;
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }


}
