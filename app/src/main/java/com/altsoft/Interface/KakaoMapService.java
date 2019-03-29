package com.altsoft.Interface;
import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface KakaoMapService {
    /// 좌표로 주소 검색
    @GET("/v2/local/geo/coord2address.json?input_coord=WGS84")
    Call<JsonObject> GetLatiLongiToAddress (@Query("y") double y, @Query("x") double x); /*y : latitude, x: longititude */
    @GET("/v2/local/search/address.json?")
    Call<JsonObject> GetAddressSearch (@Query("query") String query); /*query 주소 예) 경기도 */
    @GET("/v2/local/search/keyword.json?y=37.514322572335935&x=127.06283102249932&radius=20000")
    Call<JsonObject> GetAddressKeyword (@Query("query") String query); /*query 주소 예) 경기도 */
}
