package ir.fearefull.wheretoapp.model.api.score;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class CreatePlaceScoreRequest {
    private String user;
    private long place;
    private int total_score;
    private int food_score;
    private int service_score;
    private int ambiance_score;

    public CreatePlaceScoreRequest(String user, long place, int total_score, int food_score,
                                   int service_score, int ambiance_score) {
        this.user = user;
        this.place = place;
        this.total_score = total_score;
        this.food_score = food_score;
        this.service_score = service_score;
        this.ambiance_score = ambiance_score;
    }

    public String getUser() {
        return user;
    }

    public long getPlace() {
        return place;
    }

    public int getTotalScore() {
        return total_score;
    }

    public int getFoodScore() {
        return food_score;
    }

    public int getServiceScore() {
        return service_score;
    }

    public int getAmbianceScore() {
        return ambiance_score;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPlace(long place) {
        this.place = place;
    }

    public void setTotalScore(int total_score) {
        this.total_score = total_score;
    }

    public void setFoodScore(int food_score) {
        this.food_score = food_score;
    }

    public void setServiceScore(int service_score) {
        this.service_score = service_score;
    }

    public void setAmbianceScore(int ambiance_score) {
        this.ambiance_score = ambiance_score;
    }

    public RequestBody toRequestBody() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", user);
        jsonObject.put("place", place);
        jsonObject.put("total_score", total_score);
        jsonObject.put("food_score", food_score);
        jsonObject.put("service_score", service_score);
        jsonObject.put("ambiance_score", ambiance_score);
        return RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
    }
}
