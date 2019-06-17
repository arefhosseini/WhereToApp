package ir.fearefull.wheretoapp.controller.view_controller.relation;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.view_controller.user.UserFragment;
import ir.fearefull.wheretoapp.model.api.user.relation.UserRelation;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.controller.view_controller.base.MyFragment;

public class RelationPartFragment extends MyFragment implements RelationAdapterCallBack {

    private User user;
        private List<UserRelation> userRelationList;

        private View parentView;
        private RelationAdapter relationAdapter;
        private RecyclerView recyclerViewRelations;

    public RelationPartFragment(){
    }

    @SuppressLint("ValidFragment")
    public RelationPartFragment(String TAG, User user, List<UserRelation> userRelationList){
        this.TAG = TAG;
        this.user = user;
        this.userRelationList = new ArrayList<>();
        this.userRelationList.addAll(userRelationList);
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
        relationAdapter = new RelationAdapter(this, getContext(), this.userRelationList, user);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewRelations.addItemDecoration(
                new DividerItemDecoration(recyclerViewRelations.getContext(), DividerItemDecoration.VERTICAL));
        recyclerViewRelations.setLayoutManager(layoutManager);
        recyclerViewRelations.setAdapter(relationAdapter);
    }

    @Override
    public void onOpenUserFragment(String phoneNumber) {
        UserFragment fragment = new UserFragment(TAG, user, phoneNumber);
        openFragment(fragment, R.id.fragmentRelation);
    }
}
