package com.smart.tvpos.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.tvpos.R;
import com.smart.tvpos.global.API;
import com.smart.tvpos.util.Constants;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ACT_HomeDynamic extends Activity {

    @Bind(R.id.img_pre_button)
    ImageView btnPre;
    @Bind(R.id.img_next_button)
    ImageView btnNext;
    @Bind(R.id.img_show)
    ImageView imageShow;
    @Bind({R.id.all_full_screen})
    AutoLinearLayout linearLayout;

    private Context mContext;
    private List<String> originPicPathList;
    private int currentPos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(R.layout.act_image_player);

        ButterKnife.bind(ACT_HomeDynamic.this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        initData(extras);

    }

    private void initData(Bundle extras){

        originPicPathList = new ArrayList<>();
        currentPos = extras.getInt(Constants.ORIGIN_SELECTED_PIC_POSITION);
        if(currentPos == 3){
            currentPos = 0;
        }
        originPicPathList = extras.getStringArrayList(Constants.ORIGIN_PIC_PATH_LIST);

        RequestOptions requestOptions = new RequestOptions()
                .priority(Priority.HIGH);
        Glide.with(mContext)
                .load(API.IMG_SERVER_IP + "/" + originPicPathList.get(currentPos))
                .apply(requestOptions)
                .into(imageShow);
    }

    @OnClick({R.id.img_pre_button, R.id.img_next_button, R.id.all_full_screen})
    public void showPicture(View view) {
        switch (view.getId()) {
            case R.id.img_pre_button:
                showPreImg();
                break;
            case R.id.img_next_button:
                showNextImg();
                break;
            default:
                    finish();
        }
    }

    private void showPreImg() {

        if(currentPos == 0 || originPicPathList.size() == 1){
            CommonUtils.makeEventToast(mContext, getString(R.string.already_first_one), false);
            return;
        }

        RequestOptions requestOptions = new RequestOptions()
                .priority(Priority.HIGH);
        String url = API.IMG_SERVER_IP + "/" + originPicPathList.get(--currentPos);
        Glide.with(mContext)
                .load(url)
                .apply(requestOptions)
                .into(imageShow);
    }
    private void showNextImg() {

        if(originPicPathList.size() == 1 || currentPos == (originPicPathList.size() -1)){
            CommonUtils.makeEventToast(mContext, getString(R.string.already_last_one), false);
            return;
        }

        RequestOptions requestOptions = new RequestOptions()
                .priority(Priority.HIGH);
        Glide.with(mContext)
                .load(API.IMG_SERVER_IP + "/" + originPicPathList.get(++currentPos))
                .apply(requestOptions)
                .into(imageShow);
    }
}
