package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.RadioButton;

import com.yonyou.framework.library.base.BaseAbsAdapter;
import com.yonyou.framework.library.common.utils.StringUtil;
import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.NavigationNewEntity;
import com.yonyou.hhtpos.util.IntentUtil;

import java.util.List;

/**
 * 左导航栏一级菜单adapter
 * 作者：liushuofei on 2017/6/27 15:54
 */
public class ADA_NavigationMain extends BaseAbsAdapter<NavigationNewEntity> {

    /**传入参数 */
    private Context context;
    private OnCheckChangedListener onCheckChangedListener;

    private RadioButton mCurrentSelected;

    public ADA_NavigationMain(Context context, OnCheckChangedListener onCheckChangedListener) {
        super(context);

        this.context = context;
        this.onCheckChangedListener = onCheckChangedListener;
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_navigation_main, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final NavigationNewEntity bean = mDataSource.get(position);
        handleDataSource(position, holder, bean);

        return convertView;
    }

    private void handleDataSource(int position, final ViewHolder holder, final NavigationNewEntity bean) {
        if (null == bean)
            return;

        int resId = 0;

        switch (position) {
            case 0:
                resId = R.drawable.rb_ts_draw_left;
                break;

            case 1:
                resId = R.drawable.rb_wd_draw_left;
                break;

            case 2:
                resId = R.drawable.rb_wm_draw_left;
                break;

            case 3:
                resId = R.drawable.rb_book_draw_left;
                break;

            case 4:
                resId = R.drawable.rb_member_draw_left;
                break;

            case 5:
                resId = R.drawable.rb_basic_draw_left;
                break;

            case 6:
                resId = R.drawable.rb_business_draw_left;
                break;

            default:
                break;
        }

        // 一级菜单名称
        holder.mTxt.setText(StringUtil.getString(bean.getFunctionName()));

        // 一级菜单两侧icon
        Drawable left, right;
        Resources res = mContext.getResources();
        left = res.getDrawable(resId);
        //调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        left.setBounds(0, 0, left.getMinimumWidth(), left.getMinimumHeight());

        right = res.getDrawable(R.drawable.rb_navigation_main_right);
        //调用setCompoundDrawables时，必须调用Drawable.setBounds()方法,否则图片不显示
        right.setBounds(0, 0, right.getMinimumWidth(), right.getMinimumHeight());

        if (null != bean.getChildList() && bean.getChildList().size() > 0){
            holder.mTxt.setCompoundDrawables(left, null, right, null);
        }else {
            holder.mTxt.setCompoundDrawables(left, null, null, null);
        }

        holder.mTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != onCheckChangedListener){
                    onCheckChangedListener.onChange(bean.getChildList());
                }

                // 跳转逻辑
                if (null == bean.getChildList() || bean.getChildList().size() == 0){
                    IntentUtil.handleNavigationIntent(mContext, bean.getFunctionName());
                }

                // 更新选中状态
                if (null != mCurrentSelected && !mCurrentSelected.equals(holder.mTxt)){
                    mCurrentSelected.setChecked(false);
                }
                mCurrentSelected = holder.mTxt;
            }
        });

    }

    static class ViewHolder {
        RadioButton mTxt;

        ViewHolder(View v) {
            mTxt = (RadioButton) v.findViewById(R.id.rb_category);
        }
    }

    public interface OnCheckChangedListener {
        void onChange(List<NavigationNewEntity.SecondList> dataList);
    }
}
