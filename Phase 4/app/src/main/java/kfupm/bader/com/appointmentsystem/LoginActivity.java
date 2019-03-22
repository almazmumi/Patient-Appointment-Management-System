package kfupm.bader.com.appointmentsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextClock;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity {
    EditText usernameET,passwordET;
    Button signinBTN;
    TextView forgetPasswordBTN,createAccountBTN;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameET = findViewById(R.id.loginActivity_usernameET);
        passwordET = findViewById(R.id.loginActivity_passwordET);
        signinBTN = findViewById(R.id.loginActivity_signinBTN);
        forgetPasswordBTN = findViewById(R.id.loginActivity_forgetPasswordBTN);
        createAccountBTN = findViewById(R.id.loginActivity_signupBTN);


        // START OF LOGIN BUTTON ---------------------------------------------------------------------
        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        signinBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        // Instantiate the RequestQueue.
                        RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                        //this is the url where you want to send the request
                        String url = "https://pamsapp123.herokuapp.com/login.php";

                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if(!response.contains("Wrong")){
                                            editor.putBoolean("loggedin", true);
                                            editor.putString("id",response.split(",")[0] );
                                            editor.apply(); // commit changes

                                            Intent i = new Intent(LoginActivity.this,HomeActivity.class);
                                            i.putExtra("id",response.split(",")[0]);
                                            i.putExtra("name",response.split(",")[1]);
                                            startActivity(i);
                                            finish();
                                        }else{
                                            Toast.makeText(LoginActivity.this,"You have entered a wrong password!",Toast.LENGTH_LONG).show();
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
                                params.put("username", usernameET.getText().toString().toLowerCase());
                                params.put("password", passwordET.getText().toString().toLowerCase());
                                return params;
                            }
                        };
                        // Add the request to the RequestQueue.
                        queue.add(stringRequest);

//

            }
        });




        // END OF LOGIN BUTTON ---------------------------------------------------------------------


        // START OF CREATE ACCOUNT BUTTON ---------------------------------------------------------------------

        createAccountBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // END OF CREATE ACCOUNT BUTTON ---------------------------------------------------------------------


        // START OF FORGET PASSWORD BUTTON ---------------------------------------------------------------------

        createAccountBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        // END OF OF FORGET PASSWORD BUTTON ---------------------------------------------------------------------


    }
}
