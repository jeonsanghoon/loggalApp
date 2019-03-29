package com.altsoft.model;

public class MAIL_INFO {
    public String SMTP_ADDRESS;
    public String SMTP_ID;
    public String SMTP_PW;
    public String SMTP_PORT;
    public String SMTP_SPORT;
    public Boolean ENABLE_SSL;
    public Boolean IS_BODY_HTML;
    public String SENDER_ID;
    public String SENDER_NAME;
    /// 받는 사람 이메일 : ex) abc@careercare.co.kr;def@careercare.co.kr
    public String RECEIVE_ID;
    /// 받는 사람 메일 유형 0:받는사람에 모두추가/ 1: 각각으로 메일을 보냄
    public String ACCEPT_TYPE;
    /// 참조할 사람 이메일 : ex) abc@careercare.co.kr;def@careercare.co.kr
    public String CC_ID;
    /// 메일 제목
    public String SUBJECT;
    /// 메일 내용
    public String CONTENT;
}
