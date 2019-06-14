package ir.fearefull.wheretoapp.model.api;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class SimpleResponse implements Serializable {

    @SerializedName("status")
    private String status;

    public SimpleResponse(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
