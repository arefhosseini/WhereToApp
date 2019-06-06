package ir.fearefull.wheretoapp.ui.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.data.model.api.PlaceResponse;

public class PlaceMenuFragment extends Fragment {

    PlaceResponse placeResponse;

    public PlaceMenuFragment(){

    }

    @SuppressLint("ValidFragment")
    public PlaceMenuFragment(PlaceResponse placeResponse){
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
