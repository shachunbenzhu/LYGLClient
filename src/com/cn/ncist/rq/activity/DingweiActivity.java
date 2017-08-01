package com.cn.ncist.rq.activity ;

import java.util.List;

import android.R.anim;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.cn.ncist.rq.lyglclient.R;

//http://blog.csdn.net/ye_yun_lin/article/details/23563965

public class DingweiActivity extends Activity {
	
	private Button bt_dingwei_back ;
	//BMapManager 对象管理地图、定位、搜索功能
	private BMapManager mBMapManager;  
	private MapView mapView = null;                     //地图主控件 
	private MapController mMapController = null;  //地图控制 
	MKMapViewListener mMapListener = null;         //处理地图事件回调 
	private MKSearch mMKSearch;                         //定义搜索服务类
	//搜索
	private EditText keyWordEditText;  
	private EditText cityEditText;
	private Button queryButton;
	private static StringBuilder sb;  
	private MyLocationOverlay myLocationOverlay;
	//定位
	private Button button1; 
	private LocationManager locationManager;
	private String provider;
	//方法二 定位位置
    private BDLocation myLocation;  
    private LocationData mLocData;        //用户位置信息 
    private LocationClient mLocClient;     //定位SDK的核心类 
    private MyLocationOverlay locationOverlay = null;  //我的图层
    private PopupOverlay pop;                //弹出pop 我的位置
    private int flag=0;                             //标记变量 定位我的位置=1 POI为2
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /**
         * 创建对象BMapManager并初始化操作
         * V2.3.1中init(APIKey,null) V2.4.1在AndroidManifest中赋值AK
         * 注意 初始化操作在setContentView()前
         */
        mBMapManager = new BMapManager(getApplication());  
        mBMapManager.init(null); 
        setContentView(R.layout.activity_dingwei);  
        //获取对象
        mapView = (MapView) findViewById(R.id.map_view);  
        cityEditText = (EditText) findViewById(R.id.city_edittext);
        keyWordEditText = (EditText) findViewById(R.id.keyword_edittext);
        queryButton = (Button) findViewById(R.id.query_button);
        button1 = (Button) findViewById(R.id.button1);
        bt_dingwei_back = (Button) findViewById(R.id.bt_dingwei_back) ;
        //地图初始化
        mMapController = mapView.getController();    //获取地图控制器
        mMapController.enableClick(true);                  //设置地图是否响应点击事件
        mMapController.setZoom(16);                         //设置地图缩放级别
        mapView.setBuiltInZoomControls(true);          //显示内置缩放控件
        
        /**
         * 初始化MKSearch 调用城市和POI搜索  
         */
        mMKSearch = new MKSearch();
        mMKSearch.init(mBMapManager, new MySearchListener()); 
        queryButton.setOnClickListener(new OnClickListener() {  
            @Override  
            public void onClick(View v) {  
            	if(flag==1) {
					pop.hidePop();
					flag = 2;
				}
                mMapController = mapView.getController();
            	mMapController.setZoom(10);  
                sb = new StringBuilder();  //内容清空  
                //输入正确城市关键字
                String city = cityEditText.getText().toString().trim();  
                String keyWord = keyWordEditText.getText().toString().trim();  
                if(city.isEmpty()) { //默认城市设置为贵阳
                	city="贵阳";
                }
                //如果关键字为空只搜索城市 GEO搜索 
                if(keyWord.isEmpty()) {
                	mMKSearch.geocode(city, city);     //具体地址和城市 geocode(adress, city)
                } 
                else {
                	//搜索城市+关键字 
                    mMKSearch.setPoiPageCapacity(10);  //每页返回POI数
                    mMKSearch.poiSearchInCity(city, keyWord); 
                }
            }  
        });  
       
