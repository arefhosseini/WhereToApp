package ir.fearefull.wheretoapp.view.relation;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;
import ir.fearefull.wheretoapp.R;

public class RelationViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView, followTextView;
    private ImageView profileImageView;

    public RelationViewHolder(View view) {
        super(view);
        nameTextView = view.findViewById(R.id.nameTextView);
        followTextView = view.findViewById(R.id.followTextView);
        profileImageView = view.findViewById(R.id.profileImageView);
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public void setNameTextView(TextView nameTextView) {
        this.nameTextView = nameTextView;
    }

    public TextView getFollowTextView() {
        return followTextView;
    }

    public void setFollowTextView(TextView followTextView) {
        this.followTextView = followTextView;
    }

    public ImageView getProfileImageView() {
        return profileImageView;
    }

    public void setProfileImageView(ImageView profileImageView) {
        this.profileImageView = profileImageView;
    }
}