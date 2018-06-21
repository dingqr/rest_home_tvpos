package com.smart.framework.library.common.utils;


import android.content.Context;
import android.content.SharedPreferences;


/**
 * 客户端封装的SharedPreferences
 * 
 * @author joe
 * @version 2015.03.25
 */
public class AppSharedPreferences {

	private String  SharedPreferencesFileName= "SharedPreferencesFileName";
	private SharedPreferences settings; // static
	public AppSharedPreferences(Context context) {
		super();
		/**
		 * 载入配置文件
		 */
		settings = context.getSharedPreferences(SharedPreferencesFileName, Context.MODE_PRIVATE);
	}

	/**
	 * 存入一个String类型的数据
	 * @param optName 存入数据的名称
	 * @param value 存入数据的值
	 */
	public void putString(String optName, String value) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(optName, value);
		editor.commit();
	}
	
	/**
	 * 取出一个String类型的数据
	 * @param optName 取出数据的名称
	 * @return 取出的数据的值(默认"")
	 */
	public String getString(String optName){
		return  settings.getString(optName, "");
	}

	/**
	 * 取出一个String类型的数据
	 * @param optName 取出数据的名称
	 * @param defaultValue 默认值
     */
	public String getString(String optName, String defaultValue){
		return  settings.getString(optName, defaultValue);
	}

	/**
	 * 存入一个boolean类型的数据
	 * @param optName 存入数据的名称
	 * @param value 存入数据的值
	 */
	public void putBoolean(String optName, boolean value) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putBoolean(optName, value);
		editor.commit();
	}
	
	/**
	 * 取出一个boolean类型的数据
	 * @param optName 取出数据的名称
	 * @return 取出的数据的值(默认false)
	 */
	public boolean getBoolean(String optName){
		return settings.getBoolean(optName, false);
	}
	
	/**
	 * 存入一个int类型的数据
	 * @param optName 存入数据的名称
	 * @param value 存入数据的值
	 */
	public void putInt(String optName, int value) {
		SharedPreferences.Editor editor = settings.edit();
		editor.putInt(optName, value);
		editor.commit();
	}
	
	/**
	 * 取出一个int类型的数据
	 * @param optName 取出数据的名称
	 * @return 取出的数据的值(默认0)
	 */
	public int getInt(String optName){
		return settings.getInt(optName, 0);
	}
}
