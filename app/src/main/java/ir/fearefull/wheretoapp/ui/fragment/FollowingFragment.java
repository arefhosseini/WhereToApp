package ir.fearefull.wheretoapp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.data.model.api.Friend;
import ir.fearefull.wheretoapp.data.model.db.User;
import ir.fearefull.wheretoapp.ui.adapter.FollowingsAdapter;

public class FollowingFragment extends Fragment {

    private User user;
    private List<Friend> followingList;

    private View parentView;
    private FollowingsAdapter followingsAdapter;
    private RecyclerView recyclerViewFollowings;

    public FollowingFragment(){
    }

    @SuppressLint("ValidFragment")
    public FollowingFragment(User user, List<Friend> followingList){
        this.user = user;
        this.followingList = followingList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_following, container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerViewFollowings = parentView.findViewById(R.id.recyclerViewFollowings);
        followingsAdapter = new FollowingsAdapter(this.followingList, user, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewFollowings.setLayoutManager(layoutManager);
        recyclerViewFollowings.setAdapter(followingsAdapter);
    }

}
