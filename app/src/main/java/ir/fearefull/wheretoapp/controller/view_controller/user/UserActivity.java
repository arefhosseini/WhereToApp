package ir.fearefull.wheretoapp.controller.view_controller.user;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import androidx.appcompat.app.AppCompatActivity;
import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.local.AppDatabase;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.relation.RelationActivity;
import ir.fearefull.wheretoapp.model.api.user.UserResponse;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.model.api.relation.RelationRequest;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.utils.DatabaseInitializer;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserActivity extends AppCompatActivity {

    private User user;
    private String phoneNumber;
    private UserResponse userResponse;
    private ImageButton backImageButton, favoritePlacesImageButton, scoresImageButton,
            reviewsImageButton, placeImagesImageButton;
    private ImageView profileImageView;
    private LinearLayout followersLayout, followingsLayout;
    private TextView followTextView, userScoreTextView, firstNameTextView, lastNameTextView, followersCountTextView,
            followingsCountTextView, favoritePlacesCountTextView, scoresCountTextView,
            reviewsCountTextView, placeImagesCountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        phoneNumber = getIntent().getStringExtra("phoneNumber");
        user = DatabaseInitializer.getUser(AppDatabase.getAppDatabase(getApplicationContext()));

        backImageButton = findViewById(R.id.backImageButton);
        favoritePlacesImageButton = findViewById(R.id.favoritePlacesImageButton);
        scoresImageButton = findViewById(R.id.scoresImageButton);
        reviewsImageButton = findViewById(R.id.reviewsImageButton);
        placeImagesImageButton = findViewById(R.id.placeImagesImageButton);
        profileImageView = findViewById(R.id.profileImageView);
        followTextView = findViewById(R.id.followTextView);
        userScoreTextView = findViewById(R.id.userScoreTextView);
        firstNameTextView = findViewById(R.id.firstNameTextView);
        lastNameTextView = findViewById(R.id.lastNameTextView);
        followersCountTextView = findViewById(R.id.followersCountTextView);
        followingsCountTextView = findViewById(R.id.followingsCountTextView);
        favoritePlacesCountTextView = findViewById(R.id.favoritePlacesCountTextView);
        scoresCountTextView = findViewById(R.id.scoresCountTextView);
        reviewsCountTextView = findViewById(R.id.reviewsCountTextView);
        placeImagesCountTextView = findViewById(R.id.placeImagesCountTextView);
        followersLayout = findViewById(R.id.followersLayout);
        followingsLayout = findViewById(R.id.followingsLayout);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateUserData(UserResponse userResponse) {
        this.userResponse = userResponse;

        followTextView.setOnClickListener(onFollowClickListener);
        if (!userResponse.getPhoneNumber().equals(user.getPhoneNumber())) {
            if (userResponse.getIsFollowing() == 0) {
                followTextView.setText(R.string.follow);
                followTextView.setBackground(getDrawable(R.drawable.follow_button_shape));
            }
            else {
                followTextView.setText(R.string.dont_follow);
                followTextView.setBackground(getDrawable(R.drawable.dont_follow_light_button_shape));
            }
        }
        else
            followTextView.setVisibility(View.GONE);

        //Picasso.get().load(Constants.BASE_URL + userResponse.getProfileImage()).into(profileImageView);
        if (userResponse.getProfileImage().contains("media"))
            Picasso.get().load(Constants.BASE_URL + userResponse.getProfileImage()).into(profileImageView);
        else
            Picasso.get().load(Constants.MEDIA_URL + userResponse.getProfileImage()).into(profileImageView);

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
        Call<ResponseBody> call = service.followUser(new RelationRequest(user.getPhoneNumber(), phoneNumber).toRequestBody());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), "این کاربر به لیست دنبال کنندگان شما اضافه شد", Toast.LENGTH_LONG).show();
                userResponse.setIsFollowing(1);
                followTextView.setText(R.string.dont_follow);
                followTextView.setBackground(getDrawable(R.drawable.dont_follow_light_button_shape));
                userResponse.setFollowingsCount(userResponse.getFollowingsCount() + 1);
                followingsCountTextView.setText(String.valueOf(userResponse.getFollowingsCount()));
                assert response.body() != null;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeFollowRequest() throws JSONException {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseBody> call = service.removeFollowUser(new RelationRequest(user.getPhoneNumber(), phoneNumber).toRequestBody());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), "این کاربر از لیست دنبال کنندگان شما حذف شد", Toast.LENGTH_LONG).show();
                followTextView.setText(R.string.follow);
                followTextView.setBackground(getDrawable(R.drawable.follow_button_shape));
                userResponse.setIsFollowing(0);
                userResponse.setFollowingsCount(userResponse.getFollowingsCount() - 1);
                followingsCountTextView.setText(String.valueOf(userResponse.getFollowingsCount()));
                assert response.body() != null;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private View.OnClickListener onOpenFriendActivityFollowersSelected = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent dashboardIntent = new Intent(UserActivity.this, RelationActivity.class);
            dashboardIntent.putExtra("phoneNumber", phoneNumber);
            dashboardIntent.putExtra("selectedTab", 1);
            startActivity(dashboardIntent);
        }
    };

    private View.OnClickListener onOpenFriendActivityFollowingsSelected = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent dashboardIntent = new Intent(UserActivity.this, RelationActivity.class);
            dashboardIntent.putExtra("phoneNumber", phoneNumber);
            dashboardIntent.putExtra("selectedTab", 0);
            startActivity(dashboardIntent);
        }
    };
}
