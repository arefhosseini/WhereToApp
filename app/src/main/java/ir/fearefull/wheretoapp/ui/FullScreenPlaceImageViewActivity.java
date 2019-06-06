package ir.fearefull.wheretoapp.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.ui.adapter.FullScreenPlaceImageAdapter;
import ir.fearefull.wheretoapp.data.model.api.Place;

public class FullScreenPlaceImageViewActivity extends Activity {

    private Place place;
    private int position;
    private FullScreenPlaceImageAdapter adapter;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_place_image_view);

        viewPager = findViewById(R.id.pager);

        Intent i = getIntent();
        position = i.getIntExtra("position", 0);
        place = (Place) i.getSerializableExtra("place");

        adapter = new FullScreenPlaceImageAdapter(FullScreenPlaceImageViewActivity.this,
                place, place.getPlaceImages());

        viewPager.setAdapter(adapter);

        // displaying selected image first
        viewPager.setCurrentItem(position);
    }
}
