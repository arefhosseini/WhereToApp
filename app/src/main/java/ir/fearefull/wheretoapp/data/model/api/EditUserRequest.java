package ir.fearefull.wheretoapp.data.model.api;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class EditUserRequest{
    private String phone_number;
    private String first_name;
    private String last_name;

    public EditUserRequest(String phone_number, String first_name, String last_name) {
        this.phone_number = phone_number;
        this.first_name = first_name;
        this.last_name = last_name;
    }

    public String getPhoneNumber() {
        return phone_number;
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

    public void setFirstName(String first_name) {
        this.first_name = first_name;
    }

    public void setLastName(String last_name) {
        this.last_name = last_name;
    }

    public RequestBody toRequestBody() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("phone_number", phone_number);
        jsonObject.put("first_name", first_name);
        jsonObject.put("last_name", last_name);
        return RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
    }
}
