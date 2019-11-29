package kz.tech.testhackernewsclient.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Model {
    @Nullable
    @SerializedName("id")
    @Expose
    private String id;
    @Nullable
    @SerializedName("title")
    @Expose
    private String title;
    @Nullable
    @SerializedName("url")
    @Expose
    private String url;

    public Model() {}

    public Model(@Nullable String id, @Nullable String title, @Nullable String url) {
        this.id = id;
        this.title = title;
        this.url = url;
    }

    @Nullable
    public String getId() {
        return id;
    }

    public void setId(@Nullable String id) {
        this.id = id;
    }

    @Nullable
    public String getTitle() {
        return title;
    }

    public void setTitle(@Nullable String title) {
        this.title = title;
    }

    @Nullable
    public String getUrl() {
        return url;
    }

    public void setUrl(@Nullable String url) {
        this.url = url;
    }
}
