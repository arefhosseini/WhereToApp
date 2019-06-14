package ir.fearefull.wheretoapp.controller.view_controller.home.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.model.api.Enum.PlaceTypeEnum;
import ir.fearefull.wheretoapp.model.api.place.PlaceSummary;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.view.home.PlaceSummaryViewHolder;

public class PlacesSummaryAdapter extends RecyclerView.Adapter<PlaceSummaryViewHolder> {

    private List<PlaceSummary> placeSummaryList;
    private ViewGroup parent;

    public PlacesSummaryAdapter(List<PlaceSummary> placeSummaryList) {
        this.placeSummaryList = placeSummaryList;
    }

    @NonNull
    @Override
    public PlaceSummaryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_place_summary, parent, false);

        this.parent = parent;
        return new PlaceSummaryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceSummaryViewHolder holder, int position) {
        PlaceSummary placeSummary = placeSummaryList.get(position);
        holder.getNameTextView().setText(placeSummary.getName());
        holder.getOverallScoreTextView().setText(String.valueOf(placeSummary.getOverallScore()));

        Picasso.get().load(Constants.BASE_URL + placeSummary.getPlaceImage()).into(holder.getImageView());
        StringBuilder placeTypesString = new StringBuilder();
        for (PlaceTypeEnum placeTypeEnum : placeSummary.getPlaceTypes()) {
            if (placeSummary.getPlaceTypes().indexOf(placeTypeEnum) != placeSummary.getPlaceTypes().size() - 1)
                placeTypesString.append(placeTypeEnum.getText()).append("، ");
            else
                placeTypesString.append(placeTypeEnum.getText());
        }
        holder.getPlaceTypesTextView().setText(placeTypesString);
    }

    @Override
    public int getItemCount() {
        return placeSummaryList.size();
    }
}
