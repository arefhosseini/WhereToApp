package ir.fearefull.wheretoapp.model.api.review;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserReviewResponse implements Serializable {
    @SerializedName("id")
    private long id;

    @SerializedName("place_id")
    private long place_id;

    @SerializedName("place_name")
    private String place_name;

    @SerializedName("place_image")
    private String place_image;

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

    public UserReviewResponse(long id, long place_id, String place_name, String place_image,
                              String text, long created_date, int place_score,
                              int up_votes, int down_votes, int your_vote) {
        this.id = id;
        this.place_id = place_id;
        this.place_name = place_name;
        this.place_image = place_image;
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

    public long getPlaceId() {
        return place_id;
    }

    public String getPlaceName() {
        return place_name;
    }

    public String getPlaceImage() {
        return place_image;
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

    public void setPlaceId(long place_id) {
        this.place_id = place_id;
    }

    public void setPlaceName(String place_name) {
        this.place_name = place_name;
    }

    public void setPlaceImage(String place_image) {
        this.place_image = place_image;
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
