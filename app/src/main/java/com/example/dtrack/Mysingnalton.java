package com.example.dtrack;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class Mysingnalton {
    private static Mysingnalton mInstance;
    private RequestQueue requestQueue;
    private static Context mCtx;
    private Mysingnalton (Context Context)
    {
        mCtx = Context;
        requestQueue = getRequestQueue();
    }

    public static  synchronized Mysingnalton getInstance (Context context)
    {
        if (mInstance==null)
        {
            mInstance =new Mysingnalton(context);
        }
        return mInstance;
    }

    public RequestQueue getRequestQueue() {

        if (requestQueue==null)
        {
            requestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return requestQueue;

    }

    public<T> void addTorequestque(Request<T> request)
    {
        requestQueue.add(request);
    }
}
