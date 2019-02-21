package com.idealcn.map;

import android.graphics.Color;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;

public class MainActivity extends AppCompatActivity {

    private MapView mapView;
    private         AMap map;
    private         AMapLocationClient locationClient;
    private LocationSource.OnLocationChangedListener onLocationChangedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        if (null==map)
            map = mapView.getMap();

      /*  LatLng latLng = new LatLng(30.000000, 120.000000);
        map.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        map.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round))
                .position(latLng)
                .draggable(true)
        );*/

        //定位方式
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.interval(1000)//定位间隔
                .myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW)//定位类型
                .strokeColor(Color.argb(50,50,50,50))
                .radiusFillColor(android.R.color.transparent)
                .strokeWidth(20f)
                .showMyLocation(true);
        map.setMyLocationStyle(myLocationStyle);
        map.setOnMyLocationChangeListener(new AMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                System.out.println("onMyLocationChange: latitude: "+location.getLatitude()
                +",\n\t longitude: "+location.getLongitude()+",\n\t altitude: "+location.getAltitude()
                );
            }
        });

        map.setLocationSource(locationSource);


        locationClient = new AMapLocationClient(this);
//        locationClient.enableBackgroundLocation();
        locationClient.setLocationListener(aMapLocationListener);

        AMapLocationClientOption locationClientOption = new AMapLocationClientOption();
        locationClientOption.setNeedAddress(true)
         .setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        locationClient.setLocationOption(locationClientOption);
        if (!locationClient.isStarted())
        locationClient.startLocation();
    }

    AMapLocationListener aMapLocationListener =   new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation aMapLocation) {
            int errorCode = aMapLocation.getErrorCode();
            String errorInfo = aMapLocation.getErrorInfo();
            if (errorCode == 0)
            onLocationChangedListener.onLocationChanged(aMapLocation);
            else {
                System.out.println("onLocationChanged: errorCode: "+errorCode + ",errorInfo: "+errorInfo);
            }
        }
    };

    //定位源
    LocationSource locationSource = new LocationSource() {
        @Override
        public void activate(OnLocationChangedListener listener) {
            System.out.println("激活定位");
            onLocationChangedListener = listener;
        }

        @Override
        public void deactivate() {
            System.out.println("关闭定位");
        }
    };


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        locationClient.unRegisterLocationListener(aMapLocationListener);
        locationClient.stopLocation();
        locationClient.onDestroy();
    }
}
