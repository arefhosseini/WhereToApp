package ir.fearefull.wheretoapp.controller.view_controller.place.home.place_image.grid_view;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.view_controller.base.MyFragment;
import ir.fearefull.wheretoapp.controller.view_controller.place.home.place_image.full_screen.FullScreenPlaceImageFragment;
import ir.fearefull.wheretoapp.model.api.place.Place;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.utils.Utils;

public class GridViewPlaceImageFragment extends MyFragment implements GridViewPlaceImageAdapterCallBack {

    private View parentView;
    private Utils utils;
    private User user;
    private GridViewPlaceImageAdapter adapter;
    private ImageButton backImageButton;
    private Place place;
    private GridView gridView;
    private int columnWidth;

    public GridViewPlaceImageFragment(){
    }

    public GridViewPlaceImageFragment(String TAG, Place place, User user){
        this.TAG = TAG;
        this.place = place;
        this.user = user;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_grid_view_place_image, container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        gridView = parentView.findViewById(R.id.grid_view);
        backImageButton = parentView.findViewById(R.id.backImageButton);

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getChildFragmentManager()
                        .beginTransaction()
                        .remove(GridViewPlaceImageFragment.this)
                        .commit();
                getChildFragmentManager().popBackStack();
            }
        });

        utils = new Utils(getContext());

        // Initializing Grid View
        InitializeGridLayout();

        // GridView adapter
        adapter = new GridViewPlaceImageAdapter(getContext(), this,
                place.getPlaceImages(), columnWidth);

        // setting grid view adapter
        gridView.setAdapter(adapter);
    }

    private void InitializeGridLayout() {
        Resources r = getResources();
        float padding = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                Constants.GRID_PADDING_PLACE_IMAGE, r.getDisplayMetrics());

        columnWidth = (int) ((utils.getScreenWidth() - ((Constants.NUM_OF_COLUMNS_PLACE_IMAGE + 1) * padding)) / Constants.NUM_OF_COLUMNS_PLACE_IMAGE);

        gridView.setNumColumns(Constants.NUM_OF_COLUMNS_PLACE_IMAGE);
        gridView.setColumnWidth(columnWidth);
        gridView.setStretchMode(GridView.NO_STRETCH);
        gridView.setPadding((int) padding, (int) padding, (int) padding,
                (int) padding);
        gridView.setHorizontalSpacing((int) padding);
        gridView.setVerticalSpacing((int) padding);
    }

    @Override
    public void onOpenFullScreenFragment(int position) {
        FullScreenPlaceImageFragment fragment = new FullScreenPlaceImageFragment(TAG, place,
                position, user);
        openFragment(fragment, R.id.fragmentGridViewPlaceImage);
    }
}
