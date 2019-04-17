package kfupm.bader.com.appointmentsystem;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import kfupm.bader.com.appointmentsystem.adapters.DentistAdapter;
import kfupm.bader.com.appointmentsystem.models.Appointment;
import kfupm.bader.com.appointmentsystem.models.Clinic;
import kfupm.bader.com.appointmentsystem.models.Dentist;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClinicActivity extends AppCompatActivity {

    TextView clinicName, clinicStatus, clinicLocation,clinicProfile;

    ImageView emailBTN,phoneBTN,locationBTN,websiteBTN;
    ConstraintLayout bookButton;

    ImageButton menuBTN;
    TextView welcomeMessage;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    TextView navigationNameET;
    TextView navigationUserTypeET;
    NavigationView nv;

    ArrayAdapter<Dentist> adp1;



    private RecyclerView recyclerView;
    private List <Dentist> itemList;

    DentistAdapter itemArrayAdapter;

    Clinic c;
    int clinic_ID;
    String user_ID;
    String selectedDentist;
    EditText bookingDialogDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clinic);


        clinicName = (TextView)findViewById(R.id.clinicActivity_clinicName);
        clinicStatus = (TextView)findViewById(R.id.clinicActivity_clinicStatus);
        clinicLocation = (TextView)findViewById(R.id.clinicActivity_clinicLocation);
        clinicProfile = (TextView)findViewById(R.id.clinicActivity_clinicProfile);
        bookButton = (ConstraintLayout)findViewById(R.id.clinicActivity_primaryBookButton);

        emailBTN = (ImageView)findViewById(R.id.clinicActivity_emailButton);
        phoneBTN = (ImageView)findViewById(R.id.clinicActivity_phoneButton);
        locationBTN = (ImageView)findViewById(R.id.clinicActivity_locationButton);
        websiteBTN = (ImageView)findViewById(R.id.clinicActivity_websiteButton);




        menuBTN = findViewById(R.id.clinicActivity_menuBTN);
        welcomeMessage = findViewById(R.id.clinicActivity_loginWelcomeMessage);



        pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
        editor = pref.edit();


        final DrawerLayout dl = (DrawerLayout) findViewById(R.id.activity_clinic);

        nv = (NavigationView) findViewById(R.id.clinicActivity_nv);
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
                        startActivity(new Intent(ClinicActivity.this,LoginActivity.class));
                        break;
                    case R.id.guest_signupMenuButton:
                        startActivity(new Intent(ClinicActivity.this,SignupActivity.class));
                        break;
                    case R.id.aboutUs:
                        Toast.makeText(ClinicActivity.this, "This is us", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user_clinics:
                        startActivity(new Intent(ClinicActivity.this,ClinicsActivity.class));
                        break;
                    case R.id.user_doctors:
                        Toast.makeText(ClinicActivity.this, "Doctors", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.user_appointments:
                        startActivity(new Intent(ClinicActivity.this,AppointmentsActivity.class));
                        break;
                    case R.id.user_logout:
                        editor.putBoolean("loggedin", false);
                        editor.apply(); // commit changes
                        startActivity(new Intent(ClinicActivity.this,HomeActivity.class));
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
        user_ID = null;
        String fname = null;
        String lname = null;
        String type_ID = null;

        user_ID = (String) pref.getString("user_ID",null);
        fname = (String) pref.getString("fname",null);
        lname = (String) pref.getString("lname",null);
        type_ID = (String) pref.getString("type_ID",null);

        if(pref.getBoolean("loggedin", true)){
            welcomeMessage.setText("Welcome " + lname + "  ! ");
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
            welcomeMessage.setVisibility(View.VISIBLE);
        }else{
            bookButton.setVisibility(View.GONE);
            welcomeMessage.setVisibility(View.GONE);
            nv.getMenu().clear();
            nv.inflateMenu(R.menu.navigation_menu);
            navigationNameET.setText("Dear guest");
            navigationUserTypeET.setText("May you register ^_^");
        }


        clinic_ID = Integer.parseInt(getIntent().getStringExtra("clinic_ID"));

        final AVLoadingIndicatorView avl = (AVLoadingIndicatorView)findViewById(R.id.clinicActivity_dentistLoading);
        avl.smoothToShow();
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pamsapp123.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
        UserAPI userAPI = retrofit.create(UserAPI.class);
        Call<List<Clinic>> call = userAPI.getClinic(clinic_ID,2);
        call.enqueue(new Callback<List<Clinic>>() {
            @Override
            public void onResponse(Call<List<Clinic>> call, final retrofit2.Response<List<Clinic>> response) {

                c = response.body().get(0);
                clinicName.setText(c.getClinicName());
                clinicLocation.setText(c.getLocation().split(",")[0]);
                clinicProfile.setText(c.getClinicDescription());
                clinicStatus.setText(c.getStatusName());


                locationBTN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // Creates an Intent that will load a map of San Francisco
                        Uri gmmIntentUri = Uri.parse("geo:37.7749,-122.4194");
                        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                        mapIntent.setPackage("com.google.android.apps.maps");
                        startActivity(mapIntent);
                    }
                });

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




                Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pamsapp123.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
                UserAPI userAPI = retrofit.create(UserAPI.class);
                Call<List<Dentist>> calll = userAPI.getDentists(clinic_ID,1);
                calll.enqueue(new Callback<List<Dentist>>() {
                    @Override
                    public void onResponse(Call<List<Dentist>> call, final retrofit2.Response<List<Dentist>> response) {
                        avl.smoothToHide();
                        itemList = response.body();
                        itemArrayAdapter = new DentistAdapter(getApplicationContext(), response.body());
                        recyclerView = (RecyclerView) findViewById(R.id.clinicActivity_rv);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ClinicActivity.this));
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(itemArrayAdapter);

                        bookButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {



                                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(ClinicActivity.this);
                                LayoutInflater inflater = LayoutInflater.from(ClinicActivity.this);
                                final View dialogView = inflater.inflate(R.layout.dialog_booking, null);
                                dialogBuilder.setView(dialogView);
                                List<String> list = new ArrayList<String>();
                                for(int i=0; i<itemList.size();i++){
                                    list.add(itemList.get(i).toString());
                                }
                                final RadioGroup doctorsGroup = (RadioGroup)dialogView.findViewById(R.id.bookingDialog_doctorsGroup);
                                final RadioButton sdoctor = (RadioButton)dialogView.findViewById(R.id.bookingDialog_specificDoctor);
                                final RadioButton adoctor = (RadioButton)dialogView.findViewById(R.id.bookingDialog_anyDoctor);
                                final Spinner doctorSpinner = (Spinner)dialogView.findViewById(R.id.bookingDialog_doctors);
                                final TextView bookingDialogNotes = (TextView)dialogView.findViewById(R.id.bookingDialog_notes);
                                bookingDialogDate = (EditText)dialogView.findViewById(R.id.bookingDialog_date);

                                doctorsGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                                      @Override
                                      public void onCheckedChanged(RadioGroup group, int checkedId)
                                      {
                                          switch(doctorsGroup.getCheckedRadioButtonId()){
                                              case R.id.bookingDialog_specificDoctor:
                                                  doctorSpinner.setVisibility(View.VISIBLE);

                                                  break;
                                              case R.id.bookingDialog_anyDoctor:
                                                  doctorSpinner.setVisibility(View.GONE);
                                                  break;
                                          }
                                      }
                                  }
                                );
                                adp1=new ArrayAdapter<Dentist>(ClinicActivity.this,android.R.layout.simple_list_item_1,itemList);
//                              adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                doctorSpinner.setAdapter(adp1);

                                bookingDialogDate.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        DatePickerDialog mDate = new DatePickerDialog(ClinicActivity.this, date, 2016, 2, 24);
                                        mDate.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                                        mDate.show();

                                    }
                                });



                                dialogBuilder.setTitle("Book Appointment");
                                dialogBuilder.setPositiveButton("BOOK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // Backend Stuff
                                        selectedDentist = itemList.get(doctorSpinner.getSelectedItemPosition()).getDentistID();
                                        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pamsapp123.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
                                        UserAPI userAPI = retrofit.create(UserAPI.class);
                                        String bookingDialogNotesValue = bookingDialogNotes.getText().toString();
                                        String bookingDialogDateValue = bookingDialogDate.getText().toString();

                                        Call<ResponseBody> call = userAPI.addAppointment(user_ID,selectedDentist,bookingDialogNotesValue,bookingDialogDateValue,"4");
                                        call.enqueue(new Callback<ResponseBody>() {

                                            @Override
                                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {


                                                try {
                                                    if(response.body().string().toLowerCase().contains("added")) {
                                                        Toast.makeText(ClinicActivity.this, "Added Successfully!", Toast.LENGTH_LONG).show();
                                                    }else{
                                                        Toast.makeText(ClinicActivity.this, "There is an error, try again!!", Toast.LENGTH_LONG).show();

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
                                dialogBuilder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {

                                    }
                                });

                                final AlertDialog b = dialogBuilder.create();
                                b.show();














                            }
                        });

                    }

                    @Override
                    public void onFailure(Call<List<Dentist>> call, Throwable t) {

                    }
                });



            }

            @Override
            public void onFailure(Call<List<Clinic>> call, Throwable t) {

            }
        });



    }

    final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            view.setMinDate(System.currentTimeMillis() - 1000);
            bookingDialogDate.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
        }
    };
}
