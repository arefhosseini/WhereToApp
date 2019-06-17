package ir.fearefull.wheretoapp.controller.view_controller.place.home.place_image.full_screen;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.List;
import java.util.Objects;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.base.MyPagerAdapter;
import ir.fearefull.wheretoapp.model.api.SimpleResponse;
import ir.fearefull.wheretoapp.model.api.place.PlaceImage;
import ir.fearefull.wheretoapp.model.api.place.place_image_vote.CreatePlaceImageVoteRequest;
import ir.fearefull.wheretoapp.model.api.place.place_image_vote.RemovePlaceImageVoteRequest;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.view.base.TouchImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FullScreenPlaceImageAdapter extends MyPagerAdapter {

    private Context context;
    private List<PlaceImage> placeImageList;
    private User user;
    private LayoutInflater inflater;

    public FullScreenPlaceImageAdapter(Context context, List<PlaceImage> placeImageList, User user) {
        this.context = context;
        this.user = user;
        this.placeImageList = placeImageList;
    }

    @Override
    public int getCount() {
        return this.placeImageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TouchImageView touchImageView;
        TextView downVoteTextView, upVoteTextView;
        ImageButton downVoteImageButton, upVoteImageButton;
        
        PlaceImage placeImage = placeImageList.get(position);

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View viewLayout = Objects.requireNonNull(inflater).inflate(R.layout.card_fullscreen_place_image, container,
                false);

        touchImageView = viewLayout.findViewById(R.id.imgDisplay);
        downVoteTextView = viewLayout.findViewById(R.id.downVoteTextView);
        upVoteTextView = viewLayout.findViewById(R.id.upVoteTextView);
        downVoteImageButton = viewLayout.findViewById(R.id.downVoteImageButton);
        upVoteImageButton = viewLayout.findViewById(R.id.upVoteImageButton);

        downVoteTextView.setText(String.valueOf(placeImage.getDownVotes()));
        upVoteTextView.setText(String.valueOf(placeImage.getUpVotes()));

        if (placeImage.getYourVote() == 1) {
            upVoteImageButton.setImageResource(R.mipmap.enabled_like);
        }
        else if (placeImage.getYourVote() == -1) {
            downVoteImageButton.setImageResource(R.mipmap.enabled_dislike);
        }
        
        Picasso.get().load(Constants.BASE_URL + placeImage.getImage()).into(touchImageView);

        OnUpVoteClickClickListener onUpVoteClickClickListener =
                new OnUpVoteClickClickListener(position, upVoteTextView,
                        downVoteImageButton, downVoteTextView);
        OnDownVoteClickClickListener onDownVoteClickClickListener =
                new OnDownVoteClickClickListener(position, downVoteTextView,
                        upVoteImageButton, upVoteTextView);
        upVoteImageButton.setOnClickListener(onUpVoteClickClickListener);
        downVoteImageButton.setOnClickListener(onDownVoteClickClickListener);

        container.addView(viewLayout);

        return viewLayout;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    class OnUpVoteClickClickListener implements View.OnClickListener {

        private int position;
        private TextView upVoteTextView, downVoteTextView;
        private ImageButton downVoteImageButton;

        // constructor
        OnUpVoteClickClickListener(int position, TextView upVoteTextView,
                                   ImageButton downVoteImageButton, TextView downVoteTextView) {
            this.position = position;
            this.upVoteTextView = upVoteTextView;
            this.downVoteImageButton = downVoteImageButton;
            this.downVoteTextView = downVoteTextView;
        }

        @Override
        public void onClick(View v) {
            PlaceImage placeImage = placeImageList.get(position);
            Log.d("your Vote", String.valueOf(placeImage.getYourVote()));
            if (placeImage.getYourVote() == 0) {
                try {
                    createReviewUpVote(position, false, (ImageButton) v, upVoteTextView,
                            null, null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if (placeImage.getYourVote() == 1) {
                try {
                    removeReviewUpVote(position,  (ImageButton) v, upVoteTextView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if (placeImage.getYourVote() == -1) {
                try {
                    createReviewUpVote(position, true,  (ImageButton) v, upVoteTextView,
                            downVoteImageButton, downVoteTextView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class OnDownVoteClickClickListener implements View.OnClickListener {

        private int position;
        private TextView downVoteTextView, upVoteTextView;
        private ImageButton upVoteImageButton;

        // constructor
        OnDownVoteClickClickListener(int position, TextView downVoteTextView,
                                     ImageButton upVoteImageButton, TextView upVoteTextView) {
            this.position = position;
            this.downVoteTextView = downVoteTextView;
            this.upVoteImageButton = upVoteImageButton;
            this.upVoteTextView = upVoteTextView;
        }

        @Override
        public void onClick(View v) {
            PlaceImage placeImage = placeImageList.get(position);
            Log.d("your Vote", String.valueOf(placeImage.getYourVote()));
            if (placeImage.getYourVote() == 0) {
                try {
                    createReviewDownVote(position, false, (ImageButton) v, downVoteTextView,
                            null, null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if (placeImage.getYourVote() == -1) {
                try {
                    removeReviewDownVote(position,  (ImageButton) v, downVoteTextView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if (placeImage.getYourVote() == 1) {
                try {
                    createReviewDownVote(position, true,  (ImageButton) v, downVoteTextView,
                            upVoteImageButton, upVoteTextView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void createReviewUpVote(final int position, final boolean shouldChange, final ImageButton upVoteImageButton,
                                    final TextView upVoteTextView,
                                    @Nullable final ImageButton downVoteImageButton,
                                    @Nullable final TextView downVoteTextView) throws JSONException {
        final PlaceImage placeImage = placeImageList.get(position);
        CreatePlaceImageVoteRequest createPlaceImageVoteRequest = new CreatePlaceImageVoteRequest(
                user.getPhoneNumber(), placeImage.getId(), 1);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SimpleResponse> call = service.createPlaceImageVote(createPlaceImageVoteRequest.toRequestBody());
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                placeImageList.get(position).setYourVote(1);
                placeImageList.get(position).setUpVotes(
                        placeImageList.get(position).getUpVotes() + 1);
                upVoteTextView.setText(String.valueOf(placeImageList.get(position).getUpVotes()));
                upVoteImageButton.setImageResource(R.mipmap.enabled_like);
                if (shouldChange) {
                    placeImageList.get(position).setDownVotes(
                            placeImageList.get(position).getDownVotes() - 1);
                    Objects.requireNonNull(downVoteTextView).setText(String.valueOf(placeImageList.get(position).getDownVotes()));
                    Objects.requireNonNull(downVoteImageButton).setImageResource(R.mipmap.disabled_dislike);
                }
                Toast.makeText(context, "رای شما ثبت شد", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeReviewUpVote(final int position, final ImageButton upVoteImageButton,
                                    final TextView upVoteTextView) throws JSONException {
        final PlaceImage placeImage = placeImageList.get(position);
        RemovePlaceImageVoteRequest removePlaceImageVoteRequest = new RemovePlaceImageVoteRequest(
                user.getPhoneNumber(), placeImage.getId());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseBody> call = service.removePlaceImageVote(removePlaceImageVoteRequest.toRequestBody());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                placeImageList.get(position).setYourVote(0);
                placeImageList.get(position).setUpVotes(
                        placeImageList.get(position).getUpVotes() - 1);
                upVoteTextView.setText(String.valueOf(placeImageList.get(position).getUpVotes()));
                upVoteImageButton.setImageResource(R.mipmap.disabled_like);
                Toast.makeText(context, "رای شما حذف شد", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createReviewDownVote(final int position, final boolean shouldChange,
                                      final ImageButton downVoteImageButton, final TextView downVoteTextView,
                                      @Nullable final ImageButton upVoteImageButton,
                                      @Nullable final TextView upVoteTextView) throws JSONException {
        final PlaceImage placeImage = placeImageList.get(position);
        CreatePlaceImageVoteRequest createPlaceImageVoteRequest = new CreatePlaceImageVoteRequest(
                user.getPhoneNumber(), placeImage.getId(), -1);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SimpleResponse> call = service.createPlaceImageVote(createPlaceImageVoteRequest.toRequestBody());
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                placeImageList.get(position).setYourVote(-1);
                placeImageList.get(position).setDownVotes(
                        placeImageList.get(position).getDownVotes() + 1);
                downVoteTextView.setText(String.valueOf(placeImageList.get(position).getDownVotes()));
                downVoteImageButton.setImageResource(R.mipmap.enabled_dislike);
                Toast.makeText(context, "رای شما ثبت شد", Toast.LENGTH_SHORT).show();
                if (shouldChange) {
                    placeImageList.get(position).setUpVotes(
                            placeImageList.get(position).getUpVotes() - 1);
                    Objects.requireNonNull(upVoteTextView).setText(String.valueOf(placeImageList.get(position).getUpVotes()));
                    Objects.requireNonNull(upVoteImageButton).setImageResource(R.mipmap.disabled_like);
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeReviewDownVote(final int position, final ImageButton downVoteImageButton,
                                      final TextView downVoteTextView) throws JSONException {
        final PlaceImage placeImage = placeImageList.get(position);
        RemovePlaceImageVoteRequest removePlaceImageVoteRequest = new RemovePlaceImageVoteRequest(
                user.getPhoneNumber(), placeImage.getId());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseBody> call = service.removePlaceImageVote(removePlaceImageVoteRequest.toRequestBody());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                placeImageList.get(position).setYourVote(0);
                placeImageList.get(position).setDownVotes(
                        placeImageList.get(position).getDownVotes() - 1);
                downVoteTextView.setText(String.valueOf(placeImageList.get(position).getDownVotes()));
                downVoteImageButton.setImageResource(R.mipmap.disabled_dislike);
                Toast.makeText(context, "رای شما حذف شد", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}