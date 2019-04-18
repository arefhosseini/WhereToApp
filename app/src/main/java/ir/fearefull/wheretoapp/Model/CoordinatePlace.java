package ir.fearefull.wheretoapp.Model;

import com.google.gson.annotations.SerializedName;

public class CoordinatePlace {

    @SerializedName("latitude")
    private float latitude;
    @SerializedName("longitude")
    private float longitude;

    public CoordinatePlace(float latitude, float longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
