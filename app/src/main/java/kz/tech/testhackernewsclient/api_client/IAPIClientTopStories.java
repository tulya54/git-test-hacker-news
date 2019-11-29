package kz.tech.testhackernewsclient.api_client;

import java.util.List;

import io.reactivex.Single;
import kz.tech.testhackernewsclient.models.ModelStories;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IAPIClientTopStories {
    @GET("v0/topstories.json?print=pretty")
    Call<List<Integer>> getTopStories();
    @GET("v0/item/{articleid}.json?print=pretty")
    Call<ModelStories> getArticle(@Path("articleid") int id);
}