        bt_dingwei_back.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish() ;
				overridePendingTransition(R.anim.anim_entry, R.anim.anim_enter2) ;
			}
		}) ;
		/**
		 * 定位自己位置
		 */
        /* 方法一
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 //获取所有位置提供器
		        locationManager =  (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		        List<String> providerList = locationManager.getProviders(true);
		        if(providerList.contains(LocationManager.NETWORK_PROVIDER)) { //网络提供器
		        	provider = LocationManager.NETWORK_PROVIDER;
		        } else if(provider.contains(LocationManager.GPS_PROVIDER)) { //GPS提供器
		        	provider = LocationManager.GPS_PROVIDER;
		        } else {
		        	Toast.makeText(MainActivity.this, "No location provider to use",
		        			Toast.LENGTH_SHORT).show();
		        	return;
		        }
		        //获取到记录当前位置
		        Location location = locationManager.getLastKnownLocation(provider);
		        if(location!=null) {
		        	//定位我的位置
		        	MapController controller = mapView.getController();
		        	controller.setZoom(16);
		        	//latitude 纬度 longitude 经度
		        	GeoPoint point =  new GeoPoint((int) (location.getLatitude()*1E6),
		        			(int) (location.getLongitude()*1E6));
		        	controller.setCenter(point); //设置地图中心
		        	mapView.getOverlays().clear(); //清除地图上所有覆盖物
		        	MyLocationOverlay locationOverlay = new MyLocationOverlay(mapView);
		        	LocationData locationData = new LocationData();
		        	locationData.latitude = location.getLatitude(); //纬度
		        	locationData.longitude = location.getLongitude(); //经度
		        	locationOverlay.setData(locationData);
		        	//添加覆盖物
		        	mapView.getOverlays().add(locationOverlay);
		        	mapView.refresh(); //刷新
		        }
			}
		});
        */
        
        
        //方法二
        button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				flag = 1;
		        locationManager =  (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		        //设置缩放级别 级别越高地图显示精细
	            MapController controller = mapView.getController();
				controller.setZoom(16);
				//实例化定位服务，LocationClient类必须在主线程中声明  
		        mLocClient = new LocationClient(getApplicationContext());  
		        mLocClient.registerLocationListener(new BDLocationListenerImpl()); //注册定位监听接口
		        /** 
		         * LocationClientOption 该类用来设置定位SDK的定位方式。 
		         */  
		        LocationClientOption option = new LocationClientOption();  
		        option.setOpenGps(true); //打开GPRS  
		        option.setAddrType("all"); //返回的定位结果包含地址信息  
		        option.setCoorType("bd09ll"); //返回的定位结果是百度经纬度,默认值gcj02  
		        option.setPriority(LocationClientOption.GpsFirst); // 设置GPS优先  
		        option.setScanSpan(5000); //设置发起定位请求的间隔时间为5000ms  
		        option.disableCache(false); //禁止启用缓存定位
		        mLocClient.setLocOption(option);  //设置定位参数
		        mLocClient.start();  // 调用此方法开始定位  
		      	//定位图层初始化  
		        mapView.getOverlays().clear();  
		        locationOverlay= new MyLocationOverlay(mapView);  
		        //实例化定位数据，并设置在我的位置图层  
		        mLocData = new LocationData();  
		        locationOverlay.setData(mLocData); 
		        //添加定位图层  
		        mapView.getOverlays().add(locationOverlay);  
		        //修改定位数据后刷新图层生效  
		        mapView.refresh(); 
			}
        });
    }
    
    /** 
     * 定位接口，需要实现两个方法  
     * 参考 http://blog.csdn.net/xiaanming/article/details/11380619
     */  
    public class BDLocationListenerImpl implements BDLocationListener {  
  
        /** 
         * 接收异步返回的定位结果，参数是BDLocation类型参数 
         */  
        @Override  
        public void onReceiveLocation(BDLocation location) {  
            if (location == null || flag != 1) {  
                return;  
            }  
            MapController controller = mapView.getController();
			//设置经纬度
            DingweiActivity.this.myLocation = location;    
            mLocData.latitude = location.getLatitude();  
            mLocData.longitude = location.getLongitude();  
            GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6),
					(int) (location.getLongitude() * 1E6));
            controller.setCenter(point);
            //如果不显示定位精度圈，将accuracy赋值为0即可  
            //mLocData.accuracy = location.getRadius();  
            mLocData.direction = location.getDerect();  
            mLocData.accuracy = 0;
            //将定位数据设置到定位图层里  
            locationOverlay.setData(mLocData);  
            //更新图层数据执行刷新后生效  
            mapView.refresh();  
            //覆盖物
            if(flag==1) {
	            //添加图形
				pop = new PopupOverlay(mapView, new PopupClickListener() { 
					@Override
					public void onClickedPopup(int index) {
					}
				});
				Bitmap[] bitmaps = new Bitmap[3];
				try {
					bitmaps[0] = BitmapFactory.decodeResource(getResources(),
							R.drawable.left);
					bitmaps[1] = BitmapFactory.decodeResource(getResources(),
							R.drawable.middle);
					bitmaps[2] = BitmapFactory.decodeResource(getResources(),
							R.drawable.right);
				} catch (Exception e) {
					e.printStackTrace();
				}
				pop.showPopup(bitmaps, point, 18);
            }      
        }  
        
        /** 
         * 接收异步返回的POI查询结果，参数是BDLocation类型参数 
         */  
        @Override  
        public void onReceivePoi(BDLocation poiLocation) {  

        }  
    }  
    
    @Override
	protected void onResume() {
		mapView.onResume();
		if (mBMapManager != null) {
			mBMapManager.start();
		}
		super.onResume();
	}
    
    @Override
	protected void onDestroy() {
		mapView.destroy();
		if (mBMapManager != null) {
			mBMapManager.destroy();
			mBMapManager = null;
		}
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		mapView.onPause();
		if (mBMapManager != null) {
			mBMapManager.stop();
		}
		super.onPause();
	}	
	
	/** 
     * 内部类实现MKSearchListener接口,用于实现异步搜索服务 
     */  
    public class MySearchListener implements MKSearchListener {  
        
    	/** 
         * 根据经纬度搜索地址信息结果 
         * 同时mMKSearch.geocode(city, city)搜索城市返回至该函数
         * 
         * @param result 搜索结果 
         * @param iError 错误号（0表示正确返回） 
         */  
        @Override  
        public void onGetAddrResult(MKAddrInfo result, int iError) {  
        	if (result == null) {  
                return;  
            }  
            StringBuffer sbcity = new StringBuffer();  
            sbcity.append(result.strAddr).append("\n");   //经纬度所对应的位置  
        	mapView.getOverlays().clear();                      //清除地图上已有的所有覆盖物  
            mMapController.setCenter(result.geoPt);      //置为地图中心
            //添加原点并刷新
            LocationData locationData = new LocationData();
            locationData.latitude = result.geoPt.getLatitudeE6();
            locationData.longitude = result.geoPt.getLongitudeE6();
            myLocationOverlay = new MyLocationOverlay(mapView);
            myLocationOverlay.setData(locationData);
			mapView.getOverlays().add(myLocationOverlay);
			mapView.refresh();
            // 通过AlertDialog显示地址信息
            new AlertDialog.Builder(DingweiActivity.this)  
            .setTitle("显示当前城市地图")  
            .setMessage(sbcity.toString())  
            .setPositiveButton("关闭", new DialogInterface.OnClickListener() {  
                public void onClick(DialogInterface dialog, int whichButton) {  
                    dialog.dismiss();  
                }  
            }).create().show();
        }  
  
        /** 
         * POI搜索结果（范围检索、城市POI检索、周边检索） 
         *  
         * @param result 搜索结果 
         * @param type 返回结果类型（11,12,21:poi列表 7:城市列表） 
         * @param iError 错误号（0表示正确返回） 
         */  
        @Override  
        public void onGetPoiResult(MKPoiResult result, int type, int iError) {  
        	if (result == null) {  
                return;  
            }    
        	//获取POI并显示
        	mapView.getOverlays().clear(); 
            PoiOverlay poioverlay = new PoiOverlay(DingweiActivity.this, mapView);  //显示POI覆盖物
            poioverlay.setData(result.getAllPoi());         //设置搜索到的POI数据  
            mapView.getOverlays().add(poioverlay);    //兴趣点标注在地图上
            mapView.refresh(); 
            //设置其中一个搜索结果所在地理坐标为地图的中心  
            if(result.getNumPois() > 0) {  
                MKPoiInfo poiInfo = result.getPoi(0);  
                mMapController.setCenter(poiInfo.pt);  
            }  
            //添加StringBuffer 遍历当前页返回的POI (默认只返回10个)
            sb.append("共搜索到").append(result.getNumPois()).append("个POI\n");  
            for (MKPoiInfo poiInfo : result.getAllPoi()) {  
                sb.append("名称：").append(poiInfo.name).append("\n");
            }
         	// 通过AlertDialog显示当前页搜索到的POI  
            new AlertDialog.Builder(DingweiActivity.this)  
            .setTitle("搜索到的POI信息")  
            .setMessage(sb.toString())  
            .setPositiveButton("关闭", new DialogInterface.OnClickListener() {  
                public void onClick(DialogInterface dialog, int whichButton) {  
                    dialog.dismiss();  
                }  
            }).create().show();
        }  
  
        /** 
         * 驾车路线搜索结果 
         *  
         * @param result 搜索结果 
         * @param iError 错误号（0表示正确返回） 
         */  
        @Override  
        public void onGetDrivingRouteResult(MKDrivingRouteResult result, int iError) {  
        }  
        
        /** 
         * 公交换乘路线搜索结果 
         *  
         * @param result 搜索结果 
         * @param iError 错误号（0表示正确返回） 
         */  
        @Override  
        public void onGetTransitRouteResult(MKTransitRouteResult result, int iError) {  
        }  
  
        /** 
         * 步行路线搜索结果 
         *  
         * @param result 搜索结果 
         * @param iError 错误号（0表示正确返回） 
         */  
        @Override  
        public void onGetWalkingRouteResult(MKWalkingRouteResult result, int iError) {  
        }

		@Override
		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1, int arg2) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
			// TODO Auto-generated method stub	
		}
    }  
    
}
