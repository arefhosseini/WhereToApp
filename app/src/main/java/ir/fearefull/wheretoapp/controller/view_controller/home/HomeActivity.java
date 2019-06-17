package ir.fearefull.wheretoapp.controller.view_controller.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.local.AppDatabase;
import ir.fearefull.wheretoapp.controller.view_controller.base.ViewPagerAdapter;
import ir.fearefull.wheretoapp.controller.view_controller.home.home.HomeFragment;
import ir.fearefull.wheretoapp.controller.view_controller.home.profile.ProfileFragment;
import ir.fearefull.wheretoapp.controller.view_controller.home.search.SearchFragment;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.utils.DatabaseInitializer;
import ir.fearefull.wheretoapp.view.base.MainPager;

import static ir.fearefull.wheretoapp.utils.Constants.HOME_TAB_TAG;
import static ir.fearefull.wheretoapp.utils.Constants.PROFILE_TAB_TAG;
import static ir.fearefull.wheretoapp.utils.Constants.SEARCH_TAB_TAG;

public class HomeActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private MainPager viewPager;
    private User user;

    private int lastSelectedTab = 0;
    private String lastSelectedTabTag;
    private int[] tabIcons = {
            R.mipmap.search_selected,
            R.mipmap.home_selected,
            R.mipmap.profile_selected,
            R.mipmap.search_unselected,
            R.mipmap.home_unselected,
            R.mipmap.profile_unselected
    };
    private View tabHomeView, tabSearchView, tabProfileView;
    private SearchFragment searchFragment;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        user = DatabaseInitializer.getUser(AppDatabase.getAppDatabase(getApplicationContext()));
        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.clearFocus();
        viewPager.setOffscreenPageLimit(3);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0 && lastSelectedTab != 0) {
                    Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.icon).setBackgroundResource(tabIcons[0]);
                    Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(lastSelectedTab)).getCustomView()).findViewById(R.id.icon).setBackgroundResource(tabIcons[lastSelectedTab + 3]);
                    lastSelectedTab = 0;
                    lastSelectedTabTag = SEARCH_TAB_TAG;
                }
                else if (tab.getPosition() == 1 && lastSelectedTab != 1) {
                    Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.icon).setBackgroundResource(tabIcons[1]);
                    Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(lastSelectedTab)).getCustomView()).findViewById(R.id.icon).setBackgroundResource(tabIcons[lastSelectedTab + 3]);
                    lastSelectedTab = 1;
                    lastSelectedTabTag = HOME_TAB_TAG;
                }
                else if (tab.getPosition() == 2 && lastSelectedTab != 2) {
                    Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.icon).setBackgroundResource(tabIcons[2]);
                    Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(lastSelectedTab)).getCustomView()).findViewById(R.id.icon).setBackgroundResource(tabIcons[lastSelectedTab + 3]);
                    lastSelectedTab = 2;
                    lastSelectedTabTag = PROFILE_TAB_TAG;
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

    @Override
    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            Fragment lastFragment = getSupportFragmentManager().findFragmentByTag(HOME_TAB_TAG + "_" + count);
            if (lastFragment != null) {
                if (lastSelectedTabTag.equals(HOME_TAB_TAG))
                    getSupportFragmentManager().popBackStack();
                else
                    Objects.requireNonNull(tabLayout.getTabAt(1)).select();
            }
            else {
                lastFragment = getSupportFragmentManager().findFragmentByTag(PROFILE_TAB_TAG + "_" + count);
                if (lastFragment != null) {
                    if (lastSelectedTabTag.equals(PROFILE_TAB_TAG))
                        getSupportFragmentManager().popBackStack();
                    else
                        Objects.requireNonNull(tabLayout.getTabAt(2)).select();
                }
                else {
                    lastFragment = getSupportFragmentManager().findFragmentByTag(SEARCH_TAB_TAG + "_" + count);
                    if (lastFragment != null) {
                        if (lastSelectedTabTag.equals(SEARCH_TAB_TAG))
                            getSupportFragmentManager().popBackStack();
                        else
                            Objects.requireNonNull(tabLayout.getTabAt(0)).select();
                    }
                }
            }
            //additional code
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("InflateParams")
    private void setupTabIcons() {
        tabSearchView = getLayoutInflater().inflate(R.layout.tab_toolbar_with_icon, null);
        tabSearchView.findViewById(R.id.icon).setBackgroundResource(tabIcons[3]);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(tabSearchView);

        tabHomeView = getLayoutInflater().inflate(R.layout.tab_toolbar_with_icon, null);
        tabHomeView.findViewById(R.id.icon).setBackgroundResource(tabIcons[1]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(tabHomeView);

        tabProfileView = getLayoutInflater().inflate(R.layout.tab_toolbar_with_icon, null);
        tabProfileView.findViewById(R.id.icon).setBackgroundResource(tabIcons[5]);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setCustomView(tabProfileView);

        Objects.requireNonNull(tabLayout.getTabAt(1)).select();
    }

    private void setupViewPager(MainPager viewPager) {
        viewPager.setEnableSwipe(false);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        searchFragment = new SearchFragment(SEARCH_TAB_TAG, user);
        homeFragment = new HomeFragment(HOME_TAB_TAG, user);
        profileFragment = new ProfileFragment(PROFILE_TAB_TAG, user);
        adapter.addFragment(searchFragment, "Search");
        adapter.addFragment(homeFragment, "Home");
        adapter.addFragment(profileFragment, "Profile");
        viewPager.setAdapter(adapter);
    }
}
