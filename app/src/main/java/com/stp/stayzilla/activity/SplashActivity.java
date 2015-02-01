package com.stp.stayzilla.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.stp.stayzilla.R;
import com.stp.stayzilla.constants.AppConstants;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SplashActivity extends Activity  {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_activity);
        getResponse();
    }

    void processResponse(String response){
        //  System.out.println("Response is "+response);

        try {
            JSONObject reader = new JSONObject(response);
            JSONArray hotels  = reader.getJSONArray("hotels");
            Bundle bundle = new Bundle();
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            bundle.putString(AppConstants.RESPONSE_KEY,hotels.toString());
            intent.putExtras(bundle);
            startActivity(intent); finish();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void getResponse() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://180.92.168.7/hotels";

        StringRequest request = new StringRequest(Request.Method.POST,url,new Response.Listener<String>(){

            @Override
            public void onResponse(String response) {
                processResponse(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("checkin","02/02/2015");
                params.put("checkout","04/02/2015");
                params.put("property_type","Hotels");
                params.put("lat","11.40");
                params.put("lng","76.40");

//                params.put("user",userAccount.getUsername());
//                params.put("pass",userAccount.getPassword());


                return params;
            }
        };
        queue.add(request);
    }

}
