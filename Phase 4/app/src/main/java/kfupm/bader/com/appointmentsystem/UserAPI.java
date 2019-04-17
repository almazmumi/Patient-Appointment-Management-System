package kfupm.bader.com.appointmentsystem;

import java.util.List;

import kfupm.bader.com.appointmentsystem.models.Appointment;
import kfupm.bader.com.appointmentsystem.models.Clinic;
import kfupm.bader.com.appointmentsystem.models.Dentist;
import kfupm.bader.com.appointmentsystem.models.User;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface UserAPI {
    @FormUrlEncoded
    @POST("login.php")
    Call<ResponseBody> authenticateUser(
            @Field("username") String username,
            @Field("password") String password
    );

    @GET("p.php")
    Call<ResponseBody> getPassword(
            @Query("username") String username,
            @Query("key") String key
    );

    @GET("appointments.php")
    Call<List<Appointment>> getAppointments(
            @Query("user_ID") int user_ID,
            @Query("key") int key
    );

    @GET("appointments.php")
    Call<List<Appointment>> getAppointment(
            @Query("appointment_ID") int appointment_ID,
            @Query("key") int key
    );

    @GET("appointments.php")
    Call<ResponseBody> cancelAppointment(
            @Query("appointment_ID") int appointment_ID,
            @Query("key") int key
    );

    @FormUrlEncoded
    @POST("appointments.php")
    Call<ResponseBody> addAppointment(
            @Field("user_ID") String user_ID,
            @Field("dentist_ID") String dentist_ID,
            @Field("notes") String notes,
            @Field("apm_date") String apm_date,
            @Field("key") String key
    );


    @GET("clinics.php")
    Call<List<Clinic>> getClinics(
            @Query("key") int key
    );

    @GET("clinics.php")
    Call<List<Clinic>> getClinic(
            @Query("clinic_ID") int clinic_ID,
            @Query("key") int key
    );

    @GET("dentists.php")
    Call<List<Dentist>> getDentists(
            @Query("clinic_ID") int clinic_ID,
            @Query("key") int key
    );

    @GET("dentists.php")
    Call<List<Dentist>> getDentist(
            @Query("dentist_ID") int dentist_ID,
            @Query("key") int key
    );


    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> insertUser(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("username") String username,
            @Field("hashed_pw") String hashed_pw,
            @Field("email") String email,
            @Field("gender") String gender);



}
