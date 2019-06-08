package ir.fearefull.wheretoapp.model.api.place;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class PlaceFavoriteRequest {
    private String user;
    private long place;

    public PlaceFavoriteRequest(String user, long place) {
        this.user = user;
        this.place = place;
    }

    public String getUser() {
        return user;
    }

    public long getPlace() {
        return place;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPlace(long place) {
        this.place = place;
    }

    public RequestBody toRequestBody() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", user);
        jsonObject.put("place", place);
        return RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
    }
}