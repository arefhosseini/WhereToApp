package ir.fearefull.wheretoapp.data.model.api;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class VerifyRequest {
    private String phone_number;

    public VerifyRequest(String phone_number) {
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
