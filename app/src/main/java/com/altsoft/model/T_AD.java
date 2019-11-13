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
    /// 배너가상영역유형(M003) 1000:1km 1000:10km 1000:100km
    public Integer ITEM_TYPE ;
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
    /// 광고로 부터 거리
    public Double AD_DISTANCE ;

    /// 사이니지코드
    public Long SIGN_CODE ;
    /// 북마크 유무
    public Boolean BOOKMARK_YN;
    /// 좋아요 유무
    public Boolean FAVORITE_YN;
}

