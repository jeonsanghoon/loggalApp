package com.altsoft.model.search;

import java.io.Serializable;
import java.math.BigDecimal;

public class MOBILE_AD_SEARCH_DATA implements Serializable {
    /// 순번
    public long IDX;
    /// 광고코드
    public long AD_CODE;
    /// 제목
    public String TITLE;
    /// 부제목
    public String SUB_TITLE;
    /// 회사명
    public String COMPANY_NAME;
    /// 로고이미지 URL
    public String LOGO_URL;
    public int BANNER_TYPE;
    /// 검색위치로 부터 거리
    public BigDecimal DISTANCE;
    // 실제배너구분 1:일반배너 2:로컬박스의 배너
    public int REAL_AD_TYPE;
    /// 상세페이지 URL
    public String CONTENT_URL;
    public Boolean BOOKMARK_YN;
}
