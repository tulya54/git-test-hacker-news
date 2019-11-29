package kz.tech.testhackernewsclient;

import android.app.Application;

import kz.tech.testhackernewsclient.dagger.AppComponent;
import kz.tech.testhackernewsclient.dagger.AppModule;
import kz.tech.testhackernewsclient.dagger.DaggerAppComponent;

public class App extends Application {
    private static App app;
    private AppComponent appComponent;
    public AppComponent getComponent() {
        return appComponent;
    }
    public static App app(){
        return app;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(getApplicationContext())).build();
    }
}
