package com.altsoft.map;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.altsoft.Adapter.KakaoAddressAdapter;
import com.altsoft.Framework.Global;
import com.altsoft.Framework.control.altAutoCmpleateTextView;
import com.altsoft.Framework.map.MapInfo;
import com.altsoft.Framework.module.BaseActivity;
import com.altsoft.loggalapp.R;
import com.altsoft.loggalapp.SignageControlActivity;
import com.altsoft.loggalapp.detail.LocalboxListActivity;
import com.altsoft.loggalapp.detail.LocalboxbannerListActivity;
import com.altsoft.model.DEVICE_LOCATION;
import com.altsoft.model.DEVICE_LOCATION_COND;
import com.altsoft.model.daummap.DAUM_ADDRESS;
import com.altsoft.model.device.T_DEVICE_STATION;
import com.altsoft.model.device.T_DEVICE_STATION_COND;
import com.altsoft.model.signage.MOBILE_SIGNAGE_LIST;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapCircle;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class kakaoMapActivity extends BaseActivity implements MapView.MapViewEventListener, MapView.POIItemEventListener {
    MapView mapView;
    ViewGroup mapViewContainer;
    MapPoint mapPoint;
    MapPOIItem marker;
    Boolean bFirst = true;
    private static final String LOG_TAG = "kakaoMapActivity";
    SearchAutoCompleate searchAutoCompleate;
    //private ArrayList<T_AD> bannerlist;
    private List<T_DEVICE_STATION> stationlist;
    private List<DEVICE_LOCATION> devicelist;
    private ArrayList<MOBILE_SIGNAGE_LIST> signagelist;
    private String mapType = "banner";
    private BottomSheetBehavior sheetBehavior;
    MOBILE_SIGNAGE_LIST signagedata;
    T_DEVICE_STATION localstationdata;
    DEVICE_LOCATION devicedata;

    TextView tvTitle;
    TextView tvTitle2 ;
    LinearLayout lvBottomInfo ;
    LinearLayout lvBottomInfoDetail;
    TextView tvSubtitle;
    TextView tvUser;
    TextView tvAddress;
    Button btnInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kakao_map);

        // java code
        mapView = new MapView(this);
        mapView.setMapViewEventListener(this);
        mapView.setPOIItemEventListener(this);
        mapViewContainer = (ViewGroup) findViewById(R.id.map_view);
        //mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter("https://altsoft.ze.am/Files/201811/20181121092023.jpg", "테스트"));
        mapPoint = MapPoint.mapPointWithGeoCoord(Global.getMapInfo().latitude, Global.getMapInfo().longitude);
        mapView.setMapCenterPoint(mapPoint, true);
        mapViewContainer.addView(mapView);


        Intent intent = getIntent();
        mapType = intent.getStringExtra("mapType");
        if(mapType.equals("banner") ) {
            //bannerlist = (ArrayList<T_AD>) intent.getSerializableExtra("list1");
            this.SetMarkerAddress(Global.getMapInfo().latitude, Global.getMapInfo().longitude);
            //sheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        }
        else if(mapType.equals("localbox")) {
            locaBoxListDraw();
        }
        else if(mapType.equals("localstation")) {
            locaStationListDraw();
        }
        else if(mapType.equals("signage")){
            signagelist = (ArrayList<MOBILE_SIGNAGE_LIST>) intent.getSerializableExtra("list3");
            signageListDraw();
        }
        mapView.fitMapViewAreaToShowAllPOIItems();

        LinearLayout layoutBottomSheet = findViewById(R.id.bottom_sheet);


        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);

        sheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        sheetBehavior.setHideable(false);
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        sheetBehavior.setHideable(false);
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        tvTitle =  findViewById(R.id.txtBottomTitle);
        tvTitle2 =  findViewById(R.id.txtBottomTitle2);
        lvBottomInfo =  findViewById(R.id.layBottomInfo);
        lvBottomInfoDetail =  findViewById(R.id.layBottomInfoDetail);
        tvSubtitle =  findViewById(R.id.txtBottomSubtitle);
        tvUser =  findViewById(R.id.txtBottomUser);
        tvAddress=  findViewById(R.id.txtBottomAddress);
        btnInfo =  findViewById(R.id.btnBottonSheet);

        searchAutoCompleate = new SearchAutoCompleate();
        this.setUpViews();
        android.support.v7.widget.Toolbar mToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void setUpReferences() {
        LinearLayout layoutBottomSheet = findViewById(R.id.bottom_sheet);

        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
    }

    private void locaBoxListDraw() {

        DEVICE_LOCATION_COND Cond = new DEVICE_LOCATION_COND();
        Cond.PAGE = 1;
        Cond.PAGE_COUNT = 100000;
        Call<List<DEVICE_LOCATION>> call = Global.getAPIService().GetDeviceLocation(Cond);
        call.enqueue(new Callback<List<DEVICE_LOCATION>>() {

            @Override
            public void onResponse(Call<List<DEVICE_LOCATION>> call, Response<List<DEVICE_LOCATION>> response) {
                devicelist = response.body();
                for(int i=0; i<devicelist.size(); i++)
                {
                    MapPoint point =   MapPoint.mapPointWithGeoCoord(devicelist.get(i).LATITUDE, devicelist.get(i).LONGITUDE);
                    marker = new MapPOIItem();

                    marker.setItemName(devicelist.get(i).DEVICE_NAME); /* */
                    marker.setTag(0);
                    marker.setMapPoint(point);

                    marker.setUserObject(devicelist.get(i));
                    marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 기본으로 제공하는 BluePin 마커 모양.
                    marker.setCustomImageResourceId(R.drawable.map_pin_blue); // 마커 이미지.
                    marker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
                    marker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
                    mapView.addPOIItem(marker);
                }

                mapView.setShowCurrentLocationMarker(true);
                //   mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);
                mapView.setZoomLevel(7, true);
            }

            @Override
            public void onFailure(Call<List<DEVICE_LOCATION>> call, Throwable t) {

            }
        });

    }
    private void locaStationListDraw() {

        T_DEVICE_STATION_COND Cond = new T_DEVICE_STATION_COND();
        Cond.PAGE = 1;
        Cond.PAGE_COUNT = 100000;
        Call<List<T_DEVICE_STATION>> call = Global.getAPIService().GetDeviceStationMapList(Cond);
        call.enqueue(new Callback<List<T_DEVICE_STATION>>() {

            @Override
            public void onResponse(Call<List<T_DEVICE_STATION>> call, Response<List<T_DEVICE_STATION>> response) {
                stationlist = response.body();
                for(int i=0; i<stationlist.size(); i++)
                {
                    MapPoint point =   MapPoint.mapPointWithGeoCoord(stationlist.get(i).LATITUDE, stationlist.get(i).LONGITUDE);
                    marker = new MapPOIItem();

                    marker.setItemName(stationlist.get(i).STATION_NAME); /* */
                    marker.setTag(0);
                    marker.setMapPoint(point);

                    marker.setUserObject(stationlist.get(i));
                    marker.setMarkerType(MapPOIItem.MarkerType.CustomImage); // 기본으로 제공하는 BluePin 마커 모양.
                    marker.setCustomImageResourceId(R.drawable.stationmarker); // 마커 이미지.
                    marker.setCustomImageAutoscale(false); // hdpi, xhdpi 등 안드로이드 플랫폼의 스케일을 사용할 경우 지도 라이브러리의 스케일 기능을 꺼줌.
                    marker.setCustomImageAnchor(0.5f, 1.0f); // 마커 이미지중 기준이 되는 위치(앵커포인트) 지정 - 마커 이미지 좌측 상단 기준 x(0.0f ~ 1.0f), y(0.0f ~ 1.0f) 값.
                    mapView.addPOIItem(marker);
                }

                mapView.setShowCurrentLocationMarker(true);
             //   mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(37.53737528, 127.00557633), true);
                mapView.setZoomLevel(7, true);
            }

            @Override
            public void onFailure(Call<List<T_DEVICE_STATION>> call, Throwable t) {

            }
        });

    }
    private void signageListDraw() {
        for(int i=0; i<signagelist.size(); i++)
        {
            MapPoint point = MapPoint.mapPointWithGeoCoord(signagelist.get(i).LATITUDE, signagelist.get(i).LONGITUDE);
            marker = new MapPOIItem();
            marker.setItemName(signagelist.get(i).SIGN_NAME + "(" + (signagelist.get(i).RADIUS) + "m)"); /* */
            marker.setTag(0);
            marker.setUserObject(signagelist.get(i));
            marker.setMapPoint(point);
            marker.setMarkerType(MapPOIItem.MarkerType.YellowPin); // 기본으로 제공하는 BluePin 마커 모양.
            marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

            mapView.addPOIItem(
                    marker);
            ///mapView.selectPOIItem(marker, true);
            mapView.setMapCenterPoint(point, false);
        }
        mapView.setZoomLevel(10, true);
    }
    protected void setUpViews() {
        searchAutoCompleate.setUpViews();
        Button confirm = (Button)findViewById(R.id.btnConfirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetMarker();
            }
        });
        Global.getCommon().ProgressHide(this);
        ImageButton mylocationsearch = (ImageButton)findViewById(R.id.btnMylocation);
        mylocationsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetMyLocation();
            }
        });

    }

    private void SetMyLocation() {

        //Global.setMapInfo(new MapInfo( Global.getGpsInfo().getLatitude(), Global.getGpsInfo().getLongitude()));
        searchAutoCompleate.moveMarker(Global.getGpsInfo().getLatitude(), Global.getGpsInfo().getLongitude());
    }

    private void SetMarker() {
        Global.setMapInfo(new MapInfo(mapPoint.getMapPointGeoCoord().latitude,mapPoint.getMapPointGeoCoord().longitude));
        setResult(RESULT_OK);
        finish();
    }


    private void SetMarkerAddress(double latitude, double longitude) {
        Call<JsonObject> call = Global.getKakaoMapAPIService().GetLatiLongiToAddress(latitude, longitude);

        call.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                tvTitle.setText("기준위치");
                tvTitle2.setText("");
                tvSubtitle.setText("");
                if(bFirst) {
                    marker = new MapPOIItem();

                    String address =  Global.getMapInfo().getKakaoAddressName(response);
                    marker.setItemName("기준위치 > " + address); /* */
                    tvSubtitle.setText(address);
                    lvBottomInfo.setVisibility(View.GONE);
                    marker.setTag(0);
                    marker.setUserObject(response);
                    marker.setMapPoint(mapPoint);
                    marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
                    marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.
                    mapView.addPOIItem(marker);
                    bFirst = false;;
                }else {
                    MapPOIItem[] poliItems = mapView.getPOIItems();
                    if (poliItems.length > 0) {
                        String address =  Global.getMapInfo().getKakaoAddressName(response);
                        poliItems[0].setItemName("기준위치 > " + address);
                        tvAddress.setText("");
                        tvSubtitle.setText(address);
                        lvBottomInfo.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {
            }
        });
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {
        try {
            MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
            Log.i(LOG_TAG, String.format("MapView onMapViewCenterPointMoved (%f,%f)", mapPointGeo.latitude, mapPointGeo.longitude));
        } catch (Exception ex) {
        }
    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {
        if(mapType.equals("banner")) {
            searchAutoCompleate.moveMarker(mapPoint.getMapPointGeoCoord().latitude, mapPoint.getMapPointGeoCoord().longitude);
        }
    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        searchAutoCompleate.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();;
        searchAutoCompleate = null;
    }


    @Override
    public void onPOIItemSelected(MapView mapView, MapPOIItem mapPOIItem) {

        DecimalFormat df = new DecimalFormat("#,##0.00");
        tvSubtitle.setVisibility(View.VISIBLE);
        tvSubtitle.setVisibility(View.VISIBLE);
        lvBottomInfo.setVisibility(View.VISIBLE);
        lvBottomInfoDetail.setVisibility(View.VISIBLE);
        if(mapType.equals("signage")) {
            signagedata = (MOBILE_SIGNAGE_LIST) mapPOIItem.getUserObject();
            mapView.removeAllCircles();
            MapPoint point = MapPoint.mapPointWithGeoCoord(signagedata.LATITUDE, signagedata.LONGITUDE);
            MapCircle circle1 = new MapCircle(
                    point, // center
                    signagedata.RADIUS, // radius
                    Color.argb(128, 152, 187, 177), // strokeColor
                    Color.argb(128, 176, 229, 214) // fillColor
            );
            circle1.setTag(1234);
            mapView.addCircle(circle1);

            tvTitle.setText(signagedata.SIGN_NAME);
            if(signagedata.PLACE_DISTINCE  <= 0) {
                tvTitle2.setText(df.format(signagedata.PLACE_DISTINCE / 1000.00) + "km");
            }
            else {
                tvTitle2.setText(df.format(signagedata.PLACE_DISTINCE / 1000.00) + "km");
            }
            String address = Global.getMapInfo().getAddress(Global.getCurrentActivity(),signagedata.LATITUDE, signagedata.LONGITUDE);
            tvSubtitle.setText("");
            tvSubtitle.setVisibility(View.GONE);
            tvUser.setText(signagedata.COMPANY_NAME);
            tvAddress.setText(address);

            lvBottomInfo.setVisibility(View.VISIBLE);
        }
        else if(mapType.equals("localbox")) {
            devicedata = (DEVICE_LOCATION) mapPOIItem.getUserObject();
            tvTitle.setText(devicedata.DEVICE_NAME);

            tvTitle2.setText(df.format(devicedata.DISTANCE / 1000.00) + "km");
            tvSubtitle.setText(devicedata.DEVICE_DESC);
            tvUser.setText(devicedata.COMPANY_NAME);
            tvAddress.setText(devicedata.ADDRESS1 + " " + devicedata.ADDRESS2);
            lvBottomInfo.setVisibility(View.VISIBLE);
            lvBottomInfoDetail.setVisibility(View.VISIBLE);

        }else if(mapType.equals("localstation")) {
            localstationdata = (T_DEVICE_STATION) mapPOIItem.getUserObject();
            tvTitle.setText(localstationdata.STATION_NAME);
            localstationdata.NEW_DEVICE_CNT = localstationdata.NEW_DEVICE_CNT ==null ? 0 : localstationdata.NEW_DEVICE_CNT;
            tvTitle2.setText("로컬박스(총/신규) : " + localstationdata.DEVICE_CNT.toString() + "/" + localstationdata.NEW_DEVICE_CNT.toString()   + "개");
            tvSubtitle.setText(localstationdata.STATION_DESC);
            tvUser.setText("");
            tvAddress.setText("");
            lvBottomInfo.setVisibility(View.VISIBLE);
            lvBottomInfoDetail.setVisibility(View.GONE);
        }
        else
        {
            tvTitle.setText("기준위치");
            tvTitle2.setText("");
            String address = Global.getMapInfo().getAddress(Global.getCurrentActivity(),mapPOIItem.getMapPoint().getMapPointGeoCoord().latitude,mapPOIItem.getMapPoint().getMapPointGeoCoord().longitude);
            tvSubtitle.setText(address);
            tvAddress.setText("");

            lvBottomInfo.setVisibility(View.GONE);
        }

        btnInfo.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                BtnInfoShow();
            }
        });
        /// Bottom Sheet Expand
        sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
    }

    private void BtnInfoShow() {
        if(mapType.equals("signage")) {
            //Toast.makeText(getActivity(),adItem.TITLE  + "가 선택되었습니다.", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Global.getCurrentActivity(), SignageControlActivity.class);
            intent.putExtra("SIGN_CODE", signagedata.SIGN_CODE);
            Global.getCurrentActivity().startActivity(intent);
        }else if(mapType.equals("localbox")) {
            Intent intent = new Intent(Global.getCurrentActivity(), LocalboxbannerListActivity.class);
            intent.putExtra("DEVICE_CODE", devicedata.DEVICE_CODE);
            intent.putExtra("DEVICE_NAME", devicedata.DEVICE_NAME);
            Global.getCurrentActivity().startActivity(intent);
        }else if(mapType.equals("localstation")) {
            Intent intent = new Intent(Global.getCurrentActivity(), LocalboxListActivity.class);
            intent.putExtra("STATION_CODE", localstationdata.STATION_CODE);
            intent.putExtra("STATION_NAME", localstationdata.STATION_NAME);
            Global.getCurrentActivity().startActivity(intent);
        }
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem) {
        //Toast.makeText(this, "Clicked " + mapPOIItem.getItemName() + " Callout Balloon", Toast.LENGTH_SHORT).show();
        BtnInfoShow();
    }

    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem mapPOIItem, MapPOIItem.CalloutBalloonButtonType calloutBalloonButtonType) {
        Toast.makeText(this, "Clicked " + mapPOIItem.getItemName() + " Callout Balloon", Toast.LENGTH_SHORT).show();
        if(mapType.equals("signage")) {
            MOBILE_SIGNAGE_LIST data = (MOBILE_SIGNAGE_LIST) mapPOIItem.getUserObject();

        }else if(mapType.equals("localbox")) {
            T_DEVICE_STATION data = (T_DEVICE_STATION) mapPOIItem.getUserObject();
        }
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPoint) {

    }


    /// 자동완성
    private  class SearchAutoCompleate{
        KakaoAddressAdapter adapter;
        List<DAUM_ADDRESS> list;          // 데이터를 넣은 리스트변수
        altAutoCmpleateTextView autoCompleteTextView ;
        Boolean bAutoDrop = false;
        String beforeData = "";


        private SearchAutoCompleate(){}

        private void onResume() {
            Global.getCommon().hideSoftInputWindow( Global.getCurrentActivity(), autoCompleteTextView, true);
        }


        private void setUpViews() {

            list = new ArrayList<DAUM_ADDRESS>();
            autoCompleteTextView = (altAutoCmpleateTextView) Global.getCurrentActivity().findViewById(R.id.autoCompleteTextView);
            autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    autoCompleteTextView.dismissDropDown();
                    adapter.setSelectedItem(adapter.getObject(position));
                    moveMarker(adapter.getObject(position).latitude,adapter.getObject(position).longitude);
                }
            });
            autoCompleteTextView.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    // SearchAutoCompleate.beforeData = s.toString();
                    bAutoDrop = true;
                }

                @Override
                public void afterTextChanged(Editable s) {
                    String data = s.toString();
                     if(!beforeData.equals(data)) {
                        if (data.length() > 0) {
                            settingList(data);
                        }
                        if(adapter != null) {
                            if(adapter.getSelectedItem().address_name !=null && !(adapter.getSelectedItem().address_name.equals(s.toString())))
                            {
                                adapter.setSelectedItem(new DAUM_ADDRESS());
                            }
                        }
                    }
                    beforeData = data;
                }
            });
        }

        private void moveMarker(Double latitude, Double longitude)
        {
            mapPoint = MapPoint.mapPointWithGeoCoord(latitude, longitude);
            mapView.moveCamera(CameraUpdateFactory.newMapPoint(mapPoint));
            MapPOIItem[] poliItems = mapView.getPOIItems();
            if (poliItems.length > 0) {
                poliItems[0].setMapPoint(mapPoint);
            }
            MapPoint.GeoCoordinate mapPointGeo = mapPoint.getMapPointGeoCoord();
            SetMarkerAddress( latitude, longitude);
            bAutoDrop = false;
        }

        /// 자동완성 값 셋팅
        private void settingList(String query){
            list = new ArrayList<DAUM_ADDRESS>();


            try {
                Call<JsonObject> call = Global.getKakaoMapAPIService().GetAddressSearch(query);

                call.enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        list = new ArrayList<DAUM_ADDRESS>();
                        if (response.body() != null) {
                            JsonObject rtn = response.body();
                            for (JsonElement data : rtn.getAsJsonArray("documents")) {
                                DAUM_ADDRESS obj = new DAUM_ADDRESS();

                                obj.address_name = data.getAsJsonObject().get("address_name").getAsString();

                                if (data.getAsJsonObject().get("road_address").isJsonNull()) {
                                    obj.road_address_name = obj.address_name;
                                } else
                                    obj.road_address_name = data.getAsJsonObject().get("road_address").getAsJsonObject().get("address_name").getAsString();

                                obj.latitude = data.getAsJsonObject().get("y").getAsDouble();
                                obj.longitude = data.getAsJsonObject().get("x").getAsDouble();
                                obj.zip_code = data.getAsJsonObject().get("address").getAsJsonObject().get("zip_code").getAsString();
                                list.add(obj);
                                //list.add(data.NAME);
                            }
                        }
                        adapter = new KakaoAddressAdapter(Global.getCurrentActivity(), R.layout.autocomplate_list_item, list);
                        autoCompleteTextView.setAdapter(adapter);
                        if (bAutoDrop && list.size() >0) {
                            autoCompleteTextView.showDropDown();
                        }

                    }
                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {

                    }
                });

            }catch(Exception ex) {
                Log.d("로그", ex.getMessage());
            }
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
