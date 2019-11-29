package kz.tech.testhackernewsclient.dagger;

import javax.inject.Singleton;

import dagger.Component;
import kz.tech.testhackernewsclient.MainActivity;

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {
    void injectsActivity(MainActivity main);
}
