package kfupm.bader.com.appointmentsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.util.List;

import kfupm.bader.com.appointmentsystem.adapters.ClinicAdapter;
import kfupm.bader.com.appointmentsystem.models.Clinic;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity {
    ImageButton loginBTN,menuBTN;
    TextView welcomeMessage;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    TextView navigationNameET;
    TextView navigationUserTypeET;
    private NavigationView nv;




    private RecyclerView recyclerView;
    private List <Clinic> itemList;

    ClinicAdapter itemArrayAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        menuBTN = findViewById(R.id.homeActivity_menuBTN);
        loginBTN = findViewById(R.id.homeActivity_loginBTN);
        welcomeMessage = findViewById(R.id.homeActivity_loginWelcomeMessage);

        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
            }
        });




        final DrawerLayout dl = (DrawerLayout) findViewById(R.id.activity_home);

        nv = (NavigationView) findViewById(R.id.nv);
        View headerView = nv.getHeaderView(0);
        navigationNameET = (TextView)headerView.findViewById(R.id.navHeader_name);
        navigationUserTypeET = (TextView)headerView.findViewById(R.id.navHeader_userType);
        navigationNameET.setText("Dear guest");
        navigationUserTypeET.setText("May you register ^_^");
        menuBTN.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                dl.openDrawer(Gravity.START);

            }
        });
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.guest_loginMenuButton:
                        startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                        break;
                    case R.id.guest_signupMenuButton:
                        startActivity(new Intent(HomeActivity.this,SignupActivity.class));
                        break;
                    case R.id.aboutUs:
                        Toast.makeText(HomeActivity.this, "This is us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user_clinics:
                        startActivity(new Intent(HomeActivity.this,ClinicsActivity.class));
                        break;
                    case R.id.user_doctors:
                        Toast.makeText(HomeActivity.this, "Doctors", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user_appointments:
                        startActivity(new Intent(HomeActivity.this,AppointmentsActivity.class));
                        break;
                    case R.id.user_logout:
                        editor.putBoolean("loggedin", false);
                        editor.apply(); // commit changes
                        welcomeMessage.setVisibility(View.GONE);
                        loginBTN.setVisibility(View.VISIBLE);
                        nv.getMenu().clear();
                        nv.inflateMenu(R.menu.navigation_menu);
                        navigationNameET.setText("Dear guest");
                        navigationUserTypeET.setText("May you register ^_^");
                        break;


                }

                return true;
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


        if(pref.getBoolean("loggedin", true)){
            welcomeMessage.setText("Welcome " + fname + "  ! ");
            welcomeMessage.setVisibility(View.VISIBLE);
            loginBTN.setVisibility(View.GONE);
            nv.getMenu().clear();
            nv.inflateMenu(R.menu.user_navigation_menu);
            navigationNameET.setText(fname + " " + lname);
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
        }else{
            welcomeMessage.setVisibility(View.GONE);
            loginBTN.setVisibility(View.VISIBLE);
            nv.getMenu().clear();
            nv.inflateMenu(R.menu.navigation_menu);
            navigationNameET.setText("Dear guest");
            navigationUserTypeET.setText("May you register ^_^");
        }







        // Backend Stuff

        final AVLoadingIndicatorView avl = (AVLoadingIndicatorView)findViewById(R.id.homeActivity_clinicLoading);
        avl.smoothToShow();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pamsapp123.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<List<Clinic>> call = userAPI.getClinics(1);
        call.enqueue(new Callback<List<Clinic>>() {
            @Override
            public void onResponse(Call<List<Clinic>> call, retrofit2.Response<List<Clinic>> response) {
                avl.smoothToHide();
                itemList = response.body();
                itemArrayAdapter = new ClinicAdapter(getApplicationContext(), itemList);
                recyclerView = (RecyclerView) findViewById(R.id.homeActivity_clinicRV);
                recyclerView.setLayoutManager(new LinearLayoutManager(HomeActivity.this));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(itemArrayAdapter);

            }

            @Override
            public void onFailure(Call<List<Clinic>> call, Throwable t) {

            }
        });
    }

}
