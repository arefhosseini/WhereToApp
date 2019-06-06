package ir.fearefull.wheretoapp.data.model.api;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class UserResponse implements Serializable {
    @SerializedName("id")
    private Long id;

    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("profile_image")
    private String profile_image;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("user_score")
    private int user_score;

    @SerializedName("followers_count")
    private int followers_count;

    @SerializedName("followings_count")
    private int followings_count;

    @SerializedName("reviews_count")
    private int reviews_count;

    @SerializedName("place_scores_count")
    private int place_scores_count;

    @SerializedName("uploaded_images_count")
    private int uploaded_images_count;

    @SerializedName("favorite_places_count")
    private int favorite_places_count;

    public UserResponse(Long id, String phone_number, String profile_image, String first_name,
                        String last_name, int user_score, int followers_count, int followings_count,
                        int reviews_count, int place_scores_count, int uploaded_images_count,
                        int favorite_places_count) {
        this.id = id;
        this.phone_number = phone_number;
        this.profile_image = profile_image;
        this.first_name = first_name;
        this.last_name = last_name;
        this.user_score = user_score;
        this.followers_count = followers_count;
        this.followings_count = followings_count;
        this.reviews_count = reviews_count;
        this.place_scores_count = place_scores_count;
        this.uploaded_images_count = uploaded_images_count;
        this.favorite_places_count = favorite_places_count;
    }

    public Long getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public String getProfileImage() {
        return profile_image;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public int getUserScore() {
        return user_score;
    }

    public int getFollowersCount() {
        return followers_count;
    }

    public int getFollowingsCount() {
        return followings_count;
    }

    public int getReviewsCount() {
        return reviews_count;
    }

    public int getPlaceScoresCount() {
        return place_scores_count;
    }

    public int getUploadedImagesCount() {
        return uploaded_images_count;
    }

    public int getFavoritePlacesCount() {
        return favorite_places_count;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setProfileImage(String profile_image) {
        this.profile_image = profile_image;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public void setUserScore(int user_score) {
        this.user_score = user_score;
    }

    public void setFollowersCount(int followers_count) {
        this.followers_count = followers_count;
    }

    public void setFollowingsCount(int followings_count) {
        this.followings_count = followings_count;
    }

    public void setReviewsCount(int reviews_count) {
        this.reviews_count = reviews_count;
    }

    public void setPlaceScoresCount(int place_scores_count) {
        this.place_scores_count = place_scores_count;
    }

    public void setUploadedImagesCount(int uploaded_images_count) {
        this.uploaded_images_count = uploaded_images_count;
    }

    public void setFavoritePlacesCount(int favorite_places_count) {
        this.favorite_places_count = favorite_places_count;
    }
}
