package com.altsoft.model.common;

import java.util.Date;

/**
* 파일관련 클래스(이미지,동영상 등등
* @author 전상훈
* @version 1.0.0
* @since 2019-03-07 오전 10:35
**/

public class T_FILE {

    public T_FILE(){}
    public T_FILE(String SAVE_TYPE, String TABLE_NAME, String TABLE_KEY, Integer FILE_SEQ, Integer FILE_TYPE, String FILE_NAME, String FILE_EXT, String FILE_URL)
    {
        this.init(SAVE_TYPE, TABLE_NAME, TABLE_KEY, FILE_SEQ, FILE_TYPE, FILE_NAME,FILE_EXT, FILE_URL);
    }
    public T_FILE(String SAVE_TYPE, String TABLE_NAME, String TABLE_KEY, Integer FILE_SEQ, Integer FILE_TYPE, String FILE_NAME, String FILE_EXT, String FILE_URL, String REF_DATA1, String REF_DATA2)
    {
        this.init(SAVE_TYPE, TABLE_NAME, TABLE_KEY, FILE_SEQ, FILE_TYPE, FILE_NAME,FILE_EXT, FILE_URL,  REF_DATA1, REF_DATA2, null, null, null);
    }
    public T_FILE(String SAVE_TYPE, String TABLE_NAME, String TABLE_KEY, Integer FILE_SEQ, Integer FILE_TYPE, String FILE_NAME, String FILE_EXT, String FILE_URL, String REF_DATA1, String REF_DATA2, String REMARK, Integer INSERT_CODE, Date INSERT_DATE) {
        this.init(SAVE_TYPE, TABLE_NAME, TABLE_KEY, FILE_SEQ, FILE_TYPE, FILE_NAME
                  ,FILE_EXT, FILE_URL, REF_DATA1, REF_DATA2, REMARK, INSERT_CODE, INSERT_DATE);
    }
    private void init(String SAVE_TYPE, String TABLE_NAME, String TABLE_KEY, Integer FILE_SEQ, Integer FILE_TYPE, String FILE_NAME, String FILE_EXT, String FILE_URL)
    {
        this.init(SAVE_TYPE, TABLE_NAME, TABLE_KEY, FILE_SEQ, FILE_TYPE, FILE_NAME,FILE_EXT, FILE_URL);
    }
    private void init(String SAVE_TYPE, String TABLE_NAME, String TABLE_KEY, Integer FILE_SEQ, Integer FILE_TYPE, String FILE_NAME, String FILE_EXT, String FILE_URL, String REF_DATA1, String REF_DATA2, String REMARK, Integer INSERT_CODE, Date INSERT_DATE) {
        this.SAVE_TYPE = SAVE_TYPE;
        this.TABLE_NAME = TABLE_NAME;
        this.TABLE_KEY = TABLE_KEY;
        this.FILE_SEQ = FILE_SEQ;
        this.FILE_TYPE = FILE_TYPE;
        this.FILE_NAME = FILE_NAME;
        this.FILE_EXT = FILE_EXT;
        this.FILE_URL = FILE_URL;
        this.REF_DATA1 = REF_DATA1;
        this.REF_DATA2 = REF_DATA2;
        this.REMARK = REMARK;
        this.INSERT_CODE = INSERT_CODE;
        this.INSERT_DATE = INSERT_DATE;
    }
    /**
    * 저장유형 N:추가 U:수정 D:삭제
    * @author 전상훈
    * @version 1.0.0
    * @since 2019-03-07 오전 10:33
    **/
    public String SAVE_TYPE;
    /// 테이블명
    public String TABLE_NAME;
    /// 테이블키(키가 멀티일 경우 |를 구분자로 사용함)
    public String TABLE_KEY;
    /// 파일순번
    public Integer FILE_SEQ;
    /// 파일유형(MAIN_CODE : F001) 1:이미지, 2:동영상, 3:유튜브, 4:HTML
    public Integer FILE_TYPE;
    /// 파일명
    public String FILE_NAME;
    /// 파일확장자
    public String FILE_EXT;
    /// 파일경로
    public String FILE_URL;
    /// 참조파일1
    public String REF_DATA1;
    /// 참조파일2

    public String REF_DATA2;
    /// 비고
    public String REMARK;

    /// 등록자

    public Integer INSERT_CODE;

    /// 등록일
    public Date INSERT_DATE;
}
