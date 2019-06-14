package ir.fearefull.wheretoapp.model.api.user.favorite_place;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class UserFavoritePlacesResponse implements Serializable {
    @SerializedName("id")
    private Long id;

    @SerializedName("favorite_places")
    private List<UserFavoritePlaceResponse> favorite_places;

    public UserFavoritePlacesResponse(long id, List<UserFavoritePlaceResponse> favorite_places) {
        this.id = id;
        this.favorite_places = favorite_places;
    }

    public Long getId() {
        return id;
    }

    public List<UserFavoritePlaceResponse> getFavoritePlaces() {
        return favorite_places;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setFavoritePlaces(List<UserFavoritePlaceResponse> favorite_places) {
        this.favorite_places = favorite_places;
    }
}
