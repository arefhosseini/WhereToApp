package ir.fearefull.wheretoapp.controller.view_controller.relation.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.view_controller.relation.adapter.RelationAdapter;
import ir.fearefull.wheretoapp.model.api.user.relation.UserRelation;
import ir.fearefull.wheretoapp.model.db.User;

public class RelationPartFragment extends Fragment {

    private User user;
        private List<UserRelation> userRelationList;

        private View parentView;
        private RelationAdapter relationAdapter;
        private RecyclerView recyclerViewRelations;

    public RelationPartFragment(){
    }

    @SuppressLint("ValidFragment")
    public RelationPartFragment(User user, List<UserRelation> userRelationList){
        this.user = user;
        this.userRelationList = userRelationList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_relation_part, container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerViewRelations = parentView.findViewById(R.id.recyclerViewRelations);
        relationAdapter = new RelationAdapter(this.userRelationList, user, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewRelations.addItemDecoration(
                new DividerItemDecoration(recyclerViewRelations.getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewRelations.setLayoutManager(layoutManager);
        recyclerViewRelations.setAdapter(relationAdapter);
    }

}
