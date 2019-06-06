package ir.fearefull.wheretoapp.data.model.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlaceResponse implements Serializable {
    @SerializedName("place")
    private Place place;

    @SerializedName("score")
    private PlaceScore place_score;

    public PlaceResponse(Place place, PlaceScore place_score) {
        this.place = place;
        this.place_score = place_score;
    }

    public Place getPlace() {
        return place;
    }

    public PlaceScore getPlaceScore() {
        return place_score;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setPlaceScore(PlaceScore place_score) {
        this.place_score = place_score;
    }
}
