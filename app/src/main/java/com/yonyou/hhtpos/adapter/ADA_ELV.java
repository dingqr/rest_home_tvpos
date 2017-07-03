package com.yonyou.hhtpos.adapter;

import android.content.Context;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.yonyou.hhtpos.R;

import java.util.List;

public class ADA_ELV extends BaseExpandableListAdapter {
    private List<String> groupData;
    private List<List<String>> childData;
    private Context context;
    //用来装载某个item是否被选中
    SparseBooleanArray selected;
    SparseBooleanArray groupSelected;
    int old = -1;
    int parentPosition = -1;

    private int oldGroupPos = -1;
    private int curGroupPos = -1;
    private boolean isGroupClicked;

    private int preGroupPos;

    public ADA_ELV(List<String> groupData, List<List<String>> childData,
                   Context context) {
        super();
        this.groupData = groupData;
        this.childData = childData;
        this.context = context;
        selected = new SparseBooleanArray();
        groupSelected = new SparseBooleanArray();
    }

    @Override
    public int getGroupCount() {
        return groupData == null ? 0 : groupData.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return childData.get(groupPosition) == null ? 0 : childData.get(
                groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groupData.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return childData.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        MyGroupHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.group_item, null);
            holder = new MyGroupHolder();
            holder.txtv_group = (TextView) convertView.findViewById(R.id.group_textview);
            convertView.setTag(holder);
        } else {
            holder = (MyGroupHolder) convertView.getTag();
        }
        if (isExpanded) {
            preGroupPos = groupPosition;
        }
        //设置选中效果
        if (groupSelected.get(groupPosition)) {
            convertView.setBackgroundResource(R.color.red_check);
        } else {
            convertView.setBackgroundResource(R.color.dark);
        }
        holder.txtv_group.setText(groupData.get(groupPosition));
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        MyChildHolder holder;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.child_item, null);
            holder = new MyChildHolder();
            holder.txtv_child = (TextView) convertView.findViewById(R.id.child_textview);
            convertView.setTag(holder);
        } else {
            holder = (MyChildHolder) convertView.getTag();
        }
        //设置选中效果
        if (selected.get(childPosition) && this.parentPosition == groupPosition) {
            holder.txtv_child.setTextColor(context.getResources().getColor(
                    R.color.red_check));
        } else {
            holder.txtv_child.setTextColor(context.getResources().getColor(
                    R.color.black));
        }
        holder.txtv_child.setText(childData.get(groupPosition).get(childPosition));
        if (isGroupClicked) {
            holder.txtv_child.setTextColor(context.getResources().getColor(
                    R.color.black));
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    /**
     * 设置当前选中的group
     *
     * @param curPos 当前点击的group中item的位置
     */
    public void setSelectedGroupItem(int curPos) {
        this.curGroupPos = curPos;
        if (oldGroupPos != -1) {
            this.groupSelected.put(oldGroupPos, false);
        }
        this.groupSelected.put(curPos, true);
        oldGroupPos = curPos;
    }

    public void setGroupIsClicked(boolean isGroupClicked) {
        this.isGroupClicked = isGroupClicked;
    }

    class MyGroupHolder {
        TextView txtv_group;
    }

    class MyChildHolder {
        TextView txtv_child;
    }

    /**
     * 设置当前选中的child
     *
     * @param groupPosition
     * @param selected
     */
    public void setSelectChildItem(int groupPosition, int selected) {
        this.parentPosition = groupPosition;
        if (old != -1) {
            this.selected.put(old, false);
        }
        this.selected.put(selected, true);
        old = selected;
    }
}
