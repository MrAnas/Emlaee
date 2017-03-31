package com.app.emlaee.webServices;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sahil on 1/11/2017.
 */

public class JsonParsorUnicode {

    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";String outPut = null;

    static ArrayList<HashMap<String, String>> productlist, loginlist;
    private static HashMap<String, String> promap, loginmap;

    // constructor
    public JsonParsorUnicode() {

    }

    public String makeHttpRequest(String url, String method,
                                  List<NameValuePair> params) {

        try {

            if (method == "POST") {
                try {
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    HttpPost httpPost = new HttpPost(url);
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
					/*for unicode char*/
					httpPost.setEntity(new StringEntity(json.toString(), HTTP.UTF_8));

                    HttpResponse httpResponse = httpClient.execute(httpPost);
                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();
                }catch (Exception e){
                    e.printStackTrace();
                }

            } else if (method == "GET") {
                // request method is GET
                try {
                    DefaultHttpClient httpClient = new DefaultHttpClient();
                    String paramString = URLEncodedUtils.format(params, "utf-8");

                    url += "?" + paramString;
                    HttpGet httpGet = new HttpGet(url);
						/*for unicode char*/
                    HttpResponse httpResponse = httpClient.execute(httpGet);

                    HttpEntity httpEntity = httpResponse.getEntity();
                    is = httpEntity.getContent();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        } catch (IllegalStateException e){
            e.printStackTrace();
        }

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
            Log.d("string", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        return json;

    }
}
