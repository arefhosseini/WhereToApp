package ir.fearefull.wheretoapp.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import ir.fearefull.wheretoapp.R;

public class AddScoreDialog extends Dialog implements View.OnClickListener {

    private Button confirmButton;
    private Activity activity;
    private View rootView;

    public AddScoreDialog(Activity activity) {
        super(activity);
        this.activity = activity;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_score);
        rootView = findViewById(R.id.rootView);
        confirmButton = findViewById(R.id.confirmButton);
        confirmButton.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        dismiss();
    }
}
