package com.altsoft.Framework.kakao;

import android.content.Context;

import com.kakao.auth.ApprovalType;
import com.kakao.auth.AuthType;
import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;

public class KakaoSDKAdapter extends KakaoAdapter {

    @Override
    public ISessionConfig getSessionConfig() {
        return new ISessionConfig() {
            // 로그인 시에 인증 타입을 지정합니다.

            @Override

            public AuthType[] getAuthTypes() {
                // Auth Type
                // KAKAO_TALK  : 카카오톡 로그인 타입
                // KAKAO_STORY : 카카오스토리 로그인 타입
                // KAKAO_ACCOUNT : 웹뷰 다이얼로그를 통한 계정연결 타입
                // KAKAO_TALK_EXCLUDE_NATIVE_LOGIN : 카카오톡 로그인 타입과 함께 계정생성을 위한 버튼을 함께 제공
                // KAKAO_LOGIN_ALL : 모든 로그인 방식을 제공
                return new AuthType[] {AuthType.KAKAO_LOGIN_ALL};
            }

            @Override
            public boolean isUsingWebviewTimer() {
                return false;
            }

            @Override
            public boolean isSecureMode() {
                return false;
            }

            @Override
            public ApprovalType getApprovalType() {
                return ApprovalType.INDIVIDUAL;
            }

            @Override
            public boolean isSaveFormData() {
                return true;
            }
        };
    }


    @Override
    public IApplicationConfig getApplicationConfig() {
        return new IApplicationConfig() {
            @Override
            public Context getApplicationContext() {
                return GlobalApplication.getGlobalApplicationContext();
            }
        };
    }
}
