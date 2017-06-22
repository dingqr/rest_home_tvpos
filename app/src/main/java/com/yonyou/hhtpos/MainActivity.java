package com.yonyou.hhtpos;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.yonyou.framework.library.bean.ErrorBean;
import com.yonyou.framework.library.common.utils.AppSharedPreferences;
import com.yonyou.hhtpos.application.MyApplication;
import com.yonyou.hhtpos.db.entity.Position;
import com.yonyou.hhtpos.global.API;
import com.yonyou.hhtpos.manager.ReqCallBack;
import com.yonyou.hhtpos.manager.RequestManager;

import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 网络请求
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("token", "");
        RequestManager.getInstance().requestGetByAsyn(API.BASE_SERVER_IP, params, new ReqCallBack<List<String>>() {
            @Override
            public void onReqSuccess(List<String> applyOrderList) {

            }

            @Override
            public void onFailure(String result) {
                /**联网失败的回调*/

            }

            @Override
            public void onReqFailed(ErrorBean error) {

            }
        });

        // 本地存储
        AppSharedPreferences sharePre = new AppSharedPreferences(this);
        sharePre.putBoolean("boolean", true);

        // 数据库存储
        Position position = new Position();
        position.setId(Long.parseLong("1"));
        position.setName(getString(R.string.app_name));
        position.setLng("");
        position.setLat("");
        position.setCityName("");
        position.setCityId("");
        MyApplication.initDataBase().insertOrReplace(Position.class, position);

    }
}
