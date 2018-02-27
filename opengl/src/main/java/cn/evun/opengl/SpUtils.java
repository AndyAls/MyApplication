package cn.evun.opengl;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SpUtils {

	public static void putParam(Context context, String paramName, boolean value) {
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putBoolean(paramName, value);
		editor.commit();
	}

	
	public static void putParam(Context context, String paramName, float value) {
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putFloat(paramName, value);
		editor.commit();
	}

	
	
	public static void putParam(Context context, String paramName, String value) {
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putString(paramName, value);
		editor.commit();
	}

	public static void putParam(Context context, String paramName, int value) {
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		Editor editor = sp.edit();
		editor.putInt(paramName, value);
		editor.commit();
	}

	
	public static boolean  getBooleanParam(Context context, String paramName, boolean defValue) {
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getBoolean(paramName, defValue);
	}
	
	public static int  getIntParam(Context context, String paramName, int defValue) {
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getInt(paramName, defValue);
	}
	
	public static String  getStringParam(Context context, String paramName, String defValue) {
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getString(paramName, defValue);
	}


	public static float getFloatParam(Context context, String paramName,
			float defValue) {
		SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
		return sp.getFloat(paramName, defValue);
	}
}
