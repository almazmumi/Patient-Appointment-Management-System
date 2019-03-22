package kfupm.bader.com.appointmentsystem;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import kfupm.bader.com.appointmentsystem.models.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {


    private EditText fnameET,lnameET,hashed_pwET,confirmPasswordET,emailET;
    private TextView type_idET;
    private ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        fnameET = findViewById(R.id.signupActivity_firstnameET);
        lnameET = findViewById(R.id.signupActivity_lastnameET);
        hashed_pwET = findViewById(R.id.signupActivity_passwordET);
        confirmPasswordET = findViewById(R.id.signupActivity_confirmPasswordET);
        emailET = findViewById(R.id.signupActivity_emailET);
        type_idET = findViewById(R.id.signupActivity_typeID);






    }

//    void readMode(){
//
//        mName.setFocusableInTouchMode(false);
//        mSpecies.setFocusableInTouchMode(false);
//        mBreed.setFocusableInTouchMode(false);
//        mName.setFocusable(false);
//        mSpecies.setFocusable(false);
//        mBreed.setFocusable(false);
//
//        mGenderSpinner.setEnabled(false);
//        mBirth.setEnabled(false);
//
//        mFabChoosePic.setVisibility(View.INVISIBLE);
//
//    }
    private void postData(final String key) {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

//        readMode();

        String name = fnameET.getText().toString().trim();
        String species = lnameET.getText().toString().trim();
        String breed = hashed_pwET.getText().toString().trim();


        String birth = mBirth.getText().toString().trim();


        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        // to get today date
//        Date c = Calendar.getInstance().getTime();
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-DD");
//        String reg_data = df.format(c);
//
//        Call<User> call = apiInterface.insertUser(key, fname, lname, email, hashed_pw, reg_data, type_id);
//
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//
//                progressDialog.dismiss();
//
//                Log.i(EditorActivity.class.getSimpleName(), response.toString());
//
//                String value = response.body().getValue();
//                String message = response.body().getMassage();
//
//                if (value.equals("1")){
//                    finish();
//                } else {
//                    Toast.makeText(EditorActivity.this, message, Toast.LENGTH_SHORT).show();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//                progressDialog.dismiss();
//                Toast.makeText(EditorActivity.this, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
//            }
//        });

    }
}
