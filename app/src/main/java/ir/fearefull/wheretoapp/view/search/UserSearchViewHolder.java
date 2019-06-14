package ir.fearefull.wheretoapp.view.search;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import ir.fearefull.wheretoapp.R;

public class UserSearchViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView;
    private ImageView profileImageView;

    public UserSearchViewHolder(View view) {
        super(view);
        nameTextView = view.findViewById(R.id.nameTextView);
        profileImageView = view.findViewById(R.id.profileImageView);
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public void setNameTextView(TextView nameTextView) {
        this.nameTextView = nameTextView;
    }

    public ImageView getProfileImageView() {
        return profileImageView;
    }

    public void setProfileImageView(ImageView profileImageView) {
        this.profileImageView = profileImageView;
    }
}