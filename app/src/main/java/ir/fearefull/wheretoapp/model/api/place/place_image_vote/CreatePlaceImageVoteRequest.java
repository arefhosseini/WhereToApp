package ir.fearefull.wheretoapp.model.api.place.place_image_vote;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CreatePlaceImageVoteRequest {
    private String user;
    private long place_image;
    private int vote;

    public CreatePlaceImageVoteRequest(String user, long place_image, int vote) {
        this.user = user;
        this.place_image = place_image;
        this.vote = vote;
    }

    public String getUser() {
        return user;
    }

    public long getPlaceImage() {
        return place_image;
    }

    public int getVote() {
        return vote;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPlaceImage(long place_image) {
        this.place_image = place_image;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public RequestBody toRequestBody() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", user);
        jsonObject.put("place_image", place_image);
        jsonObject.put("vote", vote);
        return RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
    }
}
