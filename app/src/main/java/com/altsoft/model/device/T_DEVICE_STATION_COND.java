package com.altsoft.model.device;

public class T_DEVICE_STATION_COND
{
    /// 기본코드(순번)
    public Integer STATION_CODE;
    /// 스테이션명
    public String STATION_NAME;
    /// 카테고리(T_COMMON 테이블 현재 미사용코드)
    public Integer CATEGORY_CODE;
    public Boolean HIDE;
    /// 정렬
    public String SORT;
    public Integer PAGE;
    public Integer PAGE_COUNT;
    public String PV_TYPE;
}