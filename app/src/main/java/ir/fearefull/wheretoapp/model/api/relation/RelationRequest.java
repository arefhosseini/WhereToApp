package ir.fearefull.wheretoapp.model.api.relation;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RelationRequest {
    private String following;
    private String follower;

    public RelationRequest(String follower, String following) {
        this.follower = follower;
        this.following = following;
    }

    public String getFollowing() {
        return following;
    }

    public String getFollower() {
        return follower;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public void setFollower(String follower) {
        this.follower = follower;
    }

    public RequestBody toRequestBody() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("following", following);
        jsonObject.put("follower", follower);
        return RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
    }
}