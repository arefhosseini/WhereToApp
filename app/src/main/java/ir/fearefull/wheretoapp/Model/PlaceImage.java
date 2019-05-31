package ir.fearefull.wheretoapp.Model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("serial")
public class PlaceImage implements Serializable {

    @SerializedName("user")
    private long user;
    @SerializedName("place")
    private long place;
    @SerializedName("up_vote")
    private int up_vote;
    @SerializedName("down_vote")
    private int down_vote;
    @SerializedName("image")
    private String image;

    public PlaceImage(long user, long place, int up_vote, int down_vote, String image) {
        this.user = user;
        this.place = place;
        this.up_vote = up_vote;
        this.down_vote = down_vote;
        this.image = image;
    }

    public long getUser() {
        return user;
    }

    public long getPlace() {
        return place;
    }

    public int getUpVote() {
        return up_vote;
    }

    public int getDownVote() {
        return down_vote;
    }

    public String getImage() {
        return image;
    }

    public void setUser(long user) {
        this.user = user;
    }

    public void setPlace(long place) {
        this.place = place;
    }

    public void setUpVote(int up_vote) {
        this.up_vote = up_vote;
    }

    public void setDownVote(int down_vote) {
        this.down_vote = down_vote;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
