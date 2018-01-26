package xiaobumall.printfuture.com.gaodemapwebview;

import android.app.Application;

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
//        LeakCanary.install(this);
        INSTANCE = this;
        // 初始化主题色
        ConfigManage.INSTANCE.initConfig(this);
    }


}
