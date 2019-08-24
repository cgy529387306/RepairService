package com.yxw.cn.repairservice.fragment.register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yxw.cn.repairservice.BaseFragment;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.activity.user.ChooseCategoryActivity1;
import com.yxw.cn.repairservice.activity.user.RegisterSuccessActivity;
import com.yxw.cn.repairservice.adapter.MyCategoryAdapter;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.contast.SpConstant;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.Category;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.util.AppUtil;
import com.yxw.cn.repairservice.util.Base64Util;
import com.yxw.cn.repairservice.util.EventBusUtil;
import com.yxw.cn.repairservice.util.Helper;
import com.yxw.cn.repairservice.util.PreferencesHelper;
import com.yxw.cn.repairservice.util.RegionPickerUtil;
import com.yxw.cn.repairservice.util.SpUtil;
import com.yxw.cn.repairservice.util.ToastUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import cn.jpush.android.api.JPushInterface;

/**
 * 录入用户信息
 */
public class UserInfoFragment extends BaseFragment {
    @BindView(R.id.tv_name)
    TextView mTvName;
    @BindView(R.id.tv_phone)
    TextView mTvPhone;
    @BindView(R.id.tv_idCardNo)
    TextView mTvIdCardNo;
    @BindView(R.id.tv_resident)
    TextView mTvResident;
    @BindView(R.id.tv_service_provider)
    TextView mTvServiceProvider;
    @BindView(R.id.rv_category)
    RecyclerView mRvCate;
    @BindView(R.id.ll_good)
    LinearLayout mLlGood;
    @BindView(R.id.btn_confirm)
    Button mBtnConfirm;

    private List<String> mCateList = new ArrayList<String>();
    private List<Category> mCategoryList = new ArrayList<Category>();
    private MyCategoryAdapter mCateAdapter;

    @Override
    protected int getLayout() {
        return R.layout.frg_user_info;
    }

    @Override
    public void initView() {
        mCateAdapter = new MyCategoryAdapter(mCateList);
        mRvCate.setNestedScrollingEnabled(false);
        mRvCate.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        mRvCate.setAdapter(mCateAdapter);
        mTvPhone.setText(RegisterFragment.mPhone==null?"":RegisterFragment.mPhone);
    }


    @OnClick({ R.id.ll_good, R.id.ll_resident, R.id.ll_service_provider,R.id.btn_confirm})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_good:
                Intent intent = new Intent(getActivity(), ChooseCategoryActivity1.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("cateList", (Serializable) mCateList);
                intent.putExtras(bundle);
                startActivityForResult(intent,1001);
                break;
            case R.id.ll_resident:
                AppUtil.disableViewDoubleClick(view);
                RegionPickerUtil.showPicker(getActivity(), mTvResident, false);
                break;
            case R.id.ll_service_provider:
//                if (loginInfo!=null && !TextUtils.isEmpty(loginInfo.getParentId())){
//                    startActivity(JoinServiceProviderActivity.class);
//                }else{
//                    startActivity(ServiceProviderEmptyActivity.class);
//                }
                break;
            case R.id.btn_confirm:
                doRegister();
                break;
        }
    }


    private void doRegister(){
        if (Helper.isEmpty(mTvResident.getTag())){
            ToastUtil.show("请选择常驻地址");
            return;
        }
        if (Helper.isEmpty(mCategoryList)){
            ToastUtil.show("请选择擅长项目");
            return;
        }
        showLoading();
        mBtnConfirm.setEnabled(false);
        Map<String, Object> map = new HashMap<>();
        map.put("userName", RegisterFragment.mPhone);
        map.put("password", RegisterFragment.mPassword);
        map.put("invitationCode", RegisterFragment.mInviteCode);
        map.put("appSign", UrlConstant.mRoleSign);
        map.put("idCardFront", Base64Util.getBase64ImageStr(IdCardFragment.idCardFront));
        map.put("idCardBack", Base64Util.getBase64ImageStr(IdCardFragment.idCardBack));
        map.put("idCardHand", Base64Util.getBase64ImageStr(IdCardFragment.icCardBoth));
        map.put("residentArea", (String) mTvResident.getTag());
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<mCategoryList.size();i++){
            Category category = mCategoryList.get(i);
            if (i == mCategoryList.size()-1){
                sb.append(category.getName());
            }else{
                sb.append(category.getName()).append(",");
            }
        }
        map.put("category",sb.toString());
        String rid = PreferencesHelper.getInstance().getString(SpConstant.REGISTER_ID);
        if (Helper.isEmpty(rid)){
            rid = JPushInterface.getRegistrationID(getActivity());
        }
        map.put("regId", rid);
        OkGo.<ResponseData<Object>>post(UrlConstant.REGISTER_INFO)
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<ResponseData<Object>>() {
                             @Override
                             public void onSuccess(ResponseData<Object> response) {
                                 dismissLoading();
                                 mBtnConfirm.setEnabled(true);
                                 if (response!=null){
                                     if (response.isSuccess()) {
                                         toast("注册成功");
                                         SpUtil.putStr(SpConstant.LOGIN_MOBILE, RegisterFragment.mPhone);
                                         startActivity(RegisterSuccessActivity.class);
                                         EventBusUtil.post(MessageConstant.REGISTER);
                                         getActivity().finish();
                                     }else{
                                         toast(response.getMsg());
                                     }
                                 }
                             }

                             @Override
                             public void onError(Response<ResponseData<Object>> response) {
                                 super.onError(response);
                                 dismissLoading();
                                 mBtnConfirm.setEnabled(true);
                             }
                         }
                );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            mCategoryList = (List<Category>) data.getSerializableExtra("cateList");
            mCateList.clear();
            for (Category category:mCategoryList){
                mCateList.add(category.getName());
            }
            mCateAdapter.setNewData(mCateList);
        }
    }

}