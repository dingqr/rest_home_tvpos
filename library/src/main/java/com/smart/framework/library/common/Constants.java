package com.smart.framework.library.common;

import android.os.Environment;

public class Constants {
	
	public static final String APP_ID = "wx91fd14681b2d4fdb";
	/** 广播相对地址 */
	public static final String BROADCASE_ADDRESS = ".broadcast";
	/** 广播意图key */
	public static final String BROADCASE_INTENT = "intent";
	
	/** 广播 进度条状态显示key */
	public static final String BROADCASE_TYPE_STATE = "state";
	
    public static final class  Integers{
    	public static final int EVENT_BEGIN = 0X100;
    	/*刷新*/
        public static final int EVENT_REFRESH_DATA = EVENT_BEGIN + 10;
        /*加载更多*/
        public static final int EVENT_LOAD_MORE_DATA = EVENT_BEGIN + 20;
        /**加载分类信息*/
        public static final int EVENT_INIT_CATEGORY = EVENT_BEGIN + 30;
        /*懒加载时间*/
        public static final int PAGE_LAZY_LOAD_DELAY_TIME_MS = 200;
        
        /**支付宝支付*/
        public static final int PAY_TYPE_ALIPAY = 0X1;
        /**微信*/
        public static final int PAY_TYPE_WX = 0X2;
        
        public static final int CALCULATE_MAIN_VP_HEIGHT = 0x12;
        
        public static final int EVENT_CODE_WX_PAY = 0X11;
        
    }
    
    
    public static final class Paths {
        public static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
        public static final String IMAGE_LOADER_CACHE_PATH = "/Seller/Images/";
    }
    
}
