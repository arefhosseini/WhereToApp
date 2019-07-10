package ir.fearefull.wheretoapp.controller.view_controller.verify;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;
import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.view_controller.verify.fragment.SendCodeFragment;
import ir.fearefull.wheretoapp.controller.view_controller.verify.fragment.SendPhoneNumberFragment;
import ir.fearefull.wheretoapp.model.api.user.control.VerifyUserResponse;

public class VerifyActivity extends AppCompatActivity implements SendPhoneNumberFragment.OnClickListener {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
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
    public void onSendPhoneListener(VerifyUserResponse verifyUserResponse) {
        SendCodeFragment sendCodeFragment = new SendCodeFragment(verifyUserResponse);

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
