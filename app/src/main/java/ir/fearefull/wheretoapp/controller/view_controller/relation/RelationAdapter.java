package ir.fearefull.wheretoapp.controller.view_controller.relation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.List;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.model.api.SimpleResponse;
import ir.fearefull.wheretoapp.model.api.user.relation.UserRelation;
import ir.fearefull.wheretoapp.model.api.user.relation.UserRelationRequest;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.view.relation.RelationViewHolder;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RelationAdapter extends RecyclerView.Adapter<RelationViewHolder> {

    private RelationAdapterCallBack callBack;
    private Context context;
    private List<UserRelation> followingList;
    private User user;
    private ViewGroup parent;
    private GetDataService service;

    public RelationAdapter(RelationAdapterCallBack callBack, Context context,
                           List<UserRelation> followingList, User user) {
        this.callBack = callBack;
        this.context = context;
        this.followingList = followingList;
        this.user = user;
        service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
    }

    @NonNull
    @Override
    public RelationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_relation, parent, false);

        this.parent = parent;
        return new RelationViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RelationViewHolder holder, int position) {
        UserRelation userRelation = followingList.get(position);
        holder.getNameTextView().setText(String.valueOf(userRelation.getFirstName() + " " + userRelation.getLastName()));

        Picasso.get().load(Constants.BASE_URL + userRelation.getProfileImage()).into(holder.getProfileImageView());
        if (!userRelation.getPhoneNumber().equals(user.getPhoneNumber())) {
            if (userRelation.getIsFollowing() == 0) {
                holder.getFollowTextView().setText(R.string.follow);
                holder.getFollowTextView().setTextColor(context.getResources().getColor(R.color.textLightPrimary));
                holder.getFollowTextView().setBackground(context.getResources().getDrawable(R.drawable.follow_button_shape));
            }
            else {
                holder.getFollowTextView().setText(R.string.dont_follow);
                holder.getFollowTextView().setTextColor(context.getResources().getColor(R.color.textDarkPrimary));
                holder.getFollowTextView().setBackground(context.getResources().getDrawable(R.drawable.dont_follow_dark_button_shape));
            }
        }
        else
            holder.getFollowTextView().setVisibility(View.GONE);
        OnUserOpenClickListener onUserOpenClickListener = new OnUserOpenClickListener(userRelation.getPhoneNumber());
        holder.getNameTextView().setOnClickListener(onUserOpenClickListener);
        holder.getProfileImageView().setOnClickListener(onUserOpenClickListener);

        holder.getFollowTextView().setOnClickListener(new OnFollowClickListener(position));
    }

    class OnUserOpenClickListener implements View.OnClickListener {

        private String phoneNumber;

        // constructor
        OnUserOpenClickListener(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        @Override
        public void onClick(View v) {
            callBack.onOpenUserFragment(phoneNumber);
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
        Call<SimpleResponse> call = service.createUserRelation(new UserRelationRequest(user.getPhoneNumber(),
                followingList.get(position).getPhoneNumber()).toRequestBody());
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                //progressDoalog.dismiss();
                Toast.makeText(context, "این کاربر به لیست دنبال کنندگان شما اضافه شد", Toast.LENGTH_LONG).show();
                
                followingList.get(position).setIsFollowing(1);
                textView.setText(R.string.dont_follow);
                textView.setTextColor(context.getResources().getColor(R.color.textDarkPrimary));
                textView.setBackground(context.getResources().getDrawable(R.drawable.dont_follow_dark_button_shape));
                assert response.body() != null;
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void removeFollowRequest(final int position, final TextView textView) throws JSONException {
        Call<ResponseBody> call = service.removeUserRelation(new UserRelationRequest(user.getPhoneNumber(),
                followingList.get(position).getPhoneNumber()).toRequestBody());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                //progressDoalog.dismiss();
                Toast.makeText(context, "این کاربر از لیست دنبال کنندگان شما حذف شد", Toast.LENGTH_LONG).show();

                followingList.get(position).setIsFollowing(0);
                textView.setText(R.string.follow);
                textView.setTextColor(context.getResources().getColor(R.color.textLightPrimary));
                textView.setBackground(context.getResources().getDrawable(R.drawable.follow_button_shape));
                assert response.body() != null;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(context, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return followingList.size();
    }
}
