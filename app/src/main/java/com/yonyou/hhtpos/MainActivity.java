package com.yonyou.hhtpos;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        // 网络请求
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("token", "");
//        RequestManager.getInstance().requestGetByAsyn(API.BASE_SERVER_IP, params, new ReqCallBack<List<String>>() {
//            @Override
//            public void onReqSuccess(List<String> applyOrderList) {
//
//            }
//
//            @Override
//            public void onFailure(String result) {
//                /**联网失败的回调*/
//
//            }
//
//            @Override
//            public void onReqFailed(ErrorBean error) {
//
//            }
//        });

//        // 本地存储
//        AppSharedPreferences sharePre = new AppSharedPreferences(this);
//        sharePre.putBoolean("boolean", true);

//        // 数据库存储
//        Position position = new Position();
//        position.setId(Long.parseLong("1"));
//        position.setName(getString(R.string.app_name));
//        position.setLng("");
//        position.setLat("");
//        position.setCityName("");
//        position.setCityId("");
//        MyApplication.initDataBase().insertOrReplace(Position.class, position);

    }
}
