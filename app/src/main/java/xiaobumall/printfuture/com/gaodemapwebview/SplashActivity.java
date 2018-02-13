package xiaobumall.printfuture.com.gaodemapwebview;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.LoginFilter;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.nio.file.FileStore;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import xiaobumall.printfuture.com.gaodemapwebview.utils.ConfigManage;
import xiaobumall.printfuture.com.gaodemapwebview.utils.FileUtils;
import xiaobumall.printfuture.com.gaodemapwebview.utils.TimeUtils;

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
		//@TODO  picasso 加载本地图片  ----- 先去判断本地文件夹是否存在  再去判断图片是否已经下载

		String nowTime = TimeUtils.getNowTime();
		if (FileUtils.isFileExists()) {
			File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/splash");
			File[] files = file.listFiles();
			int Tag = 0;
			for (File f : files) {
				String string = f.toString().split("splash/")[1];
				//@TODO 拿到对应文件的文件名称  判断和当前时间是否相同。
				if (nowTime.equals(string)) {
					//相同  获取文件中的
					File file_ = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/splash/" + string);
					File[] files1 = file_.listFiles();
					for (File file1 : files1) {
						File[] file_list = new File(file1 + "").listFiles();
						for (File file2 : file_list) {
							Log.i(TAG, "file2: " + file2);
						}
					}
//					Random random = new Random();
//					int i = random.nextInt(files1.length);

				} else {
					/**
					 * 不相同
					 */
					Tag++;
					if (Tag == 1) {
						File defult_img = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/splash/0");
						File[] defult_img_list = defult_img.listFiles();
						int length = defult_img_list.length;
						Log.i(TAG, "subscribe: " + length);
						for (File ignored : defult_img_list) {
							Log.i(TAG, "ignored: " + ignored);
						}
					}
				}
			}
		}
//		picassoLoadImg();
	}

	private void picassoLoadImg() {
		if (!TextUtils.isEmpty(ConfigManage.INSTANCE.getBannerURL()) && !TextUtils.isEmpty(ConfigManage.INSTANCE.getSplashURL())) {
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
