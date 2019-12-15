package com.yxw.cn.repairservice.activity.order;

import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.map.Stroke;
import com.baidu.mapapi.model.LatLng;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;
import com.yxw.cn.repairservice.BaseActivity;
import com.yxw.cn.repairservice.R;
import com.yxw.cn.repairservice.adapter.GridImageAdapter;
import com.yxw.cn.repairservice.contast.MessageConstant;
import com.yxw.cn.repairservice.contast.UrlConstant;
import com.yxw.cn.repairservice.entity.OrderItem;
import com.yxw.cn.repairservice.entity.ResponseData;
import com.yxw.cn.repairservice.okgo.JsonCallback;
import com.yxw.cn.repairservice.util.Base64Util;
import com.yxw.cn.repairservice.util.EventBusUtil;
import com.yxw.cn.repairservice.util.Helper;
import com.yxw.cn.repairservice.view.FullyGridLayoutManager;
import com.yxw.cn.repairservice.view.TitleBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 签到
 */
public class OrderSignInActivity extends BaseActivity {

    @BindView(R.id.titlebar)
    TitleBar titleBar;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.et_remark)
    EditText etRemark;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.mapView)
    MapView mMapView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    private OrderItem orderItem;
    private BDLocation mLocation;
    private int type;//0:签到 1:完成
    private BaiduMap mBaiDuMap;
    private LocationClient mLocationClient;
    private MyLocationListener mLocationListener;
    private GridImageAdapter mImageAdapter;
    private List<LocalMedia> mSelectImageList = new ArrayList<>();
    private List<String> mImageList = new ArrayList<>();
    @Override
    protected int getLayoutResId() {
        return R.layout.act_order_sign_in;
    }

    @Override
    public void initView() {
        orderItem = (OrderItem) getIntent().getSerializableExtra("order");
        type = getIntent().getIntExtra("type", 0);
        initTitle();
        initRecycleView();
        initMyLocation();
        initOrderLocation();
    }

    private void initRecycleView(){
        FullyGridLayoutManager gridLayoutManager = new FullyGridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(gridLayoutManager);
        mImageAdapter = new GridImageAdapter(this, new GridImageAdapter.onAddPicClickListener() {
            @Override
            public void onAddPicClick() {
                //拍照
                PictureSelector.create(OrderSignInActivity.this)
                        .openCamera(PictureMimeType.ofImage())
                        .compress(true)// 是否压缩 true or false
                        .minimumCompressSize(100)// 小于100kb的图片不压缩
                        .forResult(PictureConfig.CHOOSE_REQUEST);
            }
        });
        mImageAdapter.setList(mSelectImageList);
        mImageAdapter.setSelectMax(6);
        mRecyclerView.setAdapter(mImageAdapter);
        mImageAdapter.setOnItemClickListener(new GridImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (mSelectImageList.size() > 0 && mSelectImageList.size()>position) {
                    LocalMedia media = mSelectImageList.get(position);
                    String pictureType = media.getPictureType();
                    int mediaType = PictureMimeType.pictureToVideo(pictureType);
                    if (mediaType == 1){
                        PictureSelector.create(OrderSignInActivity.this).externalPicturePreview(position, mSelectImageList);
                    }
                }
            }
        });
    }

    private void initTitle(){
        if (type == 0) {
            titleBar.setTitle("上门签到");
            btnConfirm.setText("立即签到");
        } else {
            titleBar.setTitle("确认服务完成");
            btnConfirm.setText("完成服务");
        }
    }

    @OnClick({R.id.btn_confirm})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                confirmArrival();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> images = PictureSelector.obtainMultipleResult(data);
                    mSelectImageList.addAll(images);
                    mImageAdapter.setList(mSelectImageList);
                    break;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.unRegisterLocationListener(mLocationListener);
        mLocationClient.stop();
        mBaiDuMap.setMyLocationEnabled(false);
        mBaiDuMap.clear();
        mMapView.onDestroy();
        mMapView = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        // 停止定位
        mBaiDuMap.setMyLocationEnabled(false);
        mLocationClient.stop();
    }

    private void initMyLocation() {
        mBaiDuMap = mMapView.getMap();
        mBaiDuMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        mLocationClient = new LocationClient(this);
        mLocationListener = new MyLocationListener();
        mLocationClient.registerLocationListener(mLocationListener);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 0;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation
        // .getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);
        option.setOpenGps(true); // 打开gps
        //可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);
        // 开启定位
        mBaiDuMap.setMyLocationEnabled(true);
        mLocationClient.start();
    }

    private void initOrderLocation(){
        LatLng point = new LatLng(orderItem.getLocationLat(), orderItem.getLocationLng());//坐标参数（纬度，经度）
        BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                .fromView(View.inflate(OrderSignInActivity.this, R.layout.view_location_icon, null));
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = new MarkerOptions()
                .position(point)
                .icon(mCurrentMarker);
        mBaiDuMap.addOverlay(option);
    }


    private void confirmArrival() {
        if (Helper.isEmpty(mSelectImageList)){
            toast("现场照片不能为空!");
            return;
        }
        mImageList.clear();
        for (LocalMedia localMedia:mSelectImageList){
            mImageList.add(Base64Util.getBase64ImageStr(localMedia.getCompressPath()));
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("acceptId", orderItem.getAcceptId());
        map.put("locationLat", mLocation.getLatitude());
        map.put("locationLng", mLocation.getLongitude());
        map.put("shot", mImageList);
        map.put("remark", etRemark.getText().toString());
        if (type==1){
            map.put("smsCode", "888888");
        }
        String requestUrl = type==1? UrlConstant.ORDER_FINISH:UrlConstant.ORDER_ARRIVAL;
        showLoading();
        OkGo.<ResponseData<Object>>post(requestUrl)
                .upJson(gson.toJson(map))
                .execute(new JsonCallback<ResponseData<Object>>() {
                    @Override
                    public void onSuccess(ResponseData<Object> response) {
                        dismissLoading();
                        if (response!=null){
                            if (response.isSuccess()){
                                finish();
                                EventBusUtil.post(MessageConstant.NOTIFY_UPDATE_ORDER);
                            }else{
                                toast(response.getMsg());
                            }
                        }
                    }

                    @Override
                    public void onError(Response<ResponseData<Object>> response) {
                        super.onError(response);
                        dismissLoading();
                    }
                });
    }

    class MyLocationListener implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location!=null && location.getLatitude() != 4.9E-324 && location.getLongitude() != 4.9E-324){
                mLocation = location;
                // 构造定位数据
                MyLocationData locData = new MyLocationData.Builder()
                        .accuracy(location.getRadius())
                        // 此处设置开发者获取到的方向信息，顺时针0-360
                        .direction(0).latitude(location.getLatitude())
                        .longitude(location.getLongitude()).build();
                // 设置定位数据
                mBaiDuMap.setMyLocationData(locData);
                // 设置自定义图标
                BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory
                        .fromView(View.inflate(OrderSignInActivity.this, R.layout.view_location_icon_my, null));
                MyLocationConfiguration config = new MyLocationConfiguration(
                        MyLocationConfiguration.LocationMode.NORMAL, true, mCurrentMarker);
                mBaiDuMap.setMyLocationConfiguration(config);
                /**
                 * 绘制圆形
                 */
                LatLng point = new LatLng(location.getLatitude(), location.getLongitude());//坐标参数（纬度，经度）
                OverlayOptions oCircle = new CircleOptions().fillColor(0x90F8EFDE)
                        .center(point).stroke(new Stroke(5, 0xAAF0D2C2))
                        .radius(2000);
                mBaiDuMap.addOverlay(oCircle);
                LatLng ll = new LatLng(location.getLatitude(),
                        location.getLongitude());
                MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(ll).zoom(12.0f);
                mBaiDuMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
                tvLocation.setText(location.getAddrStr());
                if (location.getLocType() == BDLocation.TypeServerError) {
                    Toast.makeText(OrderSignInActivity.this, "服务器错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                    Toast.makeText(OrderSignInActivity.this, "网络错误，请检查", Toast.LENGTH_SHORT).show();
                } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                    Toast.makeText(OrderSignInActivity.this, "请确认手机是否开启GPS", Toast.LENGTH_SHORT).show();
                }
                mLocationClient.stop();
            }
        }
    }


}
