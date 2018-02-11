package xiaobumall.printfuture.com.gaodemapwebview.utils;

/**
 * 创建日期：2018/2/11
 * <p>
 * 描述:
 * <p>
 * Created by admin.
 * <p>
 * gitHub: https://github.com/KungFuteddy
 * <p>
 * Orporate Name: Henan Huimeng future network technology Co. Ltd.
 */

public interface HttpCallBack {
	void onLoading(long current, long total);

	void isloading(boolean isloading);
}
