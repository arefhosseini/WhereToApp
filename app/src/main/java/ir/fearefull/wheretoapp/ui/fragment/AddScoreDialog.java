package ir.fearefull.wheretoapp.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.data.model.api.PlaceScore;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class AddScoreDialog extends DialogFragment implements View.OnClickListener {

    private PlaceScore placeScore;
    private Button confirmButton;
    private View rootView;
    private MaterialRatingBar overallScoreRatingBar, foodScoreRatingBar, serviceScoreRatingBar,
            ambianceScoreRatingBar;

    public AddScoreDialog() {
    }

    @SuppressLint("ValidFragment")
    public AddScoreDialog(PlaceScore placeScore) {
        this.placeScore = placeScore;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_add_score, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rootView = view.findViewById(R.id.rootView);
        confirmButton = view.findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(this);
        overallScoreRatingBar = view.findViewById(R.id.overallScoreRatingBar);
        foodScoreRatingBar = view.findViewById(R.id.foodScoreRatingBar);
        serviceScoreRatingBar = view.findViewById(R.id.serviceScoreRatingBar);
        ambianceScoreRatingBar = view.findViewById(R.id.ambianceScoreRatingBar);

        overallScoreRatingBar.setRating(placeScore.getTotalScore());
        foodScoreRatingBar.setRating(placeScore.getFoodScore());
        serviceScoreRatingBar.setRating(placeScore.getServiceScore());
        ambianceScoreRatingBar.setRating(placeScore.getAmbianceScore());
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent()
                .putExtra("overallScore", (int) overallScoreRatingBar.getRating())
                .putExtra("foodScore", (int) foodScoreRatingBar.getRating())
                .putExtra("serviceScore", (int) serviceScoreRatingBar.getRating())
                .putExtra("ambianceScore", (int) ambianceScoreRatingBar.getRating());
        Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
        dismiss();
    }


}
