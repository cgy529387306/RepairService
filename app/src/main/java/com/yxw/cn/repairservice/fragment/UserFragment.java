package com.yxw.cn.repairservice.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.yxw.cn.repairservice.BaseFragment;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.activity.MsgListActivity;
import com.yxw.cn.repairservice.activity.WebActivity;
import com.yxw.cn.repairservice.activity.setting.SettingActivity;
import com.yxw.cn.repairservice.activity.setting.UserFeedBackActivity;
import com.yxw.cn.repairservice.activity.user.PersonInfoActivity;
import com.yxw.cn.repairservice.activity.user.WalletActivity;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.CurrentUser;
import com.yxw.cn.repairservice.entity.LoginInfo;
import com.yxw.cn.repairservice.entity.MessageEvent;
import com.yxw.cn.repairservice.util.AppUtil;
import com.yxw.cn.repairservice.util.Helper;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 用户中心
 * Created by cgy on 2018/11/25
 */
public class UserFragment extends BaseFragment {
    @BindView(R.id.iv_avatar)
    CircleImageView mIvAvatar;
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_carry_amount)
    TextView mTvCarryAmount;
    @BindView(R.id.tv_deposit)
    TextView mTvDeposit;
    @BindView(R.id.tv_settlement_amount)
    TextView mTvSettlementAmount;

    @Override
    protected int getLayout() {
        return R.layout.frg_user;
    }

    @Override
    protected void initView() {
        notifyInfo();
    }

    public void notifyInfo() {
        if (CurrentUser.getInstance().isLogin()){
            LoginInfo loginInfo = CurrentUser.getInstance();
            mTvName.setText(Helper.isEmpty(loginInfo.getRealName())?loginInfo.getUserName():loginInfo.getRealName());
            mTvPhone.setText(loginInfo.getMobile());
            AppUtil.showPic(mContext, mIvAvatar, loginInfo.getAvatar());
            mTvCarryAmount.setText(loginInfo.getCarryAmount());
            mTvDeposit.setText(loginInfo.getDeposit());
            mTvSettlementAmount.setText(loginInfo.getSettlementAmount());
        }
    }


    @OnClick({R.id.ll_info, R.id.ll_withdrawal, R.id.tv_contact, R.id.tv_help, R.id.tv_feedback,R.id.tv_join,R.id.img_setting,R.id.iv_msg})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.ll_info:
                startActivity(PersonInfoActivity.class);
                break;
            case R.id.ll_withdrawal:
                if(CurrentUser.getInstance().isLogin() && CurrentUser.getInstance().getIdCardStatus()==3){
                    Bundle walletBundle = new Bundle();
                    walletBundle.putString("title", "钱包");
                    walletBundle.putString("carryAmount",mTvCarryAmount.getText().toString());
                    walletBundle.putString("deposit",mTvDeposit.getText().toString());
                    walletBundle.putString("settlementAmount",mTvSettlementAmount.getText().toString());
                    startActivity(WalletActivity.class, walletBundle);
                }else{
                    toast("工程师身份审核未通过!");
                }
                break;
            case R.id.tv_contact:
                new MaterialDialog.Builder(getActivity()).title("联系客服").content("确认拨打客服电话:"+ UrlConstant.CUSTOMER_TEL+"?")
                        .positiveText("确定").onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        Uri data = Uri.parse("tel:" + UrlConstant.CUSTOMER_TEL);
                        intent.setData(data);
                        startActivity(intent);
                    }
                }).negativeText("取消").show();
                break;
            case R.id.tv_help:
                Bundle webBundle = new Bundle();
                webBundle.putString("url", UrlConstant.H5_URL_HELP);
                webBundle.putString("title", "帮助中心");
                startActivity(WebActivity.class, webBundle);
                break;
            case R.id.tv_feedback:
                startActivity(UserFeedBackActivity.class);
                break;
            case R.id.tv_join:
                Bundle bundle = new Bundle();
                bundle.putString("url", UrlConstant.H5_URL_ABOUT);
                bundle.putString("title", "加入我们");
                startActivity(WebActivity.class, bundle);
                break;
            case R.id.iv_msg:
                startActivity(MsgListActivity.class);
                break;
        }
    }

    @Override
    public void onEvent(MessageEvent event) {
        super.onEvent(event);
        switch (event.getId()) {
            case MessageConstant.NOTIFY_UPDATE_INFO:
                notifyInfo();
                break;
        }
    }


}
