package com.altsoft.model;

import java.io.Serializable;

/// 클릭시 정보
public class MOBILE_AD_DETAIL_DATA implements Serializable {
    /// 배너아이디
    public Long AD_CODE;
    /// 제목
    public String TITLE;
    /// 로고 URL
    public String LOGO_URL;
    /// 광고상세 URL
    public String CONTENT_URL;
    /// 북마크유무
    public Boolean BOOKMARK_YN;
    /// 좋아요유무
    public Boolean FAVORITE_YN;
}
