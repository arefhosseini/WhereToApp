package ir.fearefull.wheretoapp.model.api.review;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PlaceReview implements Serializable {
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
    private String created_date;

    @SerializedName("up_vote")
    private int up_vote;

    @SerializedName("down_vote")
    private int down_vote;

    public PlaceReview(long id, String phone_number, String first_name, String last_name, String profile_image,
                       String text, String created_date, int up_vote, int down_vote) {
        this.id = id;
        this.phone_number = phone_number;
        this.first_name = first_name;
        this.last_name = last_name;
        this.profile_image = profile_image;
        this.text = text;
        this.created_date = created_date;
        this.up_vote = up_vote;
        this.down_vote = down_vote;
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

    public String getCreatedDate() {
        return created_date;
    }

    public int getUpVote() {
        return up_vote;
    }

    public int getDownVote() {
        return down_vote;
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

    public void setCreatedDate(String created_date) {
        this.created_date = created_date;
    }

    public void setUpVote(int up_vote) {
        this.up_vote = up_vote;
    }

    public void setDownVote(int down_vote) {
        this.down_vote = down_vote;
    }
}
