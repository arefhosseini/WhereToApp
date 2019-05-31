package ir.fearefull.wheretoapp.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import ir.fearefull.wheretoapp.Constants;
import ir.fearefull.wheretoapp.Model.Place;
import ir.fearefull.wheretoapp.Model.PlaceImage;
import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.Widget.TouchImageView;

public class FullScreenPlaceImageAdapter extends PagerAdapter {

    private Activity activity;
    private List<PlaceImage> placeImages;
    private LayoutInflater inflater;

    // constructor
    public FullScreenPlaceImageAdapter(Activity activity, Place place, List<PlaceImage> placeImages) {
        this.activity = activity;
        this.placeImages = placeImages;
    }

    @Override
    public int getCount() {
        return this.placeImages.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView touchImageView;
        Button btnClose;

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = inflater.inflate(R.layout.layout_fullscreen_place_image, container,
                false);

        touchImageView = viewLayout.findViewById(R.id.imgDisplay);
        btnClose = viewLayout.findViewById(R.id.btnClose);

        Picasso
                .get()
                .load(Constants.BASE_URL + placeImages.get(position).getImage())
                .into(touchImageView);

        // close button click event
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.finish();
            }
        });

        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);

    }
}