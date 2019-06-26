package com.altsoft.model.device;

import java.io.Serializable;
import java.util.List;

/// 모바일에 로컬박스 클릭시 가져오는 데이터 오브젝트
public class AD_DEVICE_MOBILE_M implements Serializable {
    /// 로컬박스코드
    public long DEVICE_CODE ;
    /// 로컬박스명
    public String DEVICE_NAME ;
    /// 로컬박스 회사코드
    public int DEVICE_COMPANY_CODE ;
    /// 로컬박스 회사명
    public String DEVICE_COMPANY_NAME ;
    /// 북마크여부
    public boolean BOOKMARK_YN ;
    /// 좋아요여부
    public boolean FAVORITE_YN ;
    /// 로컬박스에 내배너 리스트
    public List<AD_DEVICE_MOBILE_LIST> AD_LIST ;
}
