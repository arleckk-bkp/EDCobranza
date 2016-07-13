package com.arleckk.edcobranza.global;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by arleckk on 13/07/16.
 */
public class VolleyEverywhere {

    private static VolleyEverywhere volleyEverywhere;
    private RequestQueue requestQueue;
    private static Context context;

    private VolleyEverywhere(Context context) {
        VolleyEverywhere.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleyEverywhere getInstance(Context context) {
        if(volleyEverywhere == null) {
            volleyEverywhere = new VolleyEverywhere(context.getApplicationContext());
        }
        return volleyEverywhere;
    }

    public RequestQueue getRequestQueue() {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

}
