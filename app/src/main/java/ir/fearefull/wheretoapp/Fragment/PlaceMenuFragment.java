package ir.fearefull.wheretoapp.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.fearefull.wheretoapp.Model.Place;
import ir.fearefull.wheretoapp.R;

public class PlaceMenuFragment extends Fragment {

    Place place;

    public PlaceMenuFragment(){

    }

    @SuppressLint("ValidFragment")
    public PlaceMenuFragment(Place place){
        this.place = place;
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
