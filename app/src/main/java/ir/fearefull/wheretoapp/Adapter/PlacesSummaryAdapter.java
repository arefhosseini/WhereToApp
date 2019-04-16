package ir.fearefull.wheretoapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.fearefull.wheretoapp.Constants;
import ir.fearefull.wheretoapp.Model.PlaceSummary;
import ir.fearefull.wheretoapp.R;

public class PlacesSummaryAdapter extends RecyclerView.Adapter<PlacesSummaryAdapter.MyViewHolder> {

    private List<PlaceSummary> placeSummaryList;
    private ViewGroup parent;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView, overallScoreTextView;
        private ImageView imageView;

        public MyViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
            nameTextView = view.findViewById(R.id.nameTextView);
            overallScoreTextView = view.findViewById(R.id.overallScoreTextView);
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
        holder.nameTextView.setSelected(true);
    }

    @Override
    public int getItemCount() {
        return placeSummaryList.size();
    }
}
