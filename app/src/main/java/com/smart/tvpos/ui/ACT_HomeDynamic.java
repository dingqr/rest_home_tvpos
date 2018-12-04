package com.smart.tvpos.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.common.log.Elog;
import com.smart.framework.library.common.utils.CommonUtils;
import com.smart.tvpos.MyApplication;
import com.smart.tvpos.R;
import com.smart.tvpos.adapter.ADA_EventPhotos;
import com.smart.tvpos.bean.PartyInfoEntity;
import com.smart.tvpos.global.API;
import com.smart.tvpos.manager.ReqCallBack;
import com.smart.tvpos.manager.RequestManager;
import com.smart.tvpos.util.CommonUtil;
import com.smart.tvpos.util.Constants;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.AutoRelativeLayout;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ACT_HomeDynamic extends Activity {

    @Bind(R.id.home_dynamic_main_title)
    TextView mainTitleView;
    @Bind(R.id.home_dynamic_time)
    TextView timeView;
    @Bind(R.id.event_content_txt_all)
    TextView contentAllView;
    @Bind(R.id.event_content_txt)
    TextView contentView;
    @Bind({R.id.event_photo_list})
    RecyclerView photoListView;
    @Bind(R.id.event_content)
    AutoRelativeLayout eventContentLayout;
    @Bind(R.id.event_content_with_pic)
    AutoRelativeLayout eventWithPicLayout;

    private Context mContext;
    private String partyId;
    private List<String> photoList;
    private ADA_EventPhotos eventPhotoAdapter;

    private final int LIMIT_PHOTO_SIZE_WIDTH = 893;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        setContentView(R.layout.act_home_dynamic);

        ButterKnife.bind(ACT_HomeDynamic.this);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        init(extras);
    }

    private void init(Bundle extras){
        partyId = extras.getString("party_id");
        String title = extras.getString("party_name");

        mainTitleView.setText(title);

        if (extras.get("party_time") instanceof Date) {
            Date time = (Date) extras.get("party_time");
            SimpleDateFormat format =   new SimpleDateFormat("yyyy-MM-dd");
            timeView.setText(format.format(time));
        }

        photoList = new ArrayList<>();
        eventPhotoAdapter = new ADA_EventPhotos(photoList, mContext);

        requestData();

    }

    private void requestData() {

        final HashMap<String, String> params = new HashMap<>();
        params.put("a", "partyInfo");
        params.put("id", Constants.USER_ID);
        params.put("sign", Constants.USER_SIGN);
        params.put("partyId", partyId);
        RequestManager.getInstance().requestGetByAsyn(API.SERVER_IP, params, new ReqCallBack<PartyInfoEntity>() {

            @Override
            public void onReqSuccess(PartyInfoEntity result) {
                Elog.e("TAG", "jobItem=" + result);
                if (result == null) {
                    return;
                }

                if (result.getImages()== null || result.getImages().isEmpty()) {
                    eventWithPicLayout.setVisibility(View.GONE);
                    eventContentLayout.setVisibility(View.VISIBLE);
                    contentAllView.setMovementMethod(LinkMovementMethod.getInstance());
                    contentAllView.setText(Html.fromHtml(result.getContent(), new ImgGetter(contentAllView), null));
                }
                else {
                    eventContentLayout.setVisibility(View.INVISIBLE);
                    eventWithPicLayout.setVisibility(View.VISIBLE);
                    contentView.setMovementMethod(LinkMovementMethod.getInstance());
                    contentView.setText(Html.fromHtml(result.getContent(), new ImgGetter(contentView), null));

                    photoList.addAll(result.getImages());
                    updatePhotoListView();
                }
            }

            @Override
            public void onFailure(String result) {
                CommonUtils.makeEventToast(MyApplication.getContext(), result, false);
            }

            @Override
            public void onReqFailed(ErrorBean error) {
                CommonUtils.makeEventToast(MyApplication.getContext(), error.getMsg(), false);
            }

            @Override
            public PartyInfoEntity fromJson(String resultData) {
                JSONObject object = JSON.parseObject(resultData);

                PartyInfoEntity partyInfo = new PartyInfoEntity();
                partyInfo.setStatus(object.getString("status"));
                partyInfo.setName(object.getString("name"));
                partyInfo.setPartyStart(object.getDate("partyStart"));
                partyInfo.setPartyEnd(object.getDate("partyEnd"));
                partyInfo.setBookStart(object.getDate("bookStart"));
                partyInfo.setBookEnd(object.getDate("bookEnd"));
                partyInfo.setMaxNum(object.getInteger("maxNum"));
                partyInfo.setImg(object.getString("img"));
                partyInfo.setContent(object.getString("content"));
                partyInfo.setImages(CommonUtil.<String>fromJsonArray(object.getJSONArray("images"), String.class));
                return partyInfo;
            }
        });

    }

    class ImgGetter implements Html.ImageGetter {

        private TextView txtView;

        public ImgGetter(TextView txtView) {
            this.txtView = txtView;
        }

        @Override
        public Drawable getDrawable(String source) {

            final LevelListDrawable draw = new LevelListDrawable();
            Drawable empty = getResources().getDrawable(R.drawable.icon_place_holder);
            draw.addLevel(0, 0, empty);
            draw.setBounds(0, 0, 100, 100);

            final String url;
            if (!source.startsWith("http")) {
                url = "http://www.hafuyun.com" + source;
            }
            else {
                url = source;
            }
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Bitmap bitmap = null;
                    //请求  图片
                    try {
                        //2,获取 网络图片   该方法  为同步方法   需在子线程中执行
                        bitmap = Glide.with(mContext).asBitmap()
                                        .load(url)
                                        .into(com.bumptech.glide.request.target.Target.SIZE_ORIGINAL, com.bumptech.glide.request.target.Target.SIZE_ORIGINAL)
                                        .get();

                        if (bitmap.getWidth() > LIMIT_PHOTO_SIZE_WIDTH) {
                            bitmap = changeBitmapSize(bitmap);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //3,设置  图片
                    runUiThr(bitmap, draw, txtView);
                }
            }).start();
            return draw;
        }
    }

    private void runUiThr(final Bitmap bitmap, final LevelListDrawable draw, final TextView txtview) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (bitmap != null) {
                    BitmapDrawable drawable = new BitmapDrawable(bitmap);
                    draw.addLevel(1, 1, drawable);
                    draw.setBounds(0, 0, bitmap.getWidth(), bitmap.getHeight());
                    draw.setLevel(1);

                    CharSequence charSequence = txtview.getText();
                    txtview.setText(charSequence);
                    txtview.invalidate();
                }
            }
        });
    }

    private Bitmap changeBitmapSize(Bitmap bitmap) {
//        Bitmap bitmap = BitmapFactory.decodeStream();
        Bitmap result;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        //设置想要的大小
        int newWidth = 500;

        //计算压缩的比率
        float scaleWidth = ((float)newWidth)/width;

        //获取想要缩放的matrix
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleWidth);

        //获取新的bitmap
        result = Bitmap.createBitmap(bitmap,0,0,width,height,matrix,true);
//        bitmap.recycle();

        return result;
    }

    private void updatePhotoListView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        photoListView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.left = CommonUtil.dip2px(mContext, 4);
                outRect.right = CommonUtil.dip2px(mContext,4);
            }
        });
        photoListView.setLayoutManager(layoutManager);
        photoListView.setAdapter(eventPhotoAdapter);
        eventPhotoAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.all_full_screen, R.id.news_show, R.id.icon_pre, R.id.icon_next})
    public void onBtnClick(View view) {
        if (view.getId() == R.id.all_full_screen) {
            finish();
        }
        else {
            return;
        }
    }
}
