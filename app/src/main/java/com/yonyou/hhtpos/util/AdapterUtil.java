package com.yonyou.hhtpos.util;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.yonyou.hhtpos.base.ADA_BaseRcyclerView;

/**
 * 适配器工具类
 * 作者：liushuofei on 2017/1/5 16:01
 */
public class AdapterUtil {

    // 默认的每页加载条数
    public static final int DEFAULT_PAGE_SIZE = 60;

    /**
     * 获取加载更多页数
     */
    public static  int getPage(BaseAdapter mAdapter, int pageSize){
        int page = 1;
        int len = mAdapter.getCount();
        if (len > 0) {
            if (len % pageSize == 0) {
                page = len / pageSize + 1;
            } else {
                page = len / pageSize + 2;
            }
        }
        return page;
    }
    /**
     * 获取加载更多页数
     */
    public static  int getPage(RecyclerView.Adapter mAdapter, int pageSize){
        int page = 1;
        int len = mAdapter.getItemCount();
        if (len > 0) {
            if (len % pageSize == 0) {
                page = len / pageSize + 1;
            } else {
                page = len / pageSize + 2;
            }
        }
        return page;
    }

    /**
     * 获取加载更多页数
     * @param mAdapter  RecylcerView的Adapter
     * @param pageSize  每一页加载条数
     * @return
     */
    public static  int getPageRcy(ADA_BaseRcyclerView mAdapter, int pageSize){
        int page = 1;
        int len = mAdapter.getItemCount();
        if (len > 0) {
            if (len % pageSize == 0) {
                page = len / pageSize + 1;
            } else {
                page = len / pageSize + 2;
            }
        }
        return page;
    }

    /**
     * 手动测量listview的高度，解决ScrollView嵌套ListView导致的高度塌陷问题
     * @param listView
     */
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, listView);
            // 计算子项View的宽高
            listItem.measure(0, 0);
            // 统计所有子项的总高度
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        listView.setLayoutParams(params);
    }


}
