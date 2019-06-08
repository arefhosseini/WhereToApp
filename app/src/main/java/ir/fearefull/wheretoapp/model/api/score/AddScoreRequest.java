package ir.fearefull.wheretoapp.model.api.score;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AddScoreRequest {
    private String user;
    private long place;
    private int total_score;
    private int food_score;
    private int service_score;
    private int ambiance_score;

    public AddScoreRequest(String user, long place, int total_score, int food_score,
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

    public int getTotal_score() {
        return total_score;
    }

    public int getFood_score() {
        return food_score;
    }

    public int getService_score() {
        return service_score;
    }

    public int getAmbiance_score() {
        return ambiance_score;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPlace(long place) {
        this.place = place;
    }

    public void setTotal_score(int total_score) {
        this.total_score = total_score;
    }

    public void setFood_score(int food_score) {
        this.food_score = food_score;
    }

    public void setService_score(int service_score) {
        this.service_score = service_score;
    }

    public void setAmbiance_score(int ambiance_score) {
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
