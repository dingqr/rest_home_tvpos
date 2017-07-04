package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yonyou.framework.library.base.BaseAbsAdapter;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.PreviewListEntity;

/**
 * 预定列表adapter
 * 作者：liushuofei on 2017/6/30 10:40
 */
public class ADA_PreviewList extends BaseAbsAdapter<PreviewListEntity> {

    private PreviewListEntity currentBean;

    public ADA_PreviewList(Context context) {
        super(context);
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_preview_list, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final PreviewListEntity bean = mDataSource.get(position);
        handleDataSource(position, holder, bean);

        if (bean.isCheck()){
            currentBean = bean;
            holder.mRoot.setBackgroundResource(R.color.color_eaeaea);
            holder.mLeftLine.setVisibility(View.VISIBLE);
            holder.mBookNum.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
            holder.mBookTime.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
            holder.mBookPhone.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
            holder.mPeopleNum.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
            holder.mBookRemark.setTextColor(ContextCompat.getColor(mContext, R.color.color_eb6247));
        }else {
            holder.mRoot.setBackgroundResource(R.color.color_FFFFFF);
            holder.mLeftLine.setVisibility(View.INVISIBLE);
            holder.mBookNum.setTextColor(ContextCompat.getColor(mContext, R.color.color_222222));
            holder.mBookTime.setTextColor(ContextCompat.getColor(mContext, R.color.color_222222));
            holder.mBookPhone.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
            holder.mPeopleNum.setTextColor(ContextCompat.getColor(mContext, R.color.color_666666));
            holder.mBookRemark.setTextColor(ContextCompat.getColor(mContext, R.color.color_999999));
        }

        return convertView;
    }

    private void handleDataSource(int position, final ViewHolder holder, final PreviewListEntity bean) {
        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!bean.equals(currentBean)){
                    bean.setCheck(true);
                    if (null != currentBean){
                        currentBean.setCheck(false);
                    }
                    currentBean = bean;
                    notifyDataSetChanged();
                }
            }
        });
    }

    static class ViewHolder {
        RelativeLayout mRoot;
        View mLeftLine;
        TextView mBookNum;
        TextView mBookTime;
        TextView mBookPhone;
        View mBottomLine;
        TextView mPeopleNum;
        TextView mBookRemark;

        ViewHolder(View v) {
            mRoot = (RelativeLayout) v.findViewById(R.id.rl_root);
            mLeftLine = (View) v.findViewById(R.id.v_left_line);
            mBookNum = (TextView) v.findViewById(R.id.tv_book_number);
            mBookTime = (TextView) v.findViewById(R.id.tv_book_time);
            mBookPhone = (TextView) v.findViewById(R.id.tv_book_phone);
            mBottomLine = (View) v.findViewById(R.id.v_bottom_line);
            mPeopleNum = (TextView) v.findViewById(R.id.tv_book_people_num);
            mBookRemark = (TextView) v.findViewById(R.id.tv_book_remark);
        }
    }
}
