package com.example.dtrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

public class ViewNotifications extends AppCompatActivity {


    private RecyclerView mRecyclerView;
    private notificationAdapter mNotificationAdapter;
    private ArrayList<Notification> mNotificationList;
    private RequestQueue mRequestQueue;
    String userType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notifications);

        Intent i = getIntent();
        userType = i.getStringExtra("type");


        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNotificationList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

        //update loop
        Handler handler=new Handler();
        handler.post(new Runnable(){
            @Override
            public void run() {
                //parseJSON();
                handler.postDelayed(this,500); // set time here to refresh textView
            }
        });
    }
    private void parseJSON() {


        String url = "https://dtrack.live/generatenotificationarray.php?usertype="+userType;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject hit = jsonArray.getJSONObject(i);


                        String ID = hit.getString("ID");
                        String Topic = hit.getString("Topic");
                        String Content = hit.getString("Contetnt");
                        String userType = hit.getString("Usertype");
                        String DateTime = hit.getString("DateTime");



                        mNotificationList.add(new Notification(ID,Topic,Content,userType,DateTime));
                    }

                    mNotificationAdapter = new notificationAdapter (ViewNotifications.this,mNotificationList );
                    mRecyclerView.setAdapter(mNotificationAdapter);

                    mNotificationAdapter.setOnItemClickListner(ViewNotifications.this::onItemClick);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);
    }


    public void onItemClick(int postion) {

        Intent detailIntent = new Intent(this,DisplayNotification.class);
        Notification clickItem = mNotificationList.get(postion);

        detailIntent.putExtra("DateTime",clickItem.getDate());
        detailIntent.putExtra("Topic",clickItem.getTopic());
        detailIntent.putExtra("Contetnt",clickItem.getContent());

        startActivity(detailIntent);
    }

}