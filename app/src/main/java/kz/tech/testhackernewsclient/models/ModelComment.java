package kz.tech.testhackernewsclient.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelComment {
    @Nullable
    @SerializedName("by")
    @Expose
    private String by;

    @Nullable
    @SerializedName("id")
    @Expose
    private Integer id;

    @Nullable
    @SerializedName("kids")
    @Expose
    private List<Integer> kids;

    @Nullable
    @SerializedName("parent")
    @Expose
    private Integer parent;

    @Nullable
    @SerializedName("text")
    @Expose
    private String text;

    @Nullable
    @SerializedName("time")
    @Expose
    private Integer time;

    @Nullable
    @SerializedName("type")
    @Expose
    private String type;
}
