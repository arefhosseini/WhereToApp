package ir.fearefull.wheretoapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.Objects;

import ir.fearefull.wheretoapp.Adapter.MainPager;
import ir.fearefull.wheretoapp.Enum.PlaceTypeEnum;
import ir.fearefull.wheretoapp.Fragment.PlaceHomeFragment;
import ir.fearefull.wheretoapp.Fragment.PlaceMenuFragment;
import ir.fearefull.wheretoapp.Fragment.PlaceReviewFragment;
import ir.fearefull.wheretoapp.Model.Place;
import ir.fearefull.wheretoapp.Network.GetDataService;
import ir.fearefull.wheretoapp.Network.RetrofitClientInstance;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private MainPager viewPager;
    private TextView placeNameTextView, tabLayoutTextTextView, placeOverallScoreTextView,
            placePriceDegreeTextView, placePriceDegreeUnselectedTextView, placeTypeTextView;
    private ImageView placeImageImageView;
    private LinearLayout placeTypesLayout;
    private MaterialRatingBar placeOverallScoreRatingBar;

    private int lastSelectedTab = 0;

    private PlaceHomeFragment placeHomeFragment;
    private PlaceMenuFragment placeMenuFragment;
    private PlaceReviewFragment placeReviewFragment;

    private View tabHomeView, tabMenuView, tabReviewView;

    Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place);

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

        Intent myIntent = getIntent(); // gets the previously created intent
        long id = myIntent.getLongExtra("id", 0);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<Place> call = service.getPlace(id);
        call.enqueue(new Callback<Place>() {
            @Override
            public void onResponse(Call<Place> call, Response<Place> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateData(response.body());
            }

            @Override
            public void onFailure(Call<Place> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.e("places", t.getMessage());
            }
        });
    }

    private void generateData(Place place) {
        this.place = place;
        placeNameTextView.setText(place.getName());
        Picasso.get().load(Constants.BASE_URL + place.getPlaceImage()).into(placeImageImageView);
        placeOverallScoreRatingBar.setRating(place.getOverallScore());
        placeOverallScoreTextView.setText(String.valueOf("(" + String.valueOf(place.getAllScoresCount()) + ")"));
        placePriceDegreeTextView.setText(repeat(place.getPriceDegree(), "$"));
        placePriceDegreeUnselectedTextView.setText(repeat(4 - place.getPriceDegree(), "$"));
        for (PlaceTypeEnum placeTypeEnum: place.getPlaceTypes()) {
            placeTypeTextView = (TextView) getLayoutInflater().inflate(R.layout.card_place_type, placeTypesLayout, false);
            placeTypeTextView.setText(placeTypeEnum.getText());
            placeTypesLayout.addView(placeTypeTextView);
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

        placeHomeFragment = new PlaceHomeFragment(this.place);
        placeMenuFragment = new PlaceMenuFragment(this.place);
        placeReviewFragment = new PlaceReviewFragment(this.place);

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
}


