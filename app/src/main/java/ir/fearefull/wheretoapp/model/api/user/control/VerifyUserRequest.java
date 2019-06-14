package ir.fearefull.wheretoapp.model.api.user.control;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class VerifyUserRequest {
    private String phone_number;

    public VerifyUserRequest(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public RequestBody toRequestBody() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone_number", phone_number);
        return RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
    }
}
