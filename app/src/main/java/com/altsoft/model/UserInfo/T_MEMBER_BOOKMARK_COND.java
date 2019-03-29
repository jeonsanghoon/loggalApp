package com.altsoft.model.UserInfo;

import java.io.Serializable;

public class T_MEMBER_BOOKMARK_COND implements Serializable {
    /// 순번
    public Integer BOOKMARK_CODE;
    /// 회원코드(T_MEMBER테이블의 MEMBER_CODE)
    public Integer MEMBER_CODE;
    /// 북마크유형 (1:웹페이지 2: 로컬박스) T_COMON 테이블의 MAIN_CODE : A007
    public Integer BOOKMARK_TYPE;
    /// 사용자아이디
    public String USER_ID;
    /// 등록자 이름
    public String USER_NAME;
    /// 북마크URL
    public String BOOKMARK_URL;
    /// 페이지번보
    public Integer PAGE;
    /// 페이지당 갯수
    public Integer PAGE_COUNT;
    /// 광고제목
    public String TITLE;
    /// 디바이스명
    public String DEVICE_NAME;
    /// 정렬순서
    public String SORT_ORDER;
}
