package ir.fearefull.wheretoapp.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.fearefull.wheretoapp.Model.Place;
import ir.fearefull.wheretoapp.R;

public class PlaceHomeFragment extends Fragment {

    private Place place;
    private TextView bigTextTextView;

    public PlaceHomeFragment(){

    }

    @SuppressLint("ValidFragment")
    public PlaceHomeFragment(Place place){
        this.place = place;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_place_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        bigTextTextView = view.findViewById(R.id.bigTextTextView);
        bigTextTextView.setText(String.valueOf(bigTextTextView.getText() + place.getPlaceImages().get(0).getImage()));
    }

}
