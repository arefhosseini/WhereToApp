package ir.fearefull.wheretoapp.ui.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.data.model.api.FollowRequest;
import ir.fearefull.wheretoapp.data.model.api.Friend;
import ir.fearefull.wheretoapp.data.model.db.User;
import ir.fearefull.wheretoapp.data.remote.GetDataService;
import ir.fearefull.wheretoapp.data.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.ui.UserActivity;
import ir.fearefull.wheretoapp.utils.Constants;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FollowingsAdapter extends RecyclerView.Adapter<FollowingsAdapter.MyViewHolder> {

    private List<Friend> followingList;
    private User user;
    private Activity activity;
    private ViewGroup parent;
    private GetDataService service;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView nameTextView, followTextView;
        private ImageView profileImageView;

        public MyViewHolder(View view) {
            super(view);
            nameTextView = view.findViewById(R.id.nameTextView);
            followTextView = view.findViewById(R.id.followTextView);
            profileImageView = view.findViewById(R.id.profileImageView);
        }
    }

    public FollowingsAdapter(List<Friend> followingList, User user, Activity activity) {
        this.followingList = followingList;
        this.user = user;
        this.activity = activity;
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_friend, parent, false);

        this.parent = parent;
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Friend friend = followingList.get(position);
        holder.nameTextView.setText(String.valueOf(friend.getFirstName() + " " + friend.getLastName()));
        Picasso.get().load(Constants.BASE_URL + friend.getProfileImage()).into(holder.profileImageView);

        if (!friend.getPhoneNumber().equals(user.getPhoneNumber())) {
            if (friend.getIsFollowing() == 0) {
                holder.followTextView.setText(R.string.follow);
                holder.followTextView.setTextColor(activity.getResources().getColor(R.color.textLightPrimary));
                holder.followTextView.setBackground(activity.getDrawable(R.drawable.follow_button_shape));
            }
            else {
                holder.followTextView.setText(R.string.dont_follow);
                holder.followTextView.setTextColor(activity.getResources().getColor(R.color.textDarkPrimary));
                holder.followTextView.setBackground(activity.getDrawable(R.drawable.dont_follow_dark_button_shape));
            }
        }
        else
            holder.followTextView.setVisibility(View.GONE);
        OnUserOpenClickListener onUserOpenClickListener = new OnUserOpenClickListener(friend.getPhoneNumber());
        holder.nameTextView.setOnClickListener(onUserOpenClickListener);
        holder.profileImageView.setOnClickListener(onUserOpenClickListener);

        holder.followTextView.setOnClickListener(new OnFollowClickListener(position));
    }

    class OnUserOpenClickListener implements View.OnClickListener {

        private String phoneNumber;

        // constructor
        OnUserOpenClickListener(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @Override
        public void onClick(View v) {
            Intent dashboardIntent = new Intent(activity, UserActivity.class);
            dashboardIntent.putExtra("phoneNumber", phoneNumber);
            activity.startActivity(dashboardIntent);
        }
    }

    class OnFollowClickListener implements View.OnClickListener {

        private int position;

        // constructor
        OnFollowClickListener(int position) {
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            
            if (followingList.get(position).getIsFollowing() == 0) {
                try {
                    followRequest(position, (TextView) v);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else {
                try {
                    removeFollowRequest(position, (TextView) v);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void followRequest(final int position, final TextView textView) throws JSONException {
        Call<ResponseBody> call = service.followUser(new FollowRequest(user.getPhoneNumber(), 
                followingList.get(position).getPhoneNumber()).toRequestBody());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //progressDoalog.dismiss();
                Toast.makeText(activity, "این کاربر به لیست دنبال کنندگان شما اضافه شد", Toast.LENGTH_LONG).show();
                
                followingList.get(position).setIsFollowing(1);
                textView.setText(R.string.dont_follow);
                textView.setTextColor(activity.getResources().getColor(R.color.textDarkPrimary));
                textView.setBackground(activity.getDrawable(R.drawable.dont_follow_dark_button_shape));
                assert response.body() != null;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(activity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeFollowRequest(final int position, final TextView textView) throws JSONException {
        Call<ResponseBody> call = service.removeFollowUser(new FollowRequest(user.getPhoneNumber(),
                followingList.get(position).getPhoneNumber()).toRequestBody());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //progressDoalog.dismiss();
                Toast.makeText(activity, "این کاربر از لیست دنبال کنندگان شما حذف شد", Toast.LENGTH_LONG).show();

                followingList.get(position).setIsFollowing(0);
                textView.setText(R.string.follow);
                textView.setTextColor(activity.getResources().getColor(R.color.textLightPrimary));
                textView.setBackground(activity.getDrawable(R.drawable.follow_button_shape));
                assert response.body() != null;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(activity, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return followingList.size();
    }
}
