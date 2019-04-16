package ir.fearefull.wheretoapp.Network;

import java.util.List;

import ir.fearefull.wheretoapp.Model.PlaceSummary;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/place/")
    Call<List<PlaceSummary>> getAllPlaces();
}