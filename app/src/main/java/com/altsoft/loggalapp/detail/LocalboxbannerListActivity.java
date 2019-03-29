package com.altsoft.loggalapp.detail;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.altsoft.Framework.Global;
import com.altsoft.Framework.GsonInfo;
import com.altsoft.Framework.module.BaseActivity;
import com.altsoft.Adapter.LocalBoxListItemAdapter;
import com.altsoft.loggalapp.R;
import com.altsoft.loggalapp.WebViewActivity;
import com.altsoft.model.RTN_SAVE_DATA;
import com.altsoft.model.T_AD;
import com.altsoft.model.UserInfo.T_MEMBER_BOOKMARK;
import com.altsoft.model.device.AD_DEVICE_MOBILE_COND;
import com.altsoft.model.device.AD_DEVICE_MOBILE_LIST;
import com.altsoft.model.device.AD_DEVICE_MOBILE_M;

import java.io.FileNotFoundException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LocalboxbannerListActivity extends BaseActivity {

    Activity activity;
    ListView listview ;
    boolean bLastPage = false;
    boolean lastitemVisibleFlag = false;
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수
    Integer nPageSize = 30;
    LocalBoxListItemAdapter adapter;
    private Long deviceCode;
    ImageView btnBookmark ;
    AD_DEVICE_MOBILE_M detailData;
    View selectedview;
    AD_DEVICE_MOBILE_LIST selectedData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_localboxbanner_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        adapter = new LocalBoxListItemAdapter();
        activity = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ComponentInit();

        this.GetLocalBoxBannerList();

    }

    @Override
    public void onResume() {
        super.onResume();
        if(selectedview != null)
        {
            if(Global.getData().BANNER_BOOKMARK_YN !=null) {
                selectedData.BANNER_BOOKMARK_YN = Global.getData().BANNER_BOOKMARK_YN;
                adapter.setItem(selectedview, selectedData);
            }
        }
        selectedview = null;
        selectedData = null;
        Global.getData().BANNER_BOOKMARK_YN = null;
    }
    private void ComponentInit()
    {
        Intent intent = getIntent();
        deviceCode =  (Long)intent.getLongExtra("DEVICE_CODE",0);
        super.appBarInit_titleOnly(intent.getStringExtra("DEVICE_NAME"));
        detailData = new AD_DEVICE_MOBILE_M();
        btnBookmark = (ImageView)findViewById(R.id.btnBookmark);

        if(Global.getLoginInfo().isLogin())
        {
            btnBookmark.setVisibility(View.VISIBLE);
            btnBookmark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(detailData != null) {
                        if(detailData.BOOKMARK_YN)
                        {
                            new AlertDialog.Builder(Global.getCurrentActivity())
                                    //.setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("북마크")
                                    .setMessage("북마크 취소를 하시겠습니까?")
                                    .setPositiveButton("예", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            T_MEMBER_BOOKMARK Param = new T_MEMBER_BOOKMARK();
                                            Param.USER_ID = Global.getSecurityInfo().EncryptAes(Global.getLoginInfo().USER_ID);

                                            Param.DEVICE_CODE = detailData.DEVICE_CODE;

                                            Param.SAVE_MODE = "D";
                                            Global.getCommon().ProgressShow();
                                            Call<RTN_SAVE_DATA> call = Global.getAPIService().MemberbookmarkSave(Param);
                                            call.enqueue(new Callback<RTN_SAVE_DATA>() {
                                                @Override
                                                public void onResponse(Call<RTN_SAVE_DATA> call, Response<RTN_SAVE_DATA> response) {
                                                    Global.getCommon().ProgressHide();
                                                    btnBookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24px);
                                                    detailData.BOOKMARK_YN = false;
                                                    Global.getData().LOCALBOX_BOOKMARK_YN = false;
                                                }

                                                @Override
                                                public void onFailure(Call<RTN_SAVE_DATA> call, Throwable t) {
                                                    Global.getCommon().ProgressHide();
                                                }
                                            });
                                            dialog.dismiss();
                                        }

                                    })
                                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            // Do nothing
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();

                        }
                        else{
                            new AlertDialog.Builder(Global.getCurrentActivity())
                                    // .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setTitle("북마크")
                                    .setMessage("북마크 등록을 하시겠습니까?")
                                    .setPositiveButton("예", new DialogInterface.OnClickListener()
                                    {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            T_MEMBER_BOOKMARK Param = new T_MEMBER_BOOKMARK();
                                            Param.USER_ID = Global.getLoginInfo().USER_ID;
                                            Param.USER_ID = Global.getSecurityInfo().EncryptAes(Global.getLoginInfo().USER_ID);
                                            Param.DEVICE_CODE = detailData.DEVICE_CODE;
                                            Param.TITLE = detailData.DEVICE_NAME;

                                            Param.SAVE_MODE = "U";
                                            Global.getCommon().ProgressShow();
                                            Call<RTN_SAVE_DATA> call = Global.getAPIService().MemberbookmarkSave(Param);
                                            call.enqueue(new Callback<RTN_SAVE_DATA>() {
                                                @Override
                                                public void onResponse(Call<RTN_SAVE_DATA> call, Response<RTN_SAVE_DATA> response) {
                                                    btnBookmark.setImageResource(R.drawable.ic_baseline_bookmark_24px);
                                                    detailData.BOOKMARK_YN = true;
                                                    Global.getData().LOCALBOX_BOOKMARK_YN = true;
                                                    Global.getCommon().ProgressHide();
                                                }

                                                @Override
                                                public void onFailure(Call<RTN_SAVE_DATA> call, Throwable t) {
                                                    Global.getCommon().ProgressHide();
                                                }
                                            });

                                            dialog.dismiss();
                                        }

                                    })
                                    .setNegativeButton("아니오", new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            // Do nothing
                                            dialog.dismiss();
                                        }
                                    })
                                    .show();
                        }
                    }
                }
            });
        }
        else{
            btnBookmark.setVisibility(View.GONE);
        }
    }
    private void GetLocalBoxBannerList() {

        AD_DEVICE_MOBILE_COND Cond = new AD_DEVICE_MOBILE_COND();
        Cond.PAGE_COUNT = 10000;
        Cond.PAGE = 1;
        Cond.USER_ID = Global.getSecurityInfo().EncryptAes(Global.getLoginInfo().USER_ID);
        Cond.DEVICE_CODE = deviceCode;
        Global.getCommon().ProgressShow(this);

        Call<AD_DEVICE_MOBILE_M> call = Global.getAPIService().GetMobileAdDeviceList(Cond);

        call.enqueue(new Callback<AD_DEVICE_MOBILE_M>() {
            @Override
            public void onResponse(Call<AD_DEVICE_MOBILE_M> call, Response<AD_DEVICE_MOBILE_M> response) {

                Global.getCommon().ProgressHide(activity);
                detailData = response.body();
                if(detailData == null) return;
                if(detailData.BOOKMARK_YN)  btnBookmark.setImageResource(R.drawable.ic_baseline_bookmark_24px);
                else btnBookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24px);
                //activity.setTitle(detailData.DEVICE_NAME);
                List<AD_DEVICE_MOBILE_LIST> list = detailData.AD_LIST;

                if(bLastPage) {
                    Toast.makeText(activity,"데이터가 모두 검색되었습니다.", Toast.LENGTH_LONG).show();
                    return;
                }
                if(list.size() < nPageSize) bLastPage = true;

                if(adapter.SetDataBind(list) == true) return;
                listview = (ListView)  activity.findViewById(R.id.listview1);
                listview.setAdapter(adapter);

                listview.setOnScrollListener(new ListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleFlag && mLockListView == false) {

                            // 데이터 로드
                            if(lastitemVisibleFlag == true) {
                                Integer page = (listview.getCount() / nPageSize) + 1;
                                GetLocalBoxBannerList();
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
                        selectedData= adapter.getItem(position);
                        T_AD data = new T_AD();
                        selectedview = view;

                        try {
                            data = (T_AD) new GsonInfo(AD_DEVICE_MOBILE_LIST.class, T_AD.class).ToCopy(selectedData);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        }


                        Intent intent = new Intent(activity, WebViewActivity.class);
                        intent.putExtra("T_AD", data);
                        activity.startActivity(intent);

                    }
                });
            }

            @Override
            public void onFailure(Call<AD_DEVICE_MOBILE_M> call, Throwable t) {
                Global.getCommon().ProgressHide(activity);
            }
        });
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
