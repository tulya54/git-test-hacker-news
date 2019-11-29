package kz.tech.testhackernewsclient.api_client;

import java.util.List;

import io.reactivex.Single;
import kz.tech.testhackernewsclient.models.ModelStories;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IAPIClientNewStories {
    @GET("v0/newstories.json?print=pretty")
    Call<List<Integer>> getNewStories();
    @GET("v0/item/{articleid}.json?print=pretty")
    Call<ModelStories> getArticle(@Path("articleid") int id);
}
