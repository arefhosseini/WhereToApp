package ir.fearefull.wheretoapp.model.api.place.menu;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class MenuResponse implements Serializable {
    @SerializedName("name")
    private String name;

    @SerializedName("foods")
    private List<FoodResponse> foodResponses;

    public MenuResponse(String name, List<FoodResponse> foodResponses) {
        this.name = name;
        this.foodResponses = foodResponses;
    }

    public String getName() {
        return name;
    }

    public List<FoodResponse> getFoodResponses() {
        return foodResponses;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFoodResponses(List<FoodResponse> foodResponses) {
        this.foodResponses = foodResponses;
    }
}
