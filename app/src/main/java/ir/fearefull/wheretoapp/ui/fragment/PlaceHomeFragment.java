package ir.fearefull.wheretoapp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import ir.fearefull.wheretoapp.data.model.api.PlaceResponse;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.ui.GridViewPlaceImageActivity;
import ir.fearefull.wheretoapp.R;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class PlaceHomeFragment extends Fragment {

    private PlaceResponse placeResponse;
    private TextView placeAddressTextView, placePriceTextView, placeOpenHoursTextView,
            placeFeaturesTextView, placePhoneTextView, overallScoreTextView,
            foodScoreTextView, serviceScoreTextView, ambianceScoreTextView,
            showPlaceImagesTextView, showReviewTextView;
    private LinearLayout placePhonesLayout;
    private ImageView imagePlace1ImageView, imagePlace2ImageView, imagePlace3ImageView, imagePlace4ImageView, imagePlace5ImageView;
    private MaterialRatingBar placeOverallScoreRatingBar;
    private SeekBar oneScoreSeekBar, twoScoreSeekBar, threeScoreSeekBar, fourScoreSeekBar, fiveScoreSeekBar;
    private ImageButton addPlaceImageImageButtn, addScoreImageButton;

    public PlaceHomeFragment(){

    }

    @SuppressLint("ValidFragment")
    public PlaceHomeFragment(PlaceResponse placeResponse){
        this.placeResponse = placeResponse;
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
                        .target(new LatLng(placeResponse.getPlace().getCoordinatePlace().get(0).getLatitude(), placeResponse.getPlace().getCoordinatePlace().get(0).getLongitude()))
                        .zoom(14)
                        .bearing(0)
                        .tilt(10)
                        .build();

                googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(googlePlex));

                googleMap.addMarker(new MarkerOptions()
                        .position(new LatLng(placeResponse.getPlace().getCoordinatePlace().get(0).getLatitude(), placeResponse.getPlace().getCoordinatePlace().get(0).getLongitude()))
                        .title(placeResponse.getPlace().getName()));

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
        addPlaceImageImageButtn = view.findViewById(R.id.addPlaceImageImageButton);
        addScoreImageButton = view.findViewById(R.id.addScoreImageButton);

        placeAddressTextView.setText(placeResponse.getPlace().getAddress());
        placePriceTextView.setText(placeResponse.getPlace().getPrice());
        placeOpenHoursTextView.setText(placeResponse.getPlace().getOpenHours());
        placeFeaturesTextView.setText(placeResponse.getPlace().getFeatures());
        for (String phoneString: placeResponse.getPlace().getPhonesPlace()) {
            placePhoneTextView = (TextView) getLayoutInflater().inflate(R.layout.card_place_phone, placePhonesLayout, false);
            placePhoneTextView.setText(phoneString);
            placePhoneTextView.setOnClickListener(onClickPhoneListener);
            placePhonesLayout.addView(placePhoneTextView);
        }

        if (placeResponse.getPlace().getPlaceImages().size() >= 1)
            Picasso.get().load(Constants.BASE_URL + placeResponse.getPlace().getPlaceImages().get(0).getImage()).fit().into(imagePlace1ImageView);
        if (placeResponse.getPlace().getPlaceImages().size() >= 2)
            Picasso.get().load(Constants.BASE_URL + placeResponse.getPlace().getPlaceImages().get(1).getImage()).fit().into(imagePlace2ImageView);
        if (placeResponse.getPlace().getPlaceImages().size() >= 3)
            Picasso.get().load(Constants.BASE_URL + placeResponse.getPlace().getPlaceImages().get(2).getImage()).fit().into(imagePlace3ImageView);
        if (placeResponse.getPlace().getPlaceImages().size() >= 4)
            Picasso.get().load(Constants.BASE_URL + placeResponse.getPlace().getPlaceImages().get(3).getImage()).fit().into(imagePlace4ImageView);
        if (placeResponse.getPlace().getPlaceImages().size() >= 5)
            Picasso.get().load(Constants.BASE_URL + placeResponse.getPlace().getPlaceImages().get(4).getImage()).fit().into(imagePlace5ImageView);

        overallScoreTextView.setText(String.valueOf(placeResponse.getPlace().getOverallScore()));
        placeOverallScoreRatingBar.setRating(placeResponse.getPlace().getOverallScore());
        if (placeResponse.getPlace().getAllScoresCount() != 0) {
            oneScoreSeekBar.setProgress((100 * placeResponse.getPlace().getOneScoresCount() / placeResponse.getPlace().getAllScoresCount()));
            twoScoreSeekBar.setProgress((100 * placeResponse.getPlace().getTwoScoresCount() / placeResponse.getPlace().getAllScoresCount()));
            threeScoreSeekBar.setProgress((100 * placeResponse.getPlace().getThreeScoresCount() / placeResponse.getPlace().getAllScoresCount()));
            fourScoreSeekBar.setProgress((100 * placeResponse.getPlace().getFourScoresCount() / placeResponse.getPlace().getAllScoresCount()));
            fiveScoreSeekBar.setProgress((100 * placeResponse.getPlace().getFiveScoresCount() / placeResponse.getPlace().getAllScoresCount()));
        }
        foodScoreTextView.setText(String.valueOf(placeResponse.getPlace().getFoodScoreAverage()));
        serviceScoreTextView.setText(String.valueOf(placeResponse.getPlace().getServiceScoreAverage()));
        ambianceScoreTextView.setText(String.valueOf(placeResponse.getPlace().getAmbianceScoreAverage()));

        showPlaceImagesTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), GridViewPlaceImageActivity.class);
                i.putExtra("place", placeResponse.getPlace());
                startActivity(i);
            }
        });

        showReviewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // go to place reviews fragment
            }
        });

        addPlaceImageImageButtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add place image
            }
        });

        addScoreImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddScoreDialog addScoreDialog = new AddScoreDialog(getActivity());
                Objects.requireNonNull(addScoreDialog.getWindow()).setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                addScoreDialog.show();
            }
        });
    }

    private void onGoogleMapClick() {
        Uri gmmIntentUri = Uri.parse("geo:" + String.valueOf(placeResponse.getPlace().getCoordinatePlace().get(0).getLatitude()) +
                "," + String.valueOf(placeResponse.getPlace().getCoordinatePlace().get(0).getLongitude()) + "?q=" +
                String.valueOf(placeResponse.getPlace().getCoordinatePlace().get(0).getLatitude()) + "," +
                String.valueOf(placeResponse.getPlace().getCoordinatePlace().get(0).getLongitude()) + "(" + placeResponse.getPlace().getName() + ")");
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
