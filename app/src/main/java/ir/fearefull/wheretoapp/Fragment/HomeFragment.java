package ir.fearefull.wheretoapp.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.fearefull.wheretoapp.Adapter.PlacesSummaryAdapter;
import ir.fearefull.wheretoapp.MainActivity;
import ir.fearefull.wheretoapp.Model.PlaceSummary;
import ir.fearefull.wheretoapp.Network.GetDataService;
import ir.fearefull.wheretoapp.Network.RetrofitClientInstance;
import ir.fearefull.wheretoapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private View parentView;
    private PlacesSummaryAdapter placesSummaryAdapter;
    private RecyclerView recyclerViewPlaceSummary;

    public HomeFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        parentView = view;

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<PlaceSummary>> call = service.getAllPlaces();
        call.enqueue(new Callback<List<PlaceSummary>>() {
            @Override
            public void onResponse(Call<List<PlaceSummary>> call, Response<List<PlaceSummary>> response) {
                //progressDoalog.dismiss();
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<PlaceSummary>> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.e("places", t.getMessage());
            }
        });

    }

    /*Method to generate List of data using RecyclerView with custom adapter*/
    private void generateDataList(List<PlaceSummary> placeSummaryList) {
        Log.d("list", placeSummaryList.toString());
        recyclerViewPlaceSummary = parentView.findViewById(R.id.recyclerViewPlaceSummary);
        placesSummaryAdapter = new PlacesSummaryAdapter(placeSummaryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPlaceSummary.setLayoutManager(layoutManager);
        recyclerViewPlaceSummary.setAdapter(placesSummaryAdapter);
    }
}
