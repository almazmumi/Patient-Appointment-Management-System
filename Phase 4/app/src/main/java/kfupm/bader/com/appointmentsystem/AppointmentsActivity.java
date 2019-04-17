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

import kfupm.bader.com.appointmentsystem.adapters.AppointmentAdapter;
import kfupm.bader.com.appointmentsystem.models.Appointment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppointmentsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List <Appointment> itemList;
    TextView tv;
    AppointmentAdapter itemArrayAdapter;



    ImageButton menuBTN;
    TextView welcomeMessage;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    TextView navigationNameET;
    TextView navigationUserTypeET;
    NavigationView nv;
    String user_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointments);

        // Initializing list view with the custom adapter
        tv = (TextView)findViewById(R.id.homeActivity_apmDate);

        SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0);

        user_ID = null;
        String fname = null;
        String lname = null;
        String type_ID = null;

        user_ID = (String) pref.getString("user_ID",null);
        fname = (String) pref.getString("fname",null);
        lname = (String) pref.getString("lname",null);
        type_ID = (String) pref.getString("type_ID",null);







        menuBTN = findViewById(R.id.appointmentsActivity_menuBTN);
        welcomeMessage = findViewById(R.id.appointmentsActivity_loginWelcomeMessage);



        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();


        final DrawerLayout dl = (DrawerLayout) findViewById(R.id.activity_appointments);

        nv = (NavigationView) findViewById(R.id.appointmentsActivity_nv);
        View headerView = nv.getHeaderView(0);
        navigationNameET = (TextView)headerView.findViewById(R.id.navHeader_name);

        navigationUserTypeET = (TextView)headerView.findViewById(R.id.navHeader_userType);
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
                        startActivity(new Intent(AppointmentsActivity.this,LoginActivity.class));
                        break;
                    case R.id.guest_signupMenuButton:
                        startActivity(new Intent(AppointmentsActivity.this,SignupActivity.class));
                        break;
                    case R.id.aboutUs:
                        Toast.makeText(AppointmentsActivity.this, "This is us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user_clinics:
                        startActivity(new Intent(AppointmentsActivity.this,ClinicsActivity.class));
                        break;
                    case R.id.user_doctors:
                        Toast.makeText(AppointmentsActivity.this, "Doctors", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user_appointments:
                        dl.closeDrawer(Gravity.START);
                        break;
                    case R.id.user_logout:
                        editor.putBoolean("loggedin", false);
                        editor.apply(); // commit changes
                        startActivity(new Intent(AppointmentsActivity.this,HomeActivity.class));
                        finish();
                        break;


                }

                return true;
            }
        });



        welcomeMessage.setText("Welcome " + fname + "  ! ");
        welcomeMessage.setVisibility(View.VISIBLE);
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




    }


    @Override
    protected void onResume() {
        super.onResume();



        final AVLoadingIndicatorView avl = (AVLoadingIndicatorView)findViewById(R.id.appointmentActivity_appointmentLoading);
        avl.smoothToShow();
        // Backend Stuff
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pamsapp123.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<List<Appointment>> call = userAPI.getAppointments(Integer.parseInt(user_ID),1);
        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, retrofit2.Response<List<Appointment>> response) {
                avl.smoothToHide();
                itemList = response.body();
                itemArrayAdapter = new AppointmentAdapter(getApplicationContext(), itemList);
                recyclerView = (RecyclerView) findViewById(R.id.appointmentsActivity_recyclerView);
                recyclerView.setLayoutManager(new LinearLayoutManager(AppointmentsActivity.this));
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(itemArrayAdapter);

            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {

            }
        });

    }
}
