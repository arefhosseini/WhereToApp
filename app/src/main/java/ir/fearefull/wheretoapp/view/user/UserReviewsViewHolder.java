package ir.fearefull.wheretoapp.view.user;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import ir.fearefull.wheretoapp.R;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;

public class UserReviewsViewHolder extends RecyclerView.ViewHolder {
    private TextView createdDateTextView, nameTextView, textTextView,
            downVoteTextView, upVoteTextView, placeTextView;
    private ImageView profileImageView;
    private ImageButton downVoteImageButton, upVoteImageButton;
    private MaterialRatingBar userScoreRatingBar;

    public UserReviewsViewHolder(View view) {
        super(view);
        createdDateTextView = view.findViewById(R.id.createdDateTextView);
        nameTextView = view.findViewById(R.id.nameTextView);
        textTextView = view.findViewById(R.id.textTextView);
        downVoteTextView = view.findViewById(R.id.downVoteTextView);
        upVoteTextView = view.findViewById(R.id.upVoteTextView);
        placeTextView = view.findViewById(R.id.placeTextView);
        profileImageView = view.findViewById(R.id.profileImageView);
        downVoteImageButton = view.findViewById(R.id.downVoteImageButton);
        upVoteImageButton = view.findViewById(R.id.upVoteImageButton);
        userScoreRatingBar = view.findViewById(R.id.userScoreRatingBar);
    }

    public TextView getCreatedDateTextView() {
        return createdDateTextView;
    }

    public void setCreatedDateTextView(TextView createdDateTextView) {
        this.createdDateTextView = createdDateTextView;
    }

    public TextView getNameTextView() {
        return nameTextView;
    }

    public void setNameTextView(TextView nameTextView) {
        this.nameTextView = nameTextView;
    }

    public TextView getTextTextView() {
        return textTextView;
    }

    public void setTextTextView(TextView textTextView) {
        this.textTextView = textTextView;
    }

    public TextView getDownVoteTextView() {
        return downVoteTextView;
    }

    public void setDownVoteTextView(TextView downVoteTextView) {
        this.downVoteTextView = downVoteTextView;
    }

    public TextView getUpVoteTextView() {
        return upVoteTextView;
    }

    public void setUpVoteTextView(TextView upVoteTextView) {
        this.upVoteTextView = upVoteTextView;
    }

    public ImageView getProfileImageView() {
        return profileImageView;
    }

    public void setProfileImageView(ImageView profileImageView) {
        this.profileImageView = profileImageView;
    }

    public MaterialRatingBar getUserScoreRatingBar() {
        return userScoreRatingBar;
    }

    public void setUserScoreRatingBar(MaterialRatingBar userScoreRatingBar) {
        this.userScoreRatingBar = userScoreRatingBar;
    }

    public TextView getPlaceTextView() {
        return placeTextView;
    }

    public void setPlaceTextView(TextView placeTextView) {
        this.placeTextView = placeTextView;
    }

    public ImageButton getDownVoteImageButton() {
        return downVoteImageButton;
    }

    public void setDownVoteImageButton(ImageButton downVoteImageButton) {
        this.downVoteImageButton = downVoteImageButton;
    }

    public ImageButton getUpVoteImageButton() {
        return upVoteImageButton;
    }

    public void setUpVoteImageButton(ImageButton upVoteImageButton) {
        this.upVoteImageButton = upVoteImageButton;
    }
}