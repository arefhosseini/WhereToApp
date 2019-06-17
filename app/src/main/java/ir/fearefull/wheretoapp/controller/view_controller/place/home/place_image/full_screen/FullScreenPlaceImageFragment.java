package ir.fearefull.wheretoapp.controller.view_controller.place.home.place_image.full_screen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.view_controller.base.MyFragment;
import ir.fearefull.wheretoapp.model.api.place.Place;
import ir.fearefull.wheretoapp.model.db.User;

public class FullScreenPlaceImageFragment extends MyFragment {

    private View parentView;
    private Place place;
    private int position;
    private User user;
    private FullScreenPlaceImageAdapter adapter;
    private ImageButton backImageButton;
    private ViewPager viewPager;

    public FullScreenPlaceImageFragment(){
    }

    public FullScreenPlaceImageFragment(String TAG, Place place, int position, User user){
        this.TAG = TAG;
        this.place = place;
        this.position = position;
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_fullscreen_place_image, container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        viewPager = parentView.findViewById(R.id.pager);
        backImageButton = parentView.findViewById(R.id.backImageButton);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getChildFragmentManager()
                        .beginTransaction()
                        .remove(FullScreenPlaceImageFragment.this)
                        .commit();
                getChildFragmentManager().popBackStack();
            }
        });

        adapter = new FullScreenPlaceImageAdapter(getContext(), place.getPlaceImages(), user);

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(position);
    }
}
