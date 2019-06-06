package ir.fearefull.wheretoapp.data.model.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class PlaceReviews implements Serializable {
    @SerializedName("id")
    private long id;

    @SerializedName("reviews")
    private List<PlaceReview> place_reviews;

    public PlaceReviews(long id, List<PlaceReview> place_reviews) {
        this.id = id;
        this.place_reviews = place_reviews;
    }

    public long getId() {
        return id;
    }

    public List<PlaceReview> getPlaceReviews() {
        return place_reviews;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPlaceReviews(List<PlaceReview> place_reviews) {
        this.place_reviews = place_reviews;
    }
}
