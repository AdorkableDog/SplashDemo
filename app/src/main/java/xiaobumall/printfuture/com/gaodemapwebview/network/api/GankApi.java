package xiaobumall.printfuture.com.gaodemapwebview.network.api;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;
import xiaobumall.printfuture.com.gaodemapwebview.entity.CategoryResult;

/**
 * gank.io 接口
 * Created by bakumon on 16-12-1.
 */

public interface GankApi {

    @GET("data/{category}/{number}/{page}")
    Observable<CategoryResult> getCategoryDate(@Path("category") String category, @Path("number") int number, @Path("page") int page);

    @GET("random/data/福利/{number}")
    Observable<CategoryResult> getRandomBeauties(@Path("number") int number);

//    @GET("search/query/{key}/category/all/count/{count}/page/{page}")
//    Observable<SearchResult> getSearchResult(@Path("key") String key, @Path("count") int count, @Path("page") int page);

}
