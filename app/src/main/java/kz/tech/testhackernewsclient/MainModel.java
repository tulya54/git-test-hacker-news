package kz.tech.testhackernewsclient;

import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import kz.tech.testhackernewsclient.api_client.ApiClient;
import kz.tech.testhackernewsclient.api_client.IAPIClientBestStories;
import kz.tech.testhackernewsclient.api_client.IAPIClientNewStories;
import kz.tech.testhackernewsclient.api_client.IAPIClientTopStories;
import kz.tech.testhackernewsclient.db.BestStories;
import kz.tech.testhackernewsclient.db.DaoBest;
import kz.tech.testhackernewsclient.db.DaoNew;
import kz.tech.testhackernewsclient.db.DaoTop;
import kz.tech.testhackernewsclient.db.NewStories;
import kz.tech.testhackernewsclient.db.TopStories;
import kz.tech.testhackernewsclient.models.Model;
import kz.tech.testhackernewsclient.models.ModelStories;
import kz.tech.testhackernewsclient.mvp.ICallBack;
import kz.tech.testhackernewsclient.mvp.IModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainModel implements IModel {
    private static final String TAG = "TAG";
    private String sNew1 = "", sNew2 = "",sNew3 = "",
            sTop1 = "", sTop2 = "", sTop3 = "",
            sBest1 = "", sBest2 = "", sBest3 = "";
    private List<Model> newStoriesList;
    private List<Model> topStoriesList;
    private List<Model> bestStoriesList;
    public MainModel() {
        newStoriesList = new ArrayList<>();
        topStoriesList = new ArrayList<>();
        bestStoriesList = new ArrayList<>();
    }
    public List<Model> getNewStoriesList() {
        return newStoriesList;
    }
    public List<Model> getTopStoriesList() {
        return topStoriesList;
    }
    public List<Model> getBestStoriesList() {
        return bestStoriesList;
    }
    public void setNewStoriesList(ICallBack callBack, List<NewStories> list) {
        if (list.size() == 0) return;
        if (newStoriesList.size() > 0) { newStoriesList.clear(); }
        for (int i = 0; i < list.size(); i++) {
            newStoriesList.add(new Model(list.get(i).getIdZ(), list.get(i).getTitle(), list.get(i).getUrl()));
        }
        callBack.onResponse(newStoriesList);
    }
    public void setTopStoriesList(List<TopStories> list) {
        if (list.size() == 0) return;
        if (topStoriesList.size() > 0) { topStoriesList.clear(); }
        for (int i = 0; i < list.size(); i++) {
            topStoriesList.add(new Model(list.get(i).getIdZ(),list.get(i).getTitle(), list.get(i).getUrl()));
        }
    }
    public void setBestStoriesList(List<BestStories> list) {
        if (list.size() == 0) return;
        if (bestStoriesList.size() > 0) { bestStoriesList.clear(); }
        for (int i = 0; i < list.size(); i++) {
            bestStoriesList.add(new Model(list.get(i).getIdZ(),list.get(i).getTitle(), list.get(i).getUrl()));
        }
    }
    @Override
    public void onRequestNewStories(final ICallBack callBack, MainActivity main, final DaoNew daoNew) {
        if (newStoriesList.size() > 0) { newStoriesList.clear(); }
        final IAPIClientNewStories apiInterface = ApiClient.getClient(main).create(IAPIClientNewStories.class);
        apiInterface.getNewStories().enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                List<Integer> topStories = response.body();
                int num = response.body().size();
                for (int i = 0; i < num; i++) {
                    apiInterface.getArticle(topStories.get(i)).enqueue(new Callback<ModelStories>() {
                        @Override
                        public void onResponse(Call<ModelStories> call, Response<ModelStories> response) {
                            String id = null, title = null, url = null;
                            if (response.body() != null) {
                                if (response.body().getId() != null) {
                                    id = response.body().getId().toString();
                                    sNew1 = response.body().getId().toString();
                                }
                                if (response.body().getTitle() != null) {
                                    title = response.body().getTitle().toString();
                                    sNew2 = response.body().getTitle().toString();
                                }
                                if (response.body().getUrl() != null) {
                                    url = response.body().getUrl().toString();
                                    sNew3 = response.body().getUrl().toString();
                                }
                                newStoriesList.add(new Model(id, title, url));
                                callBack.onResponseArticle(new Model(id, title, url));
                                Single.fromCallable(() -> {   daoNew.insert(new NewStories(sNew1, sNew2, sNew3));
                                    return true;} )
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe();
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelStories> call, Throwable t) {
                            Log.e(TAG, "Error article: " + t.getMessage());
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                Log.e(TAG, "Error response: " + t.getMessage());
            }
        });
    }
    @Override
    public void onRequestTopStories(final ICallBack callBack, MainActivity main, final DaoTop daoTop) {
        if (topStoriesList.size() > 0) { topStoriesList.clear(); }
        final IAPIClientTopStories apiInterface = ApiClient.getClient(main).create(IAPIClientTopStories.class);
        apiInterface.getTopStories().enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                List<Integer> topStories = response.body();
                int num = response.body().size();
                for (int i = 0; i < num; i++) {
                    apiInterface.getArticle(topStories.get(i)).enqueue(new Callback<ModelStories>() {
                        @Override
                        public void onResponse(Call<ModelStories> call, Response<ModelStories> response) {
                            String id = null, title = null, url = null;
                            if (response.body() != null) {
                                if (response.body().getId() != null) {
                                    id = response.body().getId().toString();
                                    sTop1 = response.body().getId().toString();
                                }
                                if (response.body().getTitle() != null) {
                                    title = response.body().getTitle().toString();
                                    sTop2 = response.body().getTitle().toString();
                                }
                                if (response.body().getUrl() != null) {
                                    url = response.body().getUrl().toString();
                                    sTop3 = response.body().getUrl().toString();
                                }
                                topStoriesList.add(new Model(id, title, url));
                                callBack.onResponseArticle(new Model(id, title, url));
                                Single.fromCallable(() -> {
                                    daoTop.insert(new TopStories(sTop1, sTop2, sTop3));
                                    return true;
                                })
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe();
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelStories> call, Throwable t) {
                            Log.e(TAG, "Error article: " + t.getMessage());
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                Log.e(TAG, "Error response: " + t.getMessage());
            }
        });
    }
    @Override
    public void onRequestBestStories(final ICallBack callBack, MainActivity main, final DaoBest daoBest) {
        if (bestStoriesList.size() > 0) { bestStoriesList.clear(); }
        final IAPIClientBestStories apiInterface = ApiClient.getClient(main).create(IAPIClientBestStories.class);
        apiInterface.getBestStories().enqueue(new Callback<List<Integer>>() {
            @Override
            public void onResponse(Call<List<Integer>> call, Response<List<Integer>> response) {
                List<Integer> topStories = response.body();
                int num = response.body().size();
                for (int i = 0; i < num; i++) {
                    apiInterface.getArticle(topStories.get(i)).enqueue(new Callback<ModelStories>() {
                        @Override
                        public void onResponse(Call<ModelStories> call, Response<ModelStories> response) {
                            String id = null, title = null, url = null;
                            if (response.body() != null) {
                                if (response.body().getId() != null) {
                                    id = response.body().getId().toString();
                                    sBest1 = response.body().getId().toString();
                                }
                                if (response.body().getTitle() != null) {
                                    title = response.body().getTitle().toString();
                                    sBest2 = response.body().getTitle().toString();
                                }
                                if (response.body().getUrl() != null) {
                                    url = response.body().getUrl().toString();
                                    sBest3 = response.body().getUrl().toString();
                                }
                                bestStoriesList.add(new Model(id, title, url));
                                callBack.onResponseArticle(new Model(id, title, url));
                                Single.fromCallable(() -> {
                                    daoBest.insert(new BestStories(sBest1, sBest2, sBest3));
                                    return true;
                                })
                                        .subscribeOn(Schedulers.io())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe();
                            }
                        }
                        @Override
                        public void onFailure(Call<ModelStories> call, Throwable t) {
                            Log.e(TAG, "Error article: " + t.getMessage());
                        }
                    });
                }
            }
            @Override
            public void onFailure(Call<List<Integer>> call, Throwable t) {
                Log.e(TAG, "Error response: " + t.getMessage());
            }
        });
    }


}
