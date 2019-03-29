package com.altsoft.model.keyword;

import java.io.Serializable;

/// 코드클래스
public class CODE_DATA implements Serializable
{

    /// 코드(숫자형)
    public Integer CODE;
    /// T_KEYWORD 테이블의 KEYWORD_TYPE B006 1:지역 2:일반 3:기기
    public String CODE_TYPE;
    /// 조회용 코드(예-SEARCH_CATEGORY_CODE)
    public String SEARCH_CODE;
    /// 코드명
    public String NAME;
    /// 위도
    public Integer LATITUDE;
    /// 경도
    public Integer LONGITUDE;
    /// T_CATEGORY_KEYWORD 테이블의 CK_CODE
    public Integer CK_CODE;
}

