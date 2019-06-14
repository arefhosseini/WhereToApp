package ir.fearefull.wheretoapp.controller.view_controller.home.home;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.place.PlaceFragment;
import ir.fearefull.wheretoapp.model.api.place.PlaceSummary;
import ir.fearefull.wheretoapp.model.api.place.PlacesResponse;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.view.base.RecyclerTouchListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private User user;
    private View parentView;
    private PlacesSummaryAdapter totalPlacesAdapter, suggestedPlacesAdapter;
    private RecyclerView recyclerViewTotalPlaces, recyclerViewSuggestedPlaces;
    private List<PlaceSummary> totalPlacesList, suggestedPlacesList;

    private SwipeRefreshLayout swipeRefreshLayout;

    public HomeFragment(){
    }

    @SuppressLint("ValidFragment")
    public HomeFragment(User user){
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchData();
            }
        });

        parentView = view;
        totalPlacesList = new ArrayList<>();
        suggestedPlacesList = new ArrayList<>();

        recyclerViewTotalPlaces = view.findViewById(R.id.recyclerViewTotalPlaces);
        recyclerViewSuggestedPlaces = view.findViewById(R.id.recyclerViewSuggestedPlaces);
        totalPlacesAdapter = new PlacesSummaryAdapter(totalPlacesList);
        suggestedPlacesAdapter = new PlacesSummaryAdapter(suggestedPlacesList);
        RecyclerView.LayoutManager layoutManagerTotalPlaces = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManagerSuggestedPlaces = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewTotalPlaces.setLayoutManager(layoutManagerTotalPlaces);
        recyclerViewSuggestedPlaces.setLayoutManager(layoutManagerSuggestedPlaces);
        recyclerViewTotalPlaces.setAdapter(totalPlacesAdapter);
        recyclerViewSuggestedPlaces.setAdapter(suggestedPlacesAdapter);

        recyclerViewTotalPlaces.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerViewTotalPlaces, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PlaceSummary placeSummary = totalPlacesList.get(position);
                showPlaceFragment(placeSummary.getId());
            }

            @Override
            public void onLongClick(View view, final int position) {
            }
        }));

        recyclerViewSuggestedPlaces.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerViewSuggestedPlaces, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PlaceSummary placeSummary = suggestedPlacesList.get(position);
                showPlaceFragment(placeSummary.getId());
            }

            @Override
            public void onLongClick(View view, final int position) {
            }
        }));

        fetchData();
    }

    private void fetchData() {
        /*Create handle for the RetrofitInstance interface*/
        swipeRefreshLayout.setRefreshing(true);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<PlacesResponse> call = service.getPlaces(user.getPhoneNumber());
        call.enqueue(new Callback<PlacesResponse>() {
            @Override
            public void onResponse(Call<PlacesResponse> call, Response<PlacesResponse> response) {
                assert response.body() != null;
                generateDataList(response.body());
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<PlacesResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.e("places", t.getMessage());
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(PlacesResponse placesResponse) {
        this.totalPlacesList.clear();
        this.totalPlacesList.addAll(placesResponse.getTotalPlaces());
        this.suggestedPlacesList.clear();
        this.suggestedPlacesList.addAll(placesResponse.getSuggestedPlaces());

        totalPlacesAdapter.notifyDataSetChanged();
        suggestedPlacesAdapter.notifyDataSetChanged();

        swipeRefreshLayout.setRefreshing(false);
    }

    private void showPlaceFragment(long placeId) {
        Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager())
                .beginTransaction()
                .replace(R.id.fragmentHomeHome, new PlaceFragment(user, placeId))
                .addToBackStack(null)
                .commit();
    }
}
