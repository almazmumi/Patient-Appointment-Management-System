package kfupm.bader.com.appointmentsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class HomeActivity extends AppCompatActivity {
    ImageButton loginBTN,menuBTN,logutBTN;
    TextView welcomeMessage;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        loginBTN = findViewById(R.id.homeActivity_loginBTN);
        logutBTN= findViewById(R.id.homeActivity_logoutBTN);
        welcomeMessage = findViewById(R.id.homeActivity_loginWelcomeMessage);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();

        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));

            }
        });
        logutBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putBoolean("loggedin", false); // Storing boolean - true/false
                editor.apply(); // commit changes
                welcomeMessage.setVisibility(View.GONE);
                loginBTN.setVisibility(View.VISIBLE);
                logutBTN.setVisibility(View.GONE);
            }
        });







    }

    @Override
    protected void onResume() {
        super.onResume();
        // Check if he is signed up or not
        Intent i = getIntent();
        Bundle bd = i.getExtras();
        int user_id = -1;
        String user_name = null;
        if(bd != null) {
            user_id = Integer.valueOf((String) bd.get("id"));
            user_name = (String) bd.get("name");
        }


        if(pref.getBoolean("loggedin", false)){
            welcomeMessage.setText("Welcome "+ user_name + "  ! ");
            welcomeMessage.setVisibility(View.VISIBLE);
            loginBTN.setVisibility(View.GONE);
            logutBTN.setVisibility(View.VISIBLE);
        }else{
            welcomeMessage.setVisibility(View.GONE);
            loginBTN.setVisibility(View.VISIBLE);
            logutBTN.setVisibility(View.GONE);
        }
    }
}
