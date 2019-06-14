package ir.fearefull.wheretoapp.model.api.user.control;

import android.content.Context;
import android.net.Uri;

import java.io.File;

import ir.fearefull.wheretoapp.utils.FileUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadProfileImageRequest {

    private RequestBody phoneNumber;
    private MultipartBody.Part profileImage;

    public UploadProfileImageRequest(String phoneNumber, Uri profileImageUri, Context context) {
        this.phoneNumber = RequestBody.create(
                okhttp3.MultipartBody.FORM, phoneNumber);

        File file = FileUtils.getFile(context, profileImageUri);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        this.profileImage =
                MultipartBody.Part.createFormData("profile_image", file.getName(), requestFile);
    }

    public RequestBody getPhoneNumber() {
        return phoneNumber;
    }

    public MultipartBody.Part getProfileImage() {
        return profileImage;
    }
}
