package com.altsoft.model.UserInfo;

import java.io.Serializable;
import java.util.Date;

public class T_MEMBER_BOOKMARK implements Serializable {
    /// 저장유형 D:일경우 삭제
    public String SAVE_MODE;
    /// 순번
    public int BOOKMARK_CODE;
    /// 회원코드(T_MEMBER테이블의 MEMBER_CODE)
    public int MEMBER_CODE;
    /// 회원명
    public String MEMBER_NAME;
    /// 사용자아이디
    public String USER_ID;
    /// 북마크 명칭( 기본 : 유형이 1일 경우 URL, 2일경우 로컬박스명, 3일경우 광고제목)
    public String BOOKMARK_NAME;
    /// 마크유형(A007) 1:웹페이지, 2:로컬박스 3:광고
    public Integer BOOKMARK_TYPE;
    /// 로컬박스코드(T_DEVICE 테이블 참조)
    public Long DEVICE_CODE;
    /// 로컬박스명
    public String DEVICE_NAME;
    /// 기기설치 주소
    public String DEVICE_ADDRESS;
    /// 광고코드(T_AD 테이블 참조)
    public Long AD_CODE;
    /// 광고제목
    public String TITLE;
    /// 북마크URL
    public String BOOKMARK_URL;
    /// 메모
    public String MEMO;
    /// 비고
    public String REMARK;
    /// 등록시간
    public Date INSERT_DATE;
    /// 배너상태 T_AD테이블의 STATUS T_COMMON(MAIN_CODE:A001)
    public Integer STATUS;
    /// 사용상태
    public String STATUS_NAME;
    /// 기기 또는 배너 회사명
    public String COMPANY_NAME;
    /// 배너공유건수
    public int AD_CNT;
    /// 기기공유 건수
    public int DEVICE_CNT;
    /// 기기인증번호
    public Long AUTH_NUMBER;
    /// 기기설명
    public String DEVICE_DESC;

    public String SUB_TITLE;
    /// 로고이미지
    public String LOGO_URL;
    public int TOTAL_ROWCOUNT;
}
