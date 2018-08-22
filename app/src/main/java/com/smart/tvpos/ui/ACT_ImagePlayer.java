package com.smart.tvpos.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.tvpos.R;
import com.smart.tvpos.global.API;
import com.smart.tvpos.util.Constants;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ACT_ImagePlayer extends Activity {

    @Bind(R.id.img_pre_button)
    ImageView btnPre;
    @Bind(R.id.img_next_button)
    ImageView btnNext;
    @Bind(R.id.img_show)
    ImageView imageShow;

    private Context mContext;
    private List<String> originPicPathList;
    private int currentPos;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(R.layout.act_image_read);

        ButterKnife.bind(ACT_ImagePlayer.this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        initView();
        initData(extras);

    }

    private void initView(){
        imageShow = new ImageView(mContext);
        btnPre = new ImageView(mContext);
        btnNext = new ImageView(mContext);

        originPicPathList = new ArrayList<>();
    }

    private void initData(Bundle extras){

        String originSelectedPicPath = extras.getString(Constants.ORIGIN_SELECTED_PIC_PATH);
        RequestOptions requestOptions = new RequestOptions()
                .priority(Priority.HIGH);
        Glide.with(mContext)
                .load(API.IMG_SERVER_IP + originSelectedPicPath)
                .apply(requestOptions)
                .into(imageShow);
        currentPos = extras.getInt(Constants.ORIGIN_SELECTED_PIC_POSITION);

        originPicPathList = extras.getStringArrayList(Constants.ORIGIN_PIC_PATH_LIST);
    }

    @OnClick({R.id.img_pre_button, R.id.img_next_button})
    public void showPicture(ImageView view) {
        switch (view.getId()) {
            case R.id.img_pre_button:
                showPreImg();
                break;
            case R.id.img_next_button:
                showNextImg();
                break;
        }
    }

    private void showPreImg() {

        if(currentPos == 0 || originPicPathList.size() == 1){
            CommonUtils.makeEventToast(mContext, getString(R.string.already_first_one), false);
            return;
        }

        RequestOptions requestOptions = new RequestOptions()
                .priority(Priority.HIGH);
        Glide.with(mContext)
                .load(API.IMG_SERVER_IP + originPicPathList.get(--currentPos))
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
                .load(API.IMG_SERVER_IP + originPicPathList.get(++currentPos))
                .apply(requestOptions)
                .into(imageShow);
    }
}
