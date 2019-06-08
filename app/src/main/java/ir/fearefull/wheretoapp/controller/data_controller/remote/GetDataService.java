package ir.fearefull.wheretoapp.controller.data_controller.remote;

import java.util.List;

import ir.fearefull.wheretoapp.model.api.relation.RelationResponse;
import ir.fearefull.wheretoapp.model.api.score.AddScoreResponse;
import ir.fearefull.wheretoapp.model.api.place.PlaceResponse;
import ir.fearefull.wheretoapp.model.api.review.PlaceReviews;
import ir.fearefull.wheretoapp.model.api.place.PlaceSummary;
import ir.fearefull.wheretoapp.model.api.user.UserResponse;
import ir.fearefull.wheretoapp.model.api.user.verify.VerifyResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface GetDataService {

    @GET("/user/{yourPhoneNumber}/{userPhoneNumber}/")
    Call<UserResponse> getUser(@Path("yourPhoneNumber") String yourPhoneNumber,
                               @Path("userPhoneNumber") String userPhoneNumber);

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

    @Multipart
    @POST("place/upload/")
    Call<ResponseBody> uploadPlaceImage(
            @Part("user") RequestBody user,
            @Part("place") RequestBody place,
            @Part MultipartBody.Part file
    );

    @GET("/places/{phoneNumber}")
    Call<List<PlaceSummary>> getAllPlaces(@Path("phoneNumber") String phoneNumber);

    @GET("/place/{phoneNumber}/{id}/")
    Call<PlaceResponse> getPlace(@Path("phoneNumber") String phoneNumber, @Path("id") long id);

    @GET("/place_review/{id}")
    Call<PlaceReviews> getAllPlaceReviews(@Path("id") long id);

    @POST("/score/")
    Call<AddScoreResponse> addScore(@Body RequestBody requestBody);

    @POST("/review/")
    Call<ResponseBody> addReview(@Body RequestBody requestBody);

    @POST("/favorite_place/")
    Call<ResponseBody> addPlaceToFavorite(@Body RequestBody requestBody);

    @HTTP(method = "DELETE", path = "/favorite_place/", hasBody = true)
    Call<ResponseBody> removePlaceFromFavorite(@Body RequestBody requestBody);

    @POST("/friend/")
    Call<ResponseBody> followUser(@Body RequestBody requestBody);

    @HTTP(method = "DELETE", path = "/friend/", hasBody = true)
    Call<ResponseBody> removeFollowUser(@Body RequestBody requestBody);

    @GET("/friend/{yourPhoneNumber}/{userPhoneNumber}/")
    Call<RelationResponse> getFriends(@Path("yourPhoneNumber") String yourPhoneNumber,
                                      @Path("userPhoneNumber") String userPhoneNumber);
}