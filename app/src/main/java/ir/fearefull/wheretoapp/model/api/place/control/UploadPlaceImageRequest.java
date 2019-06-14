package ir.fearefull.wheretoapp.model.api.place.control;

import android.content.Context;
import android.net.Uri;

import java.io.File;

import ir.fearefull.wheretoapp.utils.FileUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UploadPlaceImageRequest {

    private RequestBody user;
    private RequestBody place;
    private MultipartBody.Part image;

    public UploadPlaceImageRequest(String phoneNumber, long placeId, Uri imageUri, Context context) {
        this.user = RequestBody.create(MultipartBody.FORM, phoneNumber);
        this.place = RequestBody.create(MultipartBody.FORM, String.valueOf(placeId));

        File file = FileUtils.getFile(context, imageUri);
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);

        this.image =
                MultipartBody.Part.createFormData("image", file.getName(), requestFile);
    }

    public RequestBody getUser() {
        return user;
    }

    public MultipartBody.Part getImage() {
        return image;
    }

    public RequestBody getPlace() {
        return place;
    }
}
