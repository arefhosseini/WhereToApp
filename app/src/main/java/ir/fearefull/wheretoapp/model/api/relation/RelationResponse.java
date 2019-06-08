package ir.fearefull.wheretoapp.model.api.relation;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RelationResponse implements Serializable {
    @SerializedName("followers")
    private List<Relation> followers;

    @SerializedName("followings")
    private List<Relation> followings;

    public RelationResponse(List<Relation> followers, List<Relation> followings) {
        this.followers = followers;
        this.followings = followings;
    }

    public List<Relation> getFollowers() {
        return followers;
    }

    public List<Relation> getFollowings() {
        return followings;
    }

    public void setFollowings(List<Relation> followings) {
        this.followings = followings;
    }

    public void setFollowers(List<Relation> followers) {
        this.followers = followers;
    }
}
