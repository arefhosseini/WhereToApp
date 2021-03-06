package ir.fearefull.wheretoapp.model.api.search;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import ir.fearefull.wheretoapp.model.api.Enum.PlaceTypeEnum;

public class PlaceSearchResponse implements Serializable {
    @SerializedName("id")
    private long id;

    @SerializedName("name")
    private String name;

    @SerializedName("place_types")
    private List<PlaceTypeEnum> place_types;

    @SerializedName("place_image")
    private String place_image;

    @SerializedName("overall_score")
    private float overall_score;

    @SerializedName("all_scores_count")
    private int all_scores_count;

    public PlaceSearchResponse(long id, String name, List<PlaceTypeEnum> place_types,
                        String place_image, float overall_score, int all_scores_count) {
        this.id = id;
        this.name = name;
        this.place_types = place_types;
        this.place_image = place_image;
        this.overall_score = overall_score;
        this.all_scores_count = all_scores_count;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlaceImage() {
        return place_image;
    }

    public List<PlaceTypeEnum> getPlaceTypes() {
        return place_types;
    }

    public float getOverallScore() {
        return overall_score;
    }

    public int getAllScoresCount() {
        return all_scores_count;
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

    public void setPlaceTypes(List<PlaceTypeEnum> place_types) {
        this.place_types = place_types;
    }

    public void setOverallScore(float overall_score) {
        this.overall_score = overall_score;
    }

    public void setAllScoresCount(int all_scores_count) {
        this.all_scores_count = all_scores_count;
    }
}
