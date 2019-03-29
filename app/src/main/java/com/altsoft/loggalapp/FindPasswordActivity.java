package com.altsoft.loggalapp;


import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.altsoft.asynctask.EmailSendAsyncTask;
import com.altsoft.Framework.DataInfo.SecurityInfo;
import com.altsoft.Framework.Global;
import com.altsoft.Framework.module.BaseActivity;
import com.altsoft.Interface.AsyncCallbackOnEventListener;
import com.altsoft.Interface.ServiceInfo;
import com.altsoft.model.RTN_SAVE_DATA;
import com.altsoft.model.UserInfo.T_MEMBER;
import com.altsoft.model.UserInfo.T_MEMBER_COND;
import com.altsoft.model.UserInfo.T_MEMBER_PASSWROD_CHANGE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;


public class FindPasswordActivity extends BaseActivity {

    private String strOriPassword;
    EditText editMail;
    Date startDate;
    Date endDate;
    private T_MEMBER member;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_password);
        super.appBarInit_titleOnly(this.getString(R.string.findpassword));
        /// 뒤로가기 버튼
        setSupportActionBar((android.support.v7.widget.Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CompoentInit();

    }

    private T_MEMBER_PASSWROD_CHANGE getViewData()
    {
        T_MEMBER_PASSWROD_CHANGE rtn = new T_MEMBER_PASSWROD_CHANGE();
        rtn.USER_ID = ((EditText)findViewById(R.id.rEditEmail)).getText().toString();
        rtn.ORI_PASSWORD = ((EditText)findViewById(R.id.rEditOriPassword)).getText().toString();
        rtn.PASSWORD = ((EditText)findViewById(R.id.rEditPassword)).getText().toString();
        rtn.PASSWORD_CONFIRM = ((EditText)findViewById(R.id.rEditPassword2)).getText().toString();

        if(Global.getValidityCheck().isEmpty(rtn.USER_ID))
        {
            Toast.makeText(getApplicationContext(),"이메일을 입력하세요.", Toast.LENGTH_LONG).show();
            ((EditText)findViewById(R.id.rEditEmail)).requestFocus();
            return null;
        }

        if(Global.getValidityCheck().isEmpty(rtn.ORI_PASSWORD))
        {
            Toast.makeText(getApplicationContext(),"임시패스워드를 입력하여 주시기 바랍니다.", Toast.LENGTH_LONG).show();
            ((EditText)findViewById(R.id.rEditOriPassword)).requestFocus();
            return null;
        }
        if(Global.getValidityCheck().isEmpty(rtn.PASSWORD))
        {
            Toast.makeText(getApplicationContext(),"패스워드를 입력하여 주시기 바랍니다.", Toast.LENGTH_LONG).show();
            ((EditText)findViewById(R.id.rEditPassword)).requestFocus();
            return null;
        }
        if(!Global.getValidityCheck().PasswordConfirm(rtn.PASSWORD, rtn.PASSWORD_CONFIRM )) return null;
        if(!Global.getValidityCheck().Password(rtn.PASSWORD)) return null;
        rtn.ORI_PASSWORD = Global.getSecurityInfo().ConvertSha(rtn.ORI_PASSWORD);
        if(!strOriPassword.equals(rtn.ORI_PASSWORD ))
        {
            Toast.makeText(getApplicationContext(),"임시 패스워드와 일치하지 않습니다.", Toast.LENGTH_LONG).show();
            ((EditText)findViewById(R.id.rEditOriPassword)).requestFocus();
            return null;
        }
        long diff = (new Date(System.currentTimeMillis())).getTime() -  startDate.getTime();
        if(diff/1000 >= 300) {
            Toast.makeText(getApplicationContext(),"패스워드 유효입력시간이 경과하였습니다.", Toast.LENGTH_LONG).show();
            return null;
        }
        rtn.PASSWORD =  Global.getSecurityInfo().ConvertSha(rtn.PASSWORD);


        rtn.USER_ID = Global.getSecurityInfo().EncryptAes(rtn.USER_ID);

        return rtn;
    }
    private void CompoentInit()
    {

        editMail = findViewById(R.id.rEditEmail);
        EditText editOriPassword = findViewById(R.id.rEditOriPassword);
        EditText editPassword   = findViewById(R.id.rEditPassword);
        EditText editPassword2   = findViewById(R.id.rEditPassword2);

        findViewById(R.id.btnTempToMailSend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(!Global.getValidityCheck().Email(editMail.getText().toString())) {
                    editMail.setSelectAllOnFocus(true);
                    return;
                }

                try {

                    T_MEMBER_COND cond = new T_MEMBER_COND();
                    cond.USER_ID = editMail.getText().toString();
                    cond.USER_ID = Global.getSecurityInfo().EncryptAes(cond.USER_ID);
                    Call<List<T_MEMBER>> call =  Global.getAPIService().GetMemberList(cond);

                    Global.getCallService().callService(call
                            , new ServiceInfo.Act<ArrayList<T_MEMBER>>() {
                                @Override
                                public void execute(ArrayList<T_MEMBER> list) {
                                    if(list.size() == 1)
                                    {
                                        member = list.get(0);
                                        //strOriPassword = member.PASSWORD;
                                        startDate = new Date(System.currentTimeMillis());
                                        SendEmail();
                                    }
                                    else{

                                        Toast.makeText(getApplicationContext(), "아이디가 존재하지 않습니다.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            },
                            new ServiceInfo.Act<Throwable>() {
                                @Override
                                public void execute(Throwable data) {
                                    //TODO: Do something!
                                }
                            });



                }catch(Exception ex){
                    Log.d("error",ex.getMessage());
                    Global.getCommon().ProgressHide();
                }
            }
        });
        findViewById(R.id.btnRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                T_MEMBER_PASSWROD_CHANGE data = getViewData();
                if(data == null) return ;
                Call<RTN_SAVE_DATA> call =  Global.getAPIService().MemberPasswordChange(data);

                Global.getCallService().callService(call
                        , new ServiceInfo.Act<RTN_SAVE_DATA>() {
                            @Override
                            public void execute(RTN_SAVE_DATA rtn) {
                                if(Global.getValidityCheck().isEmpty(rtn.ERROR_MESSAGE)) {
                                    Toast.makeText(getApplicationContext(), "패스워드가 정상적으로 변경되었습니다.", Toast.LENGTH_LONG).show();
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(), rtn.ERROR_MESSAGE, Toast.LENGTH_LONG).show();
                                }
                            }
                        },
                        new ServiceInfo.Act<Throwable>() {
                            @Override
                            public void execute(Throwable data) {
                                //TODO: Do something!
                            }
                        });

            }
        });

    }

    private void SendEmail()
    {
        Global.getCommon().ProgressShow();
        EmailSendAsyncTask em = new EmailSendAsyncTask(new AsyncCallbackOnEventListener<Boolean>() {
        @Override
        public void onSuccess(Boolean result) {
            Toast.makeText(getApplicationContext(), editMail.getText().toString() + "로 임시 비밀번호가 방송되었습니다.", Toast.LENGTH_LONG).show();
            Global.getCommon().ProgressHide();
        }

        @Override
        public void onFailure(Exception e) {
            Toast.makeText(getApplicationContext(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
            Global.getCommon().ProgressHide();
        }

    });
        em.email.set_from("mrc0700@gmail.com");

        em.email.set_to(editMail.getText().toString().split(","));
        em.email.set_subject("[loggal]임시비밀번호가 발급되었습니다.");
        strOriPassword = Global.getCommon().getRandomPassword(6);
        em.email.setBody("임시비밀번호가 발급되었습니다.<br/><b>임시비밀번호 : " + strOriPassword + "</b><br/>해당 번호를 모바일에 있는 기존비밀번호에 입력하세요");


        strOriPassword = Global.getSecurityInfo().ConvertSha(strOriPassword);
        em.execute();
    }

    /// 뒤로가기 버튼
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: { //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}




