package ir.fearefull.wheretoapp.controller.view_controller.place.menu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.model.api.place.menu.Food;
import ir.fearefull.wheretoapp.model.api.place.menu.Menu;
import ir.fearefull.wheretoapp.view.place.FoodViewHolder;
import ir.fearefull.wheretoapp.view.place.MenuViewHolder;

public class MenuAdapter extends ExpandableRecyclerViewAdapter<MenuViewHolder, FoodViewHolder> {

    private Context context;

    MenuAdapter(List<? extends ExpandableGroup> groups, Context context) {
        super(groups);
        this.context = context;
    }

    @Override
    public MenuViewHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_place_menu, parent, false);
        return new MenuViewHolder(view, context);
    }

    @Override
    public FoodViewHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_place_menu_food, parent, false);
        return new FoodViewHolder(view, context);
    }

    @Override
    public void onBindChildViewHolder(FoodViewHolder holder, int flatPosition,
                                      ExpandableGroup group, int childIndex) {

        final Food food = ((Menu) group).getItems().get(childIndex);
        holder.getNameTextView().setText(food.getName());
        holder.getDetailTextView().setText(food.getDetail());
        holder.getPriceTextView().setText(String.valueOf(food.getPrice() + " تومان"));
    }

    @Override
    public void onBindGroupViewHolder(MenuViewHolder holder, int flatPosition,
                                      ExpandableGroup group) {
        holder.setMenuName(group);
    }
}