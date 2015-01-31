package com.stp.stayzilla;

import android.content.Context;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by thiyaga on 31/1/15.
 */
public class HTTPClient {
    private Context localContext;
    static JSONObject responseObject = null;
    private static String url = "http://180.92.168.7/hotels";
    public static JSONObject executeHttpPost(HashMap<String, String> postParameters) throws UnsupportedEncodingException {

        HttpClient client = new DefaultHttpClient();
        HttpPost request = null;
        HttpResponse response = null;
        try {
            request = new HttpPost(url);

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (postParameters != null && postParameters.isEmpty() == false) {

            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(postParameters.size());
            String k, v;
            Iterator<String> itKeys = postParameters.keySet().iterator();
            while (itKeys.hasNext()) {
                k = itKeys.next();
                v = postParameters.get(k);
                nameValuePairs.add(new BasicNameValuePair(k, v));
            }

            UrlEncodedFormEntity urlEntity = new UrlEncodedFormEntity(nameValuePairs);
            request.setEntity(urlEntity);

        }
        try {


            response = client.execute(request);
            HttpEntity entity = response.getEntity();
            int statusCode = response.getStatusLine().getStatusCode();

            try {
                InputStream in = (InputStream) entity.getContent();
                //Header contentEncoding = Response.getFirstHeader("Content-Encoding");
                    /*if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
                        in = new GZIPInputStream(in);
                    }*/
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuilder str = new StringBuilder();
                String line = null;
                while ((line = reader.readLine()) != null) {
                    str.append(line + "\n");
                }
                in.close();
                String responseString = str.toString();
                responseObject = new JSONObject(responseString);
            } catch (IllegalStateException exc) {

                exc.printStackTrace();
            }


        } catch (Exception e) {

            Log.e("log_tag", "Error in http connection " + response);

        } finally {

        }

        return responseObject;
    }
}
