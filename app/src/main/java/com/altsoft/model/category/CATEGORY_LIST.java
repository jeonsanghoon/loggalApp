package com.altsoft.model.category;


import java.io.Serializable;

public class CATEGORY_LIST implements Serializable {
    /// 카테고리코드
    public Integer CATEGORY_CODE;
    /// 카테고리유형 T_COMMON 테이블의 B004 코드 사용, 1:광고 2:지역
    public int CATEGORY_TYPE;
    /// 상위코드
    public Integer PARENT_CATEGORY_CODE;
    /// 검색을 위한 키조함  최상위코드부터 하위 코드 순으로 |로구분 예) 1|12|100
    public String SEARCH_CATEGORY_CODE;
    /// 카테고리명
    public String CATEGORY_NAME;
}

