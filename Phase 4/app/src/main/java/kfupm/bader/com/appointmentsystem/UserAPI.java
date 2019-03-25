package kfupm.bader.com.appointmentsystem;

import java.util.List;

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

    @FormUrlEncoded
    @POST("register.php")
    Call<ResponseBody> insertUser(
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("username") String username,
            @Field("hashed_pw") String hashed_pw,
            @Field("email") String email,
            @Field("reg_date") String reg_date,
            @Field("type_ID") String type_id,
            @Field("gender") String gender);



}
