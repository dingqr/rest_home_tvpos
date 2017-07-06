package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;

import com.yonyou.framework.library.base.BaseAbsAdapter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.TakeOutListBean;

/**
 * 作者：liushuofei on 2017/7/6 15:13
 * 邮箱：lsf@yonyou.com
 */
public class ADA_TakeOutList extends BaseAbsAdapter<TakeOutListBean> {

    private TakeOutListBean currentBean;

    public ADA_TakeOutList(Context context) {
        super(context);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_packing_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final TakeOutListBean bean = mDataSource.get(position);
        handleDataSource(position, holder, bean);
        return null;
    }

    private void handleDataSource(int position, final ViewHolder holder, final TakeOutListBean bean) {
//        holder.mRoot.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!bean.equals(currentBean)){
//                    bean.setCheck(true);
//                    if (null != currentBean){
//                        currentBean.setCheck(false);
//                    }
//                    currentBean = bean;
//                    notifyDataSetChanged();
//                }
//            }
//        });
    }

    static class ViewHolder {

        ViewHolder(View v) {

        }
    }
}
