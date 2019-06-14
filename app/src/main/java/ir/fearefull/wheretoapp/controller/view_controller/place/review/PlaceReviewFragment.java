package ir.fearefull.wheretoapp.controller.view_controller.place.review;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.place.dialog.CreateReviewDialog;
import ir.fearefull.wheretoapp.controller.view_controller.place.dialog.CreateScoreDialog;
import ir.fearefull.wheretoapp.controller.view_controller.place.home.PlaceHomeFragment;
import ir.fearefull.wheretoapp.model.api.SimpleResponse;
import ir.fearefull.wheretoapp.model.api.place.PlaceResponse;
import ir.fearefull.wheretoapp.model.api.review.CreateReviewRequest;
import ir.fearefull.wheretoapp.model.api.review.PlaceReviewResponse;
import ir.fearefull.wheretoapp.model.api.review.PlaceReviewsResponse;
import ir.fearefull.wheretoapp.model.api.score.CreatePlaceScoreRequest;
import ir.fearefull.wheretoapp.model.api.score.PlaceScore;
import ir.fearefull.wheretoapp.model.db.User;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static ir.fearefull.wheretoapp.utils.Constants.CREATE_REVIEW_DIALOG;
import static ir.fearefull.wheretoapp.utils.Constants.CREATE_SCORE_DIALOG;

public class PlaceReviewFragment extends Fragment {

    private User user;
    private PlaceResponse placeResponse;
    private PlaceHomeFragment placeHomeFragment;
    private View parentView;
    private PlaceReviewsAdapter placeReviewsAdapter;
    private RecyclerView recyclerViewPlaceReviews;
    private List<PlaceReviewResponse> placeReviewResponseList;
    private TextView overallScoreTextView, foodScoreTextView, serviceScoreTextView,
            ambianceScoreTextView, yourOverallScoreTextView;
    private MaterialRatingBar placeOverallScoreRatingBar, yourOverallScoreRatingBar;
    private SeekBar oneScoreSeekBar, twoScoreSeekBar, threeScoreSeekBar,
            fourScoreSeekBar, fiveScoreSeekBar;
    private LinearLayout yourOverallScoreLayout;

    public PlaceReviewFragment(){

    }

    @SuppressLint("ValidFragment")
    public PlaceReviewFragment(User user, PlaceResponse placeResponse){
        this.user = user;
        this.placeResponse = placeResponse;
    }

