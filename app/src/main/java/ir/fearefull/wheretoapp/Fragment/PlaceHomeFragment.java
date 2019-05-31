package ir.fearefull.wheretoapp.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import ir.fearefull.wheretoapp.Constants;
import ir.fearefull.wheretoapp.GridViewPlaceImageActivity;
import ir.fearefull.wheretoapp.Model.Place;
import ir.fearefull.wheretoapp.R;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class PlaceHomeFragment extends Fragment {

    private Place place;
    private TextView placeAddressTextView, placePriceTextView, placeOpenHoursTextView,
            placeFeaturesTextView, placePhoneTextView, overallScoreTextView,
            foodScoreTextView, serviceScoreTextView, ambianceScoreTextView,
            showPlaceImagesTextView, showReviewTextView;
    private LinearLayout placePhonesLayout;
    private ImageView imagePlace1ImageView, imagePlace2ImageView, imagePlace3ImageView, imagePlace4ImageView, imagePlace5ImageView;
    private MaterialRatingBar placeOverallScoreRatingBar;
    private SeekBar oneScoreSeekBar, twoScoreSeekBar, threeScoreSeekBar, fourScoreSeekBar, fiveScoreSeekBar;

    public PlaceHomeFragment(){

    }

    @SuppressLint("ValidFragment")
    public PlaceHomeFragment(Place place){
        this.place = place;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_place_home, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frg);  //use SuppoprtMapFragment for using in fragment instead of activity  MapFragment = activity   SupportMapFragment = fragment
        Objects.requireNonNull(mapFragment).getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                googleMap.clear(); //clear old markers
                googleMap.getUiSettings().setCompassEnabled(false);
                googleMap.getUiSettings().setScrollGesturesEnabled(false);
                googleMap.getUiSettings().setMapToolbarEnabled(false);
                googleMap.getUiSettings().setZoomControlsEnabled(false);

                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(place.getCoordinatePlace().get(0).getLatitude(), place.getCoordinatePlace().get(0).getLongitude()))
                        .zoom(14)
                        .bearing(0)
                        .tilt(10)
                        .build();

                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(place.getCoordinatePlace().get(0).getLatitude(), place.getCoordinatePlace().get(0).getLongitude()))
                        .title(place.getName()));

                googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        onGoogleMapClick();
                        return false;
                    }
                });

                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener()
                {
                    @Override
                    public void onMapClick(LatLng arg0)
                    {
                        onGoogleMapClick();
                    }
                });
            }
        });

        return view;
    }

    @SuppressLint("InflateParams")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        placeAddressTextView = view.findViewById(R.id.placeAddressTextView);
        placePriceTextView = view.findViewById(R.id.placePriceTextView);
        placeOpenHoursTextView = view.findViewById(R.id.placeOpenHoursTextView);
        placeFeaturesTextView = view.findViewById(R.id.placeFeaturesTextView);
        placePhonesLayout = view.findViewById(R.id.placePhonesLayout);
        imagePlace1ImageView = view.findViewById(R.id.imagePlace1ImageView);
        imagePlace2ImageView = view.findViewById(R.id.imagePlace2ImageView);
        imagePlace3ImageView = view.findViewById(R.id.imagePlace3ImageView);
        imagePlace4ImageView = view.findViewById(R.id.imagePlace4ImageView);
        imagePlace5ImageView = view.findViewById(R.id.imagePlace5ImageView);
        overallScoreTextView = view.findViewById(R.id.overallScoreTextView);
        placeOverallScoreRatingBar = view.findViewById(R.id.placeOverallScoreRatingBar);
        oneScoreSeekBar = view.findViewById(R.id.oneScoreSeekBar);
        twoScoreSeekBar = view.findViewById(R.id.twoScoreSeekBar);
        threeScoreSeekBar = view.findViewById(R.id.threeScoreSeekBar);
        fourScoreSeekBar = view.findViewById(R.id.fourScoreSeekBar);
        fiveScoreSeekBar = view.findViewById(R.id.fiveScoreSeekBar);
        foodScoreTextView = view.findViewById(R.id.foodScoreTextView);
        serviceScoreTextView = view.findViewById(R.id.serviceScoreTextView);
        ambianceScoreTextView = view.findViewById(R.id.ambianceScoreTextView);
        showPlaceImagesTextView = view.findViewById(R.id.showPlaceImagesTextView);
        showReviewTextView = view.findViewById(R.id.showReviewTextView);

        placeAddressTextView.setText(place.getAddress());
        placePriceTextView.setText(place.getPrice());
        placeOpenHoursTextView.setText(place.getOpenHours());
        placeFeaturesTextView.setText(place.getFeatures());
        for (String phoneString: place.getPhonesPlace()) {
            placePhoneTextView = (TextView) getLayoutInflater().inflate(R.layout.card_place_phone, placePhonesLayout, false);
            placePhoneTextView.setText(phoneString);
            placePhoneTextView.setOnClickListener(onClickPhoneListener);
            placePhonesLayout.addView(placePhoneTextView);
        }

        if (place.getPlaceImages().size() >= 1)
            Picasso.get().load(Constants.BASE_URL + place.getPlaceImages().get(0).getImage()).fit().into(imagePlace1ImageView);
        if (place.getPlaceImages().size() >= 2)
            Picasso.get().load(Constants.BASE_URL + place.getPlaceImages().get(1).getImage()).fit().into(imagePlace2ImageView);
        if (place.getPlaceImages().size() >= 3)
            Picasso.get().load(Constants.BASE_URL + place.getPlaceImages().get(2).getImage()).fit().into(imagePlace3ImageView);
        if (place.getPlaceImages().size() >= 4)
            Picasso.get().load(Constants.BASE_URL + place.getPlaceImages().get(3).getImage()).fit().into(imagePlace4ImageView);
        if (place.getPlaceImages().size() >= 5)
            Picasso.get().load(Constants.BASE_URL + place.getPlaceImages().get(4).getImage()).fit().into(imagePlace5ImageView);

        overallScoreTextView.setText(String.valueOf(place.getOverallScore()));
        placeOverallScoreRatingBar.setRating(place.getOverallScore());
        if (place.getAllScoresCount() != 0) {
            oneScoreSeekBar.setProgress((100 * place.getOneScoresCount() / place.getAllScoresCount()));
            twoScoreSeekBar.setProgress((100 * place.getTwoScoresCount() / place.getAllScoresCount()));
            threeScoreSeekBar.setProgress((100 * place.getThreeScoresCount() / place.getAllScoresCount()));
            fourScoreSeekBar.setProgress((100 * place.getFourScoresCount() / place.getAllScoresCount()));
            fiveScoreSeekBar.setProgress((100 * place.getFiveScoresCount() / place.getAllScoresCount()));
        }
        foodScoreTextView.setText(String.valueOf(place.getFoodScoreAverage()));
        serviceScoreTextView.setText(String.valueOf(place.getServiceScoreAverage()));
        ambianceScoreTextView.setText(String.valueOf(place.getAmbianceScoreAverage()));

        showPlaceImagesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), GridViewPlaceImageActivity.class);
                i.putExtra("place", place);
                startActivity(i);
            }
        });

        showReviewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to place reviews fragment
            }
        });
    }

    private void onGoogleMapClick() {
        Uri gmmIntentUri = Uri.parse("geo:" + String.valueOf(place.getCoordinatePlace().get(0).getLatitude()) +
                "," + String.valueOf(place.getCoordinatePlace().get(0).getLongitude()) + "?q=" +
                String.valueOf(place.getCoordinatePlace().get(0).getLatitude()) + "," +
                String.valueOf(place.getCoordinatePlace().get(0).getLongitude()) + "(" + place.getName() + ")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    TextView.OnClickListener onClickPhoneListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", String.valueOf(((TextView) view).getText()), null));
            startActivity(intent);
        }
    };
}
