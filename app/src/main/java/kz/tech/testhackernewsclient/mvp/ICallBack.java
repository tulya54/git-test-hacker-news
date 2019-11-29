package kz.tech.testhackernewsclient.mvp;

import java.util.List;

import kz.tech.testhackernewsclient.models.Model;
import kz.tech.testhackernewsclient.models.ModelStories;

public interface ICallBack {
    void onResponseArticle(Model m);
    void onResponse(List<Model> list);
}
