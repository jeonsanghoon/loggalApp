package com.altsoft.Framework.module;


import android.Manifest;
import android.annotation.SuppressLint;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.altsoft.Framework.DataInfo.EMail;
import com.altsoft.Framework.Global;
import com.altsoft.Interface.AsyncCallbackOnEventListener;
import com.altsoft.loggalapp.FindPasswordActivity;
import com.altsoft.loggalapp.R;
import com.altsoft.model.MAIL_INFO;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;


@SuppressLint("Registered")
public abstract class BaseActivity extends AppCompatActivity {

    public static ArrayList<Activity> actList = new ArrayList<Activity>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Global.setCurrentActivity(this);

    }
    @Override
    protected  void onStart()
    {
        super.onStart();
        Global.setCurrentActivity(this);
        Global.setFragmentManager(getSupportFragmentManager());
        if(Global.getCommon().getComponentName(this).toLowerCase() != "mainactivity") {
            actList.add(this);
        }
    }

    //region Methods Toolbar
    public void onInitToolbar(Toolbar toolBar) {
        onInitToolbar(toolBar, getString(R.string.clear), -1, false);
    }

    public void onInitToolbar(Toolbar toolBar, String title) {
        onInitToolbar(toolBar, title, -1, false);
    }

    public void onInitToolbar(Toolbar toolBar, int title) {
        onInitToolbar(toolBar, title, -1, false);
    }

    public void onInitToolbar(Toolbar toolBar, int title, int icon) {
        onInitToolbar(toolBar, getString(title), icon, true);
    }

    public void onInitToolbar(Toolbar toolBar, String title, boolean displayHome) {
        onInitToolbar(toolBar, title, -1, displayHome);
    }

    public void onInitToolbar(Toolbar toolBar, int title, boolean displayHome) {
        onInitToolbar(toolBar, title, -1, displayHome);
    }

    public void onInitToolbar(Toolbar toolBar, int title, int icon, boolean displayHome) {
        onInitToolbar(toolBar, getString(title), icon, displayHome);
    }

    public void onInitToolbar(Toolbar toolBar, String title, int icon, boolean displayHome) {

        if (toolBar != null) {
            setSupportActionBar(toolBar);
            ActionBar actionBar = getSupportActionBar();

            if (actionBar != null) {
                actionBar.setTitle(title);
                actionBar.setDisplayShowHomeEnabled(displayHome);
                actionBar.setDisplayHomeAsUpEnabled(displayHome);
                if (icon != -1 && displayHome) {
                    toolBar.setNavigationIcon(ContextCompat.getDrawable(this, icon));
                }
            }
        }
    }

    public void appBarInit_titleOnly(String title)
    {
        appBarInit(title, true,false);
    }
    /// AppBar 셋팅
    public void appBarInit(String title,Boolean bTitle, Boolean bBookmark  )
    {
        TextView tvTitle = findViewById(R.id.tvTitle);
        if(title != null) tvTitle.setText(title);

        if(!(bTitle == null || bTitle == true))
            tvTitle.setVisibility(View.GONE);
        if(!(bBookmark == null || bBookmark == true))
            findViewById(R.id.btnBookmark).setVisibility(View.GONE);
    }
}
