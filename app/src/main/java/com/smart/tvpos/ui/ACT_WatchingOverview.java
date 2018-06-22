package com.smart.tvpos.ui;

import android.os.Bundle;
import android.view.View;

import com.smart.framework.library.base.BaseActivity;
import com.smart.framework.library.bean.ErrorBean;
import com.smart.framework.library.netstatus.NetUtils;
import com.smart.tvpos.R;

/**
 * Created by JoJo on 2018/6/22.
 * wechat：18510829974
 * description：监控概览页面
 */
public class ACT_WatchingOverview extends BaseActivity {
//    @Bind(R.id.column_chart_cc)
//    ColumnChartView mColumnChartCc;
//    private ColumnChartData data; // 柱形图对应的各种属性
//    private boolean hasAxes = true; // 是否要添加横纵轴的属性
//    private boolean hasAxesNames = true; // 是否设置横纵轴的名字
//    private boolean hasLabels = false; // 是否显示柱形图的数据
//    private boolean hasLabelForSelected = false; // 是否点中显示数据

    @Override
    protected void getBundleExtras(Bundle extras) {

    }

    @Override
    protected void initViewsAndEvents() {
//        generateSubcolumnsData();
    }



    private void generateSubcolumnsData() {

//        int numSubcolumns = 1;
//        int numColumns = 3; // 表示总共有三根柱子
//        List<Column> columns = new ArrayList<Column>();
//        List<SubcolumnValue> values; // 柱子的属性
//        List<AxisValue> axisValueList = new ArrayList<>();
//        Float[] floats = {30f, 20f, 50f}; // 包含柱形图的数值的数组
//        String[] selecedNames = {"选项一", "选项二", "选项三"}; // 包含柱子的名称的数组
//        int[] colors = {ContextCompat.getColor(mContext, R.color.color_07f2ab), ContextCompat.getColor(mContext, R.color.color_79d4d8), ContextCompat.getColor(mContext, R.color.color_4388bc)};
//        for (int i = 0; i < numColumns; ++i) {
//            values = new ArrayList<SubcolumnValue>();
//            values.add(new SubcolumnValue(floats[i], colors[i])); // 将柱子的数据以及颜色设置给 SubcolumnValue
//            axisValueList.add(new AxisValue(i).setLabel(selecedNames[i]));
//            Column column = new Column(values); // 设置整根柱子的属性
//            column.setHasLabels(hasLabels); // 是否显示柱子的数据
//            column.setHasLabelsOnlyForSelected(hasLabelForSelected); // 是否选中显示数据，一般为false
//            columns.add(column);
//        }
//
//        data = new ColumnChartData(columns);
//        data.setAxisXBottom(new Axis(axisValueList)); // 设置 Y 轴的属性
//        data.setAxisYLeft(new Axis().setHasLines(true)); // 设置 X 轴的属性
//        mColumnChartCc.setColumnChartData(data); // 将数据设置给显示柱形图的控件

    }

    @Override
    public void showBusinessError(ErrorBean error) {

    }

    @Override
    protected boolean isApplyKitKatTranslucency() {
        return false;
    }


    @Override
    protected int getContentViewLayoutID() {
        return R.layout.act_watching_overview;
    }

    @Override
    protected View getLoadingTargetView() {
        return null;
    }


    @Override
    protected void onNetworkConnected(NetUtils.NetType type) {

    }

    @Override
    protected void onNetworkDisConnected() {

    }

    @Override
    protected boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @Override
    protected boolean isBindEventBusHere() {
        return false;
    }

    @Override
    protected boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    protected TransitionMode getOverridePendingTransitionMode() {
        return null;
    }
}
