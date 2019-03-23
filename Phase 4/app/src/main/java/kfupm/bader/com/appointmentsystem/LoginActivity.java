package kfupm.bader.com.appointmentsystem;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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
        pref = getApplicationContext().getSharedPreferences("MyPref", 0);
        editor = pref.edit();

        signinBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Changing color button to gray and dysfunction the button
                GradientDrawable  background = (GradientDrawable)signinBTN.getBackground().mutate();
                background.setColor(Color.GRAY);
                signinBTN.setEnabled(false);

                //Validate the email field
                if(!usernameET.getText().toString().contains("@") || usernameET.getText().toString().trim().contains(" ")){
                    Snackbar.make(signinBTN,"Email field is incorrect!", Snackbar.LENGTH_LONG).show();
                }


                //Backend Stuff
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pamsapp123.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
                UserAPI userAPI = retrofit.create(UserAPI.class);
                Call<ResponseBody> call = userAPI.authenticateUser(usernameET.getText().toString().trim(),passwordET.getText().toString().trim());
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        String s = null;
                        try {
                            s = String.valueOf(response.body().string());

                        signinBTN.setText(s);
                        signinBTN.setEnabled(true);

                        if(!s.contains("ERROR:")){
                            signinBTN.setText("Logged in successfully!");
                            // save the information to the shared preference
                            Log.d("HELLO ... ",s.split(",")[1]);
                            editor.putString("user_ID",s.split(",")[0]);
                            editor.putString("fname",s.split(",")[1]);
                            editor.putString("lname",s.split(",")[2]);
                            editor.putString("type_ID",s.split(",")[3]);
                            editor.putBoolean("loggedin",true);
                            editor.apply();


                            finish();
                        }else{

                            ScheduledExecutorService scheduledExecutorService;
                            scheduledExecutorService = Executors.newScheduledThreadPool(1);
                            scheduledExecutorService.schedule(new Runnable(){
                                @Override
                                public void run() {
                                    LoginActivity.this.runOnUiThread(new Runnable(){

                                        @Override
                                        public void run() {
                                            signinBTN.setText("SIGN IN");
                                            GradientDrawable background = (GradientDrawable)signinBTN.getBackground().mutate();
                                            background.setColor(getResources().getColor(R.color.colorPrimary));
                                        }});

                                }},4, TimeUnit.SECONDS);




                        }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
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
                startActivity(new Intent(LoginActivity.this,SignupActivity.class).putExtra("type_ID", "4"));
            }
        });
        // END OF OF FORGET PASSWORD BUTTON ---------------------------------------------------------------------


    }
}
