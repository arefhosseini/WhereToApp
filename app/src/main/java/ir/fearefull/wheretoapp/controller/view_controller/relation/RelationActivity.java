package ir.fearefull.wheretoapp.controller.view_controller.relation;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.local.AppDatabase;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.base.ViewPagerAdapter;
import ir.fearefull.wheretoapp.controller.view_controller.relation.fragment.RelationFragment;
import ir.fearefull.wheretoapp.model.api.relation.RelationResponse;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.utils.DatabaseInitializer;
import ir.fearefull.wheretoapp.view.base.MainPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RelationActivity extends AppCompatActivity {

    private User user;
    private RelationResponse friendResponse;

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private MainPager viewPager;
    private TextView tabLayoutTextTextView;
    private ImageButton backImageButton;

    private int lastSelectedTab = 0;

    private RelationFragment followingFragment;
    private RelationFragment followerFragment;
    private View tabFollowingView, tabFollowerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_relation);

        user = DatabaseInitializer.getUser(AppDatabase.getAppDatabase(getApplicationContext()));
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tabs);
        backImageButton = findViewById(R.id.backImageButton);

        Intent myIntent = getIntent(); // gets the previously created intent
        String phoneNumber = myIntent.getStringExtra("phoneNumber");

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<RelationResponse> call = service.getFriends(user.getPhoneNumber(), phoneNumber);
        call.enqueue(new Callback<RelationResponse>() {
            @Override
            public void onResponse(Call<RelationResponse> call, Response<RelationResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateData(response.body());
            }

            @Override
            public void onFailure(Call<RelationResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.e("places", t.getMessage());
            }
        });

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void generateData(RelationResponse friendResponse) {
        this.friendResponse = friendResponse;

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.clearFocus();
        viewPager.setOffscreenPageLimit(2);

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

        followingFragment = new RelationFragment(user, friendResponse.getFollowings());
        followerFragment = new RelationFragment(user, friendResponse.getFollowers());

        adapter.addFragment(followingFragment, "following");
        adapter.addFragment(followerFragment, "follower");

        viewPager.setAdapter(adapter);
    }

    @SuppressLint("InflateParams")
    private void setupTabIcons() {

        tabFollowingView = getLayoutInflater().inflate(R.layout.tab_toolbar_without_icon, null);
        tabLayoutTextTextView = tabFollowingView.findViewById(R.id.text);
        tabLayoutTextTextView.setText("دنبال شوندگان");
        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(tabFollowingView);

        tabFollowerView = getLayoutInflater().inflate(R.layout.tab_toolbar_without_icon, null);
        tabLayoutTextTextView = tabFollowerView.findViewById(R.id.text);
        tabLayoutTextTextView.setText("دنبال کنندگان");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(tabFollowerView);

        Objects.requireNonNull(tabLayout.getTabAt(getIntent().getIntExtra("selectedTab", 1))).select();

    }
}
