package ir.fearefull.wheretoapp.model.api.review;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlaceReviewResponse implements Serializable {
    @SerializedName("id")
    private long id;

    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    @SerializedName("profile_image")
    private String profile_image;

    @SerializedName("text")
    private String text;

    @SerializedName("created_date")
    private long created_date;

    @SerializedName("place_score")
    private int place_score;

    @SerializedName("up_votes")
    private int up_votes;

    @SerializedName("down_votes")
    private int down_votes;

    @SerializedName("your_vote")
    private int your_vote;

    public PlaceReviewResponse(long id, String phone_number, String first_name, String last_name,
                               String profile_image, String text, long created_date, int place_score,
                               int up_votes, int down_votes, int your_vote) {
        this.id = id;
        this.phone_number = phone_number;
        this.first_name = first_name;
        this.last_name = last_name;
        this.profile_image = profile_image;
        this.text = text;
        this.created_date = created_date;
        this.place_score = place_score;
        this.up_votes = up_votes;
        this.down_votes = down_votes;
        this.your_vote = your_vote;
    }

    public long getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public String getProfileImage() {
        return profile_image;
    }

    public String getText() {
        return text;
    }

    public long getCreatedDate() {
        return created_date;
    }

    public int getPlaceScore() {
        return place_score;
    }

    public int getUpVotes() {
        return up_votes;
    }

    public int getDownVotes() {
        return down_votes;
    }

    public int getYourVote() {
        return your_vote;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public void setProfileImage(String profile_image) {
        this.profile_image = profile_image;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setCreatedDate(long created_date) {
        this.created_date = created_date;
    }

    public void setPlaceScore(int place_score) {
        this.place_score = place_score;
    }

    public void setUpVotes(int up_votes) {
        this.up_votes = up_votes;
    }

    public void setDownVotes(int down_votes) {
        this.down_votes = down_votes;
    }

    public void setYourVote(int your_vote) {
        this.your_vote = your_vote;
    }
}
