package com.smart.tvpos.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.request.RequestOptions;
import com.smart.tvpos.R;
import com.smart.tvpos.global.API;
import com.zhy.autolayout.AutoRelativeLayout;

import java.util.List;

public class ADA_CommonDetailPic extends BaseAdapter {

    private List<String> mThumbUrls;

    private List<String> mOriginUrls;

    private Context mContext;//上下文

    public ADA_CommonDetailPic(Context context, List<String> thumbUrls, List<String> originUrls){
        this.mContext = context;
        this.mThumbUrls = thumbUrls;
        this.mOriginUrls = originUrls;
    }

    @Override
    public int getCount() {
        return mThumbUrls.size() > 4 ? 4 : mThumbUrls.size();
    }

    @Override
    public Object getItem(int position) {
        return mThumbUrls.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view;
        ViewHolder viewHolder;

        if (convertView == null) {
            //加载子布局
            view = LayoutInflater.from(mContext).inflate(R.layout.item_common_detail_pic, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) view.findViewById(R.id.img_common_detail);
            viewHolder.tintLayout = (AutoRelativeLayout) view.findViewById(R.id.last_record_tint);
            viewHolder.textViewCount = (TextView) view.findViewById(R.id.tv_full_record_count);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.icon_present_none)
                .error(R.drawable.icon_present_none)
                .priority(Priority.HIGH);
        Glide.with(mContext)
                .load(API.IMG_SERVER_IP + "/" + mOriginUrls.get(position))
                .apply(requestOptions)
                .into(viewHolder.imageView);

        if(position == 3){
            viewHolder.imageView.setBackgroundColor(R.color.color_252424);
            viewHolder.imageView.setAlpha((float) 0.9);
            viewHolder.textViewCount.setText("" + mThumbUrls.size());
            viewHolder.tintLayout.setVisibility(View.VISIBLE);
        }

        return view;
    }

    private class ViewHolder {
        ImageView imageView;
        AutoRelativeLayout tintLayout;
        TextView textViewCount;
    }
}
