package kfupm.bader.com.appointmentsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kfupm.bader.com.appointmentsystem.adapters.DentistAdapter;
import kfupm.bader.com.appointmentsystem.models.Clinic;
import kfupm.bader.com.appointmentsystem.models.Dentist;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DentistActivity extends AppCompatActivity {

    private TextView dentistName,dentistStatus,dentistSpecialty;
    private ImageView emailBTN,websiteBTN,phoneBTN;

    ImageButton menuBTN,loginBTN;
    TextView welcomeMessage;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    TextView navigationNameET;
    TextView navigationUserTypeET;
    TextView dentistProfile;
    NavigationView nv;



    Dentist c;
    int dentist_ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dentist);


        dentistName = (TextView)findViewById(R.id.dentistActivity_dentistName);
        dentistStatus = (TextView)findViewById(R.id.dentistActivity_dentistStatus);
        dentistSpecialty = (TextView)findViewById(R.id.dentistActivity_dentistSpecialty);
        dentistProfile = (TextView)findViewById(R.id.dentistActivity_dentistProfile);

        emailBTN = (ImageView)findViewById(R.id.dentistActivity_emailBTN);
        websiteBTN = (ImageView)findViewById(R.id.dentistActivity_websiteBTN);
        phoneBTN = (ImageView)findViewById(R.id.dentistActivity_phoneBTN);


        loginBTN = findViewById(R.id.dentistActivity_loginBTN);
        menuBTN = findViewById(R.id.dentistActivity_menuBTN);
        welcomeMessage = findViewById(R.id.dentistActivity_loginWelcomeMessage);
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DentistActivity.this,LoginActivity.class));
            }
        });


        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();


        final DrawerLayout dl = (DrawerLayout) findViewById(R.id.activity_dentist);

        nv = (NavigationView) findViewById(R.id.dentistActivity_nv);
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
                        startActivity(new Intent(DentistActivity.this,LoginActivity.class));
                        break;
                    case R.id.guest_signupMenuButton:
                        startActivity(new Intent(DentistActivity.this,SignupActivity.class));
                        break;
                    case R.id.aboutUs:
                        Toast.makeText(DentistActivity.this, "This is us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user_clinics:
                        startActivity(new Intent(DentistActivity.this,ClinicsActivity.class));
                        break;
                    case R.id.user_doctors:
                        Toast.makeText(DentistActivity.this, "Doctors", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user_appointments:
                        startActivity(new Intent(DentistActivity.this,AppointmentsActivity.class));
                        break;
                    case R.id.user_logout:
                        editor.putBoolean("loggedin", false);
                        editor.apply(); // commit changes
                        startActivity(new Intent(DentistActivity.this,HomeActivity.class));
                        finish();
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




        dentist_ID = Integer.parseInt(getIntent().getStringExtra("dentist_ID"));

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pamsapp123.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<List<Dentist>> call = userAPI.getDentist(dentist_ID,2);
        call.enqueue(new Callback<List<Dentist>>() {
            @Override
            public void onResponse(Call<List<Dentist>> call, final retrofit2.Response<List<Dentist>> response) {
                c = response.body().get(0);


                dentistName.setText("Dr. " + c.getFname()+ " "+c.getLname());
                dentistSpecialty.setText(c.getSpecialtyName());
                dentistStatus.setText(c.getStatusName());
                dentistProfile.setText(c.getBio());

                websiteBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW,
                                Uri.parse("http://"+c.getWebsite()));
                        startActivity(intent);
                    }
                });

                phoneBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+966126020429", null)));
                    }
                });

                emailBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String[] TO = {
                                c.getEmail()
                        };

                        Intent emailIntent = new Intent(Intent.ACTION_SEND);
                        emailIntent.setData(Uri.parse("mailto:"));
                        emailIntent.setType("text/plain");
                        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

                        try {
                            startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                        } catch (android.content.ActivityNotFoundException ignored) {

                        }
                    }
                });



            }

            @Override
            public void onFailure(Call<List<Dentist>> call, Throwable t) {

            }
        });



    }
}
