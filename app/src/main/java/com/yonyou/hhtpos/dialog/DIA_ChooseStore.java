package com.yonyou.hhtpos.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.ADA_ChooseStore;
import com.yonyou.hhtpos.bean.StoreEntity;
import com.yonyou.hhtpos.widgets.DashGridView;

import java.util.ArrayList;


/**
 * Created by zj on 2017/6/27.
 * 邮箱：zjuan@yonyou.com
 * 描述：选择门店弹窗
 */
public class DIA_ChooseStore {
    private final SwipeRefreshLayout swipeRefreshLayout;
    private Dialog mDialog;
    private View mContentView;
    private ImageView ivClose;
    private Context mContext;
    private ArrayList<StoreEntity> datas = new ArrayList<>();
    private OnChooseStoreListener chooseStoreListener;
    private DashGridView gridView;
    private ADA_ChooseStore adapter;

    public DIA_ChooseStore(Context context, OnChooseStoreListener chooseStoreListener) {
        mContext = context;
        this.chooseStoreListener = chooseStoreListener;
        mDialog = new Dialog(context, R.style.ActionSheetDialogStyle);
        mContentView = LayoutInflater.from(context).inflate(R.layout.dialog_choose_store, null);
        gridView = (DashGridView) mContentView.findViewById(R.id.gridview);
        ivClose = (ImageView) mContentView.findViewById(R.id.iv_close);
        swipeRefreshLayout = (SwipeRefreshLayout) mContentView.findViewById(R.id.swipeRefreshLayout);

        mDialog.setContentView(mContentView);
        initListener();

//        setData();


    }

    /**
     * 设置监听
     */
    private void initListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter.setSelectItem(position);
                adapter.notifyDataSetChanged();
                if (chooseStoreListener != null) {
                    chooseStoreListener.onChooseStore(datas.get(position), position);
                }
                if (mDialog.isShowing()) {
                    mDialog.dismiss();
                }
            }
        });
        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mDialog != null) {
                    mDialog.dismiss();
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    /**
     * 测试数据
     */
    private void setData() {
        for (int i = 0; i < 20; i++) {
            StoreEntity storeEntity = new StoreEntity();
            storeEntity.store_name = "海底捞" + i;
            datas.add(storeEntity);
        }
    }

    public void setData(ArrayList<StoreEntity> shopList) {
        this.datas = shopList;
        adapter = new ADA_ChooseStore(mContext);
        gridView.setAdapter(adapter);
        adapter.update(datas);
    }

    public Dialog show() {
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.getWindow().setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.dimAmount = 0.8f; //背景灰度 -0.0全透明
        lp.width = 1280; // 设置宽度
        lp.height = 600;//设置高度
        lp.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        if (!mDialog.isShowing()) {
            mDialog.show();
        }
        return mDialog;
    }

    public interface OnChooseStoreListener {
        void onChooseStore(StoreEntity storeName, int position);
    }

    public ADA_ChooseStore getAdapter() {
        return adapter;
    }
}
