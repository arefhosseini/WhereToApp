package ir.fearefull.wheretoapp.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import org.json.JSONException;

import java.io.File;
import java.util.Objects;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.data.local.AppDatabase;
import ir.fearefull.wheretoapp.data.model.api.AddReviewRequest;
import ir.fearefull.wheretoapp.data.model.api.AddScoreRequest;
import ir.fearefull.wheretoapp.data.model.api.CreateUserRequest;
import ir.fearefull.wheretoapp.data.model.api.PlaceFavoriteRequest;
import ir.fearefull.wheretoapp.data.model.api.PlaceResponse;
import ir.fearefull.wheretoapp.data.model.api.UserResponse;
import ir.fearefull.wheretoapp.data.model.db.User;
import ir.fearefull.wheretoapp.ui.adapter.MainPager;
import ir.fearefull.wheretoapp.data.model.api.PlaceTypeEnum;
import ir.fearefull.wheretoapp.ui.adapter.ViewPagerAdapter;
import ir.fearefull.wheretoapp.ui.fragment.PlaceHomeFragment;
import ir.fearefull.wheretoapp.ui.fragment.PlaceMenuFragment;
import ir.fearefull.wheretoapp.ui.fragment.PlaceReviewFragment;
import ir.fearefull.wheretoapp.data.remote.GetDataService;
import ir.fearefull.wheretoapp.data.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.utils.DatabaseInitializer;
import ir.fearefull.wheretoapp.utils.FileUtils;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ir.fearefull.wheretoapp.utils.Constants.ADD_REVIEW_DIALOG;
import static ir.fearefull.wheretoapp.utils.Constants.ADD_SCORE_DIALOG;
import static ir.fearefull.wheretoapp.utils.Constants.PICK_FROM_GALLERY;

public class PlaceActivity extends AppCompatActivity {
    private User user;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private MainPager viewPager;
    private TextView placeNameTextView, tabLayoutTextTextView, placeOverallScoreTextView,
            placePriceDegreeTextView, placePriceDegreeUnselectedTextView, placeTypeTextView;
    private ImageView placeImageImageView;
    private LinearLayout placeTypesLayout;
    private MaterialRatingBar placeOverallScoreRatingBar;
    private ImageButton backImageButton, favouriteImageButton;

    private int lastSelectedTab = 0;

    private PlaceHomeFragment placeHomeFragment;
    private PlaceMenuFragment placeMenuFragment;
    private PlaceReviewFragment placeReviewFragment;

    private View tabHomeView, tabMenuView, tabReviewView;

    PlaceResponse placeResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

        user = DatabaseInitializer.getUser(AppDatabase.getAppDatabase(getApplicationContext()));
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        placeNameTextView = findViewById(R.id.placeNameTextView);
        placeImageImageView = findViewById(R.id.placeImageImageView);
        placeOverallScoreTextView = findViewById(R.id.placeOverallScoreTextView);
        placeOverallScoreRatingBar = findViewById(R.id.placeOverallScoreRatingBar);
        placePriceDegreeTextView = findViewById(R.id.placePriceDegreeTextView);
        placePriceDegreeUnselectedTextView = findViewById(R.id.placePriceDegreeUnselectedTextView);
        placeTypesLayout = findViewById(R.id.placeTypesLayout);
        backImageButton = findViewById(R.id.backImageButton);
        favouriteImageButton = findViewById(R.id.favouriteImageButton);

