package ir.fearefull.wheretoapp.model.api.score;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AddScoreResponse implements Serializable {
    @SerializedName("id")
    private long id;

    @SerializedName("user")
    private String user;

    @SerializedName("place")
    private long place;

    @SerializedName("total_score")
    private int total_score;

    @SerializedName("food_score")
    private int food_score;

    @SerializedName("service_score")
    private int service_score;

    @SerializedName("ambiance_score")
    private int ambiance_score;

    public AddScoreResponse(long id, String user, long place, int total_score, int food_score,
                            int service_score, int ambiance_score) {
        this.id = id;
        this.user = user;
        this.place = place;
        this.total_score = total_score;
        this.food_score = food_score;
        this.service_score = service_score;
        this.ambiance_score = ambiance_score;
    }

    public long getId() {
        return id;
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

    public void setId(long id) {
        this.id = id;
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
}