    public void setFragments(PlaceHomeFragment placeHomeFragment) {
        this.placeHomeFragment = placeHomeFragment;
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

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        placeReviewResponseList = new ArrayList<>();

        yourOverallScoreLayout = parentView.findViewById(R.id.yourOverallScoreLayout);
        overallScoreTextView = parentView.findViewById(R.id.overallScoreTextView);
        foodScoreTextView = parentView.findViewById(R.id.foodScoreTextView);
        serviceScoreTextView = parentView.findViewById(R.id.serviceScoreTextView);
        ambianceScoreTextView = parentView.findViewById(R.id.ambianceScoreTextView);
        yourOverallScoreTextView = parentView.findViewById(R.id.yourOverallScoreTextView);
        placeOverallScoreRatingBar = parentView.findViewById(R.id.placeOverallScoreRatingBar);
        yourOverallScoreRatingBar = parentView.findViewById(R.id.yourOverallScoreRatingBar);
        oneScoreSeekBar = parentView.findViewById(R.id.oneScoreSeekBar);
        twoScoreSeekBar = parentView.findViewById(R.id.twoScoreSeekBar);
        threeScoreSeekBar = parentView.findViewById(R.id.threeScoreSeekBar);
        fourScoreSeekBar = parentView.findViewById(R.id.fourScoreSeekBar);
        fiveScoreSeekBar = parentView.findViewById(R.id.fiveScoreSeekBar);

        oneScoreSeekBar.setOnTouchListener(disabledOnTouchListener);
        twoScoreSeekBar.setOnTouchListener(disabledOnTouchListener);
        threeScoreSeekBar.setOnTouchListener(disabledOnTouchListener);
        fourScoreSeekBar.setOnTouchListener(disabledOnTouchListener);
        fiveScoreSeekBar.setOnTouchListener(disabledOnTouchListener);

        yourOverallScoreRatingBar.setRating(placeResponse.getPlaceScore().getTotalScore());
        if (placeResponse.getPlaceScore().getTotalScore() > 0)
            yourOverallScoreTextView.setText(R.string.your_ratings);
        else
            yourOverallScoreTextView.setText(R.string.submit_your_ratings);

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
        Call<PlaceReviewsResponse> call = service.getPlaceReviews(user.getPhoneNumber(), placeResponse.getPlace().getId());
        call.enqueue(new Callback<PlaceReviewsResponse>() {
            @Override
            public void onResponse(Call<PlaceReviewsResponse> call, Response<PlaceReviewsResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<PlaceReviewsResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.e("places", t.getMessage());
            }
        });

        yourOverallScoreLayout.setOnClickListener(createScoreClickListener);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CREATE_SCORE_DIALOG:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int overallScore = Objects.requireNonNull(bundle).getInt("overallScore");
                    int foodScore = Objects.requireNonNull(bundle).getInt("foodScore");
                    int serviceScore = Objects.requireNonNull(bundle).getInt("serviceScore");
                    int ambianceScore = Objects.requireNonNull(bundle).getInt("ambianceScore");
                    yourOverallScoreRatingBar.setRating(overallScore);
                    placeResponse.getPlaceScore().setTotalScore(overallScore);
                    placeHomeFragment.changeFragment(placeResponse);
                    try {
                        createPlaceScore(new CreatePlaceScoreRequest(user.getPhoneNumber(),
                                placeResponse.getPlace().getId(), overallScore, foodScore,
                                serviceScore, ambianceScore));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Log.d("addScoreDialog", "canceled");
                    showCreateReviewDialog();
                }
                break;
            case CREATE_REVIEW_DIALOG:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String reviewText = Objects.requireNonNull(bundle).getString("reviewText");
                    try {
                        createReview(new CreateReviewRequest(user.getPhoneNumber(),
                                placeResponse.getPlace().getId(), reviewText));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void generateDataList(PlaceReviewsResponse placeReviewsResponse) {
        this.placeReviewResponseList = placeReviewsResponse.getPlaceReviews();
        recyclerViewPlaceReviews = parentView.findViewById(R.id.recyclerViewPlaceReviews);
        placeReviewsAdapter = new PlaceReviewsAdapter(this.placeReviewResponseList,
                this, user);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        recyclerViewPlaceReviews.addItemDecoration(
                new DividerItemDecoration(recyclerViewPlaceReviews.getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewPlaceReviews.setLayoutManager(layoutManager);
        recyclerViewPlaceReviews.setAdapter(placeReviewsAdapter);
    }

    private View.OnClickListener createScoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CreateScoreDialog createScoreDialog = new CreateScoreDialog(placeResponse.getPlaceScore());
            createScoreDialog.setTargetFragment(PlaceReviewFragment.this, CREATE_SCORE_DIALOG);
            createScoreDialog.show(Objects.requireNonNull(getFragmentManager()).beginTransaction(), "MyProgressDialog");
        }
    };

    private void showCreateReviewDialog() {
        CreateReviewDialog createReviewDialog = new CreateReviewDialog();
        createReviewDialog.setTargetFragment(PlaceReviewFragment.this, CREATE_REVIEW_DIALOG);
        createReviewDialog.show(Objects.requireNonNull(getFragmentManager()).beginTransaction(), "MyProgressDialog");
    }

    private void createPlaceScore(final CreatePlaceScoreRequest createPlaceScoreRequest) throws JSONException {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SimpleResponse> call = service.createScore(createPlaceScoreRequest.toRequestBody());
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateCreateScoreData(createPlaceScoreRequest);
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createReview(CreateReviewRequest createReviewRequest) throws JSONException {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SimpleResponse> call = service.createReview(createReviewRequest.toRequestBody());
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateCreateReviewData();
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateCreateScoreData(CreatePlaceScoreRequest createPlaceScoreRequest) {
        placeResponse.setPlaceScore(new PlaceScore(0, createPlaceScoreRequest.getUser(),
                createPlaceScoreRequest.getPlace(), createPlaceScoreRequest.getTotalScore(),
                createPlaceScoreRequest.getFoodScore(), createPlaceScoreRequest.getServiceScore(),
                createPlaceScoreRequest.getAmbianceScore()));
        Toast.makeText(getContext(), "امتیاز شما ثبت شد", Toast.LENGTH_SHORT).show();
        showCreateReviewDialog();
    }

    private void generateCreateReviewData() {
        Toast.makeText(getContext(), "نظر شما ثبت شد", Toast.LENGTH_SHORT).show();
    }

    private SeekBar.OnTouchListener disabledOnTouchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    };

    public void changeFragment(PlaceResponse placeResponse) {
        this.placeResponse = placeResponse;
        yourOverallScoreRatingBar.setRating(placeResponse.getPlaceScore().getTotalScore());
        if (placeResponse.getPlaceScore().getTotalScore() > 0)
            yourOverallScoreTextView.setText(R.string.your_ratings);
        else
            yourOverallScoreTextView.setText(R.string.submit_your_ratings);

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
    }
}
