package com.yxw.cn.repairservice.contast;

public class UrlConstant {

    public final static int mRoleSign = 2;  // 1工程师端 2服务商端

    public final static String AK = "SMTljHPI6DOnmoYdE1s0ZL24";
    public final static String SK = "YLD5Ff3ncjmGZOoVrgA6A9cfYje0zh4P";

    //正式环境
    public final static String BASE_USER = "http://39.98.73.166:28010";
    public final static String BASE_SERVICE = "http://39.98.73.166:28020";
    //测试环境
//    public final static String BASE_USER = "http://114.115.184.217:28010";
//    public final static String BASE_SERVICE = "http://114.115.184.217:28020";

    public final static String LOGIN = BASE_USER + "/gateway/app/api/loginByPwd";
    public final static String QUICK_LOGIN = BASE_USER + "/gateway/app/api/loginByCode";
    public final static String REGISTER = BASE_USER + "/gateway/app/api/register";
    public final static String REFRESH_TOKEN = BASE_USER + "/gateway/app/refreshToken";

    public final static String REGISTER_VALID = BASE_USER + "/ms70/api/user/auth/ingnore-authority/registerValidation";
    public final static String REGISTER_INFO = BASE_USER + "/ms70/api/user/auth/ingnore-authority/completeUserInfoRegister";

    public final static String FORGET_PASSWORD = BASE_USER + "/ms70/api/user/auth/forgetPwd";//忘记密码
    public final static String MODIFY_PASSWORD = BASE_USER + "/ms70/api/user/info/updatePwd";
    public final static String CHANGE_AVATAR = BASE_USER + "/ms70/api/user/info/updateAvatar";
    public final static String CHANGE_USERINFO = BASE_USER + "/ms70/api/user/info/update";
    public final static String GET_WORKER_INFO = BASE_USER + "/ms70/api/user/info/view";
    public final static String USER_FEEDBACK = BASE_USER + "/ms70/api/user/info/userFeedback";//用户反馈
    public final static String UPLOAD_IDCARD = BASE_USER + "/ms70/api/user/info/uploadIdCard";//身份证上传
    public final static String MY_SERVICE_LIST = BASE_USER + "/ms70/api/user/info/childService/{bindingCode}";//我的工程师列表
    public final static String MY_SERVICE_CHECK_LIST = BASE_USER + "/ms70/api/user/info/findAllByApplicationStart";//工程师加入服务商审核列表
    public final static String JOIN_SERVICE = BASE_USER + "/ms70/api/user/info/joinService/";//工程师加入服务商
    public final static String PARENT_SERVICE = BASE_USER + "/ms70/api/user/info/parentService/{bindingCode}";//隶属服务商
    public final static String SAVE_CATEGORY = BASE_USER + "/ms70/api/user/info/saveProject";//新增用户项目
    public final static String CHILD_SERVICE = BASE_USER + "/ms70/api/user/info/childService/";//我的工程师列表
    public final static String FIND_ALL_APPLY = BASE_USER + "/ms70/api/user/info/findAllByApplicationStart/";//加入服务商申请
    public final static String EXAMINE_APPLY = BASE_USER + "/ms70/api/user/info/examineApply/";//加入服务商申请 同意或者拒绝
    public final static String DELETE_ENGINEER = BASE_USER + "/ms70/api/user/info/deleteFixUse/";// 服务商端删除工程师
    public final static String UPDATE_ALIPAY_ACCOUNT = BASE_USER + "/ms70/api/user/info/saveAliplay/";//1015 - 用户 - 个人资料 - 修改支付宝账号
    public final static String APPLY_WITHDRAWAL = BASE_USER + "/ms70/api/user/info/destoonFinanceCash";//用户提现申请
    public final static String APPLY_WITHDRAWAL_LIST = BASE_USER + "/ms70/api/fix/user/transaction/record/findAll";//交易明细
    public final static String USER_SETTLEMENT = BASE_USER + "/ms70/api/fix/user/settlement/center/settlementCenter";//结算中心
    public final static String USER_SETTLEMENT_DETAIL = BASE_USER + "/ms70/api/fix/user/settlement/center/settlementDetail/";//结算明细
    public final static String CHANGE_USER_STATE = BASE_USER + "/ms70/api/user/info/updateRest/";
    public final static String UPDATE_LOCATION = BASE_USER + "/ms70/api/user/info/updateCurrent";
    public final static String UPDATE_VERSION = BASE_USER + "/ms70/api/user/info/updateSoftVersion/";
    public final static String GET_QR = BASE_USER + "/ms70/api/fix/user/generalize/code/selectAll";
    public final static String GET_PROMOTE_CODE = BASE_USER + "/ms70/api/user/info/invitationCode/";

