package com.altsoft.model.device;

import java.io.Serializable;


public class AD_DEVICE_MOBILE_LIST  implements Serializable {
    /// 로컬박스코드
    public long DEVICE_CODE;
    /// 로컬박스명
    public String DEVICE_NAME ;
    /// 조회순번
    public long IDX ;
    /// 배너코드
    public long AD_CODE ;
    /// 광고배너표시유형(T_COMMON : A005) (1:제목+이미지, 2:제목,3:이미지)
    public Integer BANNER_TYPE ;
    /// 광고 콘텐츠 유형(T_COMMON : A008, 1:HTML, 2:서브배너)
    public Integer CONTENT_TYPE ;
    /// 회사코드(T_COMPANY)
    public int COMPANY_CODE ;
    /// 회사명(T_COMPANY)
    public String COMPANY_NAME ;
    /// 로컬박스 회사코드(T_COMPANY)
    public int DEVICE_COMPANY_CODE ;
    /// 로컬박스 회사명(T_COMPANY)
    public String DEVICE_COMPANY_NAME ;
    /// 지점코드(T_STORE)
    public int STORE_CODE ;
    /// 지점명
    public String STORE_NAME ;
    /// 제목
    public String TITLE ;
    /// 부제목
    public String SUB_TITLE ;
    /// 로고 URL
    public String LOGO_URL ;
    /// 클릭수
    public Integer CLICK_CNT ;
    /// 별포인트 5점 만점 평점으로 계산
    public Integer GRADE_POINT ;
    /// 요청한사용자코드 T_MEMBER 테이블의 MEMBER_CODE
    public int MEMBER_CODE ;
    /// 요청한사용자명
    public String MEMBER_NAME ;
    /// 대표카테고리 코드
    public int CATEGORY_CODE ;
    /// 대표카테고리명
    public String CATEGORY_NAME ;
    /// 디바이스 북마크 여부
    public Boolean BOOKMARK_YN ;
    /// 공유승인상태(T_COMMON : A009) 0:미승인, 8:반려, 9:승인
    public Integer SHARE_STATUS ;
    public Boolean BANNER_BOOKMARK_YN;
    public Boolean BANNER_FAVORITE_YN;
}
