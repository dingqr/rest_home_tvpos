package com.yonyou.framework.library.widgets.cart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;

import com.yonyou.framework.library.R;
import com.yonyou.framework.library.widgets.cart.mode.Category;

import java.util.List;

/**
 * 作者：liushuofei on 2016/12/14 14:56
 * 邮箱：lsf@yonyou.com
 */
public class ShopMainAdapter extends BaseAdapter {

    private List<Category> categoryList;
    private LayoutInflater mInflater;

    private int mCurrentPosition;
    private Category mCurrentBean;

    public ShopMainAdapter(Context context, List<Category> categoryList) {
        this.categoryList = categoryList;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return categoryList.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.categorize_item, null);
            viewHolder = new ViewHolder();
            viewHolder.dishesName = (RadioButton) convertView.findViewById(R.id.rb_category);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Category bean = categoryList.get(position);
        viewHolder.dishesName.setText(bean.getDishesName());

        if (bean.isCheck()){
            mCurrentPosition = position;
            mCurrentBean = bean;
            viewHolder.dishesName.setChecked(true);
        }else{
            viewHolder.dishesName.setChecked(false);
        }

        return convertView;
    }

    static class ViewHolder {
        RadioButton dishesName;
    }

    public List<Category> getCategoryList() {
        return categoryList;
    }

    public void setCurrentSelected(Category category){
        mCurrentBean = category;
    }

    public Category getCurrentSelected(){
        return mCurrentBean;
    }

    public int getCurrentPosition(){
        return mCurrentPosition;
    }

}
