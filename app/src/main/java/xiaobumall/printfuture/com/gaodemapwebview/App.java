package xiaobumall.printfuture.com.gaodemapwebview;

import android.app.Application;


import com.lei.lib.java.rxcache.RxCache;
import com.lei.lib.java.rxcache.converter.GsonConverter;
import com.lei.lib.java.rxcache.mode.CacheMode;
import com.squareup.picasso.LruCache;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import xiaobumall.printfuture.com.gaodemapwebview.utils.ConfigManage;

//import com.squareup.leakcanary.LeakCanary;

/**
 * App
 * Created by bakumon on 2016/12/8 17:18.
 */
public class App extends Application {
	private static App INSTANCE;

	public static App getInstance() {
		return INSTANCE;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// 初始化 LeakCanary
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }

		Picasso.Builder builder = new Picasso.Builder(this);
		//配置下载器
		builder.downloader(new CustomDownLoad(this));
		LruCache cache  = new LruCache(5* 1024 * 1024);
		builder.memoryCache(cache);
		ExecutorService executorService = Executors.newFixedThreadPool(8);
		builder.executor(executorService);

		Picasso picasso  = builder.build();
		Picasso.setSingletonInstance(picasso);
		RxCache.init(this);//为RxCache提供Context
		new RxCache.Builder()
				.setDebug(true)   //开启debug，开启后会打印缓存相关日志，默认为true
				.setConverter(new GsonConverter())  //设置转换方式，默认为Gson转换
				.setCacheMode(CacheMode.BOTH)   //设置缓存模式，默认为二级缓存
				.setMemoryCacheSizeByMB(50)   //设置内存缓存的大小，单位是MB/app/filemanager/file/file?key=fd9653c3814d71a772c76eb380aa2fe3
				.setDiskCacheSizeByMB(100)    //设置磁盘缓存的大小，单位是MB
				.setDiskDirName("RxCache")    //设置磁盘缓存的文件夹名称
				.build();
		INSTANCE = this;
		// 初始化主题色
		ConfigManage.INSTANCE.initConfig(this);
	}


}
