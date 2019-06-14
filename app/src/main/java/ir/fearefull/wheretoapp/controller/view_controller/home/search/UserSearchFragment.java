package ir.fearefull.wheretoapp.controller.view_controller.home.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.view_controller.home.search.adapter.UserSearchAdapter;
import ir.fearefull.wheretoapp.model.api.search.UserSearchResponse;
import ir.fearefull.wheretoapp.model.db.User;

public class UserSearchFragment extends Fragment {

    private List<UserSearchResponse> userSearchResponseList;

    private SearchFragment searchFragment;
    private User user;
    private View parentView;
    private UserSearchAdapter userSearchAdapter;
    private RecyclerView recyclerViewUserSearch;

    public UserSearchFragment(){
        userSearchResponseList = new ArrayList<>();
    }

    UserSearchFragment(SearchFragment searchFragment, User user){
        userSearchResponseList = new ArrayList<>();
        this.searchFragment = searchFragment;
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_search_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        parentView = view;
        recyclerViewUserSearch = parentView.findViewById(R.id.recyclerViewUserSearch);
        userSearchAdapter = new UserSearchAdapter(userSearchResponseList, this, user);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewUserSearch.setLayoutManager(layoutManager);
        recyclerViewUserSearch.setAdapter(userSearchAdapter);
    }

    void setData(List<UserSearchResponse> userSearchResponseList) {
        this.userSearchResponseList.clear();
        this.userSearchResponseList.addAll(userSearchResponseList);

        userSearchAdapter.notifyDataSetChanged();
    }
}
