package xiaobumall.printfuture.com.gaodemapwebview;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.widget.FrameLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
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

public class SplashActivity extends AppCompatActivity {


	private static final String TAG = "SplashActivity";
	@BindView(R.id.img_launcher_welcome)
	AppCompatImageView mImageView;

	@BindView(R.id.img_launcher_welcome_buttom)
	AppCompatImageView imgLauncherWelcomeButtom;

	@BindView(R.id.frameLayout)
	FrameLayout frameLayout;


	private boolean isResume;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		 View view = View.inflate(this, R.layout.activity_splash, null);
		setContentView(R.layout.activity_splash);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		ButterKnife.bind(this);
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
//		String imgCacheUrl = ConfigManage.INSTANCE.getBannerURL();
//		Log.i(TAG, "subscribe: " + imgCacheUrl);

		if (!TextUtils.isEmpty(ConfigManage.INSTANCE.getBannerURL())&& !TextUtils.isEmpty(ConfigManage.INSTANCE.getSplashURL())) {
			try {
				Picasso.with(this)
						.load(ConfigManage.INSTANCE.getBannerURL())
						.into(imgLauncherWelcomeButtom, new Callback() {
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
								}, 5000);
							}

							@Override
							public void onError() {
								goHomeActivity();
							}
						});

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
								}, 5000);
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
			goHomeActivity();
		}
	}

	public void goHomeActivity() {

		Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
		startActivity(intent);
//		 Activity 切换淡入淡出动画
		finish();
		overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_out);
	}


}
