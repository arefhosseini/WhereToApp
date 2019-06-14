package ir.fearefull.wheretoapp.controller.view_controller.place.review;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.user.UserFragment;
import ir.fearefull.wheretoapp.model.api.SimpleResponse;
import ir.fearefull.wheretoapp.model.api.place.review_vote.CreateReviewVoteRequest;
import ir.fearefull.wheretoapp.model.api.place.review_vote.RemoveReviewVoteRequest;
import ir.fearefull.wheretoapp.model.api.review.PlaceReviewResponse;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.view.place.PlaceReviewsViewHolder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceReviewsAdapter extends RecyclerView.Adapter<PlaceReviewsViewHolder> {

    private List<PlaceReviewResponse> placeReviewResponseList;
    private ViewGroup parent;
    private Fragment fragment;
    private Date currentDate;
    private User user;

    public PlaceReviewsAdapter(List<PlaceReviewResponse> placeSummaryList,
                               Fragment fragment, User user) {
        this.placeReviewResponseList = placeSummaryList;
        this.fragment = fragment;
        currentDate = new Date();
        this.user = user;
    }

    @NonNull
    @Override
    public PlaceReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_place_review, parent, false);

        this.parent = parent;
        return new PlaceReviewsViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PlaceReviewsViewHolder holder, int position) {
        PlaceReviewResponse placeReviewResponse = placeReviewResponseList.get(position);

        holder.getCreatedDateTextView().setText(calculateDateDifference(placeReviewResponse.getCreatedDate()));
        holder.getNameTextView().setText(String.valueOf(placeReviewResponse.getFirstName() + " " + placeReviewResponse.getLastName()));
        holder.getNameTextView().setTag(placeReviewResponse.getPhoneNumber());
        holder.getTextTextView().setText(placeReviewResponse.getText());
        holder.getDownVoteTextView().setText(String.valueOf(placeReviewResponse.getDownVotes()));
        holder.getUpVoteTextView().setText(String.valueOf(placeReviewResponse.getUpVotes()));
        holder.getUserScoreRatingBar().setRating(placeReviewResponse.getPlaceScore());

        Picasso.get().load(Constants.BASE_URL + placeReviewResponse.getProfileImage()).into(holder.getProfileImageView());
        holder.getProfileImageView().setTag(placeReviewResponse.getPhoneNumber());

        if (placeReviewResponse.getYourVote() == 1) {
            holder.getUpVoteImageButton().setImageResource(R.mipmap.enabled_like);
        }
        else if (placeReviewResponse.getYourVote() == -1) {
            holder.getDownVoteImageButton().setImageResource(R.mipmap.enabled_dislike);
        }

        OnUserOpenClickListener onUserOpenClickListener = new OnUserOpenClickListener(placeReviewResponse.getPhoneNumber());
        OnUpVoteClickClickListener onUpVoteClickClickListener =
                new OnUpVoteClickClickListener(position, holder.getUpVoteTextView(),
                        holder.getDownVoteImageButton(), holder.getDownVoteTextView());
        OnDownVoteClickClickListener onDownVoteClickClickListener =
                new OnDownVoteClickClickListener(position, holder.getDownVoteTextView(),
                        holder.getUpVoteImageButton(), holder.getUpVoteTextView());
        holder.getNameTextView().setOnClickListener(onUserOpenClickListener);
        holder.getProfileImageView().setOnClickListener(onUserOpenClickListener);
        holder.getUpVoteImageButton().setOnClickListener(onUpVoteClickClickListener);
        holder.getDownVoteImageButton().setOnClickListener(onDownVoteClickClickListener);
    }

    @Override
    public int getItemCount() {
        return placeReviewResponseList.size();
    }

    private String calculateDateDifference(long timeStamp) {
        Date date = new Date(timeStamp);
        long difference = currentDate.getTime() - date.getTime();
        long days = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
        if (days > 365) {
            long years = days / 365;
            return years + " سال پیش";
        }
        if (days > 30) {
            long months = days / 30;
            return months + " ماه پیش";
        }
        if (days > 0) {
            return days + " روز پیش";
        }
        if (days == 0) {
            long hours = TimeUnit.HOURS.convert(difference, TimeUnit.MILLISECONDS);
            if (hours > 0)
                return hours + " ساعت پیش";
            else {
                long minutes = TimeUnit.MINUTES.convert(difference, TimeUnit.MILLISECONDS);
                if (minutes > 0)
                    return minutes + " دقیقه پیش";
                else
                    return "همین چند لحظه پیش";
            }
        }
        return "";
    }

    class OnUserOpenClickListener implements View.OnClickListener {

        private String phoneNumber;

        // constructor
        OnUserOpenClickListener(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @Override
        public void onClick(View v) {
            Objects.requireNonNull(Objects.requireNonNull(fragment.getActivity()).getSupportFragmentManager())
                    .beginTransaction()
                    .replace(R.id.fragmentPlace, new UserFragment(user, phoneNumber))
                    .addToBackStack(null)
                    .commit();
        }
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
            PlaceReviewResponse placeReviewResponse = placeReviewResponseList.get(position);
            Log.d("your Vote", String.valueOf(placeReviewResponse.getYourVote()));
            if (placeReviewResponse.getYourVote() == 0) {
                try {
                    createReviewUpVote(position, false, (ImageButton) v, upVoteTextView,
                            null, null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if (placeReviewResponse.getYourVote() == 1) {
                try {
                    removeReviewUpVote(position,  (ImageButton) v, upVoteTextView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if (placeReviewResponse.getYourVote() == -1) {
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
            PlaceReviewResponse placeReviewResponse = placeReviewResponseList.get(position);
            Log.d("your Vote", String.valueOf(placeReviewResponse.getYourVote()));
            if (placeReviewResponse.getYourVote() == 0) {
                try {
                    createReviewDownVote(position, false, (ImageButton) v, downVoteTextView,
                            null, null);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if (placeReviewResponse.getYourVote() == -1) {
                try {
                    removeReviewDownVote(position,  (ImageButton) v, downVoteTextView);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if (placeReviewResponse.getYourVote() == 1) {
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
        final PlaceReviewResponse placeReviewResponse = placeReviewResponseList.get(position);
        CreateReviewVoteRequest createReviewVoteRequest = new CreateReviewVoteRequest(user.getPhoneNumber(),
                 placeReviewResponse.getId(), 1);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SimpleResponse> call = service.createReviewVote(createReviewVoteRequest.toRequestBody());
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                placeReviewResponseList.get(position).setYourVote(1);
                placeReviewResponseList.get(position).setUpVotes(
                        placeReviewResponseList.get(position).getUpVotes() + 1);
                upVoteTextView.setText(String.valueOf(placeReviewResponseList.get(position).getUpVotes()));
                upVoteImageButton.setImageResource(R.mipmap.enabled_like);
                if (shouldChange) {
                    placeReviewResponseList.get(position).setDownVotes(
                            placeReviewResponseList.get(position).getDownVotes() - 1);
                    Objects.requireNonNull(downVoteTextView).setText(String.valueOf(placeReviewResponseList.get(position).getDownVotes()));
                    Objects.requireNonNull(downVoteImageButton).setImageResource(R.mipmap.disabled_dislike);
                }
                Toast.makeText(fragment.getContext(), "رای شما ثبت شد", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(fragment.getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeReviewUpVote(final int position, final ImageButton upVoteImageButton,
                                  final TextView upVoteTextView) throws JSONException {
        final PlaceReviewResponse placeReviewResponse = placeReviewResponseList.get(position);
        RemoveReviewVoteRequest removeReviewVoteRequest = new RemoveReviewVoteRequest(user.getPhoneNumber(),
                placeReviewResponse.getId());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseBody> call = service.removeReviewVote(removeReviewVoteRequest.toRequestBody());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                placeReviewResponseList.get(position).setYourVote(0);
                placeReviewResponseList.get(position).setUpVotes(
                        placeReviewResponseList.get(position).getUpVotes() - 1);
                upVoteTextView.setText(String.valueOf(placeReviewResponseList.get(position).getUpVotes()));
                upVoteImageButton.setImageResource(R.mipmap.disabled_like);
                Toast.makeText(fragment.getContext(), "رای شما حذف شد", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(fragment.getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createReviewDownVote(final int position, final boolean shouldChange,
                                      final ImageButton downVoteImageButton, final TextView downVoteTextView,
                                      @Nullable final ImageButton upVoteImageButton,
                                      @Nullable final TextView upVoteTextView) throws JSONException {
        final PlaceReviewResponse placeReviewResponse = placeReviewResponseList.get(position);
        CreateReviewVoteRequest createReviewVoteRequest = new CreateReviewVoteRequest(user.getPhoneNumber(),
                placeReviewResponse.getId(), -1);
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SimpleResponse> call = service.createReviewVote(createReviewVoteRequest.toRequestBody());
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                placeReviewResponseList.get(position).setYourVote(-1);
                placeReviewResponseList.get(position).setDownVotes(
                        placeReviewResponseList.get(position).getDownVotes() + 1);
                downVoteTextView.setText(String.valueOf(placeReviewResponseList.get(position).getDownVotes()));
                downVoteImageButton.setImageResource(R.mipmap.enabled_dislike);
                Toast.makeText(fragment.getContext(), "رای شما ثبت شد", Toast.LENGTH_SHORT).show();
                if (shouldChange) {
                    placeReviewResponseList.get(position).setUpVotes(
                            placeReviewResponseList.get(position).getUpVotes() - 1);
                    Objects.requireNonNull(upVoteTextView).setText(String.valueOf(placeReviewResponseList.get(position).getUpVotes()));
                    Objects.requireNonNull(upVoteImageButton).setImageResource(R.mipmap.disabled_like);
                }
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(fragment.getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeReviewDownVote(final int position, final ImageButton downVoteImageButton,
                                  final TextView downVoteTextView) throws JSONException {
        final PlaceReviewResponse placeReviewResponse = placeReviewResponseList.get(position);
        RemoveReviewVoteRequest removeReviewVoteRequest = new RemoveReviewVoteRequest(user.getPhoneNumber(),
                placeReviewResponse.getId());
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<ResponseBody> call = service.removeReviewVote(removeReviewVoteRequest.toRequestBody());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                placeReviewResponseList.get(position).setYourVote(0);
                placeReviewResponseList.get(position).setDownVotes(
                        placeReviewResponseList.get(position).getDownVotes() - 1);
                downVoteTextView.setText(String.valueOf(placeReviewResponseList.get(position).getDownVotes()));
                downVoteImageButton.setImageResource(R.mipmap.disabled_dislike);
                Toast.makeText(fragment.getContext(), "رای شما حذف شد", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(fragment.getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
