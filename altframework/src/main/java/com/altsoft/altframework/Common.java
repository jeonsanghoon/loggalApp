package com.altsoft.altframework;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ProgressBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Common {
    /// 프로그래스바 숨기기
    public void ProgressHide(Activity activity, ProgressBar progress) {
        progress.setVisibility((int) 4);
    }
    /// 프로그래스바 보여주기
    public void ProgressShow(Activity activity, ProgressBar progress) {
        progress.setVisibility((int)0);
    }

    /// 날짜형 포맷 여부
    public  boolean isValidDate(String inDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        dateFormat.setLenient(false);
        try {
            dateFormat.parse(inDate.trim() + " 00:00:00");
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
    /// 시간형 포맷 여부
    public boolean isValidTime(String inTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        dateFormat.setLenient(false);
        try {
            inTime = inTime.trim();
            if(inTime.trim().length() == 5) {inTime = inTime + ":00";}
            dateFormat.parse("2000.01.01 " + inTime.trim());
        } catch (ParseException pe) {
            return false;
        }
        return true;
    }
    // 현재 일자정보 가져오기
    public  String getCurrentTimeString()
    {
        return getCurrentTimeString("yyyyMMddHHmmss");
    }
    public String getCurrentTimeString(String Format) {
        long now = System.currentTimeMillis();
        Date date = new Date(now);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String getTime = sdf.format(date);

        return getTime;
    }

    public String leftPad(String originalString, int length,
                          char padCharacter) {
        String paddedString = originalString;
        while (paddedString.length() < length) {
            paddedString = padCharacter + paddedString;
        }
        return paddedString;
    }
    public String getCurrentTime(String timeFormat){
        return new SimpleDateFormat(timeFormat).format(System.currentTimeMillis());
    }

    public  Integer dpToPx(Context ctx, Integer dp) {
        float density = ctx.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    /// 키보드 보이기/ 숨기기
    public boolean hideSoftInputWindow(Context context, View edit_view, boolean bState) {

        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        if ( bState )
            return imm.showSoftInput(edit_view, 0);
        else
            return imm.hideSoftInputFromWindow
                    (edit_view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
}

