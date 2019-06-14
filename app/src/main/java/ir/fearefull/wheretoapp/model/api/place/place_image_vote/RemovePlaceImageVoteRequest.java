package ir.fearefull.wheretoapp.model.api.place.place_image_vote;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RemovePlaceImageVoteRequest {
    private String user;
    private long place_image;

    public RemovePlaceImageVoteRequest(String user, long place_image) {
        this.user = user;
        this.place_image = place_image;
    }

    public String getUser() {
        return user;
    }

    public long getPlaceImage() {
        return place_image;
    }


    public void setUser(String user) {
        this.user = user;
    }

    public void setPlaceImage(long place_image) {
        this.place_image = place_image;
    }

    public RequestBody toRequestBody() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", user);
        jsonObject.put("place_image", place_image);
        return RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
    }
}
