package kfupm.bader.com.appointmentsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
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
    TextView navigationNameET;
    TextView navigationUserTypeET;


    private DrawerLayout dl;
    private NavigationView nv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        loginBTN = findViewById(R.id.homeActivity_loginBTN);
        logutBTN = findViewById(R.id.homeActivity_logoutBTN);
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
                editor.putBoolean("loggedin", false);
                editor.apply(); // commit changes
                welcomeMessage.setVisibility(View.GONE);
                loginBTN.setVisibility(View.VISIBLE);
                logutBTN.setVisibility(View.GONE);
                navigationNameET.setText("Welcome our guest :)");
                navigationUserTypeET.setText("Register or smothing else will happen @_@");

            }
        });



        dl = (DrawerLayout)findViewById(R.id.activity_home);
        nv = (NavigationView)findViewById(R.id.nv);
        View headerView = nv.getHeaderView(0);
        navigationNameET = (TextView)headerView.findViewById(R.id.navHeader_name);
        navigationUserTypeET = (TextView)headerView.findViewById(R.id.navHeader_userType);

        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.account:
                        Toast.makeText(HomeActivity.this, "My Account", Toast.LENGTH_SHORT).show();
                    case R.id.settings:
                        Toast.makeText(HomeActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                    case R.id.mycart:
                        Toast.makeText(HomeActivity.this, "My Cart", Toast.LENGTH_SHORT).show();
                    default:
                        return true;
                }


            }
        });
    }


















    @Override
    protected void onResume() {
        super.onResume();

        // Check if he is signed up or not
        String user_ID = null;
        String fname = null;
        String lname = null;
        String type_ID = null;

        user_ID = (String) pref.getString("user_ID",null);
        fname = (String) pref.getString("fname",null);
        lname = (String) pref.getString("lname",null);
        type_ID = (String) pref.getString("type_ID",null);
        Log.d("hi,," , type_ID.trim()+"");


        if(pref.getBoolean("loggedin", false)){
            welcomeMessage.setText("Welcome " + lname + "  ! ");
            welcomeMessage.setVisibility(View.VISIBLE);
            loginBTN.setVisibility(View.GONE);
            logutBTN.setVisibility(View.VISIBLE);
        }else{
            welcomeMessage.setVisibility(View.GONE);
            loginBTN.setVisibility(View.VISIBLE);
            logutBTN.setVisibility(View.GONE);
        }


        navigationNameET.setText(fname + " " + lname);

        if (type_ID != null) {
            switch (type_ID.trim()){
                case "1":
                    navigationUserTypeET.setText("Administrator");
                    break;
                case "2":
                    navigationUserTypeET.setText("Manager");
                    break;
                case "3":
                    navigationUserTypeET.setText("Receptionist");
                    break;
                case "4":
                    navigationUserTypeET.setText("Patient");
                    break;
            }
        }

    }
}
