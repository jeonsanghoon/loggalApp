package com.altsoft.model.device;

import java.util.Date;

/// <summary>
/// 박스 스테이션(위치가 없는 기기의 묶음 - 관리자가 생성가능)(T_DEVICE_STATION)
/// </summary>	   
public class T_DEVICE_STATION
{
    /// 저장 유형 N:신규 U:수정 D:삭제
    public String SAVE_TYPE;
    /// 조회 순번
    public long IDX;
    /// 기본코드(순번)
    public Integer STATION_CODE;
    /// 스테이션명
    public String STATION_NAME;
    /// 카테고리(T_COMMON 테이블 현재 미사용코드)
    public Integer CATEGORY_CODE;
    /// 주소
    public String ADDRESS1;
    /// 상세주소
    public String ADDRESS2;
    /// 우편번호
    public String ZIP_CODE;
    /// 위도
    public Double LATITUDE;
    /// 경도
    public Double LONGITUDE ;
    /// 스테이션설명
    public String STATION_DESC;
    /// 비고
    public String REAMRK;
    /// 숨김여부 1: 숨김 0 : 보임
    public Boolean HIDE;
    /// 등록자
    public Integer INSERT_CODE;
    /// 등록자
    public String INSERT_NAME;
    /// 등록일
    public Date INSERT_DATE;
    /// 수정자
    public Integer UPDATE_CODE;
    /// 수정자
    public String UPDATE_NAME;
    /// 수정일
    public Date UPDATE_DATE;
    /// 스테이션당 기기수
    public Integer DEVICE_CNT;
    /// 최근 일주일간 디바이스 등록건수
    public Integer NEW_DEVICE_CNT;
    public Integer TOTAL_ROWCOUNT;

    public Boolean BOOKMARK_YN = false;

    public String LOGO_URL;
}
