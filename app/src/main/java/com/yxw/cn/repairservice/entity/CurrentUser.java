package com.yxw.cn.repairservice.entity;

import android.util.Log;

import com.yxw.cn.repairservice.util.Helper;
import com.yxw.cn.repairservice.util.JsonHelper;
import com.yxw.cn.repairservice.util.PreferencesHelper;


/**
 * 作者：cgy on 16/12/26 22:53
 * 邮箱：529387306@qq.com
 */
public class CurrentUser extends LoginInfo{

    //region 单例
    private static final String TAG = CurrentUser.class.getSimpleName();
    private static final String USER = "CurrentUser";
    private static CurrentUser me;
    /**
     * 单例
     * @return 当前用户对象
     */
    public static CurrentUser getInstance() {
        if (me == null) {
            me = new CurrentUser();
        }
        return me;
    }

    /**
     * 出生
     * <p>尼玛！终于出生了！！！</p>
     * <p>调用此方法查询是否登录过</p>
     * @return 出生与否
     */
    public boolean isLogin() {
        String json = PreferencesHelper.getInstance().getString(USER);
        me = JsonHelper.fromJson(json, CurrentUser.class);
        return me != null;
    }

    public boolean login(LoginInfo entity) {
        boolean born = false;
        String json = "";
        if (entity != null) {
            if (Helper.isNotEmpty(entity.getToken())){
                me.setToken(entity.getToken());
            }
            if (Helper.isNotEmpty(entity.getRefreshToken())){
                me.setRefreshToken(entity.getRefreshToken());
            }
            if (Helper.isNotEmpty(entity.getCategory())){
                me.setCategory(entity.getCategory());
            }
            if (Helper.isNotEmpty(entity.getResidentArea())){
                me.setResidentArea(entity.getResidentArea());
            }
            if (Helper.isNotEmpty(entity.getResidentAreaName())){
                me.setResidentAreaName(entity.getResidentAreaName());
            }
            me.setLastLoginTime(entity.getLastLoginTime());
            me.setMobile(entity.getMobile());
            me.setAvatar(entity.getAvatar());
            me.setUserName(entity.getUserName());
            me.setRealName(entity.getRealName());
            me.setUserId(entity.getUserId());
            me.setNickname(entity.getNickname());
            me.setExpire(entity.getExpire());
            me.setRole(entity.getRole());
            me.setRegisterTime(entity.getRegisterTime());
            me.setParentId(entity.getParentId());
            me.setIdCardFront(entity.getIdCardFront());
            me.setIdCardBack(entity.getIdCardBack());
            me.setIdCardHand(entity.getIdCardHand());
            me.setServiceDate(entity.getServiceDate());
            me.setServiceTime(entity.getServiceTime());
            me.setIdCardStatus(entity.getIdCardStatus());
            me.setAliplayAccount(entity.getAliplayAccount());
            me.setCarryAmount(entity.getCarryAmount());
            me.setDeposit(entity.getDeposit());
            me.setBindingCode(entity.getBindingCode());
            me.setSettlementAmount(entity.getSettlementAmount());
            me.setIsRest(entity.getIsRest());
            json = JsonHelper.toJson(me);
            born = me != null;
        }
        // 生完了
        if (!born) {
            Log.e(TAG, "尼玛，流产了！！！");
        } else {
            PreferencesHelper.getInstance().putString(USER,json);
        }
        return born;
    }

    // endregion 单例

    /**
     * 退出登录清理用户信息
     */
    public void loginOut() {
        me = null;
        PreferencesHelper.getInstance().putString(USER, "");
    }
}
