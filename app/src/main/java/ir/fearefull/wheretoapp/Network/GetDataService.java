package ir.fearefull.wheretoapp.Network;

import java.util.List;

import ir.fearefull.wheretoapp.Model.Place;
import ir.fearefull.wheretoapp.Model.PlaceReviews;
import ir.fearefull.wheretoapp.Model.PlaceSummary;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GetDataService {

    @GET("/place/")
    Call<List<PlaceSummary>> getAllPlaces();

    @GET("/place/{id}/")
    Call<Place> getPlace(@Path("id") long id);

    @GET("/place_review/{id}")
    Call<PlaceReviews> getAllPlaceReviews(@Path("id") long id);
}