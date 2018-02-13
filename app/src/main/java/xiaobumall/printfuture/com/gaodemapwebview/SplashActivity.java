package xiaobumall.printfuture.com.gaodemapwebview;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.text.TextUtils;
import android.util.Log;
import android.widget.FrameLayout;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.HashMap;
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
	Random random = new Random();


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
		HashMap imgFeastPathUrl = new HashMap();
		HashMap imgDefultPathUrl = new HashMap();
		//@TODO  picasso 加载本地图片  ----- 先去判断本地文件夹是否存在  再去判断图片是否已经下载

		String nowTime = TimeUtils.getNowTime();
		if (FileUtils.isFileExists()) {
			String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath();
			File file = new File(absolutePath + "/splash");
			File[] files = file.listFiles();
			int Tag = 0;
			for (File f : files) {
				String string = f.toString().split("splash/")[1];
				//@TODO 拿到对应文件的文件名称  判断和当前时间是否相同。
				if (nowTime.equals(string)) {
					//相同  获取文件中的
					File file_ = new File(absolutePath + "/splash/" + string);
					File[] files1 = file_.listFiles();

					int i = random.nextInt(files1.length);
					File file0 = files1[i];
					File[] file_list = new File(file0 + "").listFiles();
					for (File filelist : file_list) {
						Log.i(TAG, "img_feast_top_buttom: " + filelist);

						String top_feast_img = filelist + "";
						if (top_feast_img.contains("top.jpg")) {
							imgFeastPathUrl.put("top.jpg", filelist + "");
						}
						if (top_feast_img.contains("buttom.jpg")) {
							imgFeastPathUrl.put("buttom.jpg", filelist + "");
						}
					}
				} else {
					/**
					 * 不相同
					 */
					Tag++;//foreach循环只执行一次
					if (Tag == 1) {
						File defult_img = new File(absolutePath + "/splash/0");
						File[] defult_img_list = defult_img.listFiles();
						int i = random.nextInt(defult_img_list.length);
						File file1 = defult_img_list[i];
						File[] file_list = new File(file1 + "").listFiles();
						for (File files2 : file_list) {
							String top_feast_img = files2 + "";
							if (top_feast_img.contains("top.jpg")) {
								imgDefultPathUrl.put("top.jpg", files2 + "");
							}

							if (top_feast_img.contains("buttom.jpg")) {
								imgDefultPathUrl.put("buttom.jpg", files2 + "");
							}
						}
					}
				}
			}
		}

		if (imgFeastPathUrl.size() != 0) {//如果活动的集合不为空  图片获取从 活动集合中获取
			Log.i(TAG, "活动图片路径 top :  " + imgFeastPathUrl.get("top.jpg") + "\n" + " buttom : " + imgFeastPathUrl.get("buttom.jpg"));
			picassoLoadImg(new File(imgFeastPathUrl.get("top.jpg")+""),new File(imgFeastPathUrl.get("buttom.jpg")+""));
		} else {
			Log.i(TAG, "默认图片路径 top :  " + imgDefultPathUrl.get("top.jpg") + "\n" + " buttom : " + imgDefultPathUrl.get("buttom.jpg"));

			File top = new File(imgDefultPathUrl.get("top.jpg") + "");
			File buttom = new File(imgDefultPathUrl.get("buttom.jpg") + "");


			picassoLoadImg(top, buttom);


		}
//		picassoLoadImg();
	}

	private void picassoLoadImg(File top, File buttom) {
		if (top!=null && buttom!=null) {
			try {
				Picasso.with(this)
						.load(buttom)
						.into(imgLauncherWelcomeButtom, new Callback() {
							@Override
							public void onSuccess() {
								Handler handler = new Handler();
								handler.postDelayed(new Runnable() {
									@Override
									public void run() {
//										if (!isResume) {
//											finish();
//											return;
//										}
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
						.load(top)
						.into(mImageView, new Callback() {
							@Override
							public void onSuccess() {
								Handler handler = new Handler();
								handler.postDelayed(new Runnable() {
									@Override
									public void run() {
//										if (!isResume) {
//											finish();
//											return;
//										}
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
