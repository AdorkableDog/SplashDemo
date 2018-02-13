package xiaobumall.printfuture.com.gaodemapwebview;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import xiaobumall.printfuture.com.gaodemapwebview.entity.CategoryResults;
import xiaobumall.printfuture.com.gaodemapwebview.network.NetWork;
import xiaobumall.printfuture.com.gaodemapwebview.utils.ConfigManage;
import xiaobumall.printfuture.com.gaodemapwebview.utils.FileUtils;

/**
 * 创建日期：2018/1/26
 * <p>
 * 描述:
 * <p>
 * Created by admin.
 * <p>
 * gitHub: https://github.com/KungFuteddy
 * <p>
 * Orporate Name: Henan Huimeng future network technology Co. Ltd.
 */

public class LauncherActivity extends AppCompatActivity {

	private static final String TAG = "LauncherActivity";
	@BindView(R.id.img_launcher_welcome)
	AppCompatImageView mImageView;

	private CompositeSubscription mSubscriptions;
	private boolean isResume;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.activity_launcher);
		ButterKnife.bind(this);
		mSubscriptions = new CompositeSubscription();

		cacheRandomImg();
		subscribe();

	}

	@Override
	protected void onResume() {
		super.onResume();
		isResume = true;
	}

	@Override
	protected void onPause() {
		super.onPause();
		isResume = false;
	}

	public void subscribe() {
		String imgCacheUrl = ConfigManage.INSTANCE.getSplashURL();
		Log.i(TAG, "subscribe: " + imgCacheUrl);
		try {
			Picasso.with(this)
					.load(R.mipmap.splash)
					.into(mImageView, new Callback() {
						@Override
						public void onSuccess() {
							Handler handler = new Handler();
							handler.postDelayed(new Runnable() {
								@Override
								public void run() {
//									if (!isResume) {
//										finish();
										goHomeActivity();
										return;
//									}
								}
							}, 1500);
						}

						@Override
						public void onError() {
							goHomeActivity();
						}
					});
		} catch (Exception e) {
			goHomeActivity();
		}
	}

	private void cacheRandomImg() {
		Observable<CategoryResults> observable;
		observable = NetWork.getGankApi().getRandomBeauties("pixiu");
		Subscription subscription = observable
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<CategoryResults>() {
					@Override
					public void onCompleted() {
						Log.i(TAG, "onCompleted: ");
					}

					@Override
					public void onError(Throwable e) {
						Log.i(TAG, "onError: " + e.toString());
					}

					@Override
					public void onNext(CategoryResults meiziResult) {
						Log.i(TAG, "onNext: " + meiziResult.getStatus());
						if (meiziResult != null && meiziResult.getData() != null) {
							/***
							 * @TODO 1.创建三级目录，
							 *  app_name
							 *     |-- splash
							 *          |-- 活动file（20180214）
							 *              |-- 对应文件 --- top\buttom
							 *              |-- 对应文件 --- top\buttom
							 *              |-- 对应文件 --- top\buttom
							 *              ....
							 *           |-- 活动file（20180215）
							 *              |-- 对应文件 --- top\buttom
							 *              |-- 对应文件 --- top\buttom
							 *              |-- 对应文件 --- top\buttom
							 *              ....
							 */
							//@TODO 判断文件夹和img_是否存在 存在就不创建，没有再去创建
							createFiles(meiziResult);
						}
					}
				});
		mSubscriptions.add(subscription);
	}


	/***
	 * 创建三级目录，
	 *  app_name
	 *     |-- splash
	 *          |-- 活动file（20180214）
	 *              |-- 对应文件
	 *                       --- top\buttom
	 *              |-- 对应文件 --- top\buttom
	 *              |-- 对应文件 --- top\buttom
	 *              ....
	 *           |-- 活动file（20180215）
	 *              |-- 对应文件 --- top\buttom
	 *              |-- 对应文件 --- top\buttom
	 *              |-- 对应文件 --- top\buttom
	 *              ....
	 * @param meiziResult
	 */
	private void createFiles(CategoryResults meiziResult) {
		for (int i = 0; i < meiziResult.getData().size(); i++) {
			String showtime = meiziResult.getData().get(i).getShowtime();
			String key = meiziResult.getData().get(i).getKey();
			String topImg = meiziResult.getData().get(i).getImg().getTop();
			String buttomImg = meiziResult.getData().get(i).getImg().getBottom();
			String[] split = topImg.split(".com");
			String[] split2 = buttomImg.split(".com");
			crSDFile(split[1], "top", "splash", showtime, key);
			crSDFile(split2[1], "buttom", "splash", showtime, key);
		}
	}

	/**
	 * folder参数内容是要传进去的要建立的文件夹名。
	 * 例如建立 mnt/sdcard/splash/1/2 这样的路径文件夹。那么传进去参数就可以为 crSDFile(ceshi,1,2); 即可
	 *
	 * @param folder
	 */
	public void crSDFile(String img_url, String img_type, String... folder) {
		int length = folder.length;
		String genFolder = Environment.getExternalStorageDirectory().getAbsolutePath();
		String str = genFolder + "/";
		File file;
		for (int i = 0; i < length; i++) {
			str = str + folder[i] + "/";
			file = new File(str);
			if (i == folder.length - 1) {
				Log.i(TAG, "crSDFile:--- img_file: " + str + "img_url: " + img_url + "img_name: " + img_type);
				if (!FileUtils.isExist(str, img_type)) {
					downLoadImg(img_url, str, img_type);
				}
			}
			if (!file.exists()) {
				file.mkdir();
			}
		}
	}


	public void downLoadImg(String top, String img_file_path, String img_type) {
		final File file = FileUtils.createFile(img_file_path, img_type);
		NetWork.getGankApi().downloadFile(top).enqueue(new retrofit2.Callback<ResponseBody>() {
			@Override
			public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
				//下载文件放在子线程
				new Thread() {
					@Override
					public void run() {
						super.run();
						//保存到本地
						FileUtils.writeFile2Disk(response, file);
					}
				}.start();
			}

			@Override
			public void onFailure(Call<ResponseBody> call, Throwable t) {
			}
		});
	}


