package ir.fearefull.wheretoapp.model.api.place;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import ir.fearefull.wheretoapp.model.api.score.PlaceScore;

public class PlaceResponse implements Serializable {
    @SerializedName("place")
    private Place place;

    @SerializedName("place_score")
    private PlaceScore place_score;

    @SerializedName("is_favorite")
    private int is_favorite;

    public PlaceResponse(Place place, PlaceScore place_score, int is_favorite) {
        this.place = place;
        this.place_score = place_score;
        this.is_favorite = is_favorite;
    }

    public Place getPlace() {
        return place;
    }

    public PlaceScore getPlaceScore() {
        return place_score;
    }

    public int getIsFavorite() {
        return is_favorite;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public void setPlaceScore(PlaceScore place_score) {
        this.place_score = place_score;
    }

    public void setIsFavorite(int is_favorite) {
        this.is_favorite = is_favorite;
    }
}
