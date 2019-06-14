package ir.fearefull.wheretoapp.model.api.place.menu;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PlaceMenuResponse implements Serializable {
    @SerializedName("menus")
    private List<MenuResponse> menuResponses;

    public PlaceMenuResponse(List<MenuResponse> menuResponses) {
        this.menuResponses = menuResponses;
    }

    public List<MenuResponse> getMenuResponses() {
        return menuResponses;
    }

    public void setMenuResponses(List<MenuResponse> menuResponses) {
        this.menuResponses = menuResponses;
    }
}
