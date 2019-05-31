package ir.fearefull.wheretoapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.fearefull.wheretoapp.Adapter.PlaceReviewsAdapter;
import ir.fearefull.wheretoapp.Adapter.PlacesSummaryAdapter;
import ir.fearefull.wheretoapp.Model.Place;
import ir.fearefull.wheretoapp.Model.PlaceReview;
import ir.fearefull.wheretoapp.Model.PlaceReviews;
import ir.fearefull.wheretoapp.Model.PlaceSummary;
import ir.fearefull.wheretoapp.Network.GetDataService;
import ir.fearefull.wheretoapp.Network.RetrofitClientInstance;
import ir.fearefull.wheretoapp.PlaceActivity;
import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.Widget.RecyclerTouchListener;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceReviewFragment extends Fragment {

    View parentView;
    Place place;
    private PlaceReviewsAdapter placeReviewsAdapter;
    private RecyclerView recyclerViewPlaceReviews;
    private List<PlaceReview> placeReviewList;
    private TextView overallScoreTextView, foodScoreTextView, serviceScoreTextView, ambianceScoreTextView;
    private MaterialRatingBar placeOverallScoreRatingBar;
    private SeekBar oneScoreSeekBar, twoScoreSeekBar, threeScoreSeekBar, fourScoreSeekBar, fiveScoreSeekBar;

    public PlaceReviewFragment(){

    }

    @SuppressLint("ValidFragment")
    public PlaceReviewFragment(Place place){
        this.place = place;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_place_review, container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        placeReviewList = new ArrayList<>();

        overallScoreTextView = parentView.findViewById(R.id.overallScoreTextView);
        foodScoreTextView = parentView.findViewById(R.id.foodScoreTextView);
        serviceScoreTextView = parentView.findViewById(R.id.serviceScoreTextView);
        ambianceScoreTextView = parentView.findViewById(R.id.ambianceScoreTextView);
        placeOverallScoreRatingBar = parentView.findViewById(R.id.placeOverallScoreRatingBar);
        oneScoreSeekBar = parentView.findViewById(R.id.oneScoreSeekBar);
        twoScoreSeekBar = parentView.findViewById(R.id.twoScoreSeekBar);
        threeScoreSeekBar = parentView.findViewById(R.id.threeScoreSeekBar);
        fourScoreSeekBar = parentView.findViewById(R.id.fourScoreSeekBar);
        fiveScoreSeekBar = parentView.findViewById(R.id.fiveScoreSeekBar);

        overallScoreTextView.setText(String.valueOf(place.getOverallScore()));
        foodScoreTextView.setText(String.valueOf(place.getFoodScoreAverage()));
        serviceScoreTextView.setText(String.valueOf(place.getServiceScoreAverage()));
        ambianceScoreTextView.setText(String.valueOf(place.getAmbianceScoreAverage()));
        placeOverallScoreRatingBar.setRating(place.getOverallScore());
        if (place.getAllScoresCount() != 0) {
            oneScoreSeekBar.setProgress((100 * place.getOneScoresCount() / place.getAllScoresCount()));
            twoScoreSeekBar.setProgress((100 * place.getTwoScoresCount() / place.getAllScoresCount()));
            threeScoreSeekBar.setProgress((100 * place.getThreeScoresCount() / place.getAllScoresCount()));
            fourScoreSeekBar.setProgress((100 * place.getFourScoresCount() / place.getAllScoresCount()));
            fiveScoreSeekBar.setProgress((100 * place.getFiveScoresCount() / place.getAllScoresCount()));
        }
        foodScoreTextView.setText(String.valueOf(place.getFoodScoreAverage()));
        serviceScoreTextView.setText(String.valueOf(place.getServiceScoreAverage()));
        ambianceScoreTextView.setText(String.valueOf(place.getAmbianceScoreAverage()));

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<PlaceReviews> call = service.getAllPlaceReviews(place.getId());
        call.enqueue(new Callback<PlaceReviews>() {
            @Override
            public void onResponse(Call<PlaceReviews> call, Response<PlaceReviews> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<PlaceReviews> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.e("places", t.getMessage());
            }
        });
    }

    private void generateDataList(PlaceReviews placeReviews) {
        Log.d("review", placeReviews.getPlaceReviews().get(0).getText());
        this.placeReviewList = placeReviews.getPlaceReviews();
        recyclerViewPlaceReviews = parentView.findViewById(R.id.recyclerViewPlaceReviews);
        placeReviewsAdapter = new PlaceReviewsAdapter(this.placeReviewList, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerViewPlaceReviews.setLayoutManager(layoutManager);
        recyclerViewPlaceReviews.setAdapter(placeReviewsAdapter);
    }

}
