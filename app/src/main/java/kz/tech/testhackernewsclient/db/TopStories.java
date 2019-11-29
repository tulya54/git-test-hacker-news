package kz.tech.testhackernewsclient.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "top")
public class TopStories {

    @PrimaryKey(autoGenerate = true)
    public long id;

    public String idZ;

    public String title;

    public String url;

    public TopStories() {}

    public TopStories(String idZ, String title, String url) {
        this.idZ = idZ;
        this.title = title;
        this.url = url;
    }

    public String getIdZ() {
        return idZ;
    }

    public void setIdZ(String idZ) {
        this.idZ = idZ;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
