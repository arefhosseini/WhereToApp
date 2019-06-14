package ir.fearefull.wheretoapp.model.api.place.menu;

import android.os.Parcel;
import android.os.Parcelable;

public class Food implements Parcelable {

    private String name;
    private String detail;
    private int price;

    public Food(String name, String detail, int price) {
        this.name = name;
        this.detail = detail;
        this.price = price;
    }

    private Food(Parcel in) {
        name = in.readString();
        detail = in.readString();
        price = in.readInt();
    }

    public static final Creator<Food> CREATOR = new Creator<Food>() {
        @Override
        public Food createFromParcel(Parcel in) {
            return new Food(in);
        }

        @Override
        public Food[] newArray(int size) {
            return new Food[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDetail() {
        return detail;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(detail);
        parcel.writeInt(price);
    }
}