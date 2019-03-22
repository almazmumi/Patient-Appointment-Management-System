package kfupm.bader.com.appointmentsystem;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class DBConnection {
    public DBConnection(){

    }
    static String res = "";
    public static String connectDB(Context c, final String query){

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(c);
        //this is the url where you want to send the request
        String url = "https://androiddemoa.azurewebsites.net/login.php";
        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!res.contains("Wrong")){
                            res = response;
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }) {
            //adding parameters to the request
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("query", query);
                return params;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(stringRequest);
        return res;
    }
}
