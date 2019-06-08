package ir.fearefull.wheretoapp.view.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import ir.fearefull.wheretoapp.R;

public class PlaceSummaryViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView, overallScoreTextView, placeTypesTextView;
    private ImageView imageView;

    public PlaceSummaryViewHolder(View view) {
        super(view);
        imageView = view.findViewById(R.id.imageView);
        nameTextView = view.findViewById(R.id.nameTextView);
        overallScoreTextView = view.findViewById(R.id.overallScoreTextView);
        placeTypesTextView = view.findViewById(R.id.placeTypesTextView);
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public void setNameTextView(TextView nameTextView) {
        this.nameTextView = nameTextView;
    }

    public TextView getOverallScoreTextView() {
        return overallScoreTextView;
    }

    public void setOverallScoreTextView(TextView overallScoreTextView) {
        this.overallScoreTextView = overallScoreTextView;
    }

    public TextView getPlaceTypesTextView() {
        return placeTypesTextView;
    }

    public void setPlaceTypesTextView(TextView placeTypesTextView) {
        this.placeTypesTextView = placeTypesTextView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}