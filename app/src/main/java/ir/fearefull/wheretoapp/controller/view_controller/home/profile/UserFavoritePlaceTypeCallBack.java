package ir.fearefull.wheretoapp.controller.view_controller.home.profile;

import java.util.List;

import ir.fearefull.wheretoapp.model.api.Enum.PlaceTypeEnum;

public interface UserFavoritePlaceTypeCallBack {
    void changeUserFavoritePlace(List<PlaceTypeEnum> placeTypeEnumList);
}
