package ir.fearefull.wheretoapp.view.place;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import java.util.Objects;

import ir.fearefull.wheretoapp.R;

public class MenuViewHolder extends GroupViewHolder {

    private Fragment fragment;
    private TextView menuTextView;
    private ImageView menuImageView;

    public MenuViewHolder(View itemView, Fragment fragment) {
        super(itemView);
        this.fragment = fragment;
        menuTextView = itemView.findViewById(R.id.nameTextView);
        menuImageView = itemView.findViewById(R.id.menuImageView);
    }

    public void setMenuName(ExpandableGroup group) {
        menuTextView.setText(group.getTitle());
    }

    @Override
    public void expand() {
        menuTextView.setTextColor(ContextCompat.getColor(
                Objects.requireNonNull(fragment.getContext()), R.color.colorPrimaryDark));
        menuImageView.setImageResource(R.drawable.ic_keyboard_arrow_up_primary_dark_24dp);
    }

    @Override
    public void collapse() {
        menuTextView.setTextColor(ContextCompat.getColor(
                Objects.requireNonNull(fragment.getContext()), R.color.colorPrimary));
        menuImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_primary_24dp);
    }
}
