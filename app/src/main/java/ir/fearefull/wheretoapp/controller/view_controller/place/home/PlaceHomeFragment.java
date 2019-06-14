package ir.fearefull.wheretoapp.controller.view_controller.place.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;

import java.util.Objects;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.place.PlaceFragment;
import ir.fearefull.wheretoapp.controller.view_controller.place.dialog.CreateReviewDialog;
import ir.fearefull.wheretoapp.controller.view_controller.place.dialog.CreateScoreDialog;
import ir.fearefull.wheretoapp.controller.view_controller.place.home.place_image.GridViewPlaceImageFragment;
import ir.fearefull.wheretoapp.controller.view_controller.place.review.PlaceReviewFragment;
import ir.fearefull.wheretoapp.model.api.SimpleResponse;
import ir.fearefull.wheretoapp.model.api.place.PlaceResponse;
import ir.fearefull.wheretoapp.model.api.place.control.UploadPlaceImageRequest;
import ir.fearefull.wheretoapp.model.api.review.CreateReviewRequest;
import ir.fearefull.wheretoapp.model.api.score.CreatePlaceScoreRequest;
import ir.fearefull.wheretoapp.model.api.score.PlaceScore;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.utils.Constants;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.checkSelfPermission;
import static ir.fearefull.wheretoapp.utils.Constants.CREATE_REVIEW_DIALOG;
import static ir.fearefull.wheretoapp.utils.Constants.CREATE_SCORE_DIALOG;
import static ir.fearefull.wheretoapp.utils.Constants.PICK_FROM_GALLERY;

public class PlaceHomeFragment extends Fragment {

    private User user;
    private PlaceResponse placeResponse;
    private PlaceReviewFragment placeReviewFragment;
    private PlaceFragment placeFragment;
    private TextView placeAddressTextView, placePriceTextView, placeOpenHoursTextView,
            placeFeaturesTextView, placePhoneTextView, overallScoreTextView,
            foodScoreTextView, serviceScoreTextView, ambianceScoreTextView,
            showPlaceImagesTextView, showReviewTextView, yourOverallScoreTextView;
    private LinearLayout placePhonesLayout, yourOverallScoreLayout;
    private ImageView imagePlace1ImageView, imagePlace2ImageView, imagePlace3ImageView, imagePlace4ImageView, imagePlace5ImageView;
    private MaterialRatingBar placeOverallScoreRatingBar, yourOverallScoreRatingBar;
    private SeekBar oneScoreSeekBar, twoScoreSeekBar, threeScoreSeekBar, fourScoreSeekBar, fiveScoreSeekBar;
    private ImageButton addPlaceImageImageButton;

    public PlaceHomeFragment(){

    }

    @SuppressLint("ValidFragment")
    public PlaceHomeFragment(User user, PlaceResponse placeResponse){
        this.user = user;
        this.placeResponse = placeResponse;
    }

