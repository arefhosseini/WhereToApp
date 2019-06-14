package ir.fearefull.wheretoapp.model.api.place.review_vote;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CreateReviewVoteRequest {
    private String user;
    private long review;
    private int vote;

    public CreateReviewVoteRequest(String user, long review, int vote) {
        this.user = user;
        this.review = review;
        this.vote = vote;
    }

    public String getUser() {
        return user;
    }

    public long getReview() {
        return review;
    }

    public int getVote() {
        return vote;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setReview(long review) {
        this.review = review;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public RequestBody toRequestBody() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", user);
        jsonObject.put("review", review);
        jsonObject.put("vote", vote);
        return RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
    }
}
