package ir.fearefull.wheretoapp.controller.view_controller.place.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.place.adapter.ReviewsAdapter;
import ir.fearefull.wheretoapp.model.api.place.PlaceResponse;
import ir.fearefull.wheretoapp.model.api.review.PlaceReview;
import ir.fearefull.wheretoapp.model.api.review.PlaceReviews;
import ir.fearefull.wheretoapp.model.db.User;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReviewFragment extends Fragment {

    User user;
    PlaceResponse placeResponse;
    View parentView;
    private ReviewsAdapter reviewsAdapter;
    private RecyclerView recyclerViewPlaceReviews;
    private List<PlaceReview> placeReviewList;
    private TextView overallScoreTextView, foodScoreTextView, serviceScoreTextView, ambianceScoreTextView;
    private MaterialRatingBar placeOverallScoreRatingBar;
    private SeekBar oneScoreSeekBar, twoScoreSeekBar, threeScoreSeekBar, fourScoreSeekBar, fiveScoreSeekBar;

    public ReviewFragment(){

    }

    @SuppressLint("ValidFragment")
    public ReviewFragment(User user, PlaceResponse placeResponse){
        this.user = user;
        this.placeResponse = placeResponse;
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

        overallScoreTextView.setText(String.valueOf(placeResponse.getPlace().getOverallScore()));
        foodScoreTextView.setText(String.valueOf(placeResponse.getPlace().getFoodScoreAverage()));
        serviceScoreTextView.setText(String.valueOf(placeResponse.getPlace().getServiceScoreAverage()));
        ambianceScoreTextView.setText(String.valueOf(placeResponse.getPlace().getAmbianceScoreAverage()));
        placeOverallScoreRatingBar.setRating(placeResponse.getPlace().getOverallScore());
        if (placeResponse.getPlace().getAllScoresCount() != 0) {
            oneScoreSeekBar.setProgress((100 * placeResponse.getPlace().getOneScoresCount() / placeResponse.getPlace().getAllScoresCount()));
            twoScoreSeekBar.setProgress((100 * placeResponse.getPlace().getTwoScoresCount() / placeResponse.getPlace().getAllScoresCount()));
            threeScoreSeekBar.setProgress((100 * placeResponse.getPlace().getThreeScoresCount() / placeResponse.getPlace().getAllScoresCount()));
            fourScoreSeekBar.setProgress((100 * placeResponse.getPlace().getFourScoresCount() / placeResponse.getPlace().getAllScoresCount()));
            fiveScoreSeekBar.setProgress((100 * placeResponse.getPlace().getFiveScoresCount() / placeResponse.getPlace().getAllScoresCount()));
        }
        foodScoreTextView.setText(String.valueOf(placeResponse.getPlace().getFoodScoreAverage()));
        serviceScoreTextView.setText(String.valueOf(placeResponse.getPlace().getServiceScoreAverage()));
        ambianceScoreTextView.setText(String.valueOf(placeResponse.getPlace().getAmbianceScoreAverage()));

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<PlaceReviews> call = service.getAllPlaceReviews(placeResponse.getPlace().getId());
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
        this.placeReviewList = placeReviews.getPlaceReviews();
        recyclerViewPlaceReviews = parentView.findViewById(R.id.recyclerViewPlaceReviews);
        reviewsAdapter = new ReviewsAdapter(this.placeReviewList, getContext(), this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewPlaceReviews.setLayoutManager(layoutManager);
        recyclerViewPlaceReviews.setAdapter(reviewsAdapter);
    }
}
