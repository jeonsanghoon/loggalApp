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

import com.altsoft.Adapter.LocalBoxListViewAdapter;
import com.altsoft.Adapter.LocalStationListAdapter;
import com.altsoft.Framework.Global;
import com.altsoft.Framework.module.BaseFragment;
import com.altsoft.Interface.ServiceInfo;
import com.altsoft.loggalapp.R;
import com.altsoft.loggalapp.detail.LocalboxListActivity;
import com.altsoft.loggalapp.detail.LocalboxbannerListActivity;

import com.altsoft.model.device.T_DEVICE_STATION;
import com.altsoft.model.device.T_DEVICE_STATION_COND;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TabFragment_localStation extends BaseFragment {
    LocalStationListAdapter adapter;
    boolean lastitemVisibleFlag = false;
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수
    ListView listview;
    boolean bLastPage = false;
    Integer nPageSize = 30;
    Integer nPage = 1;
    public List<T_DEVICE_STATION> list;
    View selectedview;
    T_DEVICE_STATION selectedData;

    public TabFragment_localStation() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        adapter = new LocalStationListAdapter();
        GetLocalStation();

        return inflater.inflate(R.layout.fragment_tab_localstation, container, false);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (selectedview != null) {
            if (Global.getData().LOCALBOX_BOOKMARK_YN != null) {
                selectedData.BOOKMARK_YN = Global.getData().LOCALBOX_BOOKMARK_YN;
                adapter.setItem(selectedview, selectedData);
            }
        }
        selectedview = null;
        selectedData = null;
        Global.getData().LOCALBOX_BOOKMARK_YN = null;
    }

    public void GetLocalStation() {
        this.GetLocalStation(null);
    }

    private void GetLocalStation(Integer page) {


        try {

            T_DEVICE_STATION_COND Cond = new T_DEVICE_STATION_COND();
            Cond.PAGE = page;
            Cond.PAGE_COUNT = 100000;
            Call<List<T_DEVICE_STATION>> call = Global.getAPIService().GetDeviceStationMapList(Cond);

            Global.getCallService().callService(call
                    , new ServiceInfo.Act<ArrayList<T_DEVICE_STATION>>() {
                        @Override
                        public void execute(ArrayList<T_DEVICE_STATION> list) {
                            if (list.size() == 0) {
                                bLastPage = true;
                                Toast.makeText(Global.getCurrentActivity(), "데이터가 모두 검색되었습니다.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            Global.getData().stationlist = list;
                            if (list.size() < nPageSize) bLastPage = true;

                            if (adapter.SetDataBind(list) == true) {
                                return;
                            }

                            listview = (ListView) getActivity().findViewById(R.id.listview3);
                            listview.setAdapter(adapter);
                            listview.setOnScrollListener(new ListView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(AbsListView view, int scrollState) {
                                    if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleFlag && mLockListView == false) {
                                        // 데이터 로드
                                        if (lastitemVisibleFlag == true) {
                                            // Integer page = (listview.getCount() / nPageSize) + 1;
                                            nPage = nPage + 1;
                                            GetLocalStation(nPage);
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
                                    T_DEVICE_STATION data = adapter.getItem(position);
                                    selectedview = view;
                                    selectedData = data;
                                    //Toast.makeText(Global.getCurrentActivity(),adItem.TITLE  + "가 선택되었습니다.", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(getContext(), LocalboxListActivity.class);
                                    intent.putExtra("STATION_CODE", data.STATION_CODE);
                                    intent.putExtra("STATION_NAME", data.STATION_NAME);
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
        } catch (Exception ex) {
            Log.d("로그", ex.getMessage());
            Global.getCommon().ProgressHide();
        }
    }
}
