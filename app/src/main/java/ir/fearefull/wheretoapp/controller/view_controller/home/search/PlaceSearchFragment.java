package ir.fearefull.wheretoapp.controller.view_controller.home.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.view_controller.home.search.adapter.PlaceSearchAdapter;
import ir.fearefull.wheretoapp.controller.view_controller.place.PlaceFragment;
import ir.fearefull.wheretoapp.model.api.search.PlaceSearchResponse;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.view.base.RecyclerTouchListener;

public class PlaceSearchFragment extends Fragment {

    private SearchFragment searchFragment;
    private User user;
    private List<PlaceSearchResponse> placeSearchResponseList;

    private View parentView;
    private PlaceSearchAdapter placeSearchAdapter;
    private RecyclerView recyclerViewPlaceSearch;

    public PlaceSearchFragment(){
        placeSearchResponseList = new ArrayList<>();
    }

    PlaceSearchFragment(SearchFragment searchFragment, User user){
        placeSearchResponseList = new ArrayList<>();
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

        return inflater.inflate(R.layout.fragment_search_place, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        parentView = view;
        recyclerViewPlaceSearch = parentView.findViewById(R.id.recyclerViewPlaceSearch);
        placeSearchAdapter = new PlaceSearchAdapter(placeSearchResponseList);
        recyclerViewPlaceSearch.setLayoutManager(new GridLayoutManager(getContext(), 2));

        recyclerViewPlaceSearch.setAdapter(placeSearchAdapter);


        recyclerViewPlaceSearch.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                recyclerViewPlaceSearch, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PlaceSearchResponse placeSearchResponse = placeSearchResponseList.get(position);

                showPlaceFragment(placeSearchResponse.getId());
            }

            @Override
            public void onLongClick(View view, final int position) {
            }
        }));
    }

    void setData(List<PlaceSearchResponse> placeSearchResponseList) {
        this.placeSearchResponseList.clear();
        this.placeSearchResponseList.addAll(placeSearchResponseList);

        placeSearchAdapter.notifyDataSetChanged();
    }

    private void showPlaceFragment(long placeId) {
        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager())
                .beginTransaction()
                .replace(R.id.fragmentHomeSearch, new PlaceFragment(user, placeId))
                .addToBackStack(null)
                .commit();
    }
}
