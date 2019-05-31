package ir.fearefull.wheretoapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import ir.fearefull.wheretoapp.Adapter.FullScreenPlaceImageAdapter;
import ir.fearefull.wheretoapp.Model.Place;

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
