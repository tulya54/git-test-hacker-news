package kz.tech.testhackernewsclient.mvp;

import kz.tech.testhackernewsclient.db.DaoNew;

public interface IPresenter {
    void onStartStorage();
    void updateStories(int n);
    void getNewStories();
    void getTopStories();
    void getBestStories();
    void onDestroyView();
}
