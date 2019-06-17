package ir.fearefull.wheretoapp.controller.view_controller.user.review;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.base.MyFragment;
import ir.fearefull.wheretoapp.controller.view_controller.place.PlaceFragment;
import ir.fearefull.wheretoapp.controller.view_controller.user.UserFragment;
import ir.fearefull.wheretoapp.model.api.review.UserReviewResponse;
import ir.fearefull.wheretoapp.model.api.review.UserReviewsResponse;
import ir.fearefull.wheretoapp.model.api.user.UserResponse;
import ir.fearefull.wheretoapp.model.db.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserReviewFragment extends MyFragment implements UserReviewAdapterCallBack {
    private User user;
    private UserResponse userResponse;
    private View parentView;
    private UserReviewAdapter userReviewAdapter;
    private RecyclerView recyclerViewUserReviews;
    private List<UserReviewResponse> userReviewResponseList;

    public UserReviewFragment(){
    }

    @SuppressLint("ValidFragment")
    public UserReviewFragment(String TAG, User user, UserResponse userResponse){
        this.TAG = TAG;
        this.user = user;
        this.userResponse = userResponse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_user_review, container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        userReviewResponseList = new ArrayList<>();

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<UserReviewsResponse> call = service.getUserReviews(user.getPhoneNumber(), userResponse.getPhoneNumber());
        call.enqueue(new Callback<UserReviewsResponse>() {
            @Override
            public void onResponse(Call<UserReviewsResponse> call, Response<UserReviewsResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<UserReviewsResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.e("places", t.getMessage());
            }
        });
    }

    private void generateDataList(UserReviewsResponse userReviewsResponse) {
        this.userReviewResponseList.addAll(userReviewsResponse.getUserReviews());
        recyclerViewUserReviews = parentView.findViewById(R.id.recyclerViewUserReviews);
        userReviewAdapter = new UserReviewAdapter(this, getContext(), this.userReviewResponseList,
                userResponse, user);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        recyclerViewUserReviews.addItemDecoration(
                new DividerItemDecoration(recyclerViewUserReviews.getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewUserReviews.setLayoutManager(layoutManager);
        recyclerViewUserReviews.setAdapter(userReviewAdapter);
    }

    @Override
    public void onOpenUserFragment(String phoneNumber) {
        UserFragment fragment = new UserFragment(TAG, user, phoneNumber);
        openFragment(fragment, R.id.fragmentRelation);
    }

    @Override
    public void onOpenPlaceFragment(long placeId) {
        PlaceFragment fragment = new PlaceFragment(TAG, user, placeId);
        openFragment(fragment, R.id.fragmentUserReview);
    }
}
