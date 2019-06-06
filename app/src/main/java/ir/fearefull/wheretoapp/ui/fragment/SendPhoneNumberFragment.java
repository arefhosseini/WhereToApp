package ir.fearefull.wheretoapp.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.data.model.api.VerifyRequest;
import ir.fearefull.wheretoapp.data.model.api.VerifyResponse;
import ir.fearefull.wheretoapp.data.model.db.User;
import ir.fearefull.wheretoapp.data.remote.GetDataService;
import ir.fearefull.wheretoapp.data.remote.RetrofitClientInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendPhoneNumberFragment extends Fragment {

    private Button buttonSendPhone;
    private OnClickListener listener;
    private EditText phoneNumberEditText;
    private String phoneNumber;
    private String code;
    private String token;
    private User user;

    public SendPhoneNumberFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_send_phone_number, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        buttonSendPhone =  view.findViewById(R.id.buttonSendPhone);
        phoneNumberEditText = view.findViewById(R.id.phoneNumberEditText);

        buttonSendPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonSendPhone.isEnabled()) {
                    phoneNumber = phoneNumberEditText.getText().toString();
                    try {
                        sendCodeRequest();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getContext(), "شماره تلفن را به درستی وارد کنید", Toast.LENGTH_SHORT).show();
                }
            }
        });

        phoneNumberEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            public boolean onEditorAction(TextView v, int actionId,
                                          KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_GO || actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_SEND) {
                    if (buttonSendPhone.isEnabled()) {
                        buttonSendPhone.performClick();
                    }
                    return true;
                }
                return false;
            }
        });

        phoneNumberEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 11) {
                    String text = charSequence.toString();
                    if (text.matches("[0-9]+") && text.startsWith("09")) {
                        buttonSendPhone.setEnabled(true);
                    }
                    else {
                        buttonSendPhone.setEnabled(false);
                    }
                }
                else if (charSequence.length() > 11) {
                    Toast.makeText(getContext(), "شماره تلفن را به درستی وارد کنید", Toast.LENGTH_SHORT).show();
                    buttonSendPhone.setEnabled(false);
                }
                else {
                    buttonSendPhone.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    public void sendCodeRequest() throws JSONException {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<VerifyResponse> call = service.verifyUser(new VerifyRequest(phoneNumber).toRequestBody());
        call.enqueue(new Callback<VerifyResponse>() {
            @Override
            public void onResponse(Call<VerifyResponse> call, Response<VerifyResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateData(response.body());
            }

            @Override
            public void onFailure(Call<VerifyResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateData(VerifyResponse verifyResponse) {
        Log.d("sendPhone", verifyResponse.getVerifyCode());
        listener.onSendPhoneListener(verifyResponse);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnClickListener){      // context instanceof YourActivity
            this.listener = (OnClickListener) context; // = (YourActivity) context
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement SendPhoneFragment.OnSelectedListener");
        }
    }

    public interface OnClickListener {
        void onSendPhoneListener(VerifyResponse verifyResponse);
    }
}