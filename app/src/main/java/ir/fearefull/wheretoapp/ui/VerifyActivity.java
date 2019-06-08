package ir.fearefull.wheretoapp.ui;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.data.model.api.VerifyResponse;
import ir.fearefull.wheretoapp.ui.fragment.SendCodeFragment;
import ir.fearefull.wheretoapp.ui.fragment.SendPhoneNumberFragment;

public class VerifyActivity extends AppCompatActivity implements SendPhoneNumberFragment.OnClickListener {

    private Toolbar toolbar;
    VerifyActivity verifyActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        verifyActivity = this;

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (savedInstanceState == null) {
            // Instance of first fragment
            SendPhoneNumberFragment sendPhoneFragment = new SendPhoneNumberFragment();

            // Add Fragment to FrameLayout (SignupContainer), using FragmentManager
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();// begin  FragmentTransaction
            ft.add(R.id.SignupContainer, sendPhoneFragment);                                // add    Fragment
            ft.commit();                                                    // commit FragmentTransaction
        }
    }

    @Override
    public void onSendPhoneListener(VerifyResponse verifyResponse) {
        SendCodeFragment sendCodeFragment = new SendCodeFragment(verifyResponse);

        Bundle args = new Bundle();
        args.putInt("fragment", 1);
        sendCodeFragment.setArguments(args);          // (1) Communicate with Fragment using Bundle
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.SignupContainer, sendCodeFragment) // replace flContainer
                .addToBackStack(null)
                .commit();
    }
}