package ir.fearefull.wheretoapp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.data.model.api.Friend;
import ir.fearefull.wheretoapp.data.model.db.User;
import ir.fearefull.wheretoapp.ui.adapter.FollowersAdapter;
import ir.fearefull.wheretoapp.ui.adapter.PlaceReviewsAdapter;

public class FollowerFragment extends Fragment {

    private User user;
    private List<Friend> followerList;

    private View parentView;
    private FollowersAdapter followersAdapter;
    private RecyclerView recyclerViewFollowers;

    public FollowerFragment(){

    }

    @SuppressLint("ValidFragment")
    public FollowerFragment(User user, List<Friend> followerList){
        this.user = user;
        this.followerList = followerList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_follower, container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerViewFollowers = parentView.findViewById(R.id.recyclerViewFollowers);
        followersAdapter = new FollowersAdapter(followerList, user, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewFollowers.setLayoutManager(layoutManager);
        recyclerViewFollowers.setAdapter(followersAdapter);
    }

}
