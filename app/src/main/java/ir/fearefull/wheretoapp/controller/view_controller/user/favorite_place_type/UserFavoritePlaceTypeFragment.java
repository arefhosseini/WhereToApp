package ir.fearefull.wheretoapp.controller.view_controller.user.favorite_place_type;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import ir.fearefull.wheretoapp.R;
import ir.fearefull.wheretoapp.controller.data_controller.remote.GetDataService;
import ir.fearefull.wheretoapp.controller.data_controller.remote.RetrofitClientInstance;
import ir.fearefull.wheretoapp.controller.view_controller.base.MyFragment;
import ir.fearefull.wheretoapp.controller.view_controller.home.profile.UserFavoritePlaceTypeCallBack;
import ir.fearefull.wheretoapp.model.api.Enum.PlaceTypeEnum;
import ir.fearefull.wheretoapp.model.api.SimpleResponse;
import ir.fearefull.wheretoapp.model.api.user.UserResponse;
import ir.fearefull.wheretoapp.model.api.user.favorite_place_type.UserFavoritePlaceTypeRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFavoritePlaceTypeFragment extends MyFragment {

    private UserFavoritePlaceTypeCallBack callBack;
    private UserResponse userResponse;
    private List<PlaceTypeEnum> userFavoritePlaceTypes = new ArrayList<>();
    private LinearLayout typeItalianLayout, typeFastFoodLayout, typeIraniLayout, typeCafeLayout;
    private TextView typeItalianTextView, typeFastFoodTextView, typeIraniTextView, typeCafeTextView;
    private ImageView typeItalianImageView, typeFastFoodImageView, typeIraniImageView, typeCafeImageView;
    private Button confirmButton;
    private ImageButton backImageButton;

    public UserFavoritePlaceTypeFragment(){
    }

    public UserFavoritePlaceTypeFragment(UserFavoritePlaceTypeCallBack callBack, String TAG, UserResponse userResponse){
        this.callBack = callBack;
        this.TAG = TAG;
        this.userResponse = userResponse;
        userFavoritePlaceTypes.addAll(userResponse.getFavoritePlaceTypes());
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_user_favorite_place_type, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        backImageButton = view.findViewById(R.id.backImageButton);
        typeItalianLayout = view.findViewById(R.id.typeItalianLayout);
        typeFastFoodLayout = view.findViewById(R.id.typeFastFoodLayout);
        typeIraniLayout = view.findViewById(R.id.typeIraniLayout);
        typeCafeLayout = view.findViewById(R.id.typeCafeLayout);
        typeItalianTextView = view.findViewById(R.id.typeItalianTextView);
        typeFastFoodTextView = view.findViewById(R.id.typeFastFoodTextView);
        typeIraniTextView = view.findViewById(R.id.typeIraniTextView);
        typeCafeTextView = view.findViewById(R.id.typeCafeTextView);
        typeItalianImageView = view.findViewById(R.id.typeItalianImageView);
        typeFastFoodImageView = view.findViewById(R.id.typeFastFoodImageView);
        typeIraniImageView = view.findViewById(R.id.typeIraniImageView);
        typeCafeImageView = view.findViewById(R.id.typeCafeImageView);
        confirmButton = view.findViewById(R.id.confirmButton);

        if (userResponse.getFavoritePlaceTypes().contains(PlaceTypeEnum.Italian)) {
            typeItalianTextView.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary));
            typeItalianImageView.setImageResource(R.mipmap.type_italian_selected);
        }
        if (userResponse.getFavoritePlaceTypes().contains(PlaceTypeEnum.FastFood)) {
            typeFastFoodTextView.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary));
            typeFastFoodImageView.setImageResource(R.mipmap.type_fast_food_selected);
        }
        if (userResponse.getFavoritePlaceTypes().contains(PlaceTypeEnum.Irani)) {
            typeIraniTextView.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary));
            typeIraniImageView.setImageResource(R.mipmap.type_irani_selected);
        }
        if (userResponse.getFavoritePlaceTypes().contains(PlaceTypeEnum.Cafe)) {
            typeCafeTextView.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary));
            typeCafeImageView.setImageResource(R.mipmap.type_cafe_selected);
        }

        backImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        typeItalianLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userFavoritePlaceTypes.contains(PlaceTypeEnum.Italian)) {
                    typeItalianTextView.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary));
                    typeItalianImageView.setImageResource(R.mipmap.type_italian_selected);
                    userFavoritePlaceTypes.add(PlaceTypeEnum.Italian);
                }
                else {
                    typeItalianTextView.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.textDarkPrimary));
                    typeItalianImageView.setImageResource(R.mipmap.type_italian_unselected);
                    userFavoritePlaceTypes.remove(PlaceTypeEnum.Italian);
                }
            }
        });

        typeFastFoodLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userFavoritePlaceTypes.contains(PlaceTypeEnum.FastFood)) {
                    typeFastFoodTextView.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary));
                    typeFastFoodImageView.setImageResource(R.mipmap.type_fast_food_selected);
                    userFavoritePlaceTypes.add(PlaceTypeEnum.FastFood);
                }
                else {
                    typeFastFoodTextView.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.textDarkPrimary));
                    typeFastFoodImageView.setImageResource(R.mipmap.type_fast_food_unselected);
                    userFavoritePlaceTypes.remove(PlaceTypeEnum.FastFood);
                }
            }
        });

        typeIraniLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userFavoritePlaceTypes.contains(PlaceTypeEnum.Irani)) {
                    typeIraniTextView.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary));
                    typeIraniImageView.setImageResource(R.mipmap.type_irani_selected);
                    userFavoritePlaceTypes.add(PlaceTypeEnum.Irani);
                }
                else {
                    typeIraniTextView.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.textDarkPrimary));
                    typeIraniImageView.setImageResource(R.mipmap.type_irani_unselected);
                    userFavoritePlaceTypes.remove(PlaceTypeEnum.Irani);
                }
            }
        });

        typeCafeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!userFavoritePlaceTypes.contains(PlaceTypeEnum.Cafe)) {
                    typeCafeTextView.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.colorPrimary));
                    typeCafeImageView.setImageResource(R.mipmap.type_cafe_selected);
                    userFavoritePlaceTypes.add(PlaceTypeEnum.Cafe);
                }
                else {
                    typeCafeTextView.setTextColor(ContextCompat.getColor(Objects.requireNonNull(getContext()), R.color.textDarkPrimary));
                    typeCafeImageView.setImageResource(R.mipmap.type_cafe_unselected);
                    userFavoritePlaceTypes.remove(PlaceTypeEnum.Cafe);
                }
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserFavoritePlaceTypeRequest userFavoritePlaceTypeRequest = new UserFavoritePlaceTypeRequest(userResponse.getPhoneNumber());
                for (PlaceTypeEnum placeTypeEnum: userFavoritePlaceTypes) {
                    if (!userResponse.getFavoritePlaceTypes().contains(placeTypeEnum))
                        userFavoritePlaceTypeRequest.addToAddTypes(placeTypeEnum);
                }
                for (PlaceTypeEnum placeTypeEnum: userResponse.getFavoritePlaceTypes()) {
                    if (!userFavoritePlaceTypes.contains(placeTypeEnum))
                        userFavoritePlaceTypeRequest.addToDeleteTypes(placeTypeEnum);
                }

                GetDataService service = RetrofitClientInstance.getRetrofitInstance().create(GetDataService.class);
                Call<SimpleResponse> call;
                try {
                    call = service.controlUserFavoritePlaceType(userFavoritePlaceTypeRequest.toRequestBody());
                    call.enqueue(new Callback<SimpleResponse>() {
                        @Override
                        public void onResponse(Call<SimpleResponse> call, Response<SimpleResponse> response) {
                            //progressDoalog.dismiss();
                            assert response.body() != null;
                            Toast.makeText(getContext(), "سلیقه شما ثبت شد", Toast.LENGTH_LONG).show();
                            callBack.changeUserFavoritePlace(userFavoritePlaceTypes);
                            finish();
                        }

                        @Override
                        public void onFailure(Call<SimpleResponse> call, Throwable t) {
                            //progressDoalog.dismiss();
                            Toast.makeText(getContext(), "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
