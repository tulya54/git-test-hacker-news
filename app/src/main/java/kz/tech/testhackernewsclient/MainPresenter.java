package kz.tech.testhackernewsclient;

import android.util.Log;
import java.util.List;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import kz.tech.testhackernewsclient.db.BestStories;
import kz.tech.testhackernewsclient.db.DaoBest;
import kz.tech.testhackernewsclient.db.DaoNew;
import kz.tech.testhackernewsclient.db.DaoTop;
import kz.tech.testhackernewsclient.db.NewStories;
import kz.tech.testhackernewsclient.db.TopStories;
import kz.tech.testhackernewsclient.models.Model;
import kz.tech.testhackernewsclient.mvp.IPresenter;
import kz.tech.testhackernewsclient.mvp.ICallBack;

public class MainPresenter implements IPresenter {
    private static final String TAG = "TAG";
    private MainModel model;
    private ICallBack callBack;
    private MainActivity main;
    private DaoNew daoNew;
    private DaoTop daoTop;;
    private DaoBest daoBest;
    public MainPresenter(final MainActivity main, DaoNew daoNew, DaoTop daoTop, DaoBest daoBest) {
        this.main = main;
        this.model = new MainModel();
        this.daoNew = daoNew;
        this.daoTop = daoTop;
        this.daoBest = daoBest;
        this.callBack = new ICallBack() {
            @Override
            public void onResponseArticle(Model m) {
                main.setStory(m);
            }
            @Override
            public void onResponse(List<Model> list) {
                main.setStories(list);
            }
        };
    }
    @Override
    public void onStartStorage() {
        daoNew.getCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        if (integer > 0) {
                            daoNew.getAll()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new DisposableSingleObserver<List<NewStories>>() {
                                        @Override
                                        public void onSuccess(List<NewStories> newStoriesList) {
                                            model.setNewStoriesList(callBack, newStoriesList);
                                        }
                                        @Override
                                        public void onError(Throwable e) {
                                            Log.e(TAG, "Error: " + e.getMessage());
                                        }
                                    });
                        } else if (integer == 0) {
                            getNewStories();
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error: " + e.getMessage());
                    }
                });
        daoTop.getCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        if (integer > 0) {
                            daoTop.getAll()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new DisposableSingleObserver<List<TopStories>>() {
                                        @Override
                                        public void onSuccess(List<TopStories> topStoriesList) {
                                            model.setTopStoriesList(topStoriesList);
                                        }
                                        @Override
                                        public void onError(Throwable e) {
                                            Log.e(TAG, "Error: " + e.getMessage());
                                        }
                                    });
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error: " + e.getMessage());
                    }
                });
        daoBest.getCount()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(Integer integer) {
                        if (integer > 0) {
                            daoBest.getAll()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(new DisposableSingleObserver<List<BestStories>>() {
                                        @Override
                                        public void onSuccess(List<BestStories> bestStoriesList) {
                                            model.setBestStoriesList(bestStoriesList);
                                        }
                                        @Override
                                        public void onError(Throwable e) {
                                            Log.e(TAG, "Error: " + e.getMessage());
                                        }
                                    });
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "Error: " + e.getMessage());
                    }
                });
    }
    @Override
    public void updateStories(int n) {
        switch (n) {
            case 1: model.onRequestNewStories(callBack, main, daoNew); break;//  NEW STORIES
            case 2: model.onRequestTopStories(callBack, main, daoTop); break;//  TOP STORIES
            case 3: model.onRequestBestStories(callBack, main, daoBest); break;//  BEST STORIES
        }
    }
    @Override
    public void getNewStories() {
        int num = model.getNewStoriesList().size();
        if (num > 0) {
            main.setStories(model.getNewStoriesList());
        } else if (num == 0) {
            model.onRequestNewStories(callBack, main, daoNew);
        }
    }
    @Override
    public void getTopStories() {
        int num = model.getTopStoriesList().size();
        if (num > 0) {
            main.setStories(model.getTopStoriesList());
        } else if (num == 0) {
            model.onRequestTopStories(callBack, main, daoTop);
        }
    }
    @Override
    public void getBestStories() {
        int num = model.getBestStoriesList().size();
        if (num > 0) {
            main.setStories(model.getBestStoriesList());
        } else if (num == 0) {
            model.onRequestBestStories(callBack, main, daoBest);
        }
    }
    @Override
    public void onDestroyView() {
        if (main != null) {
            main = null;
        }
    }
}
