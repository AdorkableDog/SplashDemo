package xiaobumall.printfuture.com.gaodemapwebview.network;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import xiaobumall.printfuture.com.gaodemapwebview.network.api.GankApi;

/**
 * 网络层
 * Created by bakumon on 16-12-1.
 */

public class NetWork {
	private static GankApi gankApi;


	public static GankApi getGankApi() {
		if (gankApi == null) {
			Retrofit retrofit = new Retrofit.Builder()
					.client(new OkHttpClient())
					.baseUrl("http://center.paint-future.com")
					.addConverterFactory(GsonConverterFactory.create())
					.addCallAdapterFactory(RxJavaCallAdapterFactory.create())
					.build();
			gankApi = retrofit.create(GankApi.class);
		}
		return gankApi;
	}

}
