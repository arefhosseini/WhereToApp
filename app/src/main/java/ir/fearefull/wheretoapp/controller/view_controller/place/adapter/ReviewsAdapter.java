package ir.fearefull.wheretoapp.controller.view_controller.place.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.view_controller.user.UserActivity;
import ir.fearefull.wheretoapp.model.api.review.PlaceReview;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.view.place.PlaceReviewsViewHolder;

public class ReviewsAdapter extends RecyclerView.Adapter<PlaceReviewsViewHolder> {

    private List<PlaceReview> placeReviewList;
    private Context context;
    private ViewGroup parent;
    private Fragment fragment;
    private DateFormat dateFormat;
    private Date currentDate;

    public ReviewsAdapter(List<PlaceReview> placeSummaryList, Context context,
                          Fragment fragment) {
        this.placeReviewList = placeSummaryList;
        this.context = context;
        this.fragment = fragment;
        this.dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
        currentDate = new Date();

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
        PlaceReview placeReview = placeReviewList.get(position);

        holder.getCreatedDateTextView().setText(calculateDateDifference(placeReview.getCreatedDate()));
        holder.getNameTextView().setText(String.valueOf(placeReview.getFirstName() + " " + placeReview.getLastName()));
        holder.getNameTextView().setTag(placeReview.getPhoneNumber());
        holder.getTextTextView().setText(placeReview.getText());
        holder.getDownVoteTextView().setText(String.valueOf(placeReview.getDownVote()));
        holder.getUpVoteTextView().setText(String.valueOf(placeReview.getUpVote()));

        //Picasso.get().load(Constants.BASE_URL + placeReview.getProfileImage()).into(holder.profileImageView);
        if (placeReview.getProfileImage().contains("media"))
            Picasso.get().load(Constants.BASE_URL + placeReview.getProfileImage()).into(holder.getProfileImageView());
        else
            Picasso.get().load(Constants.MEDIA_URL + placeReview.getProfileImage()).into(holder.getProfileImageView());

        holder.getProfileImageView().setTag(placeReview.getPhoneNumber());

        OnUserOpenClickListener onUserOpenClickListener = new OnUserOpenClickListener(placeReview.getPhoneNumber());
        holder.getNameTextView().setOnClickListener(onUserOpenClickListener);
        holder.getProfileImageView().setOnClickListener(onUserOpenClickListener);
    }

    @Override
    public int getItemCount() {
        return placeReviewList.size();
    }

    private String calculateDateDifference(String dateString) {
        try {
            Date date = dateFormat.parse(dateString);
            long difference = currentDate.getTime() - date.getTime();
            long days = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
            if (days > 365) {
                long years = days / 365;
                return String.valueOf(years + " سال پیش");
            }
            if (days > 30) {
                long months = days / 30;
                return String.valueOf(months + " ماه پیش");
            }
            if (days > 0) {
                return String.valueOf(days + " روز پیش");
            }
            if (days == 0) {
                long hours = TimeUnit.HOURS.convert(difference, TimeUnit.MILLISECONDS);
                if (hours > 0)
                    return String.valueOf(hours + " ساعت پیش");
                else {
                    long minutes = TimeUnit.MINUTES.convert(difference, TimeUnit.MILLISECONDS);
                    if (minutes > 0)
                        return String.valueOf(minutes + " دقیقه پیش");
                    else
                        return "همین چند لحظه پیش";
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
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
            Intent dashboardIntent = new Intent(context, UserActivity.class);
            dashboardIntent.putExtra("phoneNumber", phoneNumber);
            fragment.startActivity(dashboardIntent);
        }
    }
}
