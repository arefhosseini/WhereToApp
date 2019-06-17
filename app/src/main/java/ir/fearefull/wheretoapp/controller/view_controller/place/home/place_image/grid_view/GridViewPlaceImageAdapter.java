package ir.fearefull.wheretoapp.controller.view_controller.place.home.place_image.grid_view;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.fearefull.wheretoapp.controller.view_controller.base.MyBaseAdapter;
import ir.fearefull.wheretoapp.model.api.place.PlaceImage;
import ir.fearefull.wheretoapp.utils.Constants;

public class GridViewPlaceImageAdapter extends MyBaseAdapter {

    private Context context;
    private GridViewPlaceImageAdapterCallBack callBack;
    private List<PlaceImage> placeImages;
    private int imageWidth;

    public GridViewPlaceImageAdapter(Context context, GridViewPlaceImageAdapterCallBack callBack,
                                     List<PlaceImage> placeImages, int imageWidth) {
        this.context = context;
        this.callBack = callBack;
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
            imageView = new ImageView(context);
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
            callBack.onOpenFullScreenFragment(position);
        }
    }
}