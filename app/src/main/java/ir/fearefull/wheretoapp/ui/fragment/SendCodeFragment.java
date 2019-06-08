package ir.fearefull.wheretoapp.ui.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;

import java.util.Objects;

import ir.fearefull.wheretoapp.data.local.AppDatabase;
import ir.fearefull.wheretoapp.data.model.db.User;
import ir.fearefull.wheretoapp.ui.EditProfileActivity;
import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.data.model.api.CreateUserRequest;
import ir.fearefull.wheretoapp.data.model.api.UserResponse;
import ir.fearefull.wheretoapp.data.model.api.VerifyRequest;
import ir.fearefull.wheretoapp.data.model.api.VerifyResponse;
import ir.fearefull.wheretoapp.data.remote.GetDataService;
import ir.fearefull.wheretoapp.data.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.ui.MainActivity;
import ir.fearefull.wheretoapp.utils.DatabaseInitializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ir.fearefull.wheretoapp.utils.Constants.staticCountdownNumber;

public class SendCodeFragment extends Fragment {

    private VerifyResponse verifyResponse;
    private Button buttonSendCode;
    private EditText codeEditText;
    private TextView countDownTextView;
    private Handler timerHandler = new Handler();
    private Runnable runnable;
    private int countdownNumber = staticCountdownNumber;
    private boolean isFinished = false;

    public SendCodeFragment() {
    }

    @SuppressLint("ValidFragment")
    public SendCodeFragment(VerifyResponse verifyResponse) {
        this.verifyResponse = verifyResponse;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_send_code, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        buttonSendCode = view.findViewById(R.id.buttonSendCode);
        codeEditText = view.findViewById(R.id.codeEditText);
        countDownTextView = view.findViewById(R.id.countDownTextView);

        buttonSendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonSendCode.isEnabled()) {
                    if (codeEditText.getText().toString().equals(verifyResponse.getVerifyCode())) {
                        try {
                            createUserRequest();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    else {
                        Toast.makeText(getContext(), "کد وارد شده صحیح نیست", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        codeEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEND) {
                    if (buttonSendCode.isEnabled()) {
                        buttonSendCode.performClick();
                    }
                    return true;
                }
                return false;
            }
        });
        codeEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 5) {
                    String text = charSequence.toString();
                    if (text.matches("[0-9]+")) {
                        buttonSendCode.setEnabled(true);
                    }
                    else {
                        buttonSendCode.setEnabled(false);
                    }
                }
                else if (charSequence.length() > 5) {
                    Toast.makeText(getContext(), "کد تایید را به درستی وارد کنید", Toast.LENGTH_SHORT).show();
                    buttonSendCode.setEnabled(false);
                }
                else {
                    buttonSendCode.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        countDownTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    sendCodeRequest();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        runnable = new Runnable() {
            @Override
            public void run() {
                if (countdownNumber > 0 && !isFinished) {
                    countDownTextView.setText(String.valueOf(countdownNumber));
                    countdownNumber -= 1;
                    timerHandler.postDelayed(this, 1000);
                }
                else if (countdownNumber <= 0 && !isFinished) {
                    countDownTextView.setText("ارسال مجدد کد؟");
                    countDownTextView.setTextColor(getResources().getColor(R.color.colorPrimary));
                    countdownNumber = staticCountdownNumber;
                }
            }
        };
        timerHandler.post(runnable);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isFinished = true;
    }

    public void sendCodeRequest() throws JSONException {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<VerifyResponse> call = service.verifyUser(new VerifyRequest(verifyResponse.getPhoneNumber()).toRequestBody());
        call.enqueue(new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateVerifyCodeData(response.body());
            }

            @Override
            public void onFailure(Call<VerifyResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createUserRequest() throws JSONException {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<UserResponse> call = service.createUser(new CreateUserRequest(verifyResponse.getPhoneNumber()).toRequestBody());
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateCreateUserData(response.body());
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateVerifyCodeData(VerifyResponse verifyResponse) {
        this.verifyResponse = verifyResponse;
        countDownTextView.setTextColor(getResources().getColor(R.color.darker_gray));
        timerHandler.post(runnable);
    }

    private void generateCreateUserData(UserResponse userResponse) {
        DatabaseInitializer.resetUsers(AppDatabase.getAppDatabase(getContext()));
        User user = new User(userResponse.getPhoneNumber());
        DatabaseInitializer.addUser(AppDatabase.getAppDatabase(getContext()), user);
        if (userResponse.getFirstName().equals("") || userResponse.getLastName().equals(""))
            showEditProfileActivity(userResponse);
        else
            showMainActivity(userResponse);
    }

    private void showEditProfileActivity(UserResponse userResponse) {
        Intent dashboardIntent = new Intent(getContext(), EditProfileActivity.class);
        dashboardIntent.putExtra("UserResponse", userResponse);

        startActivity(dashboardIntent);
        Objects.requireNonNull(getActivity()).finish();
    }

    private void showMainActivity(UserResponse userResponse) {
        Intent dashboardIntent = new Intent(getContext(), MainActivity.class);
        startActivity(dashboardIntent);
        Objects.requireNonNull(getActivity()).finish();
    }
}