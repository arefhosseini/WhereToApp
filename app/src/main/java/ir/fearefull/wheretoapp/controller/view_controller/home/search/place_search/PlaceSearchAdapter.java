package ir.fearefull.wheretoapp.controller.view_controller.home.search.place_search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.model.api.Enum.PlaceTypeEnum;
import ir.fearefull.wheretoapp.model.api.search.PlaceSearchResponse;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.view.search.PlaceSearchViewHolder;

public class PlaceSearchAdapter extends RecyclerView.Adapter<PlaceSearchViewHolder> {

    private List<PlaceSearchResponse> placeSearchResponseList;
    private ViewGroup parent;

    PlaceSearchAdapter(List<PlaceSearchResponse> placeSearchResponseList) {
        this.placeSearchResponseList = placeSearchResponseList;
    }

    @NonNull
    @Override
    public PlaceSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_place_search, parent, false);

        this.parent = parent;
        return new PlaceSearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceSearchViewHolder holder, int position) {
        PlaceSearchResponse placeSearchResponse = placeSearchResponseList.get(position);
        holder.getNameTextView().setText(placeSearchResponse.getName());
        holder.getOverallScoreTextView().setText(String.valueOf(placeSearchResponse.getOverallScore()));

        Picasso.get().load(Constants.BASE_URL + placeSearchResponse.getPlaceImage()).into(holder.getImageView());
        StringBuilder placeTypesString = new StringBuilder();
        for (PlaceTypeEnum placeTypeEnum : placeSearchResponse.getPlaceTypes()) {
            if (placeSearchResponse.getPlaceTypes().indexOf(placeTypeEnum) != placeSearchResponse.getPlaceTypes().size() - 1)
                placeTypesString.append(placeTypeEnum.getText()).append("، ");
            else
                placeTypesString.append(placeTypeEnum.getText());
        }
        holder.getPlaceTypesTextView().setText(placeTypesString);
    }

    @Override
    public int getItemCount() {
        return placeSearchResponseList.size();
    }
}
