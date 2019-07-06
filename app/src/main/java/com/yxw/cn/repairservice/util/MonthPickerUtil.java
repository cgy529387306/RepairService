package com.yxw.cn.repairservice.util;

import android.content.Context;
import android.view.View;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.yxw.cn.repairservice.listerner.OnChooseMonthListener;

import java.util.Calendar;
import java.util.Date;

public class MonthPickerUtil {

    public static void showPicker(Context context,String filterDate, OnChooseMonthListener chooseMonthListener) {
        Calendar selectDate = Calendar.getInstance();
        selectDate.setTime(Helper.string2Date(filterDate,"yyyy-MM"));

        Date date = Helper.string2Date("2000-01-01","yyyy-MM-dd");
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(date);

        Calendar endDate = Calendar.getInstance();
        TimePickerView pvTime = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                chooseMonthListener.getDateTime(date);
            }
        })
                .setType(new boolean[]{true, true, false, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setTitleSize(20)//标题文字大小
                .setTitleText("选择月份")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(false)//是否循环滚动
                .setTitleColor(0xFF333333)//标题文字颜色
                .setSubmitColor(0xFF2aaeff)//确定按钮文字颜色
                .setCancelColor(0xFF2aaeff)//取消按钮文字颜色
                .setTitleBgColor(0xFFffffff)//标题背景颜色 Night mode
                .setBgColor(0xFFffffff)//滚轮背景颜色 Night mode
                .setDate(selectDate)// 如果不设置的话，默认是系统时间*/
                .setRangDate(startDate,endDate)//起始终止年月日设定
                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(false)//是否显示为对话框样式
                .build();
        pvTime.show();
    }

    public static String getMonthStr(Date date){
        Calendar currentCal = Calendar.getInstance();
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(date);
        if (currentCal.get(Calendar.YEAR) == dateCal.get(Calendar.YEAR)){
            //同年
            if (currentCal.get(Calendar.MONTH) == dateCal.get(Calendar.MONTH)){
                return "本月";
            }else{
                return Helper.date2String(date,"M月");
            }
        }else {
            return Helper.date2String(date,"yyyy年M月");
        }

    }


}
