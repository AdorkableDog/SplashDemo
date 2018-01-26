package xiaobumall.printfuture.com.gaodemapwebview;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {

	private WebView webView;
		private String intent_url = "http://m.amap.com";
//	private String intent_url = "https://map.baidu.com/mobile/webapp/index/index/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (Build.VERSION.SDK_INT >= 23) {
			int checkPermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION);
			if (checkPermission != PackageManager.PERMISSION_GRANTED) {
				ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 1);
				ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
				Log.d("TTTT", "弹出提示");
			}
		}
		initView();

	}

	private void initView() {
		webView = (WebView) findViewById(R.id.webView);


		webView.getSettings().setDatabaseEnabled(true);//开启数据库

		webView.setFocusable(true);//获取焦点

		webView.requestFocus();

		String dir = this.getApplicationContext().getDir("database", Context.MODE_PRIVATE).getPath();//设置数据库路径

		webView.getSettings().setCacheMode(webView.getSettings().LOAD_CACHE_ELSE_NETWORK);//本地缓存

		webView.getSettings().setBlockNetworkImage(false);//显示网络图像

		webView.getSettings().setLoadsImagesAutomatically(true);//显示网络图像

		webView.getSettings().setPluginState(WebSettings.PluginState.ON);//插件支持

		webView.getSettings().setSupportZoom(false);//设置是否支持变焦

		webView.getSettings().setJavaScriptEnabled(true);//支持JavaScriptEnabled

		webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);//支持JavaScriptEnabled

		webView.getSettings().setGeolocationEnabled(true);//定位

		webView.getSettings().setGeolocationDatabasePath(dir);//数据库

		webView.getSettings().setDomStorageEnabled(true);//缓存 （ 远程web数据的本地化存储）

		WebViewClient myWebViewClient = new WebViewClient();//建立对象

		webView.setWebViewClient(myWebViewClient);//调用

		webView.loadUrl(intent_url);//百度地图地址

		webView.setWebChromeClient(new WebChromeClient() {
			//重写WebChromeClient的onGeolocationPermissionsShowPrompt
			public void onGeolocationPermissionsShowPrompt(String origin,
			                                               GeolocationPermissions.Callback callback) {
				callback.invoke(origin, true, false);
				super.onGeolocationPermissionsShowPrompt(origin, callback);
			}

		});
	}

}
