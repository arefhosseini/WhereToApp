package ir.fearefull.wheretoapp.controller.view_controller.place;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
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

import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.Objects;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.base.ViewPagerAdapter;
import ir.fearefull.wheretoapp.controller.view_controller.place.home.PlaceHomeFragment;
import ir.fearefull.wheretoapp.controller.view_controller.place.menu.PlaceMenuFragment;
import ir.fearefull.wheretoapp.controller.view_controller.place.review.PlaceReviewFragment;
import ir.fearefull.wheretoapp.model.api.Enum.PlaceTypeEnum;
import ir.fearefull.wheretoapp.model.api.SimpleResponse;
import ir.fearefull.wheretoapp.model.api.place.PlaceFavoriteRequest;
import ir.fearefull.wheretoapp.model.api.place.PlaceResponse;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.view.base.MainPager;
import ir.fearefull.wheretoapp.controller.view_controller.base.MyFragment;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceFragment extends MyFragment {

    private User user;
    private long placeId;
    private View parentView;

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

    private PlaceResponse placeResponse;

    public PlaceFragment(){
    }

    @SuppressLint("ValidFragment")
    public PlaceFragment(String TAG, User user, long placeId){
        this.TAG = TAG;
        this.user = user;
        this.placeId = placeId;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_place, container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager = parentView.findViewById(R.id.viewpager);
        tabLayout = parentView.findViewById(R.id.tabs);
        placeNameTextView = parentView.findViewById(R.id.placeNameTextView);
        placeImageImageView = parentView.findViewById(R.id.placeImageImageView);
        placeOverallScoreTextView = parentView.findViewById(R.id.placeOverallScoreTextView);
        placeOverallScoreRatingBar = parentView.findViewById(R.id.placeOverallScoreRatingBar);
        placePriceDegreeTextView = parentView.findViewById(R.id.placePriceDegreeTextView);
        placePriceDegreeUnselectedTextView = parentView.findViewById(R.id.placePriceDegreeUnselectedTextView);
        placeTypesLayout = parentView.findViewById(R.id.placeTypesLayout);
        backImageButton = parentView.findViewById(R.id.backImageButton);
        favouriteImageButton = parentView.findViewById(R.id.favouriteImageButton);

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
                        favouriteImageButton.setImageResource(R.drawable.ic_favorite_black_24dp);
                        addPlaceToFavourite();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        favouriteImageButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
                        removePlaceFromFavorite();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<PlaceResponse> call = service.getPlace(user.getPhoneNumber(), placeId);
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
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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
        placePriceDegreeTextView.setText(repeat(placeResponse.getPlace().getPriceDegree()));
        placePriceDegreeUnselectedTextView.setText(repeat(4 - placeResponse.getPlace().getPriceDegree()));
        for (PlaceTypeEnum placeTypeEnum: placeResponse.getPlace().getPlaceTypes()) {
            placeTypeTextView = (TextView) getLayoutInflater().inflate(R.layout.card_place_type, placeTypesLayout, false);
            placeTypeTextView.setText(placeTypeEnum.getText());
            placeTypesLayout.addView(placeTypeTextView);
        }

        if (placeResponse.getIsFavorite() == 0) {
            favouriteImageButton.setImageResource(R.drawable.ic_favorite_border_black_24dp);
        }
        else {
            favouriteImageButton.setImageResource(R.drawable.ic_favorite_black_24dp);
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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        placeHomeFragment = new PlaceHomeFragment(TAG, user, this.placeResponse);
        placeMenuFragment = new PlaceMenuFragment(TAG, user, this.placeResponse);
        placeReviewFragment = new PlaceReviewFragment(TAG, user, this.placeResponse);

        placeHomeFragment.setFragments(this, placeReviewFragment);
        placeReviewFragment.setFragments(placeHomeFragment);

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

    private static String repeat(int count) {
        return new String(new char[count]).replace("\0", "$");
    }

    private void addPlaceToFavourite() throws JSONException {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SimpleResponse> call = service.createUserFavoritePlace(new PlaceFavoriteRequest(user.getPhoneNumber(), placeResponse.getPlace().getId()).toRequestBody());
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                Toast.makeText(getContext(), "به مکان های مورد علاقتان اضافه شد", Toast.LENGTH_LONG).show();
                placeResponse.setIsFavorite(1);
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removePlaceFromFavorite() throws JSONException {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseBody> call = service.removeUserFavoritePlace(new PlaceFavoriteRequest(user.getPhoneNumber(), placeResponse.getPlace().getId()).toRequestBody());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "از مکان های مورد علاقتان پاک شد", Toast.LENGTH_LONG).show();
                placeResponse.setIsFavorite(0);
                assert response.body() != null;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void showReviewFragmentListener() {
        Objects.requireNonNull(tabLayout.getTabAt(0)).select();
    }
}
