package ir.fearefull.wheretoapp.data.model.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FriendResponse implements Serializable {
    @SerializedName("followers")
    private List<Friend> followers;

    @SerializedName("followings")
    private List<Friend> followings;

    public FriendResponse(List<Friend> followers, List<Friend> followings) {
        this.followers = followers;
        this.followings = followings;
    }

    public List<Friend> getFollowers() {
        return followers;
    }

    public List<Friend> getFollowings() {
        return followings;
    }

    public void setFollowings(List<Friend> followings) {
        this.followings = followings;
    }

    public void setFollowers(List<Friend> followers) {
        this.followers = followers;
    }
}
