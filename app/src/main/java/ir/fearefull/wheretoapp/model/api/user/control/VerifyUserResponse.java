package ir.fearefull.wheretoapp.model.api.user.control;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VerifyUserResponse implements Serializable {
    @SerializedName("phone_number")
    private String phone_number;

    @SerializedName("verify_code")
    private String verify_code;

    public VerifyUserResponse(String phone_number, String verify_code) {
        this.phone_number = phone_number;
        this.verify_code = verify_code;
    }

    public String getPhoneNumber() {
        return phone_number;
    }

    public String getVerifyCode() {
        return verify_code;
    }

    public void setPhoneNumber(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setVerifyCode(String verify_code) {
        this.verify_code = verify_code;
    }
}
