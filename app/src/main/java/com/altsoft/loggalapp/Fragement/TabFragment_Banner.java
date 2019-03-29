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

import com.altsoft.Framework.module.BaseFragment;
import com.altsoft.Framework.Global;
import com.altsoft.Adapter.BannerListViewAdapter;
import com.altsoft.Interface.ServiceInfo;
import com.altsoft.loggalapp.R;
import com.altsoft.loggalapp.SignageControlActivity;
import com.altsoft.loggalapp.WebViewActivity;
import com.altsoft.model.AD_SEARCH_COND;
import com.altsoft.model.T_AD;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

/**
* 배너 탭 프레그먼트
* @author 전상훈
* @version 1.0.0
* @since 2019-03-27 오후 5:39
**/
public class TabFragment_Banner extends BaseFragment {
    BannerListViewAdapter adapter;
    boolean lastitemVisibleFlag = false;
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수
    ListView listview;
    boolean bLastPage = false;
    Integer nPageSize = 30;
    Integer nPage = 1;
    View view;
    int nCnt = 0;

    View selectedview;
    T_AD selectedData;

    public TabFragment_Banner() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_tab_banner, container, false);
        adapter = new BannerListViewAdapter();
        GetBannerList();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (selectedview != null) {
            if (Global.getData().BANNER_BOOKMARK_YN != null) {
                selectedData.BOOKMARK_YN = Global.getData().BANNER_BOOKMARK_YN;
                adapter.setItem(selectedview, selectedData);
            }

        }
        selectedview = null;
        selectedData = null;
        Global.getData().BANNER_BOOKMARK_YN = null;
    }


    public void GetBannerList() {
        this.GetBannerList(1);
    }

    /// 배너정보가져오기
    public void GetBannerList(Integer page) {

        AD_SEARCH_COND Cond = new AD_SEARCH_COND();

        try {
            Cond.USER_ID = Global.getLoginInfo().USER_ID;
            //Cond.LATITUDE = Global.getMapInfo().latitude;
            //Cond.LONGITUDE = Global.getMapInfo().longitude;
            Cond.SEARCH_LAT = Global.getSecurityInfo().EncryptAes(Global.getMapInfo().latitude.toString());
            Cond.SEARCH_LONG = Global.getSecurityInfo().EncryptAes(Global.getMapInfo().longitude.toString());
            Cond.PageCount = nPageSize;
            Cond.Page = page;
            if (Cond.Page != 1 && bLastPage) {
                Toast.makeText(getActivity(), "데이터가 모두 검색되었습니다.", Toast.LENGTH_LONG).show();
                return;
            }
            nPage = Cond.Page;
            Cond.nCnt = nCnt++;

            if (Cond.Page == 1 && listview != null) {
                adapter.clearData();
                listview.setAdapter(adapter);
               // listview.setAdapter(null);
            }
            String sAddr = Global.getMapInfo().currentLocationAddress;
            Call<List<T_AD>> call = Global.getAPIService().GetBannerList(Cond);
            Global.getCallService().callService(call
                    , new ServiceInfo.Act<ArrayList<T_AD>>() {
                        @Override
                        public void execute(ArrayList<T_AD> list) {
                            if (list.size() == 0) {
                                Toast.makeText(getActivity(), "데이터가 모두 검색되었습니다.", Toast.LENGTH_LONG).show();
                                return;
                            }
                            if (list.size() < nPageSize) bLastPage = true;

                            if (adapter == null) adapter = new BannerListViewAdapter();
                            if (adapter.SetDataBind(list) == true) {
                                return;
                            }
                            if(listview == null) listview = (ListView) getView().findViewById(R.id.listview1);
                            listview.setAdapter(adapter);
                            listViewEventInit();
                        }
                    },
                    new ServiceInfo.Act<Throwable>() {
                        @Override
                        public void execute(Throwable data) {
                            //TODO: Do something!
                        }
                    });

        } catch (Exception ex) {
            Log.d("로그", ex.getMessage());
            Global.getCommon().ProgressHide();
        }
    }

    private void listViewEventInit() {
        listview.setOnScrollListener(new ListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleFlag && mLockListView == false) {

                    // 데이터 로드
                    if (lastitemVisibleFlag == true) {
                        //Integer page = (listview.getCount() / nPageSize) + 1;
                        nPage = nPage + 1;
                        GetBannerList(nPage);

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
                selectedview = view;
                T_AD adItem = adapter.getItem(position);
                selectedData = adItem;
                //Toast.makeText(getActivity(),adItem.TITLE  + "가 선택되었습니다.", Toast.LENGTH_LONG).show();
                if (adItem.SIGN_CODE == null) {
                    Intent intent = new Intent(getContext(), WebViewActivity.class);
                    intent.putExtra("T_AD", adItem);
                    getContext().startActivity(intent);
                } else {
                    /// 사이니지제어
                    Toast.makeText(getActivity(), adItem.TITLE + "가 선택되었습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), SignageControlActivity.class);
                    intent.putExtra("SIGN_CODE", adItem.SIGN_CODE);
                    getContext().startActivity(intent);
                }

            }
        });
    }
}