        Intent myIntent = getIntent(); // gets the previously created intent
        long id = myIntent.getLongExtra("id", 0);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        favouriteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (placeResponse.getIsFavorite() == 0) {
                    try {
                        favouriteImageButton.setImageResource(R.drawable.ic_favorite_white_24dp);
                        addPlaceToFavourite();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        favouriteImageButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
                        removePlaceFromFavorite();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<PlaceResponse> call = service.getPlace(user.getPhoneNumber(), id);
        call.enqueue(new Callback<PlaceResponse>() {
            @Override
            public void onResponse(Call<PlaceResponse> call, Response<PlaceResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateData(response.body());
            }

            @Override
            public void onFailure(Call<PlaceResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.e("places", t.getMessage());
            }
        });
    }

    private void generateData(PlaceResponse placeResponse) {
        this.placeResponse = placeResponse;
        placeNameTextView.setText(placeResponse.getPlace().getName());
        Picasso.get().load(Constants.BASE_URL + placeResponse.getPlace().getPlaceImage()).into(placeImageImageView);
        placeOverallScoreRatingBar.setRating(placeResponse.getPlace().getOverallScore());
        placeOverallScoreTextView.setText(String.valueOf("(" + String.valueOf(placeResponse.getPlace().getAllScoresCount()) + ")"));
        placePriceDegreeTextView.setText(repeat(placeResponse.getPlace().getPriceDegree(), "$"));
        placePriceDegreeUnselectedTextView.setText(repeat(4 - placeResponse.getPlace().getPriceDegree(), "$"));
        for (PlaceTypeEnum placeTypeEnum: placeResponse.getPlace().getPlaceTypes()) {
            placeTypeTextView = (TextView) getLayoutInflater().inflate(R.layout.card_place_type, placeTypesLayout, false);
            placeTypeTextView.setText(placeTypeEnum.getText());
            placeTypesLayout.addView(placeTypeTextView);
        }

        if (placeResponse.getIsFavorite() == 0) {
            favouriteImageButton.setImageResource(R.drawable.ic_favorite_border_white_24dp);
        }
        else {
            favouriteImageButton.setImageResource(R.drawable.ic_favorite_white_24dp);
        }

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.clearFocus();
        viewPager.setOffscreenPageLimit(3);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0 && lastSelectedTab != 0) {
                    tabLayoutTextTextView = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.text);
                    tabLayoutTextTextView.setTextColor(getResources().getColor(R.color.textLightPrimary));

                    tabLayoutTextTextView = Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(lastSelectedTab)).getCustomView()).findViewById(R.id.text);
                    tabLayoutTextTextView.setTextColor(getResources().getColor(R.color.textLightSecondary));
                    lastSelectedTab = 0;
                }
                else if (tab.getPosition() == 1 && lastSelectedTab != 1) {
                    tabLayoutTextTextView = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.text);
                    tabLayoutTextTextView.setTextColor(getResources().getColor(R.color.textLightPrimary));

                    tabLayoutTextTextView = Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(lastSelectedTab)).getCustomView()).findViewById(R.id.text);
                    tabLayoutTextTextView.setTextColor(getResources().getColor(R.color.textLightSecondary));
                    lastSelectedTab = 1;
                }
                else if (tab.getPosition() == 2 && lastSelectedTab != 2) {
                    tabLayoutTextTextView = Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.text);
                    tabLayoutTextTextView.setTextColor(getResources().getColor(R.color.textLightPrimary));

                    tabLayoutTextTextView = Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(lastSelectedTab)).getCustomView()).findViewById(R.id.text);
                    tabLayoutTextTextView.setTextColor(getResources().getColor(R.color.textLightSecondary));
                    lastSelectedTab = 2;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        setupTabIcons();
    }

    private void setupViewPager(MainPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        placeHomeFragment = new PlaceHomeFragment(user, this.placeResponse);
        placeMenuFragment = new PlaceMenuFragment(user, this.placeResponse);
        placeReviewFragment = new PlaceReviewFragment(user, this.placeResponse);

        adapter.addFragment(placeReviewFragment, "review");
        adapter.addFragment(placeMenuFragment, "menu");
        adapter.addFragment(placeHomeFragment, "home");

        viewPager.setAdapter(adapter);
    }

    @SuppressLint("InflateParams")
    private void setupTabIcons() {

        tabReviewView = getLayoutInflater().inflate(R.layout.tab_toolbar_without_icon, null);
        tabLayoutTextTextView = tabReviewView.findViewById(R.id.text);
        tabLayoutTextTextView.setText("نظرات");
        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(tabReviewView);

        tabMenuView = getLayoutInflater().inflate(R.layout.tab_toolbar_without_icon, null);
        tabLayoutTextTextView = tabMenuView.findViewById(R.id.text);
        tabLayoutTextTextView.setText("منو");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(tabMenuView);

        tabHomeView = getLayoutInflater().inflate(R.layout.tab_toolbar_without_icon, null);
        tabLayoutTextTextView = tabHomeView.findViewById(R.id.text);
        tabLayoutTextTextView.setText("اطلاعات");
        Objects.requireNonNull(tabLayout.getTabAt(2)).setCustomView(tabHomeView);

        Objects.requireNonNull(tabLayout.getTabAt(2)).select();

    }

    public static String repeat(int count, String with) {
        return new String(new char[count]).replace("\0", with);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, PICK_FROM_GALLERY);
                } else {
                    Toast.makeText(getApplicationContext(), "لطفا دسترسی به گالری را بدهید", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    uploadImage(resultUri);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
                break;
        }
    }

    private void uploadImage(Uri resultUri) {
        // use the FileUtils to get the actual file by uri
        File file = FileUtils.getFile(getApplicationContext(), resultUri);

        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        RequestBody userRequestBody = RequestBody.create(
                okhttp3.MultipartBody.FORM, user.getPhoneNumber());
        RequestBody placeRequestBody = RequestBody.create(
                okhttp3.MultipartBody.FORM, String.valueOf(placeResponse.getPlace().getId()));
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseBody> call = service.uploadPlaceImage(userRequestBody, placeRequestBody, body);
        Log.v("Upload", "uploading");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call,
                                   Response<ResponseBody> response) {
                Log.v("Upload", "success");
                Toast.makeText(getApplicationContext(), "عکس شما آپلود شد", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    private void addPlaceToFavourite() throws JSONException {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseBody> call = service.addPlaceToFavorite(new PlaceFavoriteRequest(user.getPhoneNumber(), placeResponse.getPlace().getId()).toRequestBody());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                Toast.makeText(getApplicationContext(), "به مکان های مورد علاقتان اضافه شد", Toast.LENGTH_LONG).show();
                placeResponse.setIsFavorite(1);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removePlaceFromFavorite() throws JSONException {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseBody> call = service.removePlaceFromFavorite(new PlaceFavoriteRequest(user.getPhoneNumber(), placeResponse.getPlace().getId()).toRequestBody());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), "از مکان های مورد علاقتان پاک شد", Toast.LENGTH_LONG).show();
                placeResponse.setIsFavorite(0);
                assert response.body() != null;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


