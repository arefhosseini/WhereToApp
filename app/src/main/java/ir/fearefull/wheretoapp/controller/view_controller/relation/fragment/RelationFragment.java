package ir.fearefull.wheretoapp.controller.view_controller.relation.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.view_controller.relation.adapter.RelationAdapter;
import ir.fearefull.wheretoapp.model.api.relation.Relation;
import ir.fearefull.wheretoapp.model.db.User;

public class RelationFragment extends Fragment {

    private User user;
    private List<Relation> relationList;

    private View parentView;
    private RelationAdapter relationAdapter;
    private RecyclerView recyclerViewRelations;

    public RelationFragment(){
    }

    @SuppressLint("ValidFragment")
    public RelationFragment(User user, List<Relation> relationList){
        this.user = user;
        this.relationList = relationList;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        parentView = inflater.inflate(R.layout.fragment_relation, container, false);
        return parentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        recyclerViewRelations = parentView.findViewById(R.id.recyclerViewRelations);
        relationAdapter = new RelationAdapter(this.relationList, user, getActivity());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerViewRelations.setLayoutManager(layoutManager);
        recyclerViewRelations.setAdapter(relationAdapter);
    }

}
