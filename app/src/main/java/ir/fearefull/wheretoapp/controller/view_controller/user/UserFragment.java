package ir.fearefull.wheretoapp.controller.view_controller.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.Objects;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.relation.RelationFragment;
import ir.fearefull.wheretoapp.controller.view_controller.user.favorite_place.UserFavoritePlaceFragment;
import ir.fearefull.wheretoapp.controller.view_controller.user.review.UserReviewFragment;
import ir.fearefull.wheretoapp.model.api.SimpleResponse;
import ir.fearefull.wheretoapp.model.api.user.UserResponse;
import ir.fearefull.wheretoapp.model.api.user.relation.UserRelationRequest;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.utils.Constants;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    private View parentView;
    private User user;
    private String phoneNumber;
    private UserResponse userResponse;
    private ImageButton backImageButton, favoritePlacesImageButton, reviewsImageButton;
    private ImageView profileImageView;
    private LinearLayout followersLayout, followingsLayout;
    private TextView followTextView, userScoreTextView, firstNameTextView, lastNameTextView, followersCountTextView,
            followingsCountTextView, favoritePlacesCountTextView, scoresCountTextView,
            reviewsCountTextView, placeImagesCountTextView;

    public UserFragment(){
    }

    public UserFragment(User user, String phoneNumber){
        this.user = user;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_user, container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        backImageButton = parentView.findViewById(R.id.backImageButton);
        favoritePlacesImageButton = parentView.findViewById(R.id.favoritePlacesImageButton);
        reviewsImageButton = parentView.findViewById(R.id.reviewsImageButton);
        profileImageView = parentView.findViewById(R.id.profileImageView);
        followTextView = parentView.findViewById(R.id.followTextView);
        userScoreTextView = parentView.findViewById(R.id.userScoreTextView);
        firstNameTextView = parentView.findViewById(R.id.firstNameTextView);
        lastNameTextView = parentView.findViewById(R.id.lastNameTextView);
        followersCountTextView = parentView.findViewById(R.id.followersCountTextView);
        followingsCountTextView = parentView.findViewById(R.id.followingsCountTextView);
        favoritePlacesCountTextView = parentView.findViewById(R.id.favoritePlacesCountTextView);
        scoresCountTextView = parentView.findViewById(R.id.scoresCountTextView);
        reviewsCountTextView = parentView.findViewById(R.id.reviewsCountTextView);
        placeImagesCountTextView = parentView.findViewById(R.id.placeImagesCountTextView);
        followersLayout = parentView.findViewById(R.id.followersLayout);
        followingsLayout = parentView.findViewById(R.id.followingsLayout);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getChildFragmentManager()
                        .beginTransaction()
                        .remove(UserFragment.this)
                        .commit();
                getChildFragmentManager().popBackStack();
            }
        });

        followersLayout.setOnClickListener(onOpenFriendActivityFollowersSelected);
        followingsLayout.setOnClickListener(onOpenFriendActivityFollowingsSelected);

        createUserRequest();
    }

    private void createUserRequest() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<UserResponse> call = service.getUser(user.getPhoneNumber(), phoneNumber);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateUserData(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateUserData(UserResponse userResponse) {
        this.userResponse = userResponse;

        followTextView.setOnClickListener(onFollowClickListener);
        if (!userResponse.getPhoneNumber().equals(user.getPhoneNumber())) {
            if (userResponse.getIsFollowing() == 0) {
                followTextView.setText(R.string.follow);
                followTextView.setBackground(Objects.requireNonNull(getActivity()).getDrawable(R.drawable.follow_button_shape));
            }
            else {
                followTextView.setText(R.string.dont_follow);
                followTextView.setBackground(Objects.requireNonNull(getActivity()).getDrawable(R.drawable.dont_follow_light_button_shape));
            }
        }
        else
            followTextView.setVisibility(View.GONE);

        Picasso.get().load(Constants.BASE_URL + userResponse.getProfileImage()).into(profileImageView);
        userScoreTextView.setText(String.valueOf(userResponse.getUserScore()));
        firstNameTextView.setText(userResponse.getFirstName());
        lastNameTextView.setText(userResponse.getLastName());
        followersCountTextView.setText(String.valueOf(userResponse.getFollowersCount()));
        followingsCountTextView.setText(String.valueOf(userResponse.getFollowingsCount()));
        favoritePlacesCountTextView.setText(String.valueOf(userResponse.getFavoritePlacesCount()));
        scoresCountTextView.setText(String.valueOf(userResponse.getPlaceScoresCount()));
        reviewsCountTextView.setText(String.valueOf(userResponse.getReviewsCount()));
        placeImagesCountTextView.setText(String.valueOf(userResponse.getUploadedImagesCount()));

        reviewsImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager())
                        .beginTransaction()
                        .replace(R.id.fragmentUser, new UserReviewFragment(user, UserFragment.this.userResponse))
                        .addToBackStack(null)
                        .commit();
            }
        });

        favoritePlacesImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager())
                        .beginTransaction()
                        .replace(R.id.fragmentUser, new UserFavoritePlaceFragment(user, UserFragment.this.userResponse))
                        .addToBackStack(null)
                        .commit();
            }
        });
    }

    private View.OnClickListener onFollowClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (userResponse.getIsFollowing() == 0) {
                try {
                    followRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    removeFollowRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    private void followRequest() throws JSONException {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SimpleResponse> call = service.createUserRelation(new UserRelationRequest(user.getPhoneNumber(), phoneNumber).toRequestBody());
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "این کاربر به لیست دنبال کنندگان شما اضافه شد", Toast.LENGTH_LONG).show();
                userResponse.setIsFollowing(1);
                followTextView.setText(R.string.dont_follow);
                followTextView.setBackground(Objects.requireNonNull(getActivity()).getDrawable(R.drawable.dont_follow_light_button_shape));
                userResponse.setFollowingsCount(userResponse.getFollowingsCount() + 1);
                followingsCountTextView.setText(String.valueOf(userResponse.getFollowingsCount()));
                assert response.body() != null;
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeFollowRequest() throws JSONException {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseBody> call = service.removeUserRelation(new UserRelationRequest(user.getPhoneNumber(), phoneNumber).toRequestBody());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "این کاربر از لیست دنبال کنندگان شما حذف شد", Toast.LENGTH_LONG).show();
                followTextView.setText(R.string.follow);
                followTextView.setBackground(Objects.requireNonNull(getActivity()).getDrawable(R.drawable.follow_button_shape));
                userResponse.setIsFollowing(0);
                userResponse.setFollowingsCount(userResponse.getFollowingsCount() - 1);
                followingsCountTextView.setText(String.valueOf(userResponse.getFollowingsCount()));
                assert response.body() != null;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private View.OnClickListener onOpenFriendActivityFollowersSelected = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager())
                    .beginTransaction()
                    .replace(R.id.fragmentUser, new RelationFragment(user, userResponse, 1))
                    .addToBackStack(null)
                    .commit();
        }
    };

    private View.OnClickListener onOpenFriendActivityFollowingsSelected = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager())
                    .beginTransaction()
                    .replace(R.id.fragmentUser, new RelationFragment(user, userResponse, 0))
                    .addToBackStack(null)
                    .commit();
        }
    };
}
