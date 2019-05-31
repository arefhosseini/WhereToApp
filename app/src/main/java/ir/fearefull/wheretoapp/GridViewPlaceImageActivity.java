package ir.fearefull.wheretoapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.widget.GridView;

import java.util.ArrayList;

import ir.fearefull.wheretoapp.Adapter.GridViewPlaceImageAdapter;
import ir.fearefull.wheretoapp.Model.Place;
import ir.fearefull.wheretoapp.Model.PlaceImage;

public class GridViewPlaceImageActivity extends Activity {

    private Utils utils;
    private GridViewPlaceImageAdapter adapter;
    private Place place;
    private GridView gridView;
    private int columnWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grid_view_place_image);

        Intent i = getIntent();
        place = (Place) i.getSerializableExtra("place");

        gridView = findViewById(R.id.grid_view);

        utils = new Utils(this);

        // Initializing Grid View
        InitializeGridLayout();

        // GridView adapter
        adapter = new GridViewPlaceImageAdapter(GridViewPlaceImageActivity.this, place, place.getPlaceImages(),
                columnWidth);

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
}
