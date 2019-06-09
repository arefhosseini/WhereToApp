package ir.fearefull.wheretoapp.controller.view_controller.place.place_image.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.viewpager.widget.PagerAdapter;
import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.model.api.place.Place;
import ir.fearefull.wheretoapp.model.api.place.PlaceImage;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.view.base.TouchImageView;

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

        //Picasso.get().load(Constants.BASE_URL + placeImages.get(position).getImage()).into(touchImageView);
        if (placeImages.get(position).getImage().contains("media"))
            Picasso.get().load(Constants.BASE_URL + placeImages.get(position).getImage()).into(touchImageView);
        else
            Picasso.get().load(Constants.MEDIA_URL + placeImages.get(position).getImage()).into(touchImageView);

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