package com.altsoft.Framework.map;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.widget.Toast;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import retrofit2.Response;

public class MapInfo {
    public Double latitude  = 37.5609447;
    public Double longitude =126.9795475;
    public Double currentLatitude = 0.0;
    public Double currentLongitude = 0.0;

    public String currentLocationAddress = "";
    public MapInfo() {
    }
    public MapInfo(Double lat, Double lng) {
        latitude = lat;
        longitude = lng;
    }
    public MapInfo(Context mContext,Double lat, Double lng) {
        latitude = lat;
        longitude = lng;
        getAddress( mContext,  lat,  lng);
    }
    public MapInfo(Context mContext,String addr) {
        currentLocationAddress = addr;
        findGeoPoint(mContext, addr);
    }
    /**
     * 위도,경도로 주소구하기
     * @param lat
     * @param lng
     * @return 주소
     */
    public String getAddress(Context mContext, double lat, double lng) {
        String nowAddress ="현재 위치를 확인 할 수 없습니다.";
        Geocoder geocoder = new Geocoder(mContext, Locale.KOREA);
        List<Address> address;
        try {
            if (geocoder != null) {
                //세번째 파라미터는 좌표에 대해 주소를 리턴 받는 갯수로
                //한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 최대갯수 설정
                address = geocoder.getFromLocation(lat, lng, 1);
                currentLatitude = lat;
                currentLongitude = lng;
                if (address != null && address.size() > 0) {
                    // 주소 받아오기
                    currentLocationAddress = address.get(0).getAddressLine(0).toString();
                    nowAddress  = currentLocationAddress;
                }
            }
        } catch (IOException e) {
            Toast.makeText(mContext, "주소를 가져 올 수 없습니다.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return nowAddress;
    }

    /**
     * 주소로부터 위치정보 취득
     *
     * @param address
     *            주소
     */
    public  Location findGeoPoint(Context mcontext, String address) {
        Location loc = new Location("");
        Geocoder coder = new Geocoder(mcontext);
        List<Address> addr = null;// 한좌표에 대해 두개이상의 이름이 존재할수있기에 주소배열을 리턴받기 위해 설정

        try {
            addr = coder.getFromLocationName(address, 5);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }// 몇개 까지의 주소를 원하는지 지정 1~5개 정도가 적당
        if (addr != null) {
            for (int i = 0; i < addr.size(); i++) {
                Address lating = addr.get(i);
                double lat = lating.getLatitude(); // 위도가져오기
                double lng = lating.getLongitude(); // 경도가져오기
                loc.setLatitude(lat);
                loc.setLongitude(lng);
                latitude = lat;
                longitude = lng;
            }
        }
        return loc;
    }

    public String getKakaoAddressName(Response<JsonObject> response)
    {
        if(response.body().get("documents").getAsJsonArray().size() == 0) return "";
        JsonObject obj = response.body().get("documents").getAsJsonArray().get(0).getAsJsonObject();
        return obj.get("address").getAsJsonObject().get("address_name").getAsString();
    }

    public String getKakaoRoadAddressName(Response<JsonObject> response)
    {
        if(response.body().get("documents").getAsJsonArray().size() == 0) return "";
        JsonObject obj = response.body().get("documents").getAsJsonArray().get(0).getAsJsonObject();

        if(!obj.get("road_address").isJsonNull()) {
            return obj.get("road_address").getAsJsonObject().get("address_name").getAsString();
        }
        else
            return obj.get("address").getAsJsonObject().get("address_name").getAsString();
    }

}
