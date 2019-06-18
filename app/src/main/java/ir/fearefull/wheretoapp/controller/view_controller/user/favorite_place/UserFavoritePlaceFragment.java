package ir.fearefull.wheretoapp.controller.view_controller.user.favorite_place;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.base.MyFragment;
import ir.fearefull.wheretoapp.controller.view_controller.place.PlaceFragment;
import ir.fearefull.wheretoapp.model.api.user.UserResponse;
import ir.fearefull.wheretoapp.model.api.user.favorite_place.UserFavoritePlaceResponse;
import ir.fearefull.wheretoapp.model.api.user.favorite_place.UserFavoritePlacesResponse;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.view.base.RecyclerTouchListener;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFavoritePlaceFragment extends MyFragment {

    private User user;
    private UserResponse userResponse;
    private List<UserFavoritePlaceResponse> userFavoritePlaceResponseList;

    private View parentView;
    private UserFavoritePlacesAdapter userFavoritePlacesAdapter;
    private RecyclerView recyclerViewUserFavoritePlaces;
    private ImageButton backImageButton;

    public UserFavoritePlaceFragment(){
    }

    public UserFavoritePlaceFragment(String TAG, User user, UserResponse userResponse){
        this.TAG = TAG;
        this.userResponse = userResponse;
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_user_favorite_place, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        parentView = view;
        userFavoritePlaceResponseList = new ArrayList<>();
        backImageButton = parentView.findViewById(R.id.backImageButton);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<UserFavoritePlacesResponse> call = service.getUserFavoritePlaces(user.getPhoneNumber(), userResponse.getPhoneNumber());
        call.enqueue(new Callback<UserFavoritePlacesResponse>() {
            @Override
            public void onResponse(Call<UserFavoritePlacesResponse> call, Response<UserFavoritePlacesResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<UserFavoritePlacesResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.e("places", t.getMessage());
            }
        });
    }

    private void generateDataList(UserFavoritePlacesResponse userFavoritePlacesResponse) {
        this.userFavoritePlaceResponseList.addAll(userFavoritePlacesResponse.getFavoritePlaces());
        recyclerViewUserFavoritePlaces = parentView.findViewById(R.id.recyclerViewUserFavoritePlaces);
        userFavoritePlacesAdapter = new UserFavoritePlacesAdapter(this.userFavoritePlaceResponseList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(),
                RecyclerView.VERTICAL, false);
        recyclerViewUserFavoritePlaces.setLayoutManager(layoutManager);
        recyclerViewUserFavoritePlaces.setAdapter(userFavoritePlacesAdapter);

        recyclerViewUserFavoritePlaces.addOnItemTouchListener(new RecyclerTouchListener(getContext(),
                recyclerViewUserFavoritePlaces, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                UserFavoritePlaceResponse userFavoritePlaceResponse = userFavoritePlaceResponseList.get(position);

                showPlaceFragment(userFavoritePlaceResponse.getId());
            }

            @Override
            public void onLongClick(View view, final int position) {
            }
        }));
    }

    private void showPlaceFragment(long placeId) {
        PlaceFragment fragment = new PlaceFragment(TAG, user, placeId);
        openFragment(fragment, R.id.fragmentUserFavoritePlace);
    }
}
