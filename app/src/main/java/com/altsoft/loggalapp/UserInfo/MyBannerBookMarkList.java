package com.altsoft.loggalapp.UserInfo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.altsoft.Adapter.BookMarkListViewAdapter;
import com.altsoft.Adapter.LocalBoxListItemAdapter;
import com.altsoft.Framework.Global;
import com.altsoft.Framework.GsonInfo;
import com.altsoft.Framework.enResult;
import com.altsoft.Framework.module.BaseActivity;
import com.altsoft.loggalapp.R;
import com.altsoft.loggalapp.WebViewActivity;
import com.altsoft.loggalapp.detail.LocalboxbannerListActivity;
import com.altsoft.model.RTN_SAVE_DATA;
import com.altsoft.model.T_AD;
import com.altsoft.model.UserInfo.T_MEMBER_BOOKMARK;
import com.altsoft.model.UserInfo.T_MEMBER_BOOKMARK_COND;
import com.altsoft.model.device.AD_DEVICE_MOBILE_COND;
import com.altsoft.model.device.AD_DEVICE_MOBILE_LIST;
import com.altsoft.model.device.AD_DEVICE_MOBILE_M;

import java.io.FileNotFoundException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyBannerBookMarkList extends BaseActivity {
    Activity activity;
    ListView listview ;
    boolean bLastPage = false;
    boolean lastitemVisibleFlag = false;
    private boolean mLockListView = false;          // 데이터 불러올때 중복안되게 하기위한 변수
    Integer nPageSize = 30;
    BookMarkListViewAdapter adapter;
    int selectedIndex = -1;
    View selectedview;
    List<T_MEMBER_BOOKMARK> datalist;
    T_MEMBER_BOOKMARK selectedData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_banner_book_mark_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        adapter = new BookMarkListViewAdapter();
        activity = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();


        ComponentInit();
        this.GetBookMarkList();

    }

    @Override
    public void onResume() {
        super.onResume();
        if (selectedview != null) {
            if (Global.getData().BANNER_BOOKMARK_YN  != null && Global.getData().BANNER_BOOKMARK_YN == false && selectedIndex >= 0) {

                datalist.remove(datalist.get(selectedIndex));
                adapter.notifyDataSetChanged();
            }
        }
        selectedview = null;
        selectedData = null;
        selectedIndex = -1;
        Global.getData().BANNER_BOOKMARK_YN = null;
    }
    private void ComponentInit()
    {
        super.appBarInit_titleOnly("북마크");
    }
    private void GetBookMarkList() {

        T_MEMBER_BOOKMARK_COND Cond = new T_MEMBER_BOOKMARK_COND();
        Cond.PAGE_COUNT = 10000;
        Cond.PAGE = 1;
        Cond.USER_ID = Global.getLoginInfo().USER_ID;
        Cond.USER_ID = Global.getSecurityInfo().EncryptAes(Cond.USER_ID);
        //Cond.BOOKMARK_TYPE = 2;
        Global.getCommon().ProgressShow(this);

        Call<List<T_MEMBER_BOOKMARK>> call = Global.getAPIService().GetMemberbookmarkList(Cond);

        call.enqueue(new Callback<List<T_MEMBER_BOOKMARK>>() {
            @Override
            public void onResponse(Call<List<T_MEMBER_BOOKMARK>> call, Response<List<T_MEMBER_BOOKMARK>> response) {

                Global.getCommon().ProgressHide(activity);
                datalist = response.body();
                if(datalist == null) return;

                if(bLastPage) {
                    Toast.makeText(activity,"데이터가 모두 검색되었습니다.", Toast.LENGTH_LONG).show();
                    return;
                }
                if(datalist.size() < nPageSize) bLastPage = true;

                if(adapter.SetDataBind(datalist) == true) return;
                listview = (ListView)  activity.findViewById(R.id.listview1);
                listview.setAdapter(adapter);

                listview.setOnScrollListener(new ListView.OnScrollListener() {
                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE && lastitemVisibleFlag && mLockListView == false) {

                            // 데이터 로드
                            if(lastitemVisibleFlag == true) {
                                Integer page = (listview.getCount() / nPageSize) + 1;
                                GetBookMarkList();
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
                        selectedIndex = position;
                        //T_MEMBER_BOOKMARK data = new T_MEMBER_BOOKMARK();
                        selectedview = view;


                        if(selectedData.AD_CODE != null) {
                            T_AD data = new T_AD();
                            data.TITLE = selectedData.TITLE;
                            data.AD_CODE = selectedData.AD_CODE;
                            Intent intent = new Intent(activity, WebViewActivity.class);
                            intent.putExtra("T_AD", data);
                            activity.startActivity(intent);
                        }
                        else{
                            Intent intent = new Intent(activity, LocalboxbannerListActivity.class);
                            intent.putExtra("DEVICE_CODE", selectedData.DEVICE_CODE );
                            activity.startActivity(intent);
                        }

                    }
                });
            }

            @Override
            public void onFailure(Call<List<T_MEMBER_BOOKMARK>> call, Throwable t) {
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

    }

}
