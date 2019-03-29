package com.altsoft.asynctask;

import android.os.AsyncTask;
import android.util.Log;

import com.altsoft.Framework.DataInfo.EMail;
import com.altsoft.Framework.Global;
import com.altsoft.Interface.AsyncCallbackOnEventListener;

import javax.mail.AuthenticationFailedException;
import javax.mail.MessagingException;

public class EmailSendAsyncTask extends AsyncTask<Void, Void, Boolean> {
    public EMail email;

    private AsyncCallbackOnEventListener<Boolean> mCallBack;
    public Boolean bSuccess = true;
    public Exception mException;
    public EmailSendAsyncTask(AsyncCallbackOnEventListener callback) {
        email= new EMail();
        mCallBack = callback;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        Global.getCommon().ProgressShow();
        try {
            if (email.send()) {
                //activity.displayMessage(Arrays.toString(email.get_to()) + "로 이메일이 발송되었습니다.");
                bSuccess = true;
            } else {
                mException = new Exception("이메일보내기가 실패하였습니다.");
                return false;
                // activity.displayMessage(Arrays.toString(email.get_to()) + "로 이메일이 발송이 실패하였습니다.");
            }

            return true;
        } catch (AuthenticationFailedException e) {
            Log.e(EmailSendAsyncTask.class.getName(), "Bad account details");
            e.printStackTrace();

            mException = new Exception("인증실패 하였습니다.");
            Global.getCommon().ProgressHide();
            return false;
        } catch (MessagingException e) {

            Log.e(EmailSendAsyncTask.class.getName(), "Email failed");
            e.printStackTrace();
            mException = new Exception("이메일보내기가 실패하였습니다.");
            Global.getCommon().ProgressHide();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            mException = new Exception("에러가 발생했습니다.");
            Global.getCommon().ProgressHide();
            return false;
        }
    }
    @Override
    protected void onPostExecute(Boolean result) {
        Global.getCommon().ProgressHide();
        if (mCallBack != null) {
            if(true)
                mCallBack.onSuccess(result);
            else
                mCallBack.onFailure(mException);
        }
    }
}
