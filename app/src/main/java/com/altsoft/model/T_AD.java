package com.altsoft.model;

import java.io.Serializable;
import java.math.BigInteger;

public class T_AD implements Serializable  {
    /// 광고코드
    public Long AD_CODE;
    /// 광고배너표시유형(T_COMMON : A005) (1:제목+이미지, 2:제목,3:이미지)
    public Integer BANNER_TYPE;
    /// 콘텐츠 유형(T_COMMON : A008, 1:HTML, 2:서브배너)
    public Integer CONTENT_TYPE;
    /// 광고제목
    public String TITLE;
    /// 광고부제목
    public String SUB_TITLE;
    /// 광고로고
    public String LOGO_URL;
    /// 광고 클릭 횟수
    public Integer CLICK_CNT ;
    /// 평가점수
    public String GRADE_POINT ;
    /// 광고요청한 회사 코드 T_COMPANY 테이블의 COMPANY_CODE
    public Integer COMPANY_CODE ;
    /// 요청한매장코드들 T_SOTRE 테이블의 STORE_CODE , 구분자 => | 값이 없으면 업체 전체 광고
    public Integer STORE_CODE ;
    /// 광고요청한 회사명 T_COMPANY테이블의 COMPANY_NAME
    public String COMPANY_NAME ;
    /// 요청자 코드 T_MEMBER테이블의 MEMBER_CODE
    public Integer MEMBER_CODE ;
    /// 요청자명 T_MEMBER테이블의 MEMBER_CODE
    public String MEMBER_NAME ;
    /// 대표카테고리코드
    public Integer REP_CATEGORY_CODE ;
    /// 대표카테고리명
    public Integer REP_CATEGORY_NAME ;
    /// 해당 광고가 있는 기기로 부터 거리
    public Double DEVICE_DISTANCE ;
    /// 광고로 부터 거리
    public Double AD_DISTANCE ;
    /// 자신의 위치로 부터 광고장소와 로컬박스위치와 비교하여 가까운곳 거리 리턴
    public Double DISTANCE ;
    /// 사이니지코드
    public Long SIGN_CODE ;
    /// 사이니지에 등록된 배너코드
    public Long SIGNAGE_AD_CODE ;
    /// 비콘 장치번호
    public String BEACON_DEVICE_NUMBER ;
    /// 비콘명
    public String BEACON_NAME ;
    public Boolean BOOKMARK_YN;
}

