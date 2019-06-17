package ir.fearefull.wheretoapp.view.place;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.model.api.place.menu.FoodResponse;

public class FoodViewHolder extends ChildViewHolder {

    private Context context;
    private TextView nameTextView;
    private TextView detailTextView;
    private TextView priceTextView;

    public FoodViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        nameTextView = itemView.findViewById(R.id.nameTextView);
        detailTextView = itemView.findViewById(R.id.detailTextView);
        priceTextView = itemView.findViewById(R.id.priceTextView);
    }

    public void onBind(FoodResponse foodResponse) {
        nameTextView.setText(foodResponse.getName());
        detailTextView.setText(foodResponse.getDetail());
        priceTextView.setText(String.valueOf(foodResponse.getPrice()));
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public TextView getDetailTextView() {
        return detailTextView;
    }

    public TextView getPriceTextView() {
        return priceTextView;
    }

    public void setNameTextView(TextView nameTextView) {
        this.nameTextView = nameTextView;
    }

    public void setDetailTextView(TextView detailTextView) {
        this.detailTextView = detailTextView;
    }

    public void setPriceTextView(TextView priceTextView) {
        this.priceTextView = priceTextView;
    }
}