    public void setFragments(PlaceFragment placeFragment,
                             PlaceReviewFragment placeReviewFragment) {
        this.placeFragment = placeFragment;
        this.placeReviewFragment = placeReviewFragment;
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

    @SuppressLint({"InflateParams", "ClickableViewAccessibility"})
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
        yourOverallScoreTextView = view.findViewById(R.id.yourOverallScoreTextView);
        foodScoreTextView = view.findViewById(R.id.foodScoreTextView);
        serviceScoreTextView = view.findViewById(R.id.serviceScoreTextView);
        ambianceScoreTextView = view.findViewById(R.id.ambianceScoreTextView);
        showPlaceImagesTextView = view.findViewById(R.id.showPlaceImagesTextView);
        showReviewTextView = view.findViewById(R.id.showReviewTextView);
        addPlaceImageImageButton = view.findViewById(R.id.addPlaceImageImageButton);
        yourOverallScoreLayout = view.findViewById(R.id.yourOverallScoreLayout);
        yourOverallScoreRatingBar = view.findViewById(R.id.yourOverallScoreRatingBar);

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

        oneScoreSeekBar.setOnTouchListener(disabledOnTouchListener);
        twoScoreSeekBar.setOnTouchListener(disabledOnTouchListener);
        threeScoreSeekBar.setOnTouchListener(disabledOnTouchListener);
        fourScoreSeekBar.setOnTouchListener(disabledOnTouchListener);
        fiveScoreSeekBar.setOnTouchListener(disabledOnTouchListener);

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
        yourOverallScoreRatingBar.setRating(placeResponse.getPlaceScore().getTotalScore());
        if (placeResponse.getPlaceScore().getTotalScore() > 0)
            yourOverallScoreTextView.setText(R.string.your_ratings);
        else
            yourOverallScoreTextView.setText(R.string.submit_your_ratings);

        showPlaceImagesTextView.setOnClickListener(onShowPlaceImages);
        imagePlace1ImageView.setOnClickListener(onShowPlaceImages);
        imagePlace2ImageView.setOnClickListener(onShowPlaceImages);
        imagePlace3ImageView.setOnClickListener(onShowPlaceImages);
        imagePlace4ImageView.setOnClickListener(onShowPlaceImages);
        imagePlace5ImageView.setOnClickListener(onShowPlaceImages);

        showReviewTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeFragment.showReviewFragmentListener();
            }
        });

        addPlaceImageImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (checkSelfPermission(Objects.requireNonNull(getContext()), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {
                        CropImage.activity()
                                .setFixAspectRatio(true)
                                .setAspectRatio(1, 1)
                                .setRequestedSize(500, 500, CropImageView.RequestSizeOptions.RESIZE_INSIDE)
                                .start(getContext(), PlaceHomeFragment.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        yourOverallScoreLayout.setOnClickListener(createScoreClickListener);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CropImage.activity()
                            .setFixAspectRatio(true)
                            .setAspectRatio(1, 1)
                            .setRequestedSize(500, 500, CropImageView.RequestSizeOptions.RESIZE_INSIDE)
                            .start(Objects.requireNonNull(getActivity()));
                } else {
                    Toast.makeText(getContext(), "لطفا دسترسی به گالری را بدهید", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE:
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if (resultCode == RESULT_OK) {
                    Uri resultUri = result.getUri();
                    uploadImage(resultUri);
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    Exception error = result.getError();
                }
                break;
            case CREATE_SCORE_DIALOG:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    int overallScore = Objects.requireNonNull(bundle).getInt("overallScore");
                    int foodScore = Objects.requireNonNull(bundle).getInt("foodScore");
                    int serviceScore = Objects.requireNonNull(bundle).getInt("serviceScore");
                    int ambianceScore = Objects.requireNonNull(bundle).getInt("ambianceScore");
                    placeResponse.getPlaceScore().setTotalScore(overallScore);
                    placeReviewFragment.changeFragment(placeResponse);
                    changeFragment(placeResponse);
                    try {
                        createPlaceScore(new CreatePlaceScoreRequest(user.getPhoneNumber(),
                                placeResponse.getPlace().getId(), overallScore, foodScore,
                                serviceScore, ambianceScore));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    Log.d("addScoreDialog", "canceled");
                    showCreateReviewDialog();
                }
                break;
            case CREATE_REVIEW_DIALOG:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String reviewText = Objects.requireNonNull(bundle).getString("reviewText");
                    try {
                        createReview(new CreateReviewRequest(user.getPhoneNumber(),
                                placeResponse.getPlace().getId(), reviewText));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void uploadImage(Uri resultUri) {
        UploadPlaceImageRequest uploadPlaceImageRequest = new UploadPlaceImageRequest(
                user.getPhoneNumber(), placeResponse.getPlace().getId(),
                resultUri, getContext());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SimpleResponse> call = service.uploadPlaceImage(
                uploadPlaceImageRequest.getUser(),
                uploadPlaceImageRequest.getPlace(),
                uploadPlaceImageRequest.getImage());
        Log.v("Upload", "uploading");
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call,
                                   Response<SimpleResponse> response) {
                Log.v("Upload", "success");
                Toast.makeText(getContext(), "عکس شما آپلود شد", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                Toast.makeText(getContext(), "عکس شما آپلود نشد", Toast.LENGTH_SHORT).show();
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    private void onGoogleMapClick() {
        Uri gmmIntentUri = Uri.parse("geo:" + placeResponse.getPlace().getCoordinatePlace().get(0).getLatitude() +
                "," + placeResponse.getPlace().getCoordinatePlace().get(0).getLongitude() + "?q=" +
                placeResponse.getPlace().getCoordinatePlace().get(0).getLatitude() + "," +
                placeResponse.getPlace().getCoordinatePlace().get(0).getLongitude() + "(" + placeResponse.getPlace().getName() + ")");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");
        if (mapIntent.resolveActivity(Objects.requireNonNull(getActivity()).getPackageManager()) != null) {
            startActivity(mapIntent);
        }
    }

    private TextView.OnClickListener onClickPhoneListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", String.valueOf(((TextView) view).getText()), null));
            startActivity(intent);
        }
    };

    private View.OnClickListener createScoreClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            CreateScoreDialog createScoreDialog = new CreateScoreDialog(placeResponse.getPlaceScore());
            createScoreDialog.setTargetFragment(PlaceHomeFragment.this, CREATE_SCORE_DIALOG);
            createScoreDialog.show(Objects.requireNonNull(getFragmentManager()).beginTransaction(), "MyProgressDialog");
        }
    };

    private View.OnClickListener onShowPlaceImages = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Objects.requireNonNull(Objects.requireNonNull(getActivity()).getSupportFragmentManager())
                    .beginTransaction()
                    .replace(R.id.fragmentPlace,
                            new GridViewPlaceImageFragment(placeResponse.getPlace(), user))
                    .addToBackStack(null)
                    .commit();
        }
    };

    private void showCreateReviewDialog() {
        CreateReviewDialog createReviewDialog = new CreateReviewDialog();
        createReviewDialog.setTargetFragment(PlaceHomeFragment.this, CREATE_REVIEW_DIALOG);
        createReviewDialog.show(Objects.requireNonNull(getFragmentManager()).beginTransaction(), "MyProgressDialog");
    }

    private void createPlaceScore(final CreatePlaceScoreRequest createPlaceScoreRequest) throws JSONException {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SimpleResponse> call = service.createScore(createPlaceScoreRequest.toRequestBody());
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateCreateScoreData(createPlaceScoreRequest);
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createReview(CreateReviewRequest createReviewRequest) throws JSONException {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SimpleResponse> call = service.createReview(createReviewRequest.toRequestBody());
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateCreateReviewData();
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateCreateScoreData(CreatePlaceScoreRequest createPlaceScoreRequest) {
        placeResponse.setPlaceScore(new PlaceScore(0, createPlaceScoreRequest.getUser(),
                createPlaceScoreRequest.getPlace(), createPlaceScoreRequest.getTotalScore(),
                createPlaceScoreRequest.getFoodScore(), createPlaceScoreRequest.getServiceScore(),
                createPlaceScoreRequest.getAmbianceScore()));
        Toast.makeText(getContext(), "امتیاز شما ثبت شد", Toast.LENGTH_SHORT).show();
        showCreateReviewDialog();
    }

    private void generateCreateReviewData() {
        Toast.makeText(getContext(), "نظر شما ثبت شد", Toast.LENGTH_SHORT).show();
    }

    private SeekBar.OnTouchListener disabledOnTouchListener = new View.OnTouchListener() {
        @SuppressLint("ClickableViewAccessibility")
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            return true;
        }
    };

    public void changeFragment(PlaceResponse placeResponse) {
        this.placeResponse = placeResponse;
        yourOverallScoreRatingBar.setRating(placeResponse.getPlaceScore().getTotalScore());
        if (placeResponse.getPlaceScore().getTotalScore() > 0)
            yourOverallScoreTextView.setText(R.string.your_ratings);
        else
            yourOverallScoreTextView.setText(R.string.submit_your_ratings);

        overallScoreTextView.setText(String.valueOf(placeResponse.getPlace().getOverallScore()));
        foodScoreTextView.setText(String.valueOf(placeResponse.getPlace().getFoodScoreAverage()));
        serviceScoreTextView.setText(String.valueOf(placeResponse.getPlace().getServiceScoreAverage()));
        ambianceScoreTextView.setText(String.valueOf(placeResponse.getPlace().getAmbianceScoreAverage()));
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
    }
}
