package ir.fearefull.wheretoapp.model.api.place;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PlaceImage implements Serializable {

    @SerializedName("id")
    private long id;

    @SerializedName("user")
    private long user;

    @SerializedName("place")
    private long place;

    @SerializedName("up_votes")
    private int up_votes;

    @SerializedName("down_votes")
    private int down_votes;

    @SerializedName("your_vote")
    private int your_vote;

    @SerializedName("image")
    private String image;

    public PlaceImage(long id, long user, long place, int up_votes,
                      int down_votes, int your_vote, String image) {
        this.id = id;
        this.user = user;
        this.place = place;
        this.up_votes = up_votes;
        this.down_votes = down_votes;
        this.your_vote = your_vote;
        this.image = image;
    }

    public long getId() {
        return id;
    }

    public long getUser() {
        return user;
    }

    public long getPlace() {
        return place;
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

    public String getImage() {
        return image;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public void setPlace(long place) {
        this.place = place;
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

    public void setImage(String image) {
        this.image = image;
    }
}
