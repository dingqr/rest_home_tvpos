package com.yonyou.hhtpos.util;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.widget.Toast;

import com.yonyou.hhtpos.bean.TableItem;
import com.yonyou.hhtpos.interfaces.PrinterCallback;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import woyou.aidlservice.jiuiv5.ICallback;
import woyou.aidlservice.jiuiv5.IWoyouService;

/**
 * 打印工具类
 * 作者：liushuofei on 2017/1/5 16:01
 */
public class AidlUtil {
    private static final String SERVICE＿PACKAGE = "woyou.aidlservice.jiuiv5";
    private static final String SERVICE＿ACTION = "woyou.aidlservice.jiuiv5.IWoyouService";

    private IWoyouService woyouService;
    private static AidlUtil mAidlUtil = new AidlUtil();
    private Context context;

    public static final String PRINT_TEXT = "欢迎光临(简体中文)\\n歡迎光臨(繁体中文)\\nWelcome(英文)\\n어서 오세요.(韩文)\\nいらっしゃいませ(日文)\\nWillkommen in der(德文)\\nSouhaits de bienvenue(法文)\\nยินดีต้อนรับสู่(泰文)\\nДобро пожаловать(俄文)\\nBenvenuti a(意大利语)\\nvítejte v(捷克文)\\nBEM - vindo Ao(葡萄牙文)\\nمرحبا بكم في(阿拉伯语)\\n";
    public static final byte[] PRINT_BYTE = {27,50,27,97,0,27,69,0,29,33,17,27,68,2,0,9,27,50,27,97,0,27,69,0,29,33,17,27,68,6,0,9,45,27,50,27,97,1,27,69,0,29,33,17,27,68,8,0,9,-25,-69,-109,-24,-76,-90,-27,-115,-107,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,-27,-100,-80,-27,-99,-128,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,8,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,-25,-108,-75,-24,-81,-99,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,8,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,-27,-113,-80,-27,-113,-73,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,10,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,-26,-108,-74,-23,-109,-74,-27,-111,-104,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,10,0,9,27,50,27,97,0,27,69,0,29,33,0,27,68,25,0,9,-26,-100,-115,-27,-118,-95,-27,-111,-104,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,34,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,-26,-105,-91,-26,-100,-97,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,10,0,9,50,48,49,55,45,48,56,45,48,50,27,50,27,97,0,27,69,0,29,33,0,27,68,25,0,9,-26,-105,-74,-23,-105,-76,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,32,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,-27,-115,-107,-27,-113,-73,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,10,0,9,56,27,50,27,97,0,27,69,0,29,33,0,27,68,25,0,9,-27,-82,-94,-26,-107,-80,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,34,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,2,0,9,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,27,50,10,27,97,0,27,69,0,29,33,0,-24,-113,-100,-27,-109,-127,-27,-112,-115,-25,-89,-80,27,50,27,97,0,27,69,0,29,33,0,27,68,23,0,9,-24,-89,-124,-26,-96,-68,27,50,27,97,0,27,69,0,29,33,0,27,68,31,0,9,-26,-107,-80,-23,-121,-113,27,50,27,97,0,27,69,0,29,33,0,27,68,41,0,9,-23,-121,-111,-23,-94,-99,27,50,10,27,97,0,27,69,0,29,33,0,40,49,41,99,97,105,27,50,27,97,0,27,69,0,29,33,0,27,68,31,0,9,48,46,48,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,-26,-100,-115,-27,-118,-95,-24,-76,-71,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,21,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,-26,-118,-104,-26,-119,-93,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,21,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,-26,-118,-104,-24,-82,-87,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,21,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,-26,-118,-104,-26,-119,-93,-23,-121,-111,-23,-94,-99,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,21,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,-28,-68,-104,-26,-125,-96,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,21,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,-27,-70,-108,-28,-69,-104,-23,-121,-111,-23,-94,-99,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,21,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,-27,-82,-98,-26,-108,-74,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,21,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,27,50,10,27,97,0,27,69,0,29,33,17,-27,-68,-128,-25,-91,-88,-28,-65,-95,-26,-127,-81,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,-23,-92,-112,-23,-91,-82,-26,-74,-120,-24,-76,-71,-23,-121,-111,-23,-94,-99,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,21,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,-23,-108,-128,-27,-108,-82,-27,-107,-122,-27,-109,-127,-23,-121,-111,-23,-94,-99,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,21,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,-27,-113,-81,-27,-68,-128,-27,-113,-111,-25,-91,-88,-23,-121,-111,-23,-94,-99,-17,-68,-102,27,50,27,97,0,27,69,0,29,33,0,27,68,21,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,27,50,10,27,97,0,27,69,0,29,33,0,27,68,1,0,9,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,45,27,50,10,27,97,1,27,69,0,29,33,17,-30,-128,-69,-25,-69,-109,-24,-76,-90,-26,-113,-112,-23,-122,-110,-30,-128,-69,27,50,10,27,97,0,27,69,0,29,33,0,49,-29,-128,-127,-26,-100,-84,-27,-70,-105,-26,-106,-80,-27,-94,-98,-26,-108,-81,-28,-69,-104,-26,-106,-71,-27,-68,-113,-17,-68,-102,-27,-66,-82,-28,-65,-95,-27,-110,-116,-26,-108,-81,-28,-69,-104,-27,-82,-99,13,10,50,-29,-128,-127,-26,-100,-84,-23,-92,-112,-27,-114,-123,-24,-112,-91,-28,-72,-102,-26,-105,-74,-23,-105,-76,-24,-121,-77,-27,-121,-116,-26,-103,-88,51,-25,-126,-71,13,10,51,-29,-128,-127,-26,-75,-73,-27,-70,-107,-26,-115,-98,-23,-107,-65,-26,-100,-97,-26,-117,-101,-24,-127,-104,-27,-112,-124,-27,-78,-105,-28,-67,-115,-28,-70,-70,-27,-111,-104,44,-27,-110,-88,-24,-81,-94,-25,-108,-75,-24,-81,-99,-17,-68,-102,48,49,48,45,56,52,57,49,54,48,53,49,13,10,52,-29,-128,-127,-28,-71,-80,-27,-115,-107,-26,-105,-74,-24,-81,-73,-28,-69,-108,-25,-69,-122,-26,-96,-72,-27,-81,-71,-24,-113,-100,-27,-109,-127,-27,-112,-115,-25,-89,-80,-29,-128,-127,-26,-107,-80,-23,-121,-113,-29,-128,-127,-28,-69,-73,-26,-96,-68,-29,-128,-126,-27,-90,-126,-27,-113,-111,-25,-114,-80,-26,-83,-92,-26,-74,-120,-24,-76,-71,-27,-115,-107,-27,-83,-104,-27,-100,-88,-24,-82,-95,-25,-82,-105,-23,-108,-103,-24,-81,-81,-17,-68,-116,-24,-81,-73,-27,-100,-88,50,52,-27,-80,-113,-26,-105,-74,-27,-122,-123,-26,-117,-88,-26,-119,-109,-25,-108,-75,-24,-81,-99,-17,-68,-102,49,51,51,51,49,49,54,50,57,57,50,-17,-68,-116,-26,-120,-111,-28,-69,-84,-27,-80,-122,-28,-72,-70,-26,-126,-88,-24,-65,-101,-24,-95,-116,-23,-121,-115,-26,-106,-80,-27,-82,-95,-26,-96,-72,-17,-68,-116,-28,-65,-82,-24,-82,-94,13,10,53,-29,-128,-127,-27,-121,-83,-27,-128,-97,-25,-69,-109,-24,-76,-90,-27,-80,-113,-25,-91,-88,-27,-113,-81,-28,-69,-91,-27,-100,-88,-28,-72,-128,-28,-72,-86,-26,-100,-120,-28,-71,-117,-27,-122,-123,-27,-68,-128,-27,-113,-111,-25,-91,-88,27,50,10,27,97,0,27,69,0,29,33,0,27,68,11,0,9,27,50,10,27,97,0,27,69,0,29,33,0,27,68,12,0,9,27,50,10,27,97,1,27,69,0,29,33,0,27,68,1,0,9,-25,-70,-94,-25,-127,-85,-27,-113,-80,-23,-92,-112,-23,-91,-82,-28,-70,-111,-26,-100,-115,-27,-118,-95,-26,-100,-119,-23,-103,-112,-27,-123,-84,-27,-113,-72,-26,-113,-112,-28,-66,-101,-26,-118,-128,-26,-100,-81,-26,-108,-81,-26,-116,-127,29,86,65,0};

