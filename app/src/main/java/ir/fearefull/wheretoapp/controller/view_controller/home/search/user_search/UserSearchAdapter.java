package ir.fearefull.wheretoapp.controller.view_controller.home.search.user_search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.model.api.search.UserSearchResponse;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.view.search.UserSearchViewHolder;

public class UserSearchAdapter extends RecyclerView.Adapter<UserSearchViewHolder> {

    private UserSearchAdapterCallBack callBack;
    private List<UserSearchResponse> userSearchResponseList;
    private ViewGroup parent;

    UserSearchAdapter(UserSearchAdapterCallBack callBack, List<UserSearchResponse> userSearchResponseList) {
        this.callBack = callBack;
        this.userSearchResponseList = userSearchResponseList;
    }

    @NonNull
    @Override
    public UserSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_user_search, parent, false);

        this.parent = parent;
        return new UserSearchViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull UserSearchViewHolder holder, int position) {
        UserSearchResponse userSearchResponse = userSearchResponseList.get(position);
        holder.getNameTextView().setText(String.valueOf(userSearchResponse.getFirstName() + " " + userSearchResponse.getLastName()));

        Picasso.get().load(Constants.BASE_URL + userSearchResponse.getProfileImage()).into(holder.getProfileImageView());
        OnUserOpenClickListener onUserOpenClickListener = new OnUserOpenClickListener(userSearchResponse.getPhoneNumber());
        holder.getNameTextView().setOnClickListener(onUserOpenClickListener);
        holder.getProfileImageView().setOnClickListener(onUserOpenClickListener);
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

    @Override
    public int getItemCount() {
        return userSearchResponseList.size();
    }
}