//	private void setDatas(final CategoryResults meiziResult) {
//		/**
//		 * 获取数据成功之后 ， 使用RXcache 缓存数据，
//		 */
//		RxCache.getInstance()
//				.put("meiziResult", meiziResult, 60 * 1000)
//				.compose(RxUtil.<Boolean>io_main())
//				.subscribe(new Consumer<Boolean>() {
//					@Override
//					public void accept(Boolean aBoolean) throws Exception {
//						if (aBoolean) {
//							for (int i = 0; i < meiziResult.getData().size(); i++) {
//								String img = meiziResult.getData().get(i).getImg().getBottom();
//								String Top = meiziResult.getData().get(i).getImg().getTop();
//								Log.i(TAG, "accept: 数据保存成功啦！" + img + "  ------ " + Top);
//							}
//						}
//					}
//				}, new Consumer<Throwable>() {
//					@Override
//					public void accept(Throwable throwable) throws Exception {
//						Log.i(TAG, throwable.getLocalizedMessage());
//					}
//				});
//		getDatas();
//	}

//	private void getDatas() {
//		RxCache.getInstance()
//				.get("meiziResult", false, CategoryResults.class)
//				.compose(RxUtil.<CacheResponse<CategoryResults>>io_main())
//				.subscribe(new Consumer<CacheResponse<CategoryResults>>() {
//					@Override
//					public void accept(CacheResponse<CategoryResults> cacheBeanCacheResponse) throws Exception {
//						for (int i = 0; i < cacheBeanCacheResponse.getData().getData().size(); i++) {
//							Log.i(TAG, "获取数据accept: " + cacheBeanCacheResponse.getData().getData().get(i).getImg().getBottom());
//						}
//						getRandomImg(cacheBeanCacheResponse.getData());
//					}
//				}, new Consumer<Throwable>() {
//					@Override
//					public void accept(Throwable throwable) throws Exception {
//					}
//				});
//	}

	private boolean isResumes = true;

	public void getRandomImg(final CategoryResults meiziResult) {
		ArrayList<String> ImgTopUrl = new ArrayList<>();
		ArrayList<String> ImgButtomUrl = new ArrayList<>();
//		int randomNum = new Random().nextInt(meiziResult.getData().get_$20180207());
		for (int i = 0; i < meiziResult.getData().size(); i++) {
			String publishedAt = meiziResult.getData().get(i).getShowtime();
//			String nowTime = TimeUtils.getNowTime();
//			Log.i(TAG, "getRandomImg: " + " nowTime: " + nowTime + " publishedAt: " + publishedAt);
//			if (publishedAt.equals(nowTime)) {
//				cacheMainImg(meiziResult.getData().get(i).getImg().getTop());
//				cacheRandomImg(meiziResult.getData().get(i).getImg().getBottom());
//				isResumes = false;
//			}
		}
		if (isResumes) {
			for (int i = 0; i < meiziResult.getData().size(); i++) {
				if (meiziResult.getData().get(i).getShowtime().equals("0")) {
					ImgTopUrl.add(meiziResult.getData().get(i).getImg().getTop());
					ImgButtomUrl.add(meiziResult.getData().get(i).getImg().getBottom());
					cacheMainImg(ImgTopUrl.get(0));
					cacheRandomImg(ImgButtomUrl.get(0));
				}
			}
		}
	}

	/**
	 * cache Splash  this Image.
	 *
	 * @param TopImgURL 启动页面的Img
	 */
	private void cacheMainImg(final String TopImgURL) {
		Picasso.with(this).load(TopImgURL).fetch(new Callback() {
			@Override
			public void onSuccess() {
				ConfigManage.INSTANCE.setSplashURL(TopImgURL);
			}

			@Override
			public void onError() {
			}
		});
	}

	public void goHomeActivity() {
		Intent intent = new Intent(LauncherActivity.this, SplashActivity.class);
		startActivity(intent);
		// Activity 切换淡入淡出动画
		finish();
//		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public void cacheRandomImg(final String imgUrl) {
		// 预加载 提前缓存好的欢迎图
		Picasso.with(this).load(imgUrl).fetch(new Callback() {
			@Override
			public void onSuccess() {
				ConfigManage.INSTANCE.setBannerURL(imgUrl);
			}

			@Override
			public void onError() {

			}
		});
	}
}
