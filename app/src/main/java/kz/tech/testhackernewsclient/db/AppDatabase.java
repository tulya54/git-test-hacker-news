package kz.tech.testhackernewsclient.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {NewStories.class, TopStories.class, BestStories.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DaoNew daoNew();
    public abstract DaoTop daoTop();
    public abstract DaoBest daoBest();
}