    public final static String GET_CODE = BASE_SERVICE + "/ms20/api/validateCode/code/ingnore-internal/getCode";
    public final static String GET_CODE_REGISTER = BASE_SERVICE + "/ms20/api/validateCode/code/ingnore-internal/getRegisterCode";
    public final static String GET_ALL_CATEGORY = BASE_USER + "/ms20/api/sys/dicts/ingnore-authority/categoryList";//获取项目分类列表
    public final static String GET_ALL_REGION = BASE_USER + "/ms20/api/sys/region/ingnore-authority/allRegionTree";//获取所有地区接口
    public final static String GET_EXCEPTION_REASON = BASE_USER + "/ms20/api/sys/dicts/findDictsByKey";//查询数据字典
    public final static String GET_LUNBO = BASE_USER + "/ms20/api/fix/app/picture/findAllList";//app轮播图分页查询
    public final static String GET_NOTICE = BASE_USER + "/ms20/api/fix/app/notice/findNotice";//公告列表
    public final static String GET_NOTICE_READ = BASE_USER + "/ms20/api/fix/app/notice/saveRead/";//已读

    public final static String ORDER_SERVICE_LIST = BASE_USER + "/ms60/api/fix/order/findServiceProviderOrderList/";//服务商端订单条件查询
    public final static String ORDER_SERVICE_ASSIGN = BASE_USER + "/ms60/api/fix/order/accept/serviceAssign/";//服务商指派工程师
    public final static String ORDER_RECEIVE = BASE_USER + "/ms60/api/fix/order/accept/serviceReceive/";//服务商确认接单
    public final static String ORDER_CONFIRM_RECEIVE = BASE_USER + "/ms60/api/fix/order/confirmOrder";//工程师确认接单
    public final static String ORDER_CANCEL = BASE_USER + "/ms60/api/fix/order/accept/cancelReceiveOrder/{orderId}";//工程师取消接单
    public final static String ORDER_ARRIVAL = BASE_USER + "/ms60/api/fix/order/accept/confirmArrival";//工程师确认到场
    public final static String ORDER_FINISH = BASE_USER + "/ms60/api/fix/order/accept/endServiceBycode";//工程师服务完成
    public final static String ORDER_RESERVATION = BASE_USER + "/ms60/api/fix/order/accept/reservation";//工程师预约上门时间
    public final static String ORDER_START = BASE_USER + "/ms60/api/fix/order/accept/startService/{orderId}";//工程师开始服务
    public final static String ORDER_TURN_RESERVATION = BASE_USER + "/ms60/api/fix/order/accept/turnReservation";//工程师开始服务
    public final static String ORDER_LIST = BASE_USER + "/ms60/api/fix/order/findAll";//订单条件查询
    public final static String ORDER_STATUS_LIST = BASE_USER + "/ms60/api/fix/order/getOrderStatusList";//getOrderStatusList
    public final static String ORDER_DETAIL = BASE_USER + "/ms60/api/fix/order/orderDetails/";//获取订单详情
    public final static String ORDER_EXEPTION_APPOINT = BASE_USER + "/ms60/api/fix/order/exception/appointment";//预约异常
    public final static String ORDER_EXEPTION_SIGN = BASE_USER + "/ms60/api/fix/order/exception/signIn";//签到异常
    public final static String ORDER_SERVICE_RETURN = BASE_USER + "/ms60/api/fix/order/accept/serviceReturn/";//服务商申请退单
    public final static String ORDER_DETAIL_DFP = BASE_USER + "/ms60/api/fix/order/dfpOrderDetails/";//查询订单详情信息（待分派的订单）serviceId
    public final static String ORDER_DETAIL_DJD = BASE_USER + "/ms60/api/fix/order/djdOrderDetails/";//查询订单详情信息(未接单的订单) orderId
    public final static String ORDER_DETAIL_YFP = BASE_USER + "/ms60/api/fix/order/yfpOrderDetails/";//查询订单详情信息（已接单或已分派后的订单）acceptId
    public final static String ORDER_RETURN = BASE_USER + "/ms60/api/fix/order/accept/engineerReturn";//工程师申请退单
    public final static String ORDER_RETURN_SERVICE = BASE_USER + "/ms60/api/fix/order/accept/serviceReturn";//服务商申请退单

    public final static String USER_EVALUATE = BASE_USER + "/api/fix/userOrder/evaluate";
    public final static String USER_COMPLAIN = BASE_USER + "/api/fix/userOrder/complain";

    public final static String H5_URL_ABOUT = "https://www.hmjx123.com/static/center/about_us.html";
    public final static String H5_URL_HELP = "https://www.hmjx123.com/static/center/help.html";
    public final static String H5_URL_AGREEMENT = "https://www.hmjx123.com/static/safe/safe.html";
    public final static String CUSTOMER_TEL = "13763878621";

}
