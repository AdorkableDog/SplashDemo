package xiaobumall.printfuture.com.gaodemapwebview.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesUtil {

	public static SharedPreferencesUtil instance;
	public SharedPreferences sp;
    public static final String Sys_Config = "sys_config";

	public static SharedPreferencesUtil getInstance(Context context) {
		if (instance == null) {
			instance = new SharedPreferencesUtil(context);
		}
		return instance;
	}

	@SuppressLint("WrongConstant")
	public SharedPreferencesUtil(Context context) {
		if (sp == null) {
			sp = context.getSharedPreferences(Sys_Config, Context.MODE_APPEND);
		}
	}
	
	public void setBooleanValue(String key, boolean value) {
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public void setValue(String key, Object value) {
		Editor editor = sp.edit();
		editor.putString(key, String.valueOf(value));
		editor.commit();
	}

	public String getValue(String key, String defaultValue) {
		return sp.getString(key, defaultValue);
	}

	public boolean checkExist(String userInfo, String allInfo) {
		if ((userInfo != null && userInfo.equals(""))
				|| (allInfo != null && allInfo.equals(""))) {
			return false;
		}
		return allInfo.indexOf(userInfo) >= 0;
	}

	public void setValues(String key, boolean value) {
		Editor editor = sp.edit();
		editor.putBoolean(key, value);
		editor.commit();
	}

	public boolean getValuesBoolean(String key) {
		return sp.getBoolean(key, false);
	}

	/**
	 * 保存List
	 * @param tag
	 * @param datalist
	 */
	public <T> void setDataList(String tag, List<T> datalist) {
		Editor editor = sp.edit();
		if (null == datalist || datalist.size() <= 0)
			return;
		Gson gson = new Gson();
		//转换成json数据，再保存
		String strJson = gson.toJson(datalist);
		//editor.clear();
		editor.putString(tag, strJson);
		editor.commit();

	}

	/**
	 * 获取List
	 * @param tag
	 * @return
	 */
	public <T> List<T> getDataList(String tag) {
		List<T> datalist=new ArrayList<T>();
		String strJson = sp.getString(tag, null);
		if (null == strJson) {
			return datalist;
		}
		Gson gson = new Gson();
		datalist = gson.fromJson(strJson, new TypeToken<List<T>>() {
		}.getType());
		return datalist;
	}


	public void clearData(){
		sp.edit().clear().commit();
	}

}
