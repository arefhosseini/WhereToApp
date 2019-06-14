package ir.fearefull.wheretoapp.model.api.review;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class PlaceReviewsResponse implements Serializable {
    @SerializedName("id")
    private long id;

    @SerializedName("reviews")
    private List<PlaceReviewResponse> place_reviews;

    public PlaceReviewsResponse(long id, List<PlaceReviewResponse> place_reviews) {
        this.id = id;
        this.place_reviews = place_reviews;
    }

    public long getId() {
        return id;
    }

    public List<PlaceReviewResponse> getPlaceReviews() {
        return place_reviews;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPlaceReviews(List<PlaceReviewResponse> place_reviews) {
        this.place_reviews = place_reviews;
    }
}
