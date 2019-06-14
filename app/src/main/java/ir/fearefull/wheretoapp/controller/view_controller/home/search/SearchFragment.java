package ir.fearefull.wheretoapp.controller.view_controller.home.search;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.util.List;
import java.util.Objects;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.base.ViewPagerAdapter;
import ir.fearefull.wheretoapp.model.api.search.PlaceSearchResponse;
import ir.fearefull.wheretoapp.model.api.search.UserSearchResponse;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.view.base.MainPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private View parentView;
    private User user;
    private TabLayout tabLayout;
    private MainPager viewPager;
    private TextView tabLayoutTextTextView;
    private GetDataService service;

    private int lastSelectedTab = 0;

    private UserSearchFragment userSearchFragment;
    private PlaceSearchFragment placeSearchFragment;
    private View tabUserSearchView, tabPlaceSearchView;
    private EditText searchEditText;

    public SearchFragment(){
    }

    @SuppressLint("ValidFragment")
    public SearchFragment(User user){
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_home_search, container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        viewPager = view.findViewById(R.id.viewpager);
        tabLayout = view.findViewById(R.id.tabs);
        searchEditText = view.findViewById(R.id.searchEditText);

        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.clearFocus();
        viewPager.setOffscreenPageLimit(2);

        searchEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (lastSelectedTab == 0)
                        searchUser(searchEditText.getText().toString());
                    else
                        searchPlace(searchEditText.getText().toString());
                    return true;
                }
                return false;
            }
        });

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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        userSearchFragment = new UserSearchFragment(this, user);
        placeSearchFragment = new PlaceSearchFragment(this, user);

        adapter.addFragment(userSearchFragment, "userSearch");
        adapter.addFragment(placeSearchFragment, "placeSearch");

        viewPager.setAdapter(adapter);
    }

    @SuppressLint("InflateParams")
    private void setupTabIcons() {

        tabUserSearchView = getLayoutInflater().inflate(R.layout.tab_toolbar_without_icon, null);
        tabLayoutTextTextView = tabUserSearchView.findViewById(R.id.text);
        tabLayoutTextTextView.setText("کاربران");
        Objects.requireNonNull(tabLayout.getTabAt(0)).setCustomView(tabUserSearchView);

        tabPlaceSearchView = getLayoutInflater().inflate(R.layout.tab_toolbar_without_icon, null);
        tabLayoutTextTextView = tabPlaceSearchView.findViewById(R.id.text);
        tabLayoutTextTextView.setText("مکان ها");
        Objects.requireNonNull(tabLayout.getTabAt(1)).setCustomView(tabPlaceSearchView);

        Objects.requireNonNull(tabLayout.getTabAt(Objects.requireNonNull(getActivity()).getIntent().getIntExtra("selectedTab", 1))).select();

    }

    private void searchUser(String text) {
        Call<List<UserSearchResponse>> call = service.getUsersSearch(text);
        call.enqueue(new Callback<List<UserSearchResponse>>() {
            @Override
            public void onResponse(Call<List<UserSearchResponse>> call, Response<List<UserSearchResponse>> response) {
                //progressDoalog.dismiss();

                assert response.body() != null;
                generateUserSearchDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<UserSearchResponse>> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchPlace(String text) {
        Call<List<PlaceSearchResponse>> call = service.getPlacesSearch(text);
        call.enqueue(new Callback<List<PlaceSearchResponse>>() {
            @Override
            public void onResponse(Call<List<PlaceSearchResponse>> call, Response<List<PlaceSearchResponse>> response) {
                //progressDoalog.dismiss();

                assert response.body() != null;
                generatePlaceSearchDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<PlaceSearchResponse>> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateUserSearchDataList(List<UserSearchResponse> userSearchResponseList) {
        userSearchFragment.setData(userSearchResponseList);
    }

    private void generatePlaceSearchDataList(List<PlaceSearchResponse> placeSearchResponseList) {
        placeSearchFragment.setData(placeSearchResponseList);
    }
}
