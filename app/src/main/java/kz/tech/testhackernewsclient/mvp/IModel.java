package kz.tech.testhackernewsclient.mvp;

import kz.tech.testhackernewsclient.MainActivity;
import kz.tech.testhackernewsclient.db.DaoBest;
import kz.tech.testhackernewsclient.db.DaoNew;
import kz.tech.testhackernewsclient.db.DaoTop;

public interface IModel {
    void onRequestNewStories(ICallBack callBack, MainActivity main, DaoNew daoNew);
    void onRequestTopStories(ICallBack callBack, MainActivity main, DaoTop daoTop);
    void onRequestBestStories(ICallBack callBack, MainActivity main, DaoBest daoBest);
}
