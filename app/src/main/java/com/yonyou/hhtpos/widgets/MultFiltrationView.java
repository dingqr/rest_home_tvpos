package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.adapter.FiltrationAdapter;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ybing on 2017/6/23.
 * 邮箱：ybing@yonyou.com
 * 描述：多项筛选组件
 */

public class MultFiltrationView extends LinearLayout {

    /**上下文*/
    private Context mContext;

    /**界面控件 */
    private FiltrationView flvChooseDishType;
    private FiltrationView flvChooseDishArea;
    private FiltrationView flvReserveStatus;

    /**多类型筛选数据*/
    private ArrayList<FilterItemEntity> filterItemLists;

    public MultFiltrationView(Context context) {
        this(context, null);
    }

    public MultFiltrationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultFiltrationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
    }


    private void initView() {
        //初始化view
        View convertView = LayoutInflater.from(mContext).inflate(R.layout.mult_filtration_view, this);
        flvChooseDishType = (FiltrationView) convertView.findViewById(R.id.flv_dish_type);
        flvChooseDishArea = (FiltrationView) convertView.findViewById(R.id.flv_dish_area);
        flvReserveStatus = (FiltrationView) convertView.findViewById(R.id.flv_reserve_status);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    public void setFilterItemLists(ArrayList<FilterItemEntity> filterItemLists) {
        this.filterItemLists = filterItemLists;
        if(filterItemLists!=null && filterItemLists.size()==3){
            flvChooseDishType.setData(filterItemLists.get(0));
            flvChooseDishArea.setData(filterItemLists.get(1));
            flvReserveStatus.setData(filterItemLists.get(2));
        }
    }

    public ArrayList<FilterOptionsEntity> getSelectedItems(){
        ArrayList<FilterOptionsEntity> selectedList = new ArrayList<>();
        selectedList.add(flvChooseDishType.getSelectedData());
        selectedList.add(flvChooseDishArea.getSelectedData());
        selectedList.add(flvReserveStatus.getSelectedData());
        return selectedList;
    }
}
