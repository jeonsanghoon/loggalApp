package com.altsoft.asynctask;

import android.app.Activity;
import android.os.AsyncTask;

import com.altsoft.Framework.Global;
import com.altsoft.Interface.AsyncCallbackOnEventListener;

/**
 * AsyncTask<Integer, Integer, Boolean>은 아래와 같은 형식이다
 * AsyncTask<전달받은값의종류, Update값의종류, 결과값의종류>
 * ex) AsyncTask<Void, Integer, Void>
 */
public class FtpFileUploadTask extends AsyncTask<String, Integer, String> {
    private AsyncCallbackOnEventListener<String> mCallBack;
    public Boolean bSuccess = true;
    public Exception mException;

    public FtpFileUploadTask(AsyncCallbackOnEventListener callback) {

        mCallBack = callback;

    }
    @Override
    protected String doInBackground(String... strings) {
        Global.getCommon().ProgressShow();
        String uploadFileName = strings[1];
        try {

            /*업로드 storeFile가  false일 경우 Ftp 서버에 FTP SSL 설정에 인증서가 선택되어 있는지 확인 */
            Boolean bChk = Global.getFtpInfo().ftpUploadFile(strings[0],strings[1],strings[2]);
            if(!bChk) throw new Exception("업로드에 실패하였습니다.");

        } catch (Exception e) {
            e.printStackTrace();
            mException = e;
        }
        return uploadFileName;
    }

    @Override
    protected void onProgressUpdate(Integer... values) { // 파일 다운로드 퍼센티지 표시 작업
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        // doInBackground 에서 받아온 total 값 사용 장소
        super.onPostExecute(result);
        Global.getCommon().ProgressHide();
        if (mCallBack != null) {
            if(true)
                mCallBack.onSuccess(result);
            else
                mCallBack.onFailure(mException);
        }
    }
}



