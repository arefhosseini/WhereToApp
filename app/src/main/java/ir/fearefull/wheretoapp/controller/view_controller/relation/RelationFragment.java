package ir.fearefull.wheretoapp.controller.view_controller.relation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.base.ViewPagerAdapter;
import ir.fearefull.wheretoapp.controller.view_controller.relation.fragment.RelationPartFragment;
import ir.fearefull.wheretoapp.model.api.user.UserResponse;
import ir.fearefull.wheretoapp.model.api.user.relation.UserRelationResponse;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.view.base.MainPager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RelationFragment extends Fragment {

    private User user;
    private UserResponse userResponse;
    private UserRelationResponse userRelationResponse;
    private View parentView;
    private TabLayout tabLayout;
    private MainPager viewPager;
    private TextView tabLayoutTextTextView;
    private ImageButton backImageButton;

    private int lastSelectedTab = 0;
    private int initSelectTab = 0;

    private RelationPartFragment followingFragment;
    private RelationPartFragment followerFragment;
    private View tabFollowingView, tabFollowerView;

    public RelationFragment(){
    }

    @SuppressLint("ValidFragment")
    public RelationFragment(User user, UserResponse userResponse, int initSelectTab){
        this.user = user;
        this.userResponse = userResponse;
        this.initSelectTab = initSelectTab;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_relation, container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager = parentView.findViewById(R.id.viewpager);
        tabLayout = parentView.findViewById(R.id.tabs);
        backImageButton = parentView.findViewById(R.id.backImageButton);

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<UserRelationResponse> call = service.getUserRelations(user.getPhoneNumber(),
                userResponse.getPhoneNumber());
        call.enqueue(new Callback<UserRelationResponse>() {
            @Override
            public void onResponse(Call<UserRelationResponse> call, Response<UserRelationResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateData(response.body());
            }

            @Override
            public void onFailure(Call<UserRelationResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.e("places", t.getMessage());
            }
        });

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getChildFragmentManager()
                        .beginTransaction()
                        .remove(RelationFragment.this)
                        .commit();
                getChildFragmentManager().popBackStack();
            }
        });
    }

    private void generateData(UserRelationResponse friendResponse) {
        this.userRelationResponse = friendResponse;

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
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());

        followingFragment = new RelationPartFragment(user, userRelationResponse.getFollowings());
        followerFragment = new RelationPartFragment(user, userRelationResponse.getFollowers());

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

        Objects.requireNonNull(tabLayout.getTabAt(initSelectTab)).select();

    }
}
