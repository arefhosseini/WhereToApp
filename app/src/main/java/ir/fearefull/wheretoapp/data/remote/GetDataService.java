package ir.fearefull.wheretoapp.data.remote;

import java.util.List;

import ir.fearefull.wheretoapp.data.model.api.Place;
import ir.fearefull.wheretoapp.data.model.api.PlaceResponse;
import ir.fearefull.wheretoapp.data.model.api.PlaceReviews;
import ir.fearefull.wheretoapp.data.model.api.PlaceSummary;
import ir.fearefull.wheretoapp.data.model.api.UserResponse;
import ir.fearefull.wheretoapp.data.model.api.VerifyResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GetDataService {

    @POST("/verify/")
    Call<VerifyResponse> verifyUser(@Body RequestBody requestBody);

    @POST("/user/")
    Call<UserResponse> createUser(@Body RequestBody requestBody);

    @PUT("/user/")
    Call<UserResponse> editUser(@Body RequestBody requestBody);

    @Multipart
    @POST("user/upload/")
    Call<ResponseBody> uploadProfileImage(
            @Part("phone_number") RequestBody phoneNumber,
            @Part MultipartBody.Part file
    );

    @GET("/places/{phoneNumber}")
    Call<List<PlaceSummary>> getAllPlaces(@Path("phoneNumber") String phoneNumber);

    @GET("/place/{phoneNumber}/{id}/")
    Call<PlaceResponse> getPlace(@Path("phoneNumber") String phoneNumber, @Path("id") long id);

    @GET("/place_review/{id}")
    Call<PlaceReviews> getAllPlaceReviews(@Path("id") long id);
}