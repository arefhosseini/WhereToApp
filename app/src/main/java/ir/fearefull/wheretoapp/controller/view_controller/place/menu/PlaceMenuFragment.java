package ir.fearefull.wheretoapp.controller.view_controller.place.menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.model.api.place.PlaceResponse;
import ir.fearefull.wheretoapp.model.api.place.menu.Food;
import ir.fearefull.wheretoapp.model.api.place.menu.FoodResponse;
import ir.fearefull.wheretoapp.model.api.place.menu.Menu;
import ir.fearefull.wheretoapp.model.api.place.menu.MenuResponse;
import ir.fearefull.wheretoapp.model.api.place.menu.PlaceMenuResponse;
import ir.fearefull.wheretoapp.model.db.User;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceMenuFragment extends Fragment {

    private User user;
    private PlaceResponse placeResponse;
    private View parentView;
    private MenusAdapter menusAdapter;
    private RecyclerView recyclerViewPlaceMenu;

    public PlaceMenuFragment(){
    }

    @SuppressLint("ValidFragment")
    public PlaceMenuFragment(User user, PlaceResponse placeResponse){
        this.user = user;
        this.placeResponse = placeResponse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_place_menu, container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        /*Create handle for the RetrofitInstance interface*/
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<PlaceMenuResponse> call = service.getPlaceMenus(user.getPhoneNumber(), placeResponse.getPlace().getId());
        call.enqueue(new Callback<PlaceMenuResponse>() {
            @Override
            public void onResponse(Call<PlaceMenuResponse> call, Response<PlaceMenuResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<PlaceMenuResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                Log.e("places", t.getMessage());
            }
        });
    }

    private void generateDataList(PlaceMenuResponse placeMenuResponse) {
        recyclerViewPlaceMenu = parentView.findViewById(R.id.recyclerViewPlaceMenu);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());


        RecyclerView.ItemAnimator animator = recyclerViewPlaceMenu.getItemAnimator();
        if (animator instanceof DefaultItemAnimator) {
            ((DefaultItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        List<Menu> menuList = new ArrayList<>();
        for (MenuResponse menuResponse: placeMenuResponse.getMenuResponses()) {
            List<Food> foodList = new ArrayList<>();
            for (FoodResponse foodResponse: menuResponse.getFoodResponses()) {
                Food food = new Food(foodResponse.getName(), foodResponse.getDetail(),
                        foodResponse.getPrice());
                foodList.add(food);
            }
            Menu menu = new Menu(menuResponse.getName(), foodList);
            menuList.add(menu);
        }

        menusAdapter = new MenusAdapter(menuList, this);
        recyclerViewPlaceMenu.setLayoutManager(layoutManager);
        recyclerViewPlaceMenu.setAdapter(menusAdapter);
    }

}
