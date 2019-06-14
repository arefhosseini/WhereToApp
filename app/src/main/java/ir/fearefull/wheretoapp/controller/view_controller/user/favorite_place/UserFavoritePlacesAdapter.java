package ir.fearefull.wheretoapp.controller.view_controller.user.favorite_place;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.model.api.Enum.PlaceTypeEnum;
import ir.fearefull.wheretoapp.model.api.user.favorite_place.UserFavoritePlaceResponse;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.view.user.UserFavoritePlaceViewHolder;

public class UserFavoritePlacesAdapter extends RecyclerView.Adapter<UserFavoritePlaceViewHolder>{
    private List<UserFavoritePlaceResponse> userFavoritePlaceResponseList;
    private ViewGroup parent;

    public UserFavoritePlacesAdapter(List<UserFavoritePlaceResponse> userFavoritePlaceResponseList) {
        this.userFavoritePlaceResponseList = userFavoritePlaceResponseList;
    }

    @NonNull
    @Override
    public UserFavoritePlaceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_user_favorite_place, parent, false);

        this.parent = parent;
        return new UserFavoritePlaceViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserFavoritePlaceViewHolder holder, int position) {
        UserFavoritePlaceResponse userFavoritePlaceResponse = userFavoritePlaceResponseList.get(position);
        holder.getNameTextView().setText(userFavoritePlaceResponse.getName());
        holder.getOverallScoreTextView().setText(String.valueOf(userFavoritePlaceResponse.getOverallScore()));

        Picasso.get().load(Constants.BASE_URL + userFavoritePlaceResponse.getPlaceImage()).into(holder.getImageView());
        StringBuilder placeTypesString = new StringBuilder();
        for (PlaceTypeEnum placeTypeEnum : userFavoritePlaceResponse.getPlaceTypes()) {
            if (userFavoritePlaceResponse.getPlaceTypes().indexOf(placeTypeEnum) != userFavoritePlaceResponse.getPlaceTypes().size() - 1)
                placeTypesString.append(placeTypeEnum.getText()).append("، ");
            else
                placeTypesString.append(placeTypeEnum.getText());
        }
        holder.getPlaceTypesTextView().setText(placeTypesString);
    }

    @Override
    public int getItemCount() {
        return userFavoritePlaceResponseList.size();
    }
}
