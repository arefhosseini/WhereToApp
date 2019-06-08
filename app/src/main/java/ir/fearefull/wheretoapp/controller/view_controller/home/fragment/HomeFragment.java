package ir.fearefull.wheretoapp.controller.view_controller.home.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.home.adapter.PlacesSummaryAdapter;
import ir.fearefull.wheretoapp.controller.view_controller.place.PlaceActivity;
import ir.fearefull.wheretoapp.model.api.place.PlaceSummary;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.view.base.RecyclerTouchListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    private User user;
    private View parentView;
    private PlacesSummaryAdapter placesSummaryAdapter;
    private RecyclerView recyclerViewPlaceSummary;
    private List<PlaceSummary> placeSummaryList;

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

        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        parentView = view;
        placeSummaryList = new ArrayList<>();

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<List<PlaceSummary>> call = service.getAllPlaces(user.getPhoneNumber());
        call.enqueue(new Callback<List<PlaceSummary>>() {
            @Override
            public void onResponse(Call<List<PlaceSummary>> call, Response<List<PlaceSummary>> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
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
    private void generateDataList(List<PlaceSummary> placeSumList) {
        Log.d("list", placeSummaryList.toString());
        this.placeSummaryList = placeSumList;
        recyclerViewPlaceSummary = parentView.findViewById(R.id.recyclerViewPlaceSummary);
        placesSummaryAdapter = new PlacesSummaryAdapter(placeSummaryList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPlaceSummary.setLayoutManager(layoutManager);
        recyclerViewPlaceSummary.setAdapter(placesSummaryAdapter);

        recyclerViewPlaceSummary.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerViewPlaceSummary, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                PlaceSummary placeSummary = placeSummaryList.get(position);

                Intent myIntent = new Intent(getActivity(), PlaceActivity.class);
                myIntent.putExtra("id",placeSummary.getId());
                startActivity(myIntent);
            }

            @Override
            public void onLongClick(View view, final int position) {
            }
        }));
    }
}
