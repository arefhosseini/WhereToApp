package ir.fearefull.wheretoapp.model.api.place.menu;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FoodResponse implements Serializable{
    @SerializedName("name")
    private String name;

    @SerializedName("detail")
    private String detail;

    @SerializedName("price")
    private int price;

    public FoodResponse(String name, String detail, int price) {
        this.name = name;
        this.detail = detail;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public String getDetail() {
        return detail;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
