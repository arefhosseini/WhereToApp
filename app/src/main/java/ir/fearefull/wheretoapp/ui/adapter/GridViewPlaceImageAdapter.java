package ir.fearefull.wheretoapp.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.ui.FullScreenPlaceImageViewActivity;
import ir.fearefull.wheretoapp.data.model.api.Place;
import ir.fearefull.wheretoapp.data.model.api.PlaceImage;

public class GridViewPlaceImageAdapter extends BaseAdapter {

    private Activity activity;
    private Place place;
    private List<PlaceImage> placeImages;
    private int imageWidth;

    public GridViewPlaceImageAdapter(Activity activity, Place place, List<PlaceImage> placeImages,
                                     int imageWidth) {
        this.activity = activity;
        this.place = place;
        this.placeImages = placeImages;
        this.imageWidth = imageWidth;
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
            imageView = new ImageView(activity);
        } else {
            imageView = (ImageView) convertView;
        }
        imageView.setClickable(true);
        imageView.setFocusable(true);

        // get screen dimensions
        Picasso
                .get()
                .load(Constants.BASE_URL + placeImages.get(position).getImage())
                .resize(imageWidth, imageWidth)
                .into(imageView);

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
            // on selecting grid view image
            // launch full screen activity
            Intent i = new Intent(activity, FullScreenPlaceImageViewActivity.class);
            i.putExtra("position", position);
            i.putExtra("place", place);
            activity.startActivity(i);
        }
    }
}