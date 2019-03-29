package com.altsoft.Interface;

import android.view.View;
import android.widget.ProgressBar;

import com.altsoft.Framework.Global;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ServiceInfo {
    public interface Act<T> {
        void execute(T data);
    }
    //Makes a call to the service and handles the UI manipulation for you
    public <T> void callService(Call c, final Act<T> success, final Act<Throwable> failure) {
        Global.getCommon().ProgressShow();

        c.enqueue(new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                Global.getCommon().ProgressHide();
                success.execute(response.body());

            }
            @Override
            public void onFailure(Call<T> call, Throwable t) {
                Global.getCommon().ProgressHide();
                failure.execute(t);
            }
        });
    }
}
/* 예제)
     Call<List<DEVICE_LOCATION>> call = Global.getAPIService().GetDeviceLocation(Cond);
            Global.getCallService().callService(call
                    , new ServiceInfo.Act<ArrayList<DEVICE_LOCATION>>() {
                        @Override
                        public void execute(ArrayList<DEVICE_LOCATION> list) {
                        }
                    },
                    new ServiceInfo.Act<Throwable>() {
                        @Override
                        public void execute(Throwable data) {
                            //TODO: Do something!
                        }
                    }
            );
 */