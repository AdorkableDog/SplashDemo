package xiaobumall.printfuture.com.gaodemapwebview.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Response;

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

public class FileUtils {

	private static final String TAG = "FileUtils";

	public static File createFile(Context context, String img_name) {
		File file = null;
		String state = Environment.getExternalStorageState();

		if(state.equals(Environment.MEDIA_MOUNTED)){
			file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+ img_name+".jpg");
		}else {
			file = new File(context.getCacheDir().getAbsolutePath()+"/"+ img_name+".jpg");
		}
		Log.d(TAG, "file " + file.getAbsolutePath());
		return file;
	}


	public static void writeFile2Disk(Response<ResponseBody> response, File file, HttpCallBack httpCallBack) {
		long currentLength = 0;
		OutputStream os = null;
		InputStream is = response.body().byteStream();
		long totalLength =response.body().contentLength();
		boolean isloading = false;
		try {
			os = new FileOutputStream(file);
			int len;

			byte[] buff = new byte[1024];

			while ((len = is.read(buff)) != -1) {

				os.write(buff, 0, len);
				currentLength += len;
				Log.d(TAG, "当前进度:" + currentLength);
				httpCallBack.onLoading(currentLength, totalLength);

			}
			// httpCallBack.onLoading(currentLength,totalLength,true);


		} catch (FileNotFoundException e) {
			Log.i(TAG, "writeFile2Disk: " + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (is != null) {
				try {
					is.close();
					isloading = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		httpCallBack.isloading(isloading);
	}
}
