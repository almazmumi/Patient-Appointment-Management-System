package kfupm.bader.com.appointmentsystem;

import java.util.List;

import kfupm.bader.com.appointmentsystem.models.User;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by haerul on 15/03/18.
 */

public interface ApiInterface {

    @POST("get_pets.php")
    Call<List<User>> getUsers();

    @FormUrlEncoded
    @POST("signup.php")
    Call<User> insertUser(
            @Field("key") String key,
            @Field("id") int id,
            @Field("fname") String fname,
            @Field("lname") String lname,
            @Field("username") String username,
            @Field("hashed_pw") String hashed_pw,
            @Field("email") String email,
            @Field("reg_date") String reg_date,
            @Field("type_id") int type_id);



}
