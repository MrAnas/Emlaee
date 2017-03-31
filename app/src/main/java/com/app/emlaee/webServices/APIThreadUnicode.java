package com.app.emlaee.webServices;

import android.util.Log;

import com.app.emlaee.interfaces.ExceptionListener;
import com.app.emlaee.interfaces.ResponseListener;

import org.apache.http.NameValuePair;

import java.util.List;

/**
 * Created by sahil on 1/11/2017.
 */

public class APIThreadUnicode extends Thread{

    List<NameValuePair> nameValuePair;
    String UrlLink = null;
    String REQUEST_METHOD = null;
    ResponseListener listenerResponse = null;
    ExceptionListener listenerexception = null;
    public final String tag = this.getClass().getSimpleName();


    public APIThreadUnicode(String Url, String Request_METHOD, List<NameValuePair> nameValuePair, ResponseListener listenerResponse, ExceptionListener mExceptionListener) {
        this.UrlLink = Url;
        this.REQUEST_METHOD = Request_METHOD;
        this.nameValuePair = nameValuePair;
        this.listenerexception = mExceptionListener;
        this.listenerResponse = listenerResponse;
    }


    @Override
    public void run() {
        JsonParsorUnicode parser = new JsonParsorUnicode();
        String response = parser.makeHttpRequest(this.UrlLink, this.REQUEST_METHOD, this.nameValuePair);
        Log.e(tag+"response", response);
        try {
            this.listenerResponse.handleResponse(response);

        } catch (Exception e) {
            Log.e("eRRoRs...........!!!!!!", e.toString());
        }

    }
}
