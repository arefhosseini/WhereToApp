package ir.fearefull.wheretoapp.model.api.search;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserSearchResponse implements Serializable {

    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("profile_image")
    private String profile_image;

    @SerializedName("first_name")
    private String first_name;

    @SerializedName("last_name")
    private String last_name;

    public UserSearchResponse(String phone_number, String profile_image, String first_name,
                               String last_name) {
        this.phone_number = phone_number;
        this.profile_image = profile_image;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public String getProfileImage() {
        return profile_image;
    }

    public String getFirstName() {
        return first_name;
    }

    public String getLastName() {
        return last_name;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setProfileImage(String profile_image) {
        this.profile_image = profile_image;
    }

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }
}
