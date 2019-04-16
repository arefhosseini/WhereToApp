package ir.fearefull.wheretoapp.Network;


import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static ir.fearefull.wheretoapp.Constants.BASE_URL;

public class RetrofitClientInstance {

    private static Retrofit retrofit;

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}