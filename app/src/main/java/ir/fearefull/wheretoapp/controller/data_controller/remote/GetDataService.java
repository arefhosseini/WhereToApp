package ir.fearefull.wheretoapp.controller.data_controller.remote;

import java.util.List;

import ir.fearefull.wheretoapp.model.api.SimpleResponse;
import ir.fearefull.wheretoapp.model.api.place.PlaceResponse;
import ir.fearefull.wheretoapp.model.api.place.PlacesResponse;
import ir.fearefull.wheretoapp.model.api.place.menu.PlaceMenuResponse;
import ir.fearefull.wheretoapp.model.api.review.PlaceReviewsResponse;
import ir.fearefull.wheretoapp.model.api.review.UserReviewsResponse;
import ir.fearefull.wheretoapp.model.api.search.PlaceSearchResponse;
import ir.fearefull.wheretoapp.model.api.search.UserSearchResponse;
import ir.fearefull.wheretoapp.model.api.user.UserResponse;
import ir.fearefull.wheretoapp.model.api.user.control.UserControlResponse;
import ir.fearefull.wheretoapp.model.api.user.control.VerifyUserResponse;
import ir.fearefull.wheretoapp.model.api.user.favorite_place.UserFavoritePlacesResponse;
import ir.fearefull.wheretoapp.model.api.user.relation.UserRelationResponse;
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

    @POST("/user/")
    Call<UserControlResponse> createUser(@Body RequestBody requestBody);

    @PUT("/user/")
    Call<UserControlResponse> editUser(@Body RequestBody requestBody);

    @HTTP(method = "DELETE", path = "/user/", hasBody = true)
    Call<ResponseBody> removeUser(@Body RequestBody requestBody);

    @POST("/user/verify/")
    Call<VerifyUserResponse> verifyUser(@Body RequestBody requestBody);

    @Multipart
    @POST("/user/profile_image/")
    Call<SimpleResponse> uploadUserProfileImage(
            @Part("phone_number") RequestBody phoneNumber,
            @Part MultipartBody.Part file
    );

    @POST("/user/relation/")
    Call<SimpleResponse> createUserRelation(@Body RequestBody requestBody);

    @HTTP(method = "DELETE", path = "/user/relation/", hasBody = true)
    Call<ResponseBody> removeUserRelation(@Body RequestBody requestBody);

    @POST("/user/favorite_place/")
    Call<SimpleResponse> createUserFavoritePlace(@Body RequestBody requestBody);

    @HTTP(method = "DELETE", path = "/user/favorite_place/", hasBody = true)
    Call<ResponseBody> removeUserFavoritePlace(@Body RequestBody requestBody);

    @POST("/user/favorite_place_type/")
    Call<SimpleResponse> createUserFavoritePlaceType(@Body RequestBody requestBody);

    @HTTP(method = "DELETE", path = "/user/favorite_place_type/", hasBody = true)
    Call<ResponseBody> removeUserFavoritePlaceType(@Body RequestBody requestBody);

    @GET("/user/{yourPhoneNumber}/{userPhoneNumber}/")
    Call<UserResponse> getUser(@Path("yourPhoneNumber") String yourPhoneNumber,
                               @Path("userPhoneNumber") String userPhoneNumber);

    @GET("/user/{yourPhoneNumber}/{userPhoneNumber}/review/")
    Call<UserReviewsResponse> getUserReviews(@Path("yourPhoneNumber") String yourPhoneNumber,
                                             @Path("userPhoneNumber") String userPhoneNumber);

    @GET("/user/{yourPhoneNumber}/{userPhoneNumber}/place_score/")
    Call<UserResponse> getUserPlaceScores(@Path("yourPhoneNumber") String yourPhoneNumber,
                                      @Path("userPhoneNumber") String userPhoneNumber);

    @GET("/user/{yourPhoneNumber}/{userPhoneNumber}/relation/")
    Call<UserRelationResponse> getUserRelations(@Path("yourPhoneNumber") String yourPhoneNumber,
                                                @Path("userPhoneNumber") String userPhoneNumber);

    @GET("/user/{yourPhoneNumber}/{userPhoneNumber}/image/")
    Call<UserRelationResponse> getUserPlaceImages(@Path("yourPhoneNumber") String yourPhoneNumber,
                                                  @Path("userPhoneNumber") String userPhoneNumber);

    @GET("/user/{yourPhoneNumber}/{userPhoneNumber}/favorite_place/")
    Call<UserFavoritePlacesResponse> getUserFavoritePlaces(@Path("yourPhoneNumber") String yourPhoneNumber,
                                                           @Path("userPhoneNumber") String userPhoneNumber);

    @Multipart
    @POST("/place/image/")
    Call<SimpleResponse> uploadPlaceImage(
            @Part("user") RequestBody user,
            @Part("place") RequestBody place,
            @Part MultipartBody.Part file
    );

    @POST("/place/review/")
    Call<SimpleResponse> createReview(@Body RequestBody requestBody);

    @POST("/place/score/")
    Call<SimpleResponse> createScore(@Body RequestBody requestBody);

    @PUT("/place/score/")
    Call<SimpleResponse> editScore(@Body RequestBody requestBody);

    @POST("/place/image/vote/")
    Call<SimpleResponse> createPlaceImageVote(@Body RequestBody requestBody);

    @HTTP(method = "DELETE", path = "/place/image/vote/", hasBody = true)
    Call<ResponseBody> removePlaceImageVote(@Body RequestBody requestBody);

    @POST("/place/review/vote/")
    Call<SimpleResponse> createReviewVote(@Body RequestBody requestBody);

    @HTTP(method = "DELETE", path = "/place/review/vote/", hasBody = true)
    Call<ResponseBody> removeReviewVote(@Body RequestBody requestBody);

    @GET("/place/{phoneNumber}")
    Call<PlacesResponse> getPlaces(@Path("phoneNumber") String phoneNumber);

    @GET("/place/{phoneNumber}/{id}/")
    Call<PlaceResponse> getPlace(@Path("phoneNumber") String phoneNumber, @Path("id") long id);

    @GET("/place/{phoneNumber}/{id}/review/")
    Call<PlaceReviewsResponse> getPlaceReviews(@Path("phoneNumber") String phoneNumber, @Path("id") long id);

    @GET("/place/{phoneNumber}/{id}/menu/")
    Call<PlaceMenuResponse> getPlaceMenus(@Path("phoneNumber") String phoneNumber, @Path("id") long id);

    @GET("/search/user/{text}/")
    Call<List<UserSearchResponse>> getUsersSearch(@Path("text") String text);

    @GET("/search/place/{text}/")
    Call<List<PlaceSearchResponse>> getPlacesSearch(@Path("text") String text);
}