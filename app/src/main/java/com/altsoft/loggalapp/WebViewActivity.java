package com.altsoft.loggalapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.altsoft.Framework.Global;
import com.altsoft.Framework.module.BaseActivity;
import com.altsoft.model.MOBILE_AD_DETAIL_COND;
import com.altsoft.model.MOBILE_AD_DETAIL_DATA;
import com.altsoft.model.RTN_SAVE_DATA;
import com.altsoft.model.T_AD;
import com.altsoft.model.UserInfo.T_MEMBER_BOOKMARK;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewActivity extends BaseActivity {
    private WebView mWebView;
    ImageButton btnBookmark ;
    Activity activity;
    MOBILE_AD_DETAIL_DATA detailData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activity = this;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ComponentInit();
        Intent intent = getIntent();
        T_AD data = (T_AD) intent.getSerializableExtra("T_AD");
        activity.setTitle(data.TITLE);

        ((TextView)findViewById(R.id.tvTitle)).setText(data.TITLE);
        doQuery(data);

    }
    private void ComponentInit()
    {
        btnBookmark = (ImageButton)findViewById(R.id.btnBookmark);

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
                                            Param.USER_ID = Global.getLoginInfo().USER_ID;
                                            Param.USER_ID = Global.getSecurityInfo().EncryptAes(Global.getLoginInfo().USER_ID);
                                            Param.AD_CODE = detailData.AD_CODE;
                                            Param.SAVE_MODE = "D";
                                            Global.getCommon().ProgressShow();
                                            Call<RTN_SAVE_DATA> call = Global.getAPIService().MemberbookmarkSave(Param);
                                            call.enqueue(new Callback<RTN_SAVE_DATA>() {
                                                @Override
                                                public void onResponse(Call<RTN_SAVE_DATA> call, Response<RTN_SAVE_DATA> response) {
                                                    Global.getCommon().ProgressHide();
                                                    btnBookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24px);
                                                    detailData.BOOKMARK_YN = false;
                                                    Global.getData().BANNER_BOOKMARK_YN = false;
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
                                            Param.AD_CODE = detailData.AD_CODE;
                                            Param.TITLE = detailData.TITLE;

                                            Param.SAVE_MODE = "U";
                                            Global.getCommon().ProgressShow();
                                            Call<RTN_SAVE_DATA> call = Global.getAPIService().MemberbookmarkSave(Param);
                                            call.enqueue(new Callback<RTN_SAVE_DATA>() {
                                                @Override
                                                public void onResponse(Call<RTN_SAVE_DATA> call, Response<RTN_SAVE_DATA> response) {
                                                    btnBookmark.setImageResource(R.drawable.ic_baseline_bookmark_24px);
                                                    detailData.BOOKMARK_YN = true;
                                                    Global.getData().BANNER_BOOKMARK_YN = true;
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
        mWebView = (WebView) findViewById(R.id.webView);
        mWebView.getSettings().setJavaScriptEnabled(true);
        //mWebView.loadUrl("http://www.pois.co.kr/mobile/login.do");

        //mWebView.loadUrl("http://www.naver.com"); // 접속 URL
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClientClass(){
            boolean loadingFinished = true;
            boolean redirect = false;

            long last_page_start;
            long now;
            // Load the url
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (!loadingFinished) {
                    redirect = true;
                }

                loadingFinished = false;
                view.loadUrl(url);
                return false;
            }
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.i("p","pagestart");
                loadingFinished = false;
                last_page_start = System.nanoTime();
                Global.getCommon().ProgressShow();
            }
            public void onPageFinished(WebView view, String url) {
                Log.i("p","pagefinish");
                if(!redirect){
                    loadingFinished = true;
                }
                //call remove_splash in 500 miSec
                if(loadingFinished && !redirect){
                    now = System.nanoTime();
                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    Global.getCommon().ProgressHide();
                                }
                            },
                            500);
                } else{
                    redirect = false;
                }
            }
        });
    }
    private void doQuery( T_AD data){
        MOBILE_AD_DETAIL_COND Cond = new MOBILE_AD_DETAIL_COND();
        Cond.AD_CODE = data.AD_CODE;
        Cond.USER_ID = Global.getLoginInfo().USER_ID;
//        Global.getCommon().ProgressShow(this);

        Call<MOBILE_AD_DETAIL_DATA> call = Global.getAPIService().GetMobileAdDetail(Cond);
        call.enqueue(new Callback<MOBILE_AD_DETAIL_DATA>() {
            @Override
            public void onResponse(Call<MOBILE_AD_DETAIL_DATA> call, Response<MOBILE_AD_DETAIL_DATA> response) {
//                Global.getCommon().ProgressHide(activity);
                detailData =  response.body();
                mWebView.loadUrl(  detailData.CONTENT_URL);
                if(detailData.BOOKMARK_YN)  btnBookmark.setImageResource(R.drawable.ic_baseline_bookmark_24px);
                else btnBookmark.setImageResource(R.drawable.ic_baseline_bookmark_border_24px);
            }

            @Override
            public void onFailure(Call<MOBILE_AD_DETAIL_DATA> call, Throwable t) {

            }
        });
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    private class WebViewClientClass extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Log.d("check URL",url);
            view.loadUrl(url);
            return true;
        }
    }

    //// 뒤로가기 이벤트
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
