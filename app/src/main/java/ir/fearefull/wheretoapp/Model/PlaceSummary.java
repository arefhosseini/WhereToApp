package ir.fearefull.wheretoapp.Model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class PlaceSummary {

    @SerializedName("id")
    private long id;
    @SerializedName("name")
    private String name;
    @SerializedName("place_types")
    private List<TypePlaceEnum> place_types = new ArrayList<>();
    @SerializedName("place_image")
    private String place_image;
    @SerializedName("overall_score")
    private float overall_score;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlaceImage() {
        return place_image;
    }

    public List<TypePlaceEnum> getPlaceTypes() {
        return place_types;
    }

    public float getOverallScore() {
        return overall_score;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaceImage(String place_image) {
        this.place_image = place_image;
    }

    public void setPlaceTypes(List<TypePlaceEnum> place_types) {
        this.place_types = place_types;
    }

    public void setOverallScore(float overall_score) {
        this.overall_score = overall_score;
    }
}
