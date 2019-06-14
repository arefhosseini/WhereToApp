package ir.fearefull.wheretoapp.model.api.place.review_vote;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class RemoveReviewVoteRequest {
    private String user;
    private long review;

    public RemoveReviewVoteRequest(String user, long review) {
        this.user = user;
        this.review = review;
    }

    public String getUser() {
        return user;
    }

    public long getReview() {
        return review;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setReview(long review) {
        this.review = review;
    }

    public RequestBody toRequestBody() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", user);
        jsonObject.put("review", review);
        return RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
    }
}
