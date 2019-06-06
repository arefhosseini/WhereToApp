package ir.fearefull.wheretoapp.ui.adapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.data.model.api.PlaceSummary;
import ir.fearefull.wheretoapp.data.model.api.PlaceTypeEnum;
import ir.fearefull.wheretoapp.R;

public class PlacesSummaryAdapter extends RecyclerView.Adapter<PlacesSummaryAdapter.MyViewHolder> {

    private List<PlaceSummary> placeSummaryList;
    private ViewGroup parent;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView, overallScoreTextView, placeTypesTextView;
        private ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            nameTextView = view.findViewById(R.id.nameTextView);
            overallScoreTextView = view.findViewById(R.id.overallScoreTextView);
            placeTypesTextView = view.findViewById(R.id.placeTypesTextView);
        }
    }

    public PlacesSummaryAdapter(List<PlaceSummary> placeSummaryList) {
        this.placeSummaryList = placeSummaryList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_place_summary, parent, false);

        this.parent = parent;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PlaceSummary placeSummary = placeSummaryList.get(position);
        holder.nameTextView.setText(placeSummary.getName());
        holder.overallScoreTextView.setText(String.valueOf(placeSummary.getOverallScore()));
        Picasso.get().load(Constants.BASE_URL + placeSummary.getPlaceImage()).into(holder.imageView);
        StringBuilder placeTypesString = new StringBuilder();
        for (PlaceTypeEnum placeTypeEnum : placeSummary.getPlaceTypes()) {
            if (placeSummary.getPlaceTypes().indexOf(placeTypeEnum) != placeSummary.getPlaceTypes().size() - 1)
                placeTypesString.append(placeTypeEnum.getText()).append("، ");
            else
                placeTypesString.append(placeTypeEnum.getText());
        }
        holder.placeTypesTextView.setText(placeTypesString);
    }

    @Override
    public int getItemCount() {
        return placeSummaryList.size();
    }
}
