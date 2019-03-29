package com.altsoft.model;

import java.io.Serializable;
import java.math.BigInteger;

/// <summary>
/// 모바일배너상세 조건
/// </summary>
public class MOBILE_AD_DETAIL_COND implements Serializable {
    /// 배너코드
    public Long AD_CODE;
    /// 사용자아이디(이메일)
    public String USER_ID;
}
