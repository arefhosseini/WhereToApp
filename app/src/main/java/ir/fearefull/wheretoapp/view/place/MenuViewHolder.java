package ir.fearefull.wheretoapp.view.place;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

import ir.fearefull.wheretoapp.R;

public class MenuViewHolder extends GroupViewHolder {

    private Context context;
    private TextView menuTextView;
    private ImageView menuImageView;

    public MenuViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        menuTextView = itemView.findViewById(R.id.nameTextView);
        menuImageView = itemView.findViewById(R.id.menuImageView);
    }

    public void setMenuName(ExpandableGroup group) {
        menuTextView.setText(group.getTitle());
    }

    @Override
    public void expand() {
        menuTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        menuImageView.setImageResource(R.drawable.ic_keyboard_arrow_up_primary_dark_24dp);
    }

    @Override
    public void collapse() {
        menuTextView.setTextColor(ContextCompat.getColor(context, R.color.colorPrimary));
        menuImageView.setImageResource(R.drawable.ic_keyboard_arrow_down_primary_24dp);
    }
}
