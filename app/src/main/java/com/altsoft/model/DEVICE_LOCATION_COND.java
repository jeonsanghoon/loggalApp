package com.altsoft.model;

import java.io.Serializable;
/**
* 로컬박스 조회조건
* @author 전상훈
* @version 1.0.0
* @since 2019-03-13 오전 10:13
**/
public class DEVICE_LOCATION_COND implements Serializable
{
    public String MODE;
    public Integer STATION_CODE;
    public Integer COMPANY_CODE;
    public String SEARCH_CODE;
    
    /** 위도(검색위치 기준:현위치) **/
    public Double LATITUDE;
    /** 경도(검색위치 기준:현위치) **/
    public Double LONGITUDE;
   /** AES256으로 암호화된 위도 **/
    public String SEARCH_LAT;
    /** AES256으로 암호화된 경도 **/
    public String SEARCH_LONG;
    /** 거리 (m 기준) **/
    public Double DISTANCE;
    public String LOCATION_NAME;

    /** 로컬박스명 또는 소유주로 검색 % **/
    public String SEARCH_TEXT;
    /** 현재페이지 **/
    public Integer PAGE;
    /** 페이지당 건수 **/
    public Integer PAGE_COUNT;
    /** 사용자 아이디 **/
    public String USER_ID;
}
