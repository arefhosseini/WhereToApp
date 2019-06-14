package ir.fearefull.wheretoapp.model.api.review;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserReviewsResponse implements Serializable {
    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("reviews")
    private List<UserReviewResponse> user_reviews;

    public UserReviewsResponse(String phone_number, List<UserReviewResponse> user_reviews) {
        this.phone_number = phone_number;
        this.user_reviews = user_reviews;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public List<UserReviewResponse> getUserReviews() {
        return user_reviews;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setUserReviews(List<UserReviewResponse> user_reviews) {
        this.user_reviews = user_reviews;
    }
}
