package com.altsoft.loggalapp.Fragement;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.altsoft.Framework.Global;
import com.altsoft.Framework.module.BaseFragment;
import com.altsoft.Adapter.LocalBoxListViewAdapter;
import com.altsoft.Interface.ServiceInfo;
import com.altsoft.loggalapp.R;
import com.altsoft.loggalapp.detail.LocalboxbannerListActivity;
import com.altsoft.model.DEVICE_LOCATION;
import com.altsoft.model.DEVICE_LOCATION_COND;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;


public class TabFragment_localbox extends BaseFragment {
    LocalBoxListViewAdapter adapter;
    boolean lastitemVisibleFlag = false;
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수
    ListView listview;
    boolean bLastPage = false;
    Integer nPageSize = 30;
    Integer nPage = 1;
    public  List<DEVICE_LOCATION> list;
    View selectedview;
    DEVICE_LOCATION selectedData;

    public TabFragment_localbox(){

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        adapter = new LocalBoxListViewAdapter();
        GetDeviceLocation();
        return inflater.inflate(R.layout.fragment_tab_localbox, container, false);
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

            if(Global.getData().LOCALBOX_FAVORITE_YN !=null) {
                selectedData.FAVORITE_YN = Global.getData().LOCALBOX_FAVORITE_YN;
                adapter.setItem(selectedview, selectedData);
            }

        }
        selectedview = null;

        Global.getData().LOCALBOX_BOOKMARK_YN = null;
        Global.getData().LOCALBOX_FAVORITE_YN = null;
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
            Cond.PAGE  = page == null ? 1 : page;
            Cond.USER_ID = Global.getLoginInfo().USER_ID;

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

            Call<List<DEVICE_LOCATION>> call = Global.getAPIService().GetDeviceLocation(Cond);
            Global.getCallService().callService(call
                    , new ServiceInfo.Act<ArrayList<DEVICE_LOCATION>>() {
                        @Override
                        public void execute(ArrayList<DEVICE_LOCATION> list) {
                            if(list.size() == 0) {
                                bLastPage = true;
                                Toast.makeText(Global.getCurrentActivity(),"데이터가 모두 검색되었습니다.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            Global.getData().devicelist = list;
                            if(list.size() < nPageSize) bLastPage = true;

                            if(adapter.SetDataBind(list) == true) {
                                return;
                            }

                            listview = (ListView) getActivity().findViewById(R.id.listview2);
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
                                    DEVICE_LOCATION data = adapter.getItem(position);
                                    selectedview = view;
                                    selectedData = data;
                                    //Toast.makeText(Global.getCurrentActivity(),adItem.TITLE  + "가 선택되었습니다.", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getContext(), LocalboxbannerListActivity.class);
                                    intent.putExtra("DEVICE_CODE", data.DEVICE_CODE );
                                    intent.putExtra("DEVICE_NAME", data.DEVICE_NAME );
                                    getContext().startActivity(intent);

                                }
                            });
 }
                    },
                    new ServiceInfo.Act<Throwable>() {
                        @Override
                        public void execute(Throwable data) {
                            //TODO: Do something!
                        }
                    }
            );
        }catch(Exception ex) {
            Log.d("로그", ex.getMessage());
            Global.getCommon().ProgressHide();
        }
    }
}
