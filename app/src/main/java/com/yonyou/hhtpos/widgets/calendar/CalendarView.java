package com.yonyou.hhtpos.widgets.calendar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.yonyou.hhtpos.R;

import java.util.ArrayList;
import java.util.List;

public class CalendarView extends LinearLayout {
	private MonthDateView monthDateView;
	private DateViewClick dateViewClick;
	private List<DayAndPrice> listDayAndPrice = new ArrayList<>();
	private List<WorkOrRelax> listWorkOrRelax = new ArrayList<>();
	public CalendarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		View view = LayoutInflater.from(context).inflate(R.layout.view_calendar, this);
		monthDateView = (MonthDateView) view.findViewById(R.id.v_month_date);
		monthDateView.setDayAndPriceList(listDayAndPrice);
		monthDateView.setDaysWorkOrRelaxList(listWorkOrRelax);
		monthDateView.setDateClick(new MonthDateView.DateClick() {
			
			@Override
			public void onClickOnDate() {
				if(dateViewClick != null){
					dateViewClick.dateClick();
				}
			}
		});
	}

	/**
	 * 设置有事务的号码
	 * @param listDayAndPrice
	 */
	public void setListDayAndPrice(List<DayAndPrice> listDayAndPrice) {
		this.listDayAndPrice = listDayAndPrice;
		monthDateView.setDayAndPriceList(listDayAndPrice);
	}
	/**
	 * 设置哪天是休息天的号码
	 * @param listWorkOrRelax
	 */
	public void setListWorkOrRelax(List<WorkOrRelax> listWorkOrRelax) {
		this.listWorkOrRelax = listWorkOrRelax;
		monthDateView.setDaysWorkOrRelaxList(listWorkOrRelax);
	}
	
	/**
	 * 获取所选择的年份
	 * @return
	 */
	public int getSelectYear(){
		return monthDateView.getmSelYear();
	}
	
	/**
	 * 获取所选择的月份
	 * @return
	 */
	public int getSelectMonth(){
		return monthDateView.getmSelMonth();
	}
	/**
	 * 获取所选择的日期
	 * @return
	 */
	public int getSelectDay(){
		return monthDateView.getmSelDay();
	}
	
	/**
	 * 设置日期的click事件
	 * @param dateViewClick
	 */
	public void setDateViewClick(DateViewClick dateViewClick) {
		this.dateViewClick = dateViewClick;
	}
	public interface DateViewClick{
		public void dateClick();
	}

}
