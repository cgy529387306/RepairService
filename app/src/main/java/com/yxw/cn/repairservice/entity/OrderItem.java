package com.yxw.cn.repairservice.entity;

import java.io.Serializable;

public class OrderItem implements Serializable {

    /**
     * acceptId : 7816288277080637440
     * address : 福建省福州市鼓楼区软件园40号2楼
     * agencyId : 350102
     * bookingEndTime : null
     * bookingStartTime : null
     * categoryCName : DIY装机服务（基础版）
     * categoryId : 43
     * categoryPName : 京东服务
     * city : 福州市
     * contactClient : 0
     * createTime : 2019-05-04 15:12:59
     * district : 鼓楼区
     * endTime : null
     * fee : null
     * fixDesc : null
     * handleStatus : 1
     * locationLat : 26.088114
     * locationLng : 119.310492
     * mobile : 13800000000
     * name : 张三
     * operaterId : 7814087432699707392
     * orderId : 7812913626866974720
     * orderSn : JDA12019050400016
     * orderSnOriginal : null
     * orderStatus : 30
     * orderStatusName : 待指派
     * payStatus : 1
     * province : 福建省
     * receiveStatus : 1
     * receiveTime : 2019-05-13 22:42:38
     * remark :
     * serviceProvider : null
     * serviceStatus : 1
     * sfee : null
     * source : 2
     * totalPrice : 150.0
     * userId : null
     */

    private String acceptId;
    private String address;
    private int agencyId;
    private String bookingEndTime;
    private String bookingStartTime;
    private String categoryCName;
    private int categoryId;
    private String categoryPName;
    private String city;
    private int contactClient;
    private String createTime;
    private String district;
    private String endTime;
    private int fee;
    private String fixDesc;
    private int handleStatus;
    private double locationLat;
    private double locationLng;
    private String mobile;
    private String name;
    private String operaterId;
    private String orderId;
    private String orderSn;
    private String orderSnOriginal;
    private int orderStatus;
    private String orderStatusName;
    private int payStatus;
    private String province;
    private int receiveStatus;
    private String receiveTime;
    private String remark;
    private String serviceProvider;
    private int serviceStatus;
    private int sfee;
    private int source;
    private double totalPrice;
    private String userId;

    public String getAcceptId() {
        return acceptId;
    }

    public void setAcceptId(String acceptId) {
        this.acceptId = acceptId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(int agencyId) {
        this.agencyId = agencyId;
    }

    public String getBookingEndTime() {
        return bookingEndTime;
    }

    public void setBookingEndTime(String bookingEndTime) {
        this.bookingEndTime = bookingEndTime;
    }

    public String getBookingStartTime() {
        return bookingStartTime;
    }

    public void setBookingStartTime(String bookingStartTime) {
        this.bookingStartTime = bookingStartTime;
    }

    public String getCategoryCName() {
        return categoryCName;
    }

    public void setCategoryCName(String categoryCName) {
        this.categoryCName = categoryCName;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryPName() {
        return categoryPName;
    }

    public void setCategoryPName(String categoryPName) {
        this.categoryPName = categoryPName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getContactClient() {
        return contactClient;
    }

    public void setContactClient(int contactClient) {
        this.contactClient = contactClient;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public String getFixDesc() {
        return fixDesc;
    }

    public void setFixDesc(String fixDesc) {
        this.fixDesc = fixDesc;
    }

    public int getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(int handleStatus) {
        this.handleStatus = handleStatus;
    }

    public double getLocationLat() {
        return locationLat;
    }

    public void setLocationLat(double locationLat) {
        this.locationLat = locationLat;
    }

    public double getLocationLng() {
        return locationLng;
    }

    public void setLocationLng(double locationLng) {
        this.locationLng = locationLng;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperaterId() {
        return operaterId;
    }

    public void setOperaterId(String operaterId) {
        this.operaterId = operaterId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderSn() {
        return orderSn;
    }

    public void setOrderSn(String orderSn) {
        this.orderSn = orderSn;
    }

    public String getOrderSnOriginal() {
        return orderSnOriginal;
    }

    public void setOrderSnOriginal(String orderSnOriginal) {
        this.orderSnOriginal = orderSnOriginal;
    }

    public int getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusName() {
        return orderStatusName;
    }

    public void setOrderStatusName(String orderStatusName) {
        this.orderStatusName = orderStatusName;
    }

    public int getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(int payStatus) {
        this.payStatus = payStatus;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public int getReceiveStatus() {
        return receiveStatus;
    }

    public void setReceiveStatus(int receiveStatus) {
        this.receiveStatus = receiveStatus;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getServiceProvider() {
        return serviceProvider;
    }

    public void setServiceProvider(String serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    public int getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(int serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public int getSfee() {
        return sfee;
    }

    public void setSfee(int sfee) {
        this.sfee = sfee;
    }

    public int getSource() {
        return source;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
