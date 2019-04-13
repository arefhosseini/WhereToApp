package ir.fearefull.wheretoapp;

import android.annotation.SuppressLint;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.fearefull.wheretoapp.Adapter.MainPager;
import ir.fearefull.wheretoapp.Fragment.HomeFragment;
import ir.fearefull.wheretoapp.Fragment.ProfileFragment;
import ir.fearefull.wheretoapp.Fragment.SearchFragment;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private MainPager viewPager;

    private int lastSelectedTab = 0;
    private int[] tabIcons = {
            R.mipmap.search_selected,
            R.mipmap.home_selected,
            R.mipmap.profile_selected,
            R.mipmap.search_unselected,
            R.mipmap.home_unselected,
            R.mipmap.profile_unselected
    };
    private View viewTabHome, viewTabSearch, viewTabProfile;
    private SearchFragment searchFragment;
    private HomeFragment homeFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);

        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.clearFocus();
        viewPager.setOffscreenPageLimit(4);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0 && lastSelectedTab != 0) {
                    Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.icon).setBackgroundResource(tabIcons[0]);
                    Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(lastSelectedTab)).getCustomView()).findViewById(R.id.icon).setBackgroundResource(tabIcons[lastSelectedTab + 3]);
                    lastSelectedTab = 0;
                }
                else if (tab.getPosition() == 1 && lastSelectedTab != 1) {
                    Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.icon).setBackgroundResource(tabIcons[1]);
                    Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(lastSelectedTab)).getCustomView()).findViewById(R.id.icon).setBackgroundResource(tabIcons[lastSelectedTab + 3]);
                    lastSelectedTab = 1;
                }
                else if (tab.getPosition() == 2 && lastSelectedTab != 2) {
                    Objects.requireNonNull(tab.getCustomView()).findViewById(R.id.icon).setBackgroundResource(tabIcons[2]);
                    Objects.requireNonNull(Objects.requireNonNull(tabLayout.getTabAt(lastSelectedTab)).getCustomView()).findViewById(R.id.icon).setBackgroundResource(tabIcons[lastSelectedTab + 3]);
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

    @SuppressLint("InflateParams")
    private void setupTabIcons() {
        viewTabSearch = getLayoutInflater().inflate(R.layout.tab_toolbar_with_icon, null);
        viewTabSearch.findViewById(R.id.icon).setBackgroundResource(tabIcons[3]);
        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(viewTabSearch);

        viewTabHome = getLayoutInflater().inflate(R.layout.tab_toolbar_with_icon, null);
        viewTabHome.findViewById(R.id.icon).setBackgroundResource(tabIcons[1]);
        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(viewTabHome);

        viewTabProfile = getLayoutInflater().inflate(R.layout.tab_toolbar_with_icon, null);
        viewTabProfile.findViewById(R.id.icon).setBackgroundResource(tabIcons[5]);
        Objects.requireNonNull(tabLayout.getTabAt(2)).setCustomView(viewTabProfile);

        Objects.requireNonNull(tabLayout.getTabAt(1)).select();
    }

    private void setupViewPager(MainPager viewPager) {
        viewPager.setEnableSwipe(false);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        searchFragment = new SearchFragment();
        homeFragment = new HomeFragment();
        profileFragment = new ProfileFragment();
        adapter.addFragment(searchFragment, "Search");
        adapter.addFragment(homeFragment, "Home");
        adapter.addFragment(profileFragment, "Profile");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return null;
        }
    }
}
