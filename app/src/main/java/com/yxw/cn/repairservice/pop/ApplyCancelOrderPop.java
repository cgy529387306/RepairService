package com.yxw.cn.repairservice.pop;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.EngineerInfo;
import com.yxw.cn.repairservice.entity.InServiceInfo;
import com.yxw.cn.repairservice.entity.OrderItem;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.util.EventBusUtil;

import java.util.List;


/**
 * Created by chenqm
 */
public class ApplyCancelOrderPop extends PopupWindow implements View.OnClickListener {
    private View mContentView;
    private Activity mContext;
    private TextView mTvEngineer;
    private TextView mTvCancel;
    private TextView mTvConfirm;
    private InServiceInfo item;
    private SelectListener mSelectListener;

    public interface SelectListener {
        void onComfirm(InServiceInfo orderItem);
    }

    public ApplyCancelOrderPop(Activity context,InServiceInfo item,SelectListener mSelectListener) {
        this.mContext = context;
        this.item = item;
        this.mSelectListener = mSelectListener;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContentView = inflater.inflate(R.layout.pop_apply_cancel_order, null);
        // 设置SelectPicPopupWindow的View
        this.setContentView(mContentView);
        setFocusable(false);
        setOutsideTouchable(false);
        setWidth(ViewPager.LayoutParams.MATCH_PARENT);
        setHeight(ViewPager.LayoutParams.MATCH_PARENT);
        setBackgroundDrawable(new BitmapDrawable());
        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        initView();
        initListener();
    }

    private void initView() {
        mTvCancel = mContentView.findViewById(R.id.tv_cancel);
        mTvConfirm = mContentView.findViewById(R.id.tv_confirm);
    }

    private void initListener() {
        mTvCancel.setOnClickListener(this);
        mTvConfirm.setOnClickListener(this);
    }

    /**
     * 显示popupWindow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {
        if (!this.isShowing()) {
            // 以下拉方式显示popupwindow
            this.showAtLocation(parent, Gravity.CENTER, 0, 0);
        } else {
            this.dismiss();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        dismiss();
        if (id == R.id.tv_cancel){
        }else if (id == R.id.tv_confirm){
            mSelectListener.onComfirm(item);
        }
    }
}
