package ir.fearefull.wheretoapp.model.api.place.menu;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

public class Menu extends ExpandableGroup<Food> {

    public Menu(String title, List<Food> foods) {
        super(title, foods);
    }
}