package xiaobumall.printfuture.com.gaodemapwebview;

import rx.Observable;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.Log;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import xiaobumall.printfuture.com.gaodemapwebview.entity.CategoryResult;
import xiaobumall.printfuture.com.gaodemapwebview.network.NetWork;
import xiaobumall.printfuture.com.gaodemapwebview.utils.ConfigManage;

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

	private static final String TAG = "launcherActivity";
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

//		String imgCacheUrl = "http://ww3.sinaimg.cn/large/610dc034jw1f7rmrmrscrj20u011hgp1.jpg";//ConfigManage.INSTANCE.getBannerURL();
		String imgCacheUrl = ConfigManage.INSTANCE.getSplashURL();

		Log.i(TAG, "subscribe: " + imgCacheUrl);
		if (!TextUtils.isEmpty(imgCacheUrl)) {
			try {
				Picasso.with(this)
						.load(ConfigManage.INSTANCE.getSplashURL())
						.into(mImageView, new Callback() {
							@Override
							public void onSuccess() {
								Handler handler = new Handler();
								handler.postDelayed(new Runnable() {
									@Override
									public void run() {
										if (!isResume) {
											finish();
											return;
										}
										goHomeActivity();
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
		} else {

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
										if (!isResume) {
											finish();
											return;
										}
										goHomeActivity();
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
	}

	private void cacheRandomImg() {

		Observable<CategoryResult> observable;
		observable = NetWork.getGankApi().getRandomBeauties(1);
		Subscription subscription = observable
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<CategoryResult>() {
					@Override
					public void onCompleted() {
						Log.i(TAG, "onCompleted: ");
					}

					@Override
					public void onError(Throwable e) {
						Log.i(TAG, "onError: ");
					}

					@Override
					public void onNext(CategoryResult meiziResult) {
						if (meiziResult != null && meiziResult.results != null && meiziResult.results.size() > 0 && meiziResult.results.get(0).url != null) {

							ArrayList<String> ImgUrl = new ArrayList<>();
							ImgUrl.add("http://ww4.sinaimg.cn/large/610dc034jw1f2uyg3nvq7j20gy0p6myx.jpg");
							ImgUrl.add("http://ww2.sinaimg.cn/large/610dc034gw1f9lmfwy2nij20u00u076w.jpg");
							ImgUrl.add("https://ws1.sinaimg.cn/large/610dc034ly1ffyp4g2vwxj20u00tu77b.jpg");
//							mHomeView.cacheImg(meiziResult.results.get(0).url);
//							String MainImgURL = "http://ww1.sinaimg.cn/large/7a8aed7bjw1f27uhoko12j20ez0miq4p.jpg";
							String MainImgURL = "http://ww4.sinaimg.cn/mw690/9844520fjw1f4fqrpw1fvj21911wlb2b.jpg";
							//保存 启动页面的图片
							cacheMainImg(MainImgURL);
							cacheImg(ImgUrl);
						}
					}
				});

		mSubscriptions.add(subscription);

	}

	/**
	 * cache Splash  this Image.
	 *
	 * @param SplashImgURL 启动页面的Img
	 */
	private void cacheMainImg(final String SplashImgURL) {
		Picasso.with(this).load(SplashImgURL).fetch(new Callback() {
			@Override
			public void onSuccess() {
				ConfigManage.INSTANCE.setSplashURL(SplashImgURL);
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
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	public void cacheImg(final List<String> imgUrl) {
		// 预加载 提前缓存好的欢迎图
//        for (int i = 0; i < imgUrl.size(); i++) {

		final int randomNum = new Random().nextInt(3);
		Picasso.with(this).load(imgUrl.get(randomNum)).fetch(new Callback() {
			@Override
			public void onSuccess() {
				ConfigManage.INSTANCE.setBannerURL(imgUrl.get(randomNum));
			}

			@Override
			public void onError() {

			}
		});
	}
}
