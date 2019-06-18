package ir.fearefull.wheretoapp.model.api.user.favorite_place_type;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ir.fearefull.wheretoapp.model.api.Enum.PlaceTypeEnum;
import okhttp3.MediaType;
import okhttp3.RequestBody;

public class UserFavoritePlaceTypeRequest {
    private String phone_number;
    private ArrayList<String> add_types;
    private ArrayList<String> delete_types;

    public UserFavoritePlaceTypeRequest(String phone_number) {
        this.phone_number = phone_number;
        add_types = new ArrayList<>();
        delete_types = new ArrayList<>();
    }

    public void addToAddTypes(PlaceTypeEnum placeTypeEnum) {
        add_types.add(placeTypeEnumToString(placeTypeEnum));
    }

    public void removeFromAddTypes(PlaceTypeEnum placeTypeEnum) {
        add_types.remove(placeTypeEnumToString(placeTypeEnum));
    }

    public void addToDeleteTypes(PlaceTypeEnum placeTypeEnum) {
        delete_types.add(placeTypeEnumToString(placeTypeEnum));
    }

    public void removeFromDeleteTypes(PlaceTypeEnum placeTypeEnum) {
        delete_types.remove(placeTypeEnumToString(placeTypeEnum));
    }

    private String placeTypeEnumToString(PlaceTypeEnum placeTypeEnum) {
        if (placeTypeEnum == PlaceTypeEnum.Italian)
            return "Italian";
        else if (placeTypeEnum == PlaceTypeEnum.FastFood)
            return "FastFood";
        else if (placeTypeEnum == PlaceTypeEnum.Irani)
            return "Irani";
        else
            return "Cafe";
    }

    public RequestBody toRequestBody() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("user", phone_number);
        jsonObject.put("add_types", new JSONArray(add_types));
        jsonObject.put("delete_types", new JSONArray(delete_types));
        return RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
    }
}
