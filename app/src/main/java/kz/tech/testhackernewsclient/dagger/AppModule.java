package kz.tech.testhackernewsclient.dagger;

import android.content.Context;

import androidx.room.Room;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import kz.tech.testhackernewsclient.db.AppDatabase;
import kz.tech.testhackernewsclient.db.DaoBest;
import kz.tech.testhackernewsclient.db.DaoNew;
import kz.tech.testhackernewsclient.db.DaoTop;


@Module
public class AppModule {
    private Context context;
    public AppModule(Context context) {
        this.context = context;
    }
    @Singleton
    @Provides
    public Context provideContext(){
        return context;
    }
    @Singleton
    @Provides
    public AppDatabase provideDatabase(Context context) {
        return Room.databaseBuilder(context, AppDatabase.class, "database")
                .build();
    }
    @Singleton @Provides
    public DaoNew provideDaoNew(AppDatabase appDatabase){
        return appDatabase.daoNew();
    }
    @Singleton @Provides
    public DaoTop provideDaoTop(AppDatabase appDatabase){
        return appDatabase.daoTop();
    }
    @Singleton @Provides
    public DaoBest provideDaoBest(AppDatabase appDatabase){
        return appDatabase.daoBest();
    }
}
