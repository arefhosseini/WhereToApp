package ir.fearefull.wheretoapp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.Objects;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import ir.fearefull.wheretoapp.R;

public class AddReviewDialog extends DialogFragment implements View.OnClickListener {

    private Button confirmButton;
    private View rootView;
    private EditText reviewEditText;


    public AddReviewDialog() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.dialog_add_review, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        rootView = view.findViewById(R.id.rootView);
        confirmButton = view.findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(this);
        reviewEditText = view.findViewById(R.id.reviewEditText);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        Intent i = new Intent()
                .putExtra("reviewText", reviewEditText.getText().toString());
        Objects.requireNonNull(getTargetFragment()).onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, i);
        dismiss();
    }
}
