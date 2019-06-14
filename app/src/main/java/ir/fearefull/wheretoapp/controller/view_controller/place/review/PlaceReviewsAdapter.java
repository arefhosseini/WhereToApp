package ir.fearefull.wheretoapp.controller.view_controller.place.review;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.view_controller.user.UserFragment;
import ir.fearefull.wheretoapp.model.api.review.PlaceReviewResponse;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.view.place.PlaceReviewsViewHolder;

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

        OnUserOpenClickListener onUserOpenClickListener = new OnUserOpenClickListener(placeReviewResponse.getPhoneNumber());
        holder.getNameTextView().setOnClickListener(onUserOpenClickListener);
        holder.getProfileImageView().setOnClickListener(onUserOpenClickListener);
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
}
