package com.altsoft.Interface;

import com.altsoft.model.AD_SEARCH_COND;
import com.altsoft.model.DEVICE_LOCATION;
import com.altsoft.model.DEVICE_LOCATION_COND;
import com.altsoft.model.MOBILE_AD_DETAIL_COND;
import com.altsoft.model.MOBILE_AD_DETAIL_DATA;
import com.altsoft.model.RTN_SAVE_DATA;
import com.altsoft.model.T_AD;
import com.altsoft.model.UserInfo.LOGIN_COND;
import com.altsoft.model.UserInfo.LOGIN_DATA;
import com.altsoft.model.UserInfo.T_MEMBER;
import com.altsoft.model.UserInfo.T_MEMBER_BOOKMARK;
import com.altsoft.model.UserInfo.T_MEMBER_BOOKMARK_COND;
import com.altsoft.model.UserInfo.T_MEMBER_COND;
import com.altsoft.model.UserInfo.T_MEMBER_PASSWROD_CHANGE;
import com.altsoft.model.UserInfo.T_MEMBER_SNS_UPDATE;
import com.altsoft.model.category.CATEGORY_COND;
import com.altsoft.model.category.CATEGORY_LIST;
import com.altsoft.model.common.T_FILE;
import com.altsoft.model.device.AD_DEVICE_MOBILE_COND;
import com.altsoft.model.device.AD_DEVICE_MOBILE_M;
import com.altsoft.model.device.T_DEVICE_STATION;
import com.altsoft.model.device.T_DEVICE_STATION_COND;
import com.altsoft.model.keyword.CODE_DATA;
import com.altsoft.model.keyword.KEYWORD_COND;
import com.altsoft.model.search.MOBILE_AD_SEARCH_COND;
import com.altsoft.model.search.MOBILE_AD_SEARCH_DATA;
import com.altsoft.model.signage.MOBILE_SIGNAGE_COND;
import com.altsoft.model.signage.MOBILE_SIGNAGE_LIST;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface MobileService {

    @POST("/api/advertising/GetAdList")
    Call<List<T_AD>> GetBannerList (@Body AD_SEARCH_COND Cond);
    @POST("/api/loggalBox/GetDeviceLocation")
    Call<List<DEVICE_LOCATION>> GetDeviceLocation(@Body DEVICE_LOCATION_COND Cond);
    @POST("/api/advertising/GetMobileAdDetail")
    Call<MOBILE_AD_DETAIL_DATA> GetMobileAdDetail(@Body MOBILE_AD_DETAIL_COND Cond);
    @POST("/api/advertising/GetMobileAdDeviceList")
    Call<AD_DEVICE_MOBILE_M> GetMobileAdDeviceList(@Body AD_DEVICE_MOBILE_COND Cond);
    @POST("/api/advertising/GetCategoryList")
    Call<List<CATEGORY_LIST>> GetCategoryList(@Body CATEGORY_COND Cond);
    @POST("/api/advertising/GetKeywordAutoCompleateList")
    Call<List<CODE_DATA>> GetKeywordAutoCompleateList(@Body KEYWORD_COND Cond);
    @POST("/api/advertising/GetMobileAdSearchList")
    Call<List<MOBILE_AD_SEARCH_DATA>> GetMobileAdSearchList(@Body MOBILE_AD_SEARCH_COND Cond);
    @POST("/api/signage/GetMobileSignageList")
    Call<List<MOBILE_SIGNAGE_LIST>> GetMobileSignageList(@Body MOBILE_SIGNAGE_COND Cond);

    /*로그인관련*/
    @POST("/api/Account/SaveMember")
    Call<RTN_SAVE_DATA> SaveMember(@Body T_MEMBER Cond);
    @POST("/api/Account/GetMobileLogin")
    Call<LOGIN_DATA> GetMobileLogin(@Body LOGIN_COND Cond);
    @POST("/api/Account/MemberPasswordChange")
    Call<RTN_SAVE_DATA> MemberPasswordChange(@Body T_MEMBER_PASSWROD_CHANGE Cond);
    @POST("/api/Account/GetMobileLoginMemberList")
    Call<List<T_MEMBER>> GetMemberList(@Body T_MEMBER_COND Cond);

    @POST("/api/Account/MemberSnsIDUpdate")
    Call<RTN_SAVE_DATA> MemberSnsIDUpdate(@Body T_MEMBER_SNS_UPDATE Cond);

    /*북마크관련*/
    @POST("/api/Account/MemberbookmarkSave")
    Call<RTN_SAVE_DATA> MemberbookmarkSave(@Body T_MEMBER_BOOKMARK Param);
    @POST("/api/Account/GetMemberbookmarkList")
    Call<List<T_MEMBER_BOOKMARK>> GetMemberbookmarkList(@Body T_MEMBER_BOOKMARK_COND Cond);


    @POST("/api/loggalBox/GetDeviceStationMapList")
    Call<List<T_DEVICE_STATION>> GetDeviceStationMapList(@Body T_DEVICE_STATION_COND Cond);


    @POST("/api/loggalBox/GetFileList")
    Call<List<T_FILE>> GetFileList(@Body T_DEVICE_STATION_COND Cond);

    @POST("/api/common/FileSave")
    Call<RTN_SAVE_DATA> FileSave(@Body T_FILE Cond);
}