    private AidlUtil() {
    }

    public static AidlUtil getInstance() {
        return mAidlUtil;
    }

    /**
     * 连接服务
     *
     * @param context context
     */
    public void connectPrinterService(Context context) {
        this.context = context.getApplicationContext();
        Intent intent = new Intent();
        intent.setPackage(SERVICE＿PACKAGE);
        intent.setAction(SERVICE＿ACTION);
        context.getApplicationContext().startService(intent);
        context.getApplicationContext().bindService(intent, connService, Context.BIND_AUTO_CREATE);
    }

    /**
     * 断开服务
     *
     * @param context context
     */
    public void disconnectPrinterService(Context context) {
        if (woyouService != null) {
            context.getApplicationContext().unbindService(connService);
            woyouService = null;
        }
    }

    public boolean isConnect() {
        return woyouService != null;
    }

    private ServiceConnection connService = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            woyouService = null;
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            woyouService = IWoyouService.Stub.asInterface(service);
        }
    };

    public ICallback generateCB(final PrinterCallback printerCallback){
        return new ICallback.Stub(){
            @Override
            public void  onRunResult(boolean isSuccess, int code, String msg) throws RemoteException {

            }

        };
    }

    /**
     * 设置打印浓度
     */
    private int[] darkness = new int[]{0x0600, 0x0500, 0x0400, 0x0300, 0x0200, 0x0100, 0,
            0xffff, 0xfeff, 0xfdff, 0xfcff, 0xfbff, 0xfaff};

    public void setDarkness(int index) {
        if (woyouService == null) {
            Toast.makeText(context,"服务已断开！", Toast.LENGTH_LONG).show();
            return;
        }

        int k = darkness[index];
        try {
            woyouService.sendRAWData(ESCUtil.setPrinterDarkness(k), null);
            woyouService.printerSelfChecking(null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 取得打印机系统信息，放在list中
     *
     * @return list
     */
    public List<String> getPrinterInfo() {
        if (woyouService == null) {
            Toast.makeText(context,"服务已断开！", Toast.LENGTH_LONG).show();
            return null;
        }

        List<String> info = new ArrayList<>();
        try {
            info.add(woyouService.getPrinterSerialNo());
            info.add(woyouService.getPrinterModal());
            info.add(woyouService.getPrinterVersion());
            info.add(woyouService.getPrintedLength()+"");
            info.add("");
            //info.add(woyouService.getServiceVersion());
            PackageManager packageManager = context.getPackageManager();
            try {
                PackageInfo packageInfo = packageManager.getPackageInfo(SERVICE＿PACKAGE, 0);
                if(packageInfo != null){
                    info.add(packageInfo.versionName);
                    info.add(packageInfo.versionCode+"");
                }else{
                    info.add("");info.add("");
                }
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }

        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return info;
    }

    /**
     * 初始化打印机
     */
    public void initPrinter() {
        if (woyouService == null) {
            Toast.makeText(context,"服务已断开！", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            woyouService.printerInit(null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印二维码
     */
    public void printQr(String data, int modulesize, int errorlevel) {
        if (woyouService == null) {
            Toast.makeText(context,"服务已断开！", Toast.LENGTH_LONG).show();
            return;
        }


        try {
			woyouService.setAlignment(1, null);
            woyouService.printQRCode(data, modulesize, errorlevel, null);
            woyouService.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印条形码
     */
    public void printBarCode(String data, int symbology, int height, int width, int textposition) {
        if (woyouService == null) {
            Toast.makeText(context,"服务已断开！", Toast.LENGTH_LONG).show();
            return;
        }


        try {
            woyouService.printBarCode(data, symbology, height, width, textposition, null);
            woyouService.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印文字
     */
    public void printText(String content, float size, boolean isBold, boolean isUnderLine) {
        if (woyouService == null) {
            Toast.makeText(context,"服务已断开！", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            if (isBold) {
                woyouService.sendRAWData(ESCUtil.boldOn(), null);
            } else {
                woyouService.sendRAWData(ESCUtil.boldOff(), null);
            }

            if (isUnderLine) {
                woyouService.sendRAWData(ESCUtil.underlineWithOneDotWidthOn(), null);
            } else {
                woyouService.sendRAWData(ESCUtil.underlineOff(), null);
            }

            woyouService.printTextWithFont(content, null, size, null);
            woyouService.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    /*
    *打印图片
     */
    public void printBitmap(Bitmap bitmap) {
        if (woyouService == null) {
            Toast.makeText(context,"服务已断开！", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            woyouService.setAlignment(1, null);
            woyouService.printBitmap(bitmap, null);
            woyouService.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * 打印表格
     */
    public void printTable(LinkedList<TableItem> list) {
        if (woyouService == null) {
            Toast.makeText(context,"服务已断开！", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            for (TableItem tableItem : list) {
                Log.i("kaltin", "printTable: "+tableItem.getText()[0]+tableItem.getText()[1]+tableItem.getText()[2]);
                woyouService.printColumnsText(tableItem.getText(), tableItem.getWidth(), tableItem.getAlign(), null);
            }
            woyouService.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /*
    * 空打三行！
     */
    public void print3Line(){
        if (woyouService == null) {
            Toast.makeText(context,"服务已断开！", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            woyouService.lineWrap(3, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void sendRawData(byte[] data) {
        if (woyouService == null) {
            Toast.makeText(context,"服务已断开！", Toast.LENGTH_LONG).show();
            return;
        }

        try {
            woyouService.sendRAWData(data, null);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
