package ir.fearefull.wheretoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
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
import java.util.concurrent.TimeUnit;

import ir.fearefull.wheretoapp.Constants;
import ir.fearefull.wheretoapp.Model.PlaceReview;
import ir.fearefull.wheretoapp.R;

public class PlaceReviewsAdapter extends RecyclerView.Adapter<PlaceReviewsAdapter.MyViewHolder> {

    private List<PlaceReview> placeReviewList;
    private Context context;
    private ViewGroup parent;
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

    public PlaceReviewsAdapter(List<PlaceReview> placeSummaryList, Context context) {
        this.placeReviewList = placeSummaryList;
        this.context = context;
        this.dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT);
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
        holder.nameTextView.setOnClickListener(userClickListener);
        holder.textTextView.setText(placeReview.getText());
        holder.downVoteTextView.setText(String.valueOf(placeReview.getDownVote()));
        holder.upVoteTextView.setText(String.valueOf(placeReview.getUpVote()));
        Picasso.get().load(Constants.BASE_URL + placeReview.getProfileImage()).into(holder.profileImageView);
        holder.profileImageView.setTag(placeReview.getPhoneNumber());
        holder.profileImageView.setOnClickListener(userClickListener);
    }

    @Override
    public int getItemCount() {
        return placeReviewList.size();
    }

    private View.OnClickListener userClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Toast.makeText(context, String.valueOf("User " + view.getTag() + " should open"), Toast.LENGTH_LONG).show();
        }
    };

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
}
