package ir.fearefull.wheretoapp.model.api.user.favorite_place;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import ir.fearefull.wheretoapp.model.api.Enum.PlaceTypeEnum;

public class UserFavoritePlaceResponse implements Serializable {
    @SerializedName("id")
    private Long id;

    @SerializedName("name")
    private String name;

    @SerializedName("place_image")
    private String place_image;

    @SerializedName("overall_score")
    private float overall_score;

    @SerializedName("place_types")
    private List<PlaceTypeEnum> place_types;

    @SerializedName("is_your_favorite")
    private int is_your_favorite;

    public UserFavoritePlaceResponse(long id, String name, String place_image, float overall_score,
                                     List<PlaceTypeEnum> place_types, int is_your_favorite) {
        this.id = id;
        this.name = name;
        this.place_image = place_image;
        this.overall_score = overall_score;
        this.place_types = place_types;
        this.is_your_favorite = is_your_favorite;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPlaceImage() {
        return place_image;
    }

    public float getOverallScore() {
        return overall_score;
    }

    public List<PlaceTypeEnum> getPlaceTypes() {
        return place_types;
    }

    public int getIsYourFavorite() {
        return is_your_favorite;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPlaceImage(String place_image) {
        this.place_image = place_image;
    }

    public void setOverallScore(float overall_score) {
        this.overall_score = overall_score;
    }

    public void setPlaceTypes(List<PlaceTypeEnum> place_types) {
        this.place_types = place_types;
    }

    public void setIsYourFavorite(int is_your_favorite) {
        this.is_your_favorite = is_your_favorite;
    }
}
