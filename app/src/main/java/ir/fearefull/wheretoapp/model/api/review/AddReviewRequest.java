package ir.fearefull.wheretoapp.model.api.review;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AddReviewRequest {
    private String user;
    private long place;
    private String text;

    public AddReviewRequest(String user, long place, String text) {
        this.user = user;
        this.place = place;
        this.text = text;
    }

    public String getUser() {
        return user;
    }

    public long getPlace() {
        return place;
    }

    public String getText() {
        return text;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPlace(long place) {
        this.place = place;
    }

    public void setText(String text) {
        this.text = text;
    }

    public RequestBody toRequestBody() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", user);
        jsonObject.put("place", place);
        jsonObject.put("text", text);
        return RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
    }
}
