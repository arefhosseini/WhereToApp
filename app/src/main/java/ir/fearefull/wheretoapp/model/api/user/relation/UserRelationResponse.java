package ir.fearefull.wheretoapp.model.api.user.relation;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserRelationResponse implements Serializable {
    @SerializedName("followers")
    private List<UserRelation> followers;

    @SerializedName("followings")
    private List<UserRelation> followings;

    public UserRelationResponse(List<UserRelation> followers, List<UserRelation> followings) {
        this.followers = followers;
        this.followings = followings;
    }

    public List<UserRelation> getFollowers() {
        return followers;
    }

    public List<UserRelation> getFollowings() {
        return followings;
    }

    public void setFollowings(List<UserRelation> followings) {
        this.followings = followings;
    }

    public void setFollowers(List<UserRelation> followers) {
        this.followers = followers;
    }
}
