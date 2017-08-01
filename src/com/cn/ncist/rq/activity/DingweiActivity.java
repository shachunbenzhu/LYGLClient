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
	//BMapManager ��������ͼ����λ����������
	private BMapManager mBMapManager;  
	private MapView mapView = null;                     //��ͼ���ؼ� 
	private MapController mMapController = null;  //��ͼ���� 
	MKMapViewListener mMapListener = null;         //�����ͼ�¼��ص� 
	private MKSearch mMKSearch;                         //��������������
	//����
	private EditText keyWordEditText;  
	private EditText cityEditText;
	private Button queryButton;
	private static StringBuilder sb;  
	private MyLocationOverlay myLocationOverlay;
	//��λ
	private Button button1; 
	private LocationManager locationManager;
	private String provider;
	//������ ��λλ��
    private BDLocation myLocation;  
    private LocationData mLocData;        //�û�λ����Ϣ 
    private LocationClient mLocClient;     //��λSDK�ĺ����� 
    private MyLocationOverlay locationOverlay = null;  //�ҵ�ͼ��
    private PopupOverlay pop;                //����pop �ҵ�λ��
    private int flag=0;                             //��Ǳ��� ��λ�ҵ�λ��=1 POIΪ2
	
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /**
         * ��������BMapManager����ʼ������
         * V2.3.1��init(APIKey,null) V2.4.1��AndroidManifest�и�ֵAK
         * ע�� ��ʼ��������setContentView()ǰ
         */
        mBMapManager = new BMapManager(getApplication());  
        mBMapManager.init(null); 
        setContentView(R.layout.activity_dingwei);  
        //��ȡ����
        mapView = (MapView) findViewById(R.id.map_view);  
        cityEditText = (EditText) findViewById(R.id.city_edittext);
        keyWordEditText = (EditText) findViewById(R.id.keyword_edittext);
        queryButton = (Button) findViewById(R.id.query_button);
        button1 = (Button) findViewById(R.id.button1);
        bt_dingwei_back = (Button) findViewById(R.id.bt_dingwei_back) ;
        //��ͼ��ʼ��
        mMapController = mapView.getController();    //��ȡ��ͼ������
        mMapController.enableClick(true);                  //���õ�ͼ�Ƿ���Ӧ����¼�
        mMapController.setZoom(16);                         //���õ�ͼ���ż���
        mapView.setBuiltInZoomControls(true);          //��ʾ�������ſؼ�
        
        /**
         * ��ʼ��MKSearch ���ó��к�POI����  
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
                sb = new StringBuilder();  //�������  
                //������ȷ���йؼ���
                String city = cityEditText.getText().toString().trim();  
                String keyWord = keyWordEditText.getText().toString().trim();  
                if(city.isEmpty()) { //Ĭ�ϳ�������Ϊ����
                	city="����";
                }
                //����ؼ���Ϊ��ֻ�������� GEO���� 
                if(keyWord.isEmpty()) {
                	mMKSearch.geocode(city, city);     //�����ַ�ͳ��� geocode(adress, city)
                } 
                else {
                	//��������+�ؼ��� 
                    mMKSearch.setPoiPageCapacity(10);  //ÿҳ����POI��
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
		 * ��λ�Լ�λ��
		 */
        /* ����һ
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				 //��ȡ����λ���ṩ��
		        locationManager =  (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		        List<String> providerList = locationManager.getProviders(true);
		        if(providerList.contains(LocationManager.NETWORK_PROVIDER)) { //�����ṩ��
		        	provider = LocationManager.NETWORK_PROVIDER;
		        } else if(provider.contains(LocationManager.GPS_PROVIDER)) { //GPS�ṩ��
		        	provider = LocationManager.GPS_PROVIDER;
		        } else {
		        	Toast.makeText(MainActivity.this, "No location provider to use",
		        			Toast.LENGTH_SHORT).show();
		        	return;
		        }
		        //��ȡ����¼��ǰλ��
		        Location location = locationManager.getLastKnownLocation(provider);
		        if(location!=null) {
		        	//��λ�ҵ�λ��
		        	MapController controller = mapView.getController();
		        	controller.setZoom(16);
		        	//latitude γ�� longitude ����
		        	GeoPoint point =  new GeoPoint((int) (location.getLatitude()*1E6),
		        			(int) (location.getLongitude()*1E6));
		        	controller.setCenter(point); //���õ�ͼ����
		        	mapView.getOverlays().clear(); //�����ͼ�����и�����
		        	MyLocationOverlay locationOverlay = new MyLocationOverlay(mapView);
		        	LocationData locationData = new LocationData();
		        	locationData.latitude = location.getLatitude(); //γ��
		        	locationData.longitude = location.getLongitude(); //����
		        	locationOverlay.setData(locationData);
		        	//��Ӹ�����
		        	mapView.getOverlays().add(locationOverlay);
		        	mapView.refresh(); //ˢ��
		        }
			}
		});
        */
        
        
        //������
        button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				flag = 1;
		        locationManager =  (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		        //�������ż��� ����Խ�ߵ�ͼ��ʾ��ϸ
	            MapController controller = mapView.getController();
				controller.setZoom(16);
				//ʵ������λ����LocationClient����������߳�������  
		        mLocClient = new LocationClient(getApplicationContext());  
		        mLocClient.registerLocationListener(new BDLocationListenerImpl()); //ע�ᶨλ�����ӿ�
		        /** 
		         * LocationClientOption �����������ö�λSDK�Ķ�λ��ʽ�� 
		         */  
		        LocationClientOption option = new LocationClientOption();  
		        option.setOpenGps(true); //��GPRS  
		        option.setAddrType("all"); //���صĶ�λ���������ַ��Ϣ  
		        option.setCoorType("bd09ll"); //���صĶ�λ����ǰٶȾ�γ��,Ĭ��ֵgcj02  
		        option.setPriority(LocationClientOption.GpsFirst); // ����GPS����  
		        option.setScanSpan(5000); //���÷���λ����ļ��ʱ��Ϊ5000ms  
		        option.disableCache(false); //��ֹ���û��涨λ
		        mLocClient.setLocOption(option);  //���ö�λ����
		        mLocClient.start();  // ���ô˷�����ʼ��λ  
		      	//��λͼ���ʼ��  
		        mapView.getOverlays().clear();  
		        locationOverlay= new MyLocationOverlay(mapView);  
		        //ʵ������λ���ݣ����������ҵ�λ��ͼ��  
		        mLocData = new LocationData();  
		        locationOverlay.setData(mLocData); 
		        //��Ӷ�λͼ��  
		        mapView.getOverlays().add(locationOverlay);  
		        //�޸Ķ�λ���ݺ�ˢ��ͼ����Ч  
		        mapView.refresh(); 
			}
        });
    }
    
    /** 
     * ��λ�ӿڣ���Ҫʵ����������  
     * �ο� http://blog.csdn.net/xiaanming/article/details/11380619
     */  
    public class BDLocationListenerImpl implements BDLocationListener {  
  
        /** 
         * �����첽���صĶ�λ�����������BDLocation���Ͳ��� 
         */  
        @Override  
        public void onReceiveLocation(BDLocation location) {  
            if (location == null || flag != 1) {  
                return;  
            }  
            MapController controller = mapView.getController();
			//���þ�γ��
            DingweiActivity.this.myLocation = location;    
            mLocData.latitude = location.getLatitude();  
            mLocData.longitude = location.getLongitude();  
            GeoPoint point = new GeoPoint((int) (location.getLatitude() * 1E6),
					(int) (location.getLongitude() * 1E6));
            controller.setCenter(point);
            //�������ʾ��λ����Ȧ����accuracy��ֵΪ0����  
            //mLocData.accuracy = location.getRadius();  
            mLocData.direction = location.getDerect();  
            mLocData.accuracy = 0;
            //����λ�������õ���λͼ����  
            locationOverlay.setData(mLocData);  
            //����ͼ������ִ��ˢ�º���Ч  
            mapView.refresh();  
            //������
            if(flag==1) {
	            //���ͼ��
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
         * �����첽���ص�POI��ѯ�����������BDLocation���Ͳ��� 
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
     * �ڲ���ʵ��MKSearchListener�ӿ�,����ʵ���첽�������� 
     */  
    public class MySearchListener implements MKSearchListener {  
        
    	/** 
         * ���ݾ�γ��������ַ��Ϣ��� 
         * ͬʱmMKSearch.geocode(city, city)�������з������ú���
         * 
         * @param result ������� 
         * @param iError ����ţ�0��ʾ��ȷ���أ� 
         */  
        @Override  
        public void onGetAddrResult(MKAddrInfo result, int iError) {  
        	if (result == null) {  
                return;  
            }  
            StringBuffer sbcity = new StringBuffer();  
            sbcity.append(result.strAddr).append("\n");   //��γ������Ӧ��λ��  
        	mapView.getOverlays().clear();                      //�����ͼ�����е����и�����  
            mMapController.setCenter(result.geoPt);      //��Ϊ��ͼ����
            //���ԭ�㲢ˢ��
            LocationData locationData = new LocationData();
            locationData.latitude = result.geoPt.getLatitudeE6();
            locationData.longitude = result.geoPt.getLongitudeE6();
            myLocationOverlay = new MyLocationOverlay(mapView);
            myLocationOverlay.setData(locationData);
			mapView.getOverlays().add(myLocationOverlay);
			mapView.refresh();
            // ͨ��AlertDialog��ʾ��ַ��Ϣ
            new AlertDialog.Builder(DingweiActivity.this)  
            .setTitle("��ʾ��ǰ���е�ͼ")  
            .setMessage(sbcity.toString())  
            .setPositiveButton("�ر�", new DialogInterface.OnClickListener() {  
                public void onClick(DialogInterface dialog, int whichButton) {  
                    dialog.dismiss();  
                }  
            }).create().show();
        }  
  
        /** 
         * POI�����������Χ����������POI�������ܱ߼����� 
         *  
         * @param result ������� 
         * @param type ���ؽ�����ͣ�11,12,21:poi�б� 7:�����б� 
         * @param iError ����ţ�0��ʾ��ȷ���أ� 
         */  
        @Override  
        public void onGetPoiResult(MKPoiResult result, int type, int iError) {  
        	if (result == null) {  
                return;  
            }    
        	//��ȡPOI����ʾ
        	mapView.getOverlays().clear(); 
            PoiOverlay poioverlay = new PoiOverlay(DingweiActivity.this, mapView);  //��ʾPOI������
            poioverlay.setData(result.getAllPoi());         //������������POI����  
            mapView.getOverlays().add(poioverlay);    //��Ȥ���ע�ڵ�ͼ��
            mapView.refresh(); 
            //��������һ������������ڵ�������Ϊ��ͼ������  
            if(result.getNumPois() > 0) {  
                MKPoiInfo poiInfo = result.getPoi(0);  
                mMapController.setCenter(poiInfo.pt);  
            }  
            //���StringBuffer ������ǰҳ���ص�POI (Ĭ��ֻ����10��)
            sb.append("��������").append(result.getNumPois()).append("��POI\n");  
            for (MKPoiInfo poiInfo : result.getAllPoi()) {  
                sb.append("���ƣ�").append(poiInfo.name).append("\n");
            }
         	// ͨ��AlertDialog��ʾ��ǰҳ��������POI  
            new AlertDialog.Builder(DingweiActivity.this)  
            .setTitle("��������POI��Ϣ")  
            .setMessage(sb.toString())  
            .setPositiveButton("�ر�", new DialogInterface.OnClickListener() {  
                public void onClick(DialogInterface dialog, int whichButton) {  
                    dialog.dismiss();  
                }  
            }).create().show();
        }  
  
        /** 
         * �ݳ�·��������� 
         *  
         * @param result ������� 
         * @param iError ����ţ�0��ʾ��ȷ���أ� 
         */  
        @Override  
        public void onGetDrivingRouteResult(MKDrivingRouteResult result, int iError) {  
        }  
        
        /** 
         * ��������·��������� 
         *  
         * @param result ������� 
         * @param iError ����ţ�0��ʾ��ȷ���أ� 
         */  
        @Override  
        public void onGetTransitRouteResult(MKTransitRouteResult result, int iError) {  
        }  
  
        /** 
         * ����·��������� 
         *  
         * @param result ������� 
         * @param iError ����ţ�0��ʾ��ȷ���أ� 
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
