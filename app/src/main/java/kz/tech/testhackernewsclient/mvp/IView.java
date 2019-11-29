package kz.tech.testhackernewsclient.mvp;

import java.util.List;

import kz.tech.testhackernewsclient.models.Model;

public interface IView {
    void setStory(Model m);
    void setStories(List<Model> list);
    void showLoading();
    void hideLoading();
    void onToastMsg(String msg);
}
