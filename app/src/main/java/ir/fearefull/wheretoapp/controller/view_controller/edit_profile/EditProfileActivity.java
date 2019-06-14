package ir.fearefull.wheretoapp.controller.view_controller.edit_profile;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.json.JSONException;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.local.AppDatabase;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.home.HomeActivity;
import ir.fearefull.wheretoapp.model.api.SimpleResponse;
import ir.fearefull.wheretoapp.model.api.user.control.EditUserRequest;
import ir.fearefull.wheretoapp.model.api.user.control.UploadProfileImageRequest;
import ir.fearefull.wheretoapp.model.api.user.control.UserControlResponse;
import ir.fearefull.wheretoapp.model.db.User;
import ir.fearefull.wheretoapp.utils.Constants;
import ir.fearefull.wheretoapp.utils.DatabaseInitializer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static ir.fearefull.wheretoapp.utils.Constants.PICK_FROM_GALLERY;


public class EditProfileActivity extends AppCompatActivity {
    UserControlResponse userControlResponse;
    EditText phoneNumberEditText, firstNameEditText, lastNameEditText;
    Button confirmButton;
    ImageButton backImageButton;
    FrameLayout profileImageLayout;
    ImageView profileImageView;
    Uri resultUri;

    boolean sendProfileImage = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userControlResponse = (UserControlResponse) getIntent().getSerializableExtra("UserControlResponse");
        backImageButton = findViewById(R.id.backImageButton);
        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        confirmButton = findViewById(R.id.confirmButton);
        profileImageLayout = findViewById(R.id.profileImageLayout);
        profileImageView = findViewById(R.id.profileImageView);

        Picasso.get().load(Constants.BASE_URL + userControlResponse.getProfileImage()).into(profileImageView);
        phoneNumberEditText.setText(userControlResponse.getPhoneNumber());
        firstNameEditText.setText(userControlResponse.getFirstName());
        lastNameEditText.setText(userControlResponse.getLastName());

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sendProfileImage)
                    uploadImage();
                checkEditProfile();
            }
        });
        profileImageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    if (ActivityCompat.checkSelfPermission(EditProfileActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(EditProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PICK_FROM_GALLERY);
                    } else {
                        CropImage.activity()
                                .setFixAspectRatio(true)
                                .setAspectRatio(1, 1)
                                .setRequestedSize(200, 200, CropImageView.RequestSizeOptions.RESIZE_INSIDE)
                                .start(EditProfileActivity.this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults)
    {
        switch (requestCode) {
            case PICK_FROM_GALLERY:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    CropImage.activity()
                            .setFixAspectRatio(true)
                            .setAspectRatio(1, 1)
                            .setRequestedSize(200, 200, CropImageView.RequestSizeOptions.RESIZE_INSIDE)
                            .start(EditProfileActivity.this);
                } else {
                    Toast.makeText(this, "لطفا دسترسی به گالری را بدهید", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                profileImageView.setImageURI(resultUri);
                sendProfileImage = true;
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private void uploadImage() {
        UploadProfileImageRequest uploadProfileImageRequest = new UploadProfileImageRequest(
                userControlResponse.getPhoneNumber(), resultUri, getApplicationContext());

        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<SimpleResponse> call =
                service.uploadUserProfileImage(uploadProfileImageRequest.getPhoneNumber(), uploadProfileImageRequest.getProfileImage());
        call.enqueue(new Callback<SimpleResponse>() {
            @Override
            public void onResponse(Call<SimpleResponse> call,
                                   Response<SimpleResponse> response) {
                Log.v("Upload", "success");
                Toast.makeText(getApplicationContext(), "عکس شما آپلود شد", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<SimpleResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "عکس شما آپلود نشد", Toast.LENGTH_SHORT).show();
                Log.e("Upload error:", t.getMessage());
            }
        });
    }

    private void checkEditProfile() {
        String firstName = firstNameEditText.getText().toString();
        String lastName = lastNameEditText.getText().toString();
        if (!firstName.equals("") || !lastName.equals("")) {
            try {
                editUser(firstName, lastName);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        else
            finishEditing();

    }

    private void editUser(String firstName, String lastName) throws JSONException {
        GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
        Call<UserControlResponse> call = service.editUser(new EditUserRequest(userControlResponse.getPhoneNumber(),
                firstName, lastName).toRequestBody());
        call.enqueue(new Callback<UserControlResponse>() {
            @Override
            public void onResponse(Call<UserControlResponse> call, Response<UserControlResponse> response) {
                //progressDoalog.dismiss();
                assert response.body() != null;
                generateData(response.body());
            }

            @Override
            public void onFailure(Call<UserControlResponse> call, Throwable t) {
                //progressDoalog.dismiss();
                Toast.makeText(getApplicationContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void generateData(UserControlResponse userControlResponse) {
        this.userControlResponse = userControlResponse;
        Toast.makeText(getApplicationContext(), "اطلاعات شما با موفقیت تغییر پیدا کرد", Toast.LENGTH_SHORT).show();
        User user = DatabaseInitializer.getUser(AppDatabase.getAppDatabase(getApplicationContext()));
        user.setFirstName(userControlResponse.getFirstName());
        user.setLastName(userControlResponse.getLastName());
        user.setProfileImage(userControlResponse.getProfileImage());
        DatabaseInitializer.updateUser(AppDatabase.getAppDatabase(getApplicationContext()), user);

        finishEditing();
    }

    private void finishEditing() {
        Intent dashboardIntent = new Intent(EditProfileActivity.this, HomeActivity.class);
        dashboardIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(dashboardIntent);
    }
}
