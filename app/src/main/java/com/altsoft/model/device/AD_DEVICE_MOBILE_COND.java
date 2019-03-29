package com.altsoft.model.device;

import java.io.Serializable;

public class AD_DEVICE_MOBILE_COND implements Serializable {
    /// 검색페이지 디폴트 1
    public Integer PAGE;
    /// 페이지당 데이터 건수 10000
    public Integer PAGE_COUNT;
    public long DEVICE_CODE;
    public String USER_ID;
    /// 대표카테고리
    public Integer REP_CATEGORY_CODE;
    /// T_CATEGORY 테이블의 CATEGORY_TYPE 카테고리유형 T_COMMON 테이블의 B004 코드 사용, 1:광고 2:지역 3:내배너
    public Integer CATEGORY_TYPE;
    /// 배너코드
    public Long AD_CODE;
    /// 키워드명 >> 값이 있으면 조건에추가 LIKE '%검색조건%'
    public String KEYWORD_NAME;
    /// 카테고리와 키워드 연결 테이블의 기본키
    public Integer CK_CODE;
    /// 0:보임 1:숨김 2:모두 디폴트는 보임(0)
    public Integer HIDE;
}
