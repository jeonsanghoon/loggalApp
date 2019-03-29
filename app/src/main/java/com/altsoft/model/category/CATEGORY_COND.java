package com.altsoft.model.category;

import java.io.Serializable;

public class CATEGORY_COND implements Serializable {
    /// 카테고리 일련번호 => 값이 있으면 equal 검색
    public Integer CATEGORY_CODE;
    /// 카테고리유형 T_COMMON 테이블의 B004 코드 사용, 1:광고 2:지역=> 값이 있으면 equal 검색
    public Integer CATEGORY_TYPE;
    public String CATEGORY_TYPES;
    /// 상위카테고리코드 => 값이 있으면 equal 검색
    public Integer PARENT_CATEGORY_CODE;
    /// 레벨깊이 => 값이 있으면 equal 검색
    public Integer LEVEL_DEPTH;
    /// 검색을 위한 키조함  최상위코드부터 하위 코드 순으로 |로구분 예) 1|12|100 => 값이 있으면 LIKE 뒤에 % 검색 예) SEARCH_CATEGORY_CODE LIKE '1|12|%'
    public String SEARCH_CATEGORY_CODE;
    /// 카테고리명 => 값이 있으면 앞뒤 LIKE 검색 예) CATEGORY_NAME LIKE N'%레스토%'
    public String CATEGORY_NAME;
    /// 숨김 처리여부 false(0:보임), true(1:숨김)
    public Boolean HIDE;
}
