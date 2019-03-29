package com.altsoft.model.UserInfo;

import java.io.Serializable;
import java.util.Date;

public class T_MEMBER implements Serializable {
    /// N:추가 U:수정 D:삭제
    public String SAVE_MODE;
    /// 순번(일련번호)
    public Integer MEMBER_CODE;
    /// T_COMMON 테이블의 MAIN_CODE:U001 사용
    public Integer USER_TYPE;
    /// 사용자아이디(E-Mail)
    public String USER_ID;

    /// 암호(SHA1으로 암호화)
    public String PASSWORD;
    /// 사용자명
    public String USER_NAME;
    /// 이메일
    public String EMAIL;
    /// 일반전화
    public String PHONE;
    /// 모바일번호
    public String MOBILE;
    /// 기본주소
    public String ADDRESS1;
    /// 상세주소
    public String ADDRESS2;
    /// 우편번호
    public String ZIP_CODE;
    /// 생년월일(yyyyMMdd)
    public String BIRTH;
    /// 성별 : T_COMMON 테이블의 MAIN_CODE : H001(1:남, 2:여) 사용
    public Integer GENDER;
    /// 비밀번호 변경시 URL, 요청시 EMAIL + MEMBER_CODE + USER_NAME를 SHA1으로 암호화하여 저장, 변경후 빈값으로 초기화
    public String PASSWORD_CHANGE_URL;
    /// 비밀번호 변경시 URL, 요청시 EMAIL + MEMBER_CODE + USER_NAME를 SHA1으로 암호화하여 저장, 변경후 빈값으로 초기화
    public Date PASSWORD_AUTH_TIME;
    /// 공유인증번호 기본:00
    public String SHARE_AUTH_NUMBER ="00";
    /// 비고
    public String REMARK;
    public String KAKAO_ID;
    public String GOOGLE_ID;
    public String NAVER_ID;
    public String FACEBOOK_ID;
    /// 숨김여부(1:숨김 0:보임)
    public Boolean HIDE;
    /// 등록자(T_MEMBER의 MEMBER_CODE)
    public int INSERT_CODE;
    /// 등록일
    public Date INSERT_DATE;
    /// 수정자(T_MEMBER의 MEMBER_CODE)
    public int UPDATE_CODE;
    /// 수정자
    public String UPDATE_NAME;
    /// 수정일
    public Date UPDATE_DATE;
    public String profileImagePath;
    public String thumnailPath;
}
