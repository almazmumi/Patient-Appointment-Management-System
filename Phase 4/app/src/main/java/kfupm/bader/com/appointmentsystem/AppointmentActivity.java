package kfupm.bader.com.appointmentsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import kfupm.bader.com.appointmentsystem.models.Appointment;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AppointmentActivity extends AppCompatActivity {

    int appointment_ID;
    Appointment appointment;
    TextView dateTV,statusTV,idTV,clinicNameTV,doctorNameTV,commentsTV,commentsHeaderTV;
    ImageView clinicEmail,clinicPhone,clinicLocation;
    ImageView doctorEmail,doctorPhone;
    ImageView cancelAppointmentButton;


    ImageButton menuBTN;
    TextView welcomeMessage;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    TextView navigationNameET;
    TextView navigationUserTypeET;
    NavigationView nv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        dateTV = (TextView)findViewById(R.id.appointmentActivity_apmDate);
        statusTV = (TextView)findViewById(R.id.clinicActivity_clinicStatus);
        idTV = (TextView)findViewById(R.id.appointmentActivity_ID);
        clinicNameTV = (TextView)findViewById(R.id.appointmentActivity_clinicName);
        doctorNameTV = (TextView)findViewById(R.id.appointmrntActivity_doctorName);
        commentsTV = (TextView)findViewById(R.id.appointmentActivity_commentTextView);
        commentsHeaderTV = (TextView)findViewById(R.id.appointmentActivity_commentHeaderTV);

        clinicEmail = (ImageView)findViewById(R.id.appointmentActivity_clinicEmail);
        clinicLocation = (ImageView)findViewById(R.id.appointmentActivity_clinicLocation);
        clinicPhone = (ImageView)findViewById(R.id.appointmentActivity_clinicPhone);

        doctorEmail = (ImageView)findViewById(R.id.appointmentActivity_doctorEmail);
        doctorPhone = (ImageView)findViewById(R.id.appointmentActivity_doctorPhone);
        cancelAppointmentButton = (ImageView)findViewById(R.id.appointmentActivity_cancelButton);



        menuBTN = findViewById(R.id.appointmentActivity_menuBTN);
        welcomeMessage = findViewById(R.id.appointmentActivity_loginWelcomeMessage);



        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();


        final DrawerLayout dl = (DrawerLayout) findViewById(R.id.activity_appointment);

        nv = (NavigationView) findViewById(R.id.appointmentActivity_nv);
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
                        startActivity(new Intent(AppointmentActivity.this,LoginActivity.class));
                        break;
                    case R.id.guest_signupMenuButton:
                        startActivity(new Intent(AppointmentActivity.this,SignupActivity.class));
                        break;
                    case R.id.aboutUs:
                        Toast.makeText(AppointmentActivity.this, "This is us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user_clinics:
                        startActivity(new Intent(AppointmentActivity.this,ClinicsActivity.class));
                        break;
                    case R.id.user_doctors:
                        Toast.makeText(AppointmentActivity.this, "Doctors", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user_appointments:
                        startActivity(new Intent(AppointmentActivity.this,AppointmentsActivity.class));
                        break;
                    case R.id.user_logout:
                        editor.putBoolean("loggedin", false);
                        editor.apply(); // commit changes
                        startActivity(new Intent(AppointmentActivity.this,HomeActivity.class));
                        finish();
                        break;


                }

                return true;
            }
        });


        // Check if he is signed up or not
        String user_ID = null;
        String fname = null;
        String lname = null;
        String type_ID = null;

        user_ID = (String) pref.getString("user_ID",null);
        fname = (String) pref.getString("fname",null);
        lname = (String) pref.getString("lname",null);
        type_ID = (String) pref.getString("type_ID",null);



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
        appointment_ID = Integer.parseInt(getIntent().getStringExtra("appointment_ID"));
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pamsapp123.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<List<Appointment>> call = userAPI.getAppointment(appointment_ID,2);
        call.enqueue(new Callback<List<Appointment>>() {
            @Override
            public void onResponse(Call<List<Appointment>> call, final retrofit2.Response<List<Appointment>> response) {
                appointment = response.body().get(0);
                dateTV.setText("Date: " + response.body().get(0).getApmDate());
                statusTV.setText(response.body().get(0).getStatusName());
                idTV.setText("#"+response.body().get(0).getAppointmentID());
                clinicNameTV.setText(response.body().get(0).getClinicName());
                doctorNameTV.setText("Dr. "+ response.body().get(0).getFname() + " " +  response.body().get(0).getLname());


                if((response.body().get(0).getReceptComment() != null)){
                    commentsTV.setText("" + response.body().get(0).getReceptComment());
                    commentsTV.setVisibility(View.VISIBLE);
                    commentsHeaderTV.setVisibility(View.VISIBLE);
                }else{
                    commentsTV.setVisibility(View.GONE);
                    commentsHeaderTV.setVisibility(View.GONE);
                }

                if(!response.body().get(0).getStatusName().toLowerCase().contains("cancelled")
                        ||!response.body().get(0).getStatusName().toLowerCase().contains("rejected")){
                    cancelAppointmentButton.setVisibility(View.VISIBLE);

                }else{
                    cancelAppointmentButton.setVisibility(View.GONE);
                }

                cancelAppointmentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pamsapp123.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
                        UserAPI userAPI = retrofit.create(UserAPI.class);
                        Call<ResponseBody> call = userAPI.cancelAppointment(appointment_ID,3);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                                statusTV.setText("Cancelled");
                                cancelAppointmentButton.setVisibility(View.GONE);

                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                });

                clinicEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String[] TO = {
                                response.body().get(0).getCemail()
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
                clinicLocation.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Creates an Intent that will load a map of San Francisco
                        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                });
                clinicPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+966126020429", null)));
                    }
                });

                doctorEmail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String[] TO = {
                                response.body().get(0).getDemail()
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




                doctorPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+966565806744", null)));
                    }
                });


            }

            @Override
            public void onFailure(Call<List<Appointment>> call, Throwable t) {
                commentsTV.setText(t+"");
            }
        });
    }
}
