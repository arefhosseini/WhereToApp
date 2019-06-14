package ir.fearefull.wheretoapp.controller.view_controller.place.home.place_image.adapter;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.view_controller.place.home.place_image.FullScreenPlaceImageFragment;
import ir.fearefull.wheretoapp.model.api.place.Place;
import ir.fearefull.wheretoapp.model.api.place.PlaceImage;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.utils.Constants;

public class GridViewPlaceImageAdapter extends BaseAdapter {

    private Fragment fragment;
    private Place place;
    private User user;
    private List<PlaceImage> placeImages;
    private int imageWidth;

    public GridViewPlaceImageAdapter(Fragment fragment, Place place, List<PlaceImage> placeImages,
                                     int imageWidth, User user) {
        this.fragment = fragment;
        this.place = place;
        this.placeImages = placeImages;
        this.imageWidth = imageWidth;
        this.user = user;
    }

    @Override
    public int getCount() {
        return placeImages.size();
    }

    @Override
    public Object getItem(int position) {
        return placeImages.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(fragment.getContext());
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setClickable(true);
        imageView.setFocusable(true);

        Picasso.get().load(Constants.BASE_URL + placeImages.get(position).getImage()).resize(imageWidth, imageWidth).into(imageView);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(imageWidth,
                imageWidth));

        Log.i("created", String.valueOf(position));
        // image view click listener
        imageView.setOnClickListener(new OnImageClickListener(position));

        return imageView;
    }

    class OnImageClickListener implements View.OnClickListener {

        private int position;

        // constructor
        OnImageClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            Objects.requireNonNull(Objects.requireNonNull(fragment.getActivity()).getSupportFragmentManager())
                    .beginTransaction()
                    .replace(R.id.fragmentGridViewPlaceImage,
                            new FullScreenPlaceImageFragment(place, position, user))
                    .addToBackStack(null)
                    .commit();
        }
    }
}