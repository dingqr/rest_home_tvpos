package com.yonyou.hhtpos.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yonyou.hhtpos.R;
import com.yonyou.hhtpos.bean.FilterItemEntity;
import com.yonyou.hhtpos.bean.FilterOptionsEntity;

import java.util.ArrayList;


/**
 * Created by ybing on 2017/6/23.
 * 邮箱：ybing@yonyou.com
 * 描述：多类型筛选，每个类型内部为单选的组件
 */

public class MultipleFiltrationView extends LinearLayout {

    /**上下文*/
    private Context mContext;

    /**界面控件 */
    private FiltrationView flvChooseDishType;
    private FiltrationView flvChooseDishArea;
    private FiltrationView flvReserveStatus;
    private FiltrationView flvOrderResource;

    /**多类型筛选数据*/
    private ArrayList<FilterItemEntity> filterItemLists;

    public MultipleFiltrationView(Context context) {
        this(context, null);
    }

    public MultipleFiltrationView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultipleFiltrationView(Context context, AttributeSet attrs, int defStyleAttr) {
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
        flvOrderResource = (FiltrationView) convertView.findViewById(R.id.flv_order_resource);
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
        if(filterItemLists!=null && filterItemLists.size()==4){
            flvChooseDishType.setData(filterItemLists.get(0));
            flvChooseDishArea.setData(filterItemLists.get(1));
            flvReserveStatus.setData(filterItemLists.get(2));
            flvOrderResource.setData(filterItemLists.get(3));
        }
    }

    public ArrayList<FilterOptionsEntity> getSelectedItems(){
        ArrayList<FilterOptionsEntity> selectedList = new ArrayList<>();
        selectedList.add(flvChooseDishType.getSelectedData());
        selectedList.add(flvChooseDishArea.getSelectedData());
        selectedList.add(flvReserveStatus.getSelectedData());
        selectedList.add(flvOrderResource.getSelectedData());
        return selectedList;
    }

    public void reset(){
        flvChooseDishArea.reset();
        flvChooseDishType.reset();
        flvReserveStatus.reset();
        flvOrderResource.reset();
    }
}
