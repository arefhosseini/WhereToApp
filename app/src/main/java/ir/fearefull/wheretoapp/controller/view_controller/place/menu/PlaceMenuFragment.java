package ir.fearefull.wheretoapp.controller.view_controller.place.menu;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.model.api.place.PlaceResponse;
import ir.fearefull.wheretoapp.model.db.User;

public class PlaceMenuFragment extends Fragment {

    User user;
    PlaceResponse placeResponse;

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

        return inflater.inflate(R.layout.fragment_place_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }

}
