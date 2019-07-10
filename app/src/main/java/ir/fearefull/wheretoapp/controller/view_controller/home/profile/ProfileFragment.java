package ir.fearefull.wheretoapp.controller.view_controller.home.profile;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.local.AppDatabase;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.base.MyFragment;
import ir.fearefull.wheretoapp.controller.view_controller.edit_profile.EditProfileActivity;
import ir.fearefull.wheretoapp.controller.view_controller.relation.RelationFragment;
import ir.fearefull.wheretoapp.controller.view_controller.user.favorite_place.UserFavoritePlaceFragment;
import ir.fearefull.wheretoapp.controller.view_controller.user.favorite_place_type.UserFavoritePlaceTypeFragment;
import ir.fearefull.wheretoapp.controller.view_controller.user.review.UserReviewFragment;
import ir.fearefull.wheretoapp.controller.view_controller.verify.VerifyActivity;
import ir.fearefull.wheretoapp.model.api.Enum.PlaceTypeEnum;
import ir.fearefull.wheretoapp.model.api.user.UserResponse;
import ir.fearefull.wheretoapp.model.api.user.control.UserControlResponse;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.utils.DatabaseInitializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends MyFragment implements UserFavoritePlaceTypeCallBack {

    private User user;
    private UserResponse userResponse;
    private ImageButton logoutImageButton, favoritePlaceTypesImageButton, favoritePlacesImageButton,
            reviewsImageButton;
    private ImageView profileImageView;
    private LinearLayout followersLayout, followingsLayout,
            favoritePlaceTypesLayout, placeTypesLayout;
    private RelativeLayout favoritePlacesLayout, reviewsLayout;
    private TextView editProfileTextView, userScoreTextView, firstNameTextView, lastNameTextView,
            followersCountTextView, followingsCountTextView, favoritePlaceTypesCountTextView,
            favoritePlacesCountTextView, scoresCountTextView, reviewsCountTextView,
            placeImagesCountTextView, placeTypeTextView;

    public ProfileFragment(){

    }

    @SuppressLint("ValidFragment")
    public ProfileFragment(String TAG, User user){
        this.user = user;
        this.TAG = TAG;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        logoutImageButton = view.findViewById(R.id.logoutImageButton);
        favoritePlacesImageButton = view.findViewById(R.id.favoritePlacesImageButton);
        favoritePlaceTypesImageButton = view.findViewById(R.id.favoritePlaceTypesImageButton);
        reviewsImageButton = view.findViewById(R.id.reviewsImageButton);
        profileImageView = view.findViewById(R.id.profileImageView);
        editProfileTextView = view.findViewById(R.id.editProfileTextView);
        userScoreTextView = view.findViewById(R.id.userScoreTextView);
        firstNameTextView = view.findViewById(R.id.firstNameTextView);
        lastNameTextView = view.findViewById(R.id.lastNameTextView);
        followersCountTextView = view.findViewById(R.id.followersCountTextView);
        followingsCountTextView = view.findViewById(R.id.followingsCountTextView);
        favoritePlaceTypesCountTextView = view.findViewById(R.id.favoritePlaceTypesCountTextView);
        favoritePlacesCountTextView = view.findViewById(R.id.favoritePlacesCountTextView);
        scoresCountTextView = view.findViewById(R.id.scoresCountTextView);
        reviewsCountTextView = view.findViewById(R.id.reviewsCountTextView);
        placeImagesCountTextView = view.findViewById(R.id.placeImagesCountTextView);
        followersLayout = view.findViewById(R.id.followersLayout);
        followingsLayout = view.findViewById(R.id.followingsLayout);
        placeTypesLayout = view.findViewById(R.id.placeTypesLayout);
        favoritePlacesLayout = view.findViewById(R.id.favoritePlacesLayout);
        reviewsLayout = view.findViewById(R.id.reviewsLayout);
        favoritePlaceTypesLayout = view.findViewById(R.id.favoritePlaceTypesLayout);

        followersLayout.setOnClickListener(onOpenFriendActivityFollowersSelected);
        followingsLayout.setOnClickListener(onOpenFriendActivityFollowingsSelected);

        logoutImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });
        editProfileTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditProfileActivity();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        createUserRequest();
    }

    private void createUserRequest() {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<UserResponse> call = service.getUser(user.getPhoneNumber(), user.getPhoneNumber());
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

        if (userResponse.getFavoritePlaceTypes().isEmpty()) {
            favoritePlaceTypesCountTextView.setText("0");
        }
        else {
            favoritePlaceTypesCountTextView.setVisibility(View.GONE);
            placeTypesLayout.setVisibility(View.VISIBLE);
            placeTypesLayout.removeAllViews();
            for (PlaceTypeEnum placeTypeEnum: userResponse.getFavoritePlaceTypes()) {
                placeTypeTextView = (TextView) getLayoutInflater().inflate(R.layout.card_place_type, placeTypesLayout, false);
                placeTypeTextView.setText(placeTypeEnum.getText());
                placeTypesLayout.addView(placeTypeTextView);
            }
        }

        favoritePlaceTypesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserFavoritePlaceTypeFragment fragment = new UserFavoritePlaceTypeFragment(ProfileFragment.this,
                        TAG, ProfileFragment.this.userResponse);
                openFragment(fragment, R.id.fragmentHomeProfile);
            }
        });

        reviewsLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserReviewFragment fragment = new UserReviewFragment(TAG, user, ProfileFragment.this.userResponse);
                openFragment(fragment, R.id.fragmentHomeProfile);
            }
        });

        favoritePlacesLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserFavoritePlaceFragment fragment = new UserFavoritePlaceFragment(TAG, user, ProfileFragment.this.userResponse);
                openFragment(fragment, R.id.fragmentHomeProfile);
            }
        });
    }

    private void showEditProfileActivity() {
        Intent dashboardIntent = new Intent(getContext(), EditProfileActivity.class);
        UserControlResponse userControlResponse = new UserControlResponse(
                0L, userResponse.getPhoneNumber(), userResponse.getProfileImage(),
                userResponse.getFirstName(), userResponse.getLastName(),
                userResponse.getFavoritePlaceTypes()
        );
        dashboardIntent.putExtra("UserControlResponse", userControlResponse);
        dashboardIntent.putExtra("isOpenedByRegister", false);

        startActivity(dashboardIntent);
    }

    private void logOut() {
        DatabaseInitializer.resetUsers(AppDatabase.getAppDatabase(getContext()));
        Intent dashboardIntent = new Intent(getContext(), VerifyActivity.class);
        dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dashboardIntent);
    }

    private View.OnClickListener onOpenFriendActivityFollowersSelected = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RelationFragment fragment = new RelationFragment(TAG, user, userResponse, 1);
            openFragment(fragment, R.id.fragmentHomeProfile);
        }
    };

    private View.OnClickListener onOpenFriendActivityFollowingsSelected = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            RelationFragment fragment = new RelationFragment(TAG, user, userResponse, 0);
            openFragment(fragment, R.id.fragmentHomeProfile);
        }
    };

    @Override
    public void changeUserFavoritePlace(List<PlaceTypeEnum> placeTypeEnumList) {
        this.userResponse.setFavoritePlaceTypes(placeTypeEnumList);
        if (userResponse.getFavoritePlaceTypes().isEmpty()) {
            placeTypesLayout.setVisibility(View.GONE);
            favoritePlaceTypesCountTextView.setVisibility(View.VISIBLE);
            favoritePlaceTypesCountTextView.setText("0");
        }
        else {
            favoritePlaceTypesCountTextView.setVisibility(View.GONE);
            placeTypesLayout.setVisibility(View.VISIBLE);
            placeTypesLayout.removeAllViews();
            for (PlaceTypeEnum placeTypeEnum: userResponse.getFavoritePlaceTypes()) {
                placeTypeTextView = (TextView) getLayoutInflater().inflate(R.layout.card_place_type, placeTypesLayout, false);
                placeTypeTextView.setText(placeTypeEnum.getText());
                placeTypesLayout.addView(placeTypeTextView);
            }
        }
    }
}
