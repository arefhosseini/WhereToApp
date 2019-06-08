package ir.fearefull.wheretoapp.ui.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import ir.fearefull.wheretoapp.ui.UserActivity;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.data.model.api.PlaceReview;
import ir.fearefull.wheretoapp.R;

public class PlaceReviewsAdapter extends RecyclerView.Adapter<PlaceReviewsAdapter.MyViewHolder> {

    private List<PlaceReview> placeReviewList;
    private Context context;
    private ViewGroup parent;
    private Fragment fragment;
    private DateFormat dateFormat;
    private Date currentDate;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView createdDateTextView, nameTextView, textTextView, downVoteTextView, upVoteTextView;
        private ImageView profileImageView;

        public MyViewHolder(View view) {
            super(view);
            createdDateTextView = view.findViewById(R.id.createdDateTextView);
            nameTextView = view.findViewById(R.id.nameTextView);
            textTextView = view.findViewById(R.id.textTextView);
            downVoteTextView = view.findViewById(R.id.downVoteTextView);
            upVoteTextView = view.findViewById(R.id.upVoteTextView);
            profileImageView = view.findViewById(R.id.profileImageView);
        }
    }

    public PlaceReviewsAdapter(List<PlaceReview> placeSummaryList, Context context,
                               Fragment fragment) {
        this.placeReviewList = placeSummaryList;
        this.context = context;
        this.fragment = fragment;
        this.dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
        currentDate = new Date();

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_place_review, parent, false);

        this.parent = parent;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PlaceReview placeReview = placeReviewList.get(position);

        holder.createdDateTextView.setText(calculateDateDifference(placeReview.getCreatedDate()));
        holder.nameTextView.setText(String.valueOf(placeReview.getFirstName() + " " + placeReview.getLastName()));
        holder.nameTextView.setTag(placeReview.getPhoneNumber());
        holder.textTextView.setText(placeReview.getText());
        holder.downVoteTextView.setText(String.valueOf(placeReview.getDownVote()));
        holder.upVoteTextView.setText(String.valueOf(placeReview.getUpVote()));
        Picasso.get().load(Constants.BASE_URL + placeReview.getProfileImage()).into(holder.profileImageView);
        holder.profileImageView.setTag(placeReview.getPhoneNumber());

        OnUserOpenClickListener onUserOpenClickListener = new OnUserOpenClickListener(placeReview.getPhoneNumber());
        holder.nameTextView.setOnClickListener(onUserOpenClickListener);
        holder.profileImageView.setOnClickListener(onUserOpenClickListener);
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