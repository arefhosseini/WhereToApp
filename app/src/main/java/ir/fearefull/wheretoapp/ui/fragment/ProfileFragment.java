package ir.fearefull.wheretoapp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.Objects;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.data.local.AppDatabase;
import ir.fearefull.wheretoapp.data.model.api.CreateUserRequest;
import ir.fearefull.wheretoapp.data.model.api.UserResponse;
import ir.fearefull.wheretoapp.data.model.db.User;
import ir.fearefull.wheretoapp.data.remote.GetDataService;
import ir.fearefull.wheretoapp.data.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.ui.EditProfileActivity;
import ir.fearefull.wheretoapp.ui.FriendActivity;
import ir.fearefull.wheretoapp.ui.UserActivity;
import ir.fearefull.wheretoapp.ui.VerifyActivity;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.utils.DatabaseInitializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileFragment extends Fragment {

    private User user;
    private UserResponse userResponse;
    private ImageButton logoutImageButton, favoritePlacesImageButton, scoresImageButton,
            reviewsImageButton, placeImagesImageButton;
    private ImageView profileImageView;
    private LinearLayout followersLayout, followingsLayout;
    private TextView editProfileTextView, userScoreTextView, firstNameTextView, lastNameTextView, followersCountTextView,
            followingsCountTextView, favoritePlacesCountTextView, scoresCountTextView,
            reviewsCountTextView, placeImagesCountTextView;

    public ProfileFragment(){

    }

    @SuppressLint("ValidFragment")
    public ProfileFragment(User user){
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        logoutImageButton = view.findViewById(R.id.logoutImageButton);
        favoritePlacesImageButton = view.findViewById(R.id.favoritePlacesImageButton);
        scoresImageButton = view.findViewById(R.id.scoresImageButton);
        reviewsImageButton = view.findViewById(R.id.reviewsImageButton);
        placeImagesImageButton = view.findViewById(R.id.placeImagesImageButton);
        profileImageView = view.findViewById(R.id.profileImageView);
        editProfileTextView = view.findViewById(R.id.editProfileTextView);
        userScoreTextView = view.findViewById(R.id.userScoreTextView);
        firstNameTextView = view.findViewById(R.id.firstNameTextView);
        lastNameTextView = view.findViewById(R.id.lastNameTextView);
        followersCountTextView = view.findViewById(R.id.followersCountTextView);
        followingsCountTextView = view.findViewById(R.id.followingsCountTextView);
        favoritePlacesCountTextView = view.findViewById(R.id.favoritePlacesCountTextView);
        scoresCountTextView = view.findViewById(R.id.scoresCountTextView);
        reviewsCountTextView = view.findViewById(R.id.reviewsCountTextView);
        placeImagesCountTextView = view.findViewById(R.id.placeImagesCountTextView);
        followersLayout = view.findViewById(R.id.followersLayout);
        followingsLayout = view.findViewById(R.id.followingsLayout);

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
    }

    private void showEditProfileActivity() {
        Intent dashboardIntent = new Intent(getContext(), EditProfileActivity.class);
        dashboardIntent.putExtra("UserResponse", userResponse);

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
            Intent dashboardIntent = new Intent(getActivity(), FriendActivity.class);
            dashboardIntent.putExtra("phoneNumber", user.getPhoneNumber());
            dashboardIntent.putExtra("selectedTab", 1);
            startActivity(dashboardIntent);
        }
    };

    private View.OnClickListener onOpenFriendActivityFollowingsSelected = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent dashboardIntent = new Intent(getActivity(), FriendActivity.class);
            dashboardIntent.putExtra("phoneNumber", user.getPhoneNumber());
            dashboardIntent.putExtra("selectedTab", 0);
            startActivity(dashboardIntent);
        }
    };
}
