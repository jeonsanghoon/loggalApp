package com.altsoft.loggalapp.Fragement;


import android.content.Intent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.altsoft.Framework.Global;
import com.altsoft.Framework.enResult;
import com.altsoft.Framework.module.BaseFragment;
import com.altsoft.loggalapp.LoginActivity;
import com.altsoft.loggalapp.MainActivity;
import com.altsoft.loggalapp.R;
import com.altsoft.loggalapp.UserInfo.MyBannerBookMarkList;

import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.LogoutResponseCallback;
import com.ss.bottomnavigation.BottomNavigation;


public class TabFragment_Myinfo extends BaseFragment {
    TextView tvUserId;
    TextView tvUserName;
    LinearLayout layLogined;
    Button btnLogin;
    Button btnImgPic;
    public View view;
    ImageView img_profile;
    public TabFragment_Myinfo(){

    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tab_myinfo, container, false);
        tvUserId = ((TextView) view.findViewById(R.id.tvUserId));
        tvUserName = ((TextView) view.findViewById(R.id.tvUserName));
        btnLogin = view.findViewById(R.id.btnLogin);
        final Button btnLogout = view.findViewById(R.id.btnLogout);
        layLogined = view.findViewById(R.id.layLogined);
        img_profile = view.findViewById(R.id.img_profile);
        btnImgPic =view.findViewById(R.id.btnImgPic);
        if (Global.getLoginInfo().isLogin()) {
            btnLogin.setVisibility(View.GONE);
            //  btnLogout.setVisibility(View.VISIBLE);
            layLogined.setVisibility(View.VISIBLE);
            btnImgPic.setVisibility(View.VISIBLE);
            tvUserId.setText(Global.getLoginInfo().getData().USER_ID);
            tvUserName.setText(Global.getLoginInfo().getData().USER_NAME);

            Global.getEditInfo().SetCircleImage(img_profile, Global.getLoginInfo().getData().thumnailPath);
        } else {
            Global.getEditInfo().SetCircleImage(img_profile,null);
            btnLogin.setVisibility(View.VISIBLE);
            layLogined.setVisibility(View.GONE);
            btnImgPic.setVisibility(View.GONE);
        }

        btnImgPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Global.getFileInfo().ImageProfilePic();
            /*    Intent i = new Intent(
                        Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                getActivity().startActivityForResult(i, enResult.ImagePic.getValue());*/
            }
        });


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Global.getCurrentActivity(), LoginActivity.class);
                Global.getCurrentActivity().startActivityForResult(intent, enResult.LoginRequest.getValue());
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserManagement.getInstance().requestLogout(new LogoutResponseCallback() {
                    @Override
                    public void onCompleteLogout() {
                        Log.d("로그아웃", "로그아웃되었음");
                    }
                });
                BottomNavigation bottomNavigation = (BottomNavigation) MainActivity.activity.findViewById(R.id.bottom_navigation);
                tvUserId.setText("");
                tvUserName.setText("");
                bottomNavigation.getTabItems().get(3).setText("내정보");
                ImageView img_profile = view.findViewById(R.id.img_profile);
                img_profile.setVisibility(View.GONE);
                Global.getEditInfo().SetCircleImage(img_profile, null);
                Global.getLoginInfo().setData(null);
                Toast.makeText(
                        Global.getCurrentActivity(),
                        "로그아웃되었습니다.",
                        Toast.LENGTH_LONG).show();
                btnLogin.setVisibility(View.VISIBLE);

                view.findViewById(R.id.layLogined).setVisibility(View.GONE);
                //view.findViewById(R.id.layLogined).setVisibility(View.GONE);
                ((MainActivity)getActivity()).afterLoginExec();

            }
        });

        view.findViewById(R.id.btnMyBookmarkList).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Global.getCurrentActivity(), MyBannerBookMarkList.class);
                Global.getCurrentActivity().startActivityForResult(intent, enResult.BannerRequest.getValue());
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == enResult.LoginRequest.getValue()) {
            if (resultCode == -1) {

                return;
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (btnLogin != null) {
            if (Global.getLoginInfo().isLogin()) {
                btnLogin.setVisibility(View.GONE);
                //  btnLogout.setVisibility(View.VISIBLE);
                layLogined.setVisibility(View.VISIBLE);



            } else {
                Global.getEditInfo().SetCircleImage(img_profile, "");
                btnLogin.setVisibility(View.VISIBLE);
                layLogined.setVisibility(View.GONE);
            }
        }
      //  if(view != null) view.findViewById(R.id.btnImgLoad).performClick();
    }
}
