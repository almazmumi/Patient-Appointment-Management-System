package kfupm.bader.com.appointmentsystem;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ActionBarContextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import kfupm.bader.com.appointmentsystem.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {


    private EditText fnameET,lnameET,hashed_pwET,confirmPasswordET,emailET,usernameET,phoneNumber1ET,phoneNumber2ET;
    private Button signupBTN;
    private Spinner genderSP;
    private Button addPhoneNumberBTN;
    private UserAPI userAPIInterface;

    private TextView  textView;

    String username,email,hashed_pw,fname,lname,reg_date,type_ID,gender,phoneNumber1,phoneNumber2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        usernameET = findViewById(R.id.signupActivity_usernameET);
        fnameET = findViewById(R.id.signupActivity_firstnameET);
        lnameET = findViewById(R.id.signupActivity_lastnameET);
        hashed_pwET = findViewById(R.id.signupActivity_passwordET);
        confirmPasswordET = findViewById(R.id.signupActivity_confirmPasswordET);
        emailET = findViewById(R.id.signupActivity_emailET);
        signupBTN = findViewById(R.id.signupActivity_signupBTN);
        phoneNumber1ET = findViewById(R.id.signupActivity_phoneNumber1);
        phoneNumber2ET = findViewById(R.id.signupActivity_phoneNumber2);
        addPhoneNumberBTN = findViewById(R.id.signupActivity_addPhoneNumberBTN);
        genderSP = findViewById(R.id.signupActivity_gender);



        TextView type_idTV = findViewById(R.id.signupActivity_typeID);
        final TextView test = findViewById(R.id.textView4);

        // to get register date (Current Date)
        Date todayDate = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        reg_date = formatter.format(todayDate);

        // to get type id
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                type_ID= null;
            } else {
                type_ID= extras.getString("type_ID");
                switch (type_ID){
                    case "1":
                        type_idTV.setText("Administrator");
                        break;
                    case "2":
                        type_idTV.setText("Manager");
                        break;
                    case "3":
                        type_idTV.setText("Receptionist");
                        break;
                    case "4":
                        type_idTV.setText("Patient");
                        break;
                }

            }
        } else {
            type_ID = (String) savedInstanceState.getSerializable("type_id");
        }





        // START OF ADD SECOND PHONE NUMBER BUTTON ---------------------------------------------------------------------
        addPhoneNumberBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(phoneNumber2ET.getVisibility() == View.GONE){
                    phoneNumber2ET.setVisibility(View.VISIBLE);
                    addPhoneNumberBTN.setText("Delete second Phone Number");
                }else if(phoneNumber2ET.getVisibility() == View.VISIBLE){
                    phoneNumber2ET.setVisibility(View.GONE);
                    addPhoneNumberBTN.setText("Add second Phone Number");
                }
            }
        });
        // END OF ADD SECOND PHONE NUMBER BUTTON ---------------------------------------------------------------------

        // START OF BACK BUTTON ---------------------------------------------------------------------
        ImageButton backBTN = findViewById(R.id.signupActivity_backBTN);
        backBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // EBD OF BACK BUTTON -----------------------------------------------------------------------

        // START OF SUBMIT BUTTON ---------------------------------------------------------------------
        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Changing color button to gray and dysfunction the button
                GradientDrawable  background = (GradientDrawable)signupBTN.getBackground().mutate();
                background.setColor(Color.GRAY);
                signupBTN.setEnabled(false);

                // Validating Edittexts
                if(fnameET.getText().toString().length() == 0
                   || lnameET.getText().toString().length() == 0
                        || usernameET.getText().toString().length() == 0
                        || hashed_pwET.getText().toString().length() == 0
                        || confirmPasswordET.getText().toString().length() == 0
                        || emailET.getText().toString().length() == 0
                        || phoneNumber1ET.getText().toString().length() == 0
                        ){
                    Snackbar.make(signupBTN,"Please fill all blanks!", Snackbar.LENGTH_LONG).show();
                }
                // To confirm that password and confirmed password are matched
                else if(!confirmPasswordET.getText().toString().equals(hashed_pwET.getText().toString())){
                    Snackbar.make(signupBTN,"The password and confirm password doesn't match!", Snackbar.LENGTH_LONG).show();
                }

                else if(!emailET.getText().toString().contains("@") || emailET.getText().toString().trim().contains(" ")){
                    Snackbar.make(signupBTN,"Email field is incorrect!", Snackbar.LENGTH_LONG).show();
                }else{
                    fname = fnameET.getText().toString();
                    lname = lnameET.getText().toString();
                    username = usernameET.getText().toString();
                    hashed_pw = hashed_pwET.getText().toString();
                    email = emailET.getText().toString();
                    gender = String.valueOf(genderSP.getSelectedItem());
                    phoneNumber1 = phoneNumber1ET.getText().toString();
                    phoneNumber2 = phoneNumber2ET.getText().toString().length() == 0? "":phoneNumber2ET.getText().toString();

                    // Backend Stuff
                    Retrofit retrofit = new Retrofit.Builder().baseUrl("https://pamsapp123.herokuapp.com/").addConverterFactory(GsonConverterFactory.create()).build();
                    UserAPI userAPI = retrofit.create(UserAPI.class);
                    Call<ResponseBody> call = userAPI.insertUser(fname,lname,username,hashed_pw,email,reg_date,type_ID,gender);
                    call.enqueue(new Callback<ResponseBody>() {

                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                            try {
                                String s = String.valueOf(response.body().string());
                                signupBTN.setText(s);
                                signupBTN.setEnabled(true);

                                if(!s.contains("ERROR:")){
                                    finish();
                                }else{

                                    ScheduledExecutorService scheduledExecutorService;
                                    scheduledExecutorService = Executors.newScheduledThreadPool(1);
                                    scheduledExecutorService.schedule(new Runnable(){
                                          @Override
                                          public void run() {
                                              SignupActivity.this.runOnUiThread(new Runnable(){

                                                  @Override
                                                  public void run() {
                                                      signupBTN.setText("SIGN UP");
                                                      GradientDrawable  background = (GradientDrawable)signupBTN.getBackground().mutate();
                                                      background.setColor(getResources().getColor(R.color.colorPrimary));
                                                  }});

                                          }},5,TimeUnit.SECONDS);




                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(SignupActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }


            }
        });
        // END OF SUBMIT SECOND PHONE NUMBER BUTTON ---------------------------------------------------------------------
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
