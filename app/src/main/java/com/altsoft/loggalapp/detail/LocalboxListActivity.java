package com.altsoft.loggalapp.detail;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.altsoft.Adapter.LocalBoxListViewAdapter;
import com.altsoft.Framework.Global;
import com.altsoft.Framework.enResult;
import com.altsoft.Framework.module.BaseActivity;
import com.altsoft.loggalapp.R;
import com.altsoft.model.DEVICE_LOCATION;
import com.altsoft.model.DEVICE_LOCATION_COND;
import com.altsoft.model.device.AD_DEVICE_MOBILE_LIST;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Intent.FLAG_ACTIVITY_SINGLE_TOP;

public class LocalboxListActivity extends BaseActivity  {
    LocalBoxListViewAdapter adapter;
    boolean lastitemVisibleFlag = false;
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수
    ListView listview;
    boolean bLastPage = false;
    Integer nPageSize = 30;
    Integer nPage = 1;
    public  List<DEVICE_LOCATION> list;
    Activity activity;
    Integer stationCode;

    View selectedview;
    DEVICE_LOCATION selectedData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localbox_list);

        android.support.v7.widget.Toolbar mToolbar = (android.support.v7.widget.Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.ComponentInit();

        GetDeviceLocation();
    }
    @Override
    public void onResume() {
        super.onResume();
        if(selectedview != null)
        {
            if(Global.getData().LOCALBOX_BOOKMARK_YN !=null) {
                selectedData.BOOKMARK_YN = Global.getData().LOCALBOX_BOOKMARK_YN;
                adapter.setItem(selectedview, selectedData);
            }
        }
        selectedview = null;
        selectedData = null;
        Global.getData().LOCALBOX_BOOKMARK_YN = null;
    }
    /// 컴포넌트 초기화
    public void ComponentInit()
    {
        activity = this;

        adapter = new LocalBoxListViewAdapter();
        Intent intent = getIntent();
        stationCode = intent.getIntExtra("STATION_CODE",0);
        super.appBarInit_titleOnly(intent.getStringExtra("STATION_NAME"));
    }
    public void GetDeviceLocation()
    {
        this.GetDeviceLocation(null);
    }
    private void GetDeviceLocation(Integer page) {

        DEVICE_LOCATION_COND Cond = new DEVICE_LOCATION_COND();
        try {
            Cond.LATITUDE = Global.getMapInfo().latitude;
            Cond.LONGITUDE = Global.getMapInfo().longitude;
            Cond.PAGE_COUNT = nPageSize;
            Cond.STATION_CODE = stationCode;
            Cond.USER_ID = Global.getLoginInfo().USER_ID;

            Cond.PAGE  = page == null ? 1 : page;
            if(Cond.PAGE != 1 && bLastPage) {
                Toast.makeText(Global.getCurrentActivity(),"데이터가 모두 검색되었습니다.", Toast.LENGTH_LONG).show();
                return;
            }
            if(Cond.PAGE == 1 && listview != null) {
                adapter.clearData();
                listview.setAdapter(adapter);
                adapter = new LocalBoxListViewAdapter();
            }
            String sAddr = Global.getMapInfo().currentLocationAddress;
            Global.getCommon().ProgressShow(Global.getCurrentActivity());
            Call<List<DEVICE_LOCATION>> call = Global.getAPIService().GetDeviceLocation(Cond);
            call.enqueue(new Callback<List<DEVICE_LOCATION>>() {
                @Override
                public void onResponse(Call<List<DEVICE_LOCATION>> call, Response<List<DEVICE_LOCATION>> response) {
                    Global.getCommon().ProgressHide(Global.getCurrentActivity());
                    list = response.body();

                    if(list.size() == 0) {
                        bLastPage = true;
                        Toast.makeText(Global.getCurrentActivity(),"데이터가 모두 검색되었습니다.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    Global.getData().devicelist = list;
                    if(list.size() < nPageSize) bLastPage = true;

                    if(adapter.SetDataBind(list) == true) return;

                    listview = (ListView) findViewById(R.id.listview_localboxDetail);
                    listview.setAdapter(adapter);
                    listview.setOnScrollListener(new ListView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(AbsListView view, int scrollState) {
                            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleFlag && mLockListView == false) {
                                // 데이터 로드
                                if(lastitemVisibleFlag == true) {
                                    // Integer page = (listview.getCount() / nPageSize) + 1;
                                    nPage = nPage + 1;
                                    GetDeviceLocation(nPage);
                                }
                                mLockListView = false;
                                lastitemVisibleFlag = false;
                            }
                        }

                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                            lastitemVisibleFlag = (totalItemCount > 0) && (firstVisibleItem + visibleItemCount >= totalItemCount);
                        }
                    });

                    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            selectedData = adapter.getItem(position);
                            selectedview = view;
                            //Toast.makeText(Global.getCurrentActivity(),adItem.TITLE  + "가 선택되었습니다.", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(activity, LocalboxbannerListActivity.class);
                            intent.putExtra("DEVICE_CODE", selectedData.DEVICE_CODE );
                            intent.putExtra("DEVICE_NAME", selectedData.DEVICE_NAME );
                            activity.startActivity(intent);
                        }
                    });
                }

                @Override
                public void onFailure(Call<List<DEVICE_LOCATION>> call, Throwable t) {
                    Global.getCommon().ProgressHide(Global.getCurrentActivity());
                }
            });

        }catch(Exception ex) {
            Log.d("로그", ex.getMessage());
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
