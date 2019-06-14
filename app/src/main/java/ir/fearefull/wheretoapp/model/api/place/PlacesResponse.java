package ir.fearefull.wheretoapp.model.api.place;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PlacesResponse implements Serializable {
    @SerializedName("total_places")
    private List<PlaceSummary> total_places;

    @SerializedName("suggested_places")
    private List<PlaceSummary> suggested_places;

    public PlacesResponse(List<PlaceSummary> total_places, List<PlaceSummary> suggested_places) {
        this.total_places = total_places;
        this.suggested_places = suggested_places;
    }

    public List<PlaceSummary> getTotalPlaces() {
        return total_places;
    }

    public List<PlaceSummary> getSuggestedPlaces() {
        return suggested_places;
    }

    public void setTotalPlaces(List<PlaceSummary> total_places) {
        this.total_places = total_places;
    }

    public void setSuggestedPlaces(List<PlaceSummary> suggested_places) {
        this.suggested_places = suggested_places;
    }
}
