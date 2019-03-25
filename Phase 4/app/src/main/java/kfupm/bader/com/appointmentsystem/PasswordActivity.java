package kfupm.bader.com.appointmentsystem;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PasswordActivity extends AppCompatActivity {

    TextView passwordTV,responseMessageTV;
    EditText usernameET;
    Button submitBTN,copyBTN;

    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        usernameET = (EditText)findViewById(R.id.passwordActivity_emailET);
        passwordTV = (TextView)findViewById(R.id.passwordActivity_passwordTV);
        responseMessageTV = (TextView)findViewById(R.id.passwordActivity_responseMessageTV);
        submitBTN = (Button)findViewById(R.id.passwordActivity_submitBTN);
        copyBTN = (Button)findViewById(R.id.passwordActivity_copyBTN);

        // START OF BACK BUTTON ---------------------------------------------------------------------
        ImageButton backBTN = findViewById(R.id.passwordActivity_backBTN);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // EBD OF BACK BUTTON -----------------------------------------------------------------------

        copyBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("password", passwordTV.getText().toString());
                clipboard.setPrimaryClip(clip);
            }
        });

        submitBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passwordTV.setText("");
                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pamsapp123.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
                UserAPI userAPI = retrofit.create(UserAPI.class);
                Call<ResponseBody> call = userAPI.getPassword(usernameET.getText().toString().trim(),"skldjklfjcdskzfj39u989jdsf");
                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                        String s = null;


                        try {
                            s = String.valueOf(response.body().string());
                            if(!s.contains("ERROR:")){
                                passwordTV.setText(s);
                                responseMessageTV.setText("");

                            }else{
                                responseMessageTV.setText(s);

                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        responseMessageTV.setText("Logged in successfully!");

                    }
                });
            }
        });

    }
}
