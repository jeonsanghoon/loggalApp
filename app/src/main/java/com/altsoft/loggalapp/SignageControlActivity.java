package com.altsoft.loggalapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.VideoView;

import com.altsoft.Framework.module.BaseActivity;
import com.altsoft.Framework.Global;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.util.Calendar;
import java.util.TimeZone;

public class SignageControlActivity extends BaseActivity {

    private EditText txtDate;private EditText txtTime;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static final int IMAGE_CAPTURE = 1;
    private static final int IMAGE_LOAD =2;
    private static final int VIDEO_CAPTURE =3;
    ImageView imageView;
    VideoView videoView;
    Uri videoUri;
    String filePath;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signage_control);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) findViewById(R.id.imgPreView);
        videoView = (VideoView) findViewById(R.id.videoPreView);

        setTitle("사이니지제어");
        txtDate =(EditText)findViewById(R.id.txtDate);
        txtTime =(EditText)findViewById(R.id.txtTime);
        txtDate.setFocusable(false);
        txtTime.setFocusable(false);
        isImgPreViewShow();

    }

    /// 카메라 클릭시 이벤트
    public void btnCarema_onClick(View v){
        Toast.makeText(this,"카메라가 클릭되었습니다.", Toast.LENGTH_LONG).show();
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, IMAGE_CAPTURE);
        }
    }

    /// 비디오 클릭시 이벤트
    public void btnVideo_onClick(View v){
        Toast.makeText(this,"비디오가 클릭되었습니다.", Toast.LENGTH_LONG).show();
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FRONT)) {
            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
            filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/"  + Global.getCommon().getCurrentTimeString() + ".mp4";
            File mediaFile = new File( filePath);
            videoUri = Uri.fromFile(mediaFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, videoUri);

            startActivityForResult(intent, VIDEO_CAPTURE);
        } else {
            Toast.makeText(this, "No camera on device", Toast.LENGTH_LONG).show();
        }
    }

    /// 겔러리 클릭시 이벤트
    public void btnGallery_onClick(View v){
        Toast.makeText(this,"갤러리가 클릭되었습니다.", Toast.LENGTH_LONG).show();
        Intent i = new Intent(
                Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        startActivityForResult(i, IMAGE_LOAD);


    }

    /// 버튼선택후 결과 이벤트
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView imageView = (ImageView) findViewById(R.id.imgPreView);
        VideoView videoView = (VideoView) findViewById(R.id.videoPreView);
        Bitmap bm;
        isImgPreViewShow(true);
        if (requestCode == IMAGE_CAPTURE && resultCode == RESULT_OK) {
            /*Bundle extras = data.getExtras();
            bm = (Bitmap) extras.get("data");

            imageView.setImageBitmap(bm); */

            Uri imageUri = data.getData();

            filePath = Global.getFileInfo().getRealPathFromURI(this, imageUri);
            if(filePath == null) {
                filePath = Global.getFileInfo().getImagePathFromInputStreamUri(this, imageUri);
            }
            Glide.with(this)
                    .load(imageUri)
                    .apply(new RequestOptions().override(100, 100))
                    .into(imageView)
            ;
        }
        else if (requestCode == IMAGE_LOAD && resultCode == RESULT_OK && null != data) {
            // Let's read picked image data - its URI
            Uri imageUri = data.getData();

            filePath = Global.getFileInfo().getRealPathFromURI(this, imageUri);
            if(filePath == null) {
                filePath = Global.getFileInfo().getImagePathFromInputStreamUri(this, imageUri);
            }
            Glide.with(this)
                    .load(imageUri)
                    .apply(new RequestOptions().override(100, 100))
                    .into(imageView)
            ;
      }
      else if (requestCode ==  VIDEO_CAPTURE && resultCode == RESULT_OK) {
            MediaController vidControl = new MediaController(this);
            vidControl.setAnchorView(videoView);
            videoView.setMediaController(vidControl);
            videoView.setVideoURI(videoUri);
            videoView.start();

            isImgPreViewShow(false);
        }
    }
    //// 업로드 유형에 따른 미리보기 숨김
    private void isImgPreViewShow()
    {
        isImgPreViewShow(null);
    }
    //// 업로드 유형에 따른 미리보기 숨김
    private void isImgPreViewShow(Boolean isShow) {

        LinearLayout layVideo = (LinearLayout)findViewById(R.id.layVideo);
        LinearLayout layImage = (LinearLayout)findViewById(R.id.layImage);
        if(isShow == null)
        {
            layImage.setVisibility(View.INVISIBLE);
            layVideo.setVisibility(View.INVISIBLE);
            videoView.getLayoutParams().height = 0;
            imageView.getLayoutParams().height = 0;
        }
        else if(isShow == true)
        {
            layImage.setVisibility(View.VISIBLE);
            layVideo.setVisibility(View.INVISIBLE);
            imageView.getLayoutParams().height = 400;
            videoView.getLayoutParams().height = 0;
        }
        else{
            layImage.setVisibility(View.INVISIBLE);
            layVideo.setVisibility(View.VISIBLE);
            imageView.getLayoutParams().height = 0;
            videoView.getLayoutParams().height = 400;
        }
    }

    /// 날짜 선택 클릭시 이벤트
    public void txtDate_onClick(View v){
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        final Calendar c = Calendar.getInstance();
        try {
            if (Global.getCommon().isValidDate(txtDate.getText().toString()) == true) {
                String sDate = txtDate.getText().toString();
                String[] arrDate = sDate.split("\\.");
                mYear = Integer.parseInt(arrDate[0]);
                mMonth =Integer.parseInt( arrDate[1])-1;
                mDay = Integer.parseInt(arrDate[2]);
            }
            else{
                throw new Exception("날짜포맷이 아닙니다.");
            }
        }catch(Exception ex) {
            mYear = c.get(Calendar.YEAR);
            mMonth = c.get(Calendar.MONTH);
            mDay = c.get(Calendar.DAY_OF_MONTH);
        }

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String month = Global.getCommon().leftPad(Integer.toString(monthOfYear + 1), 2, '0');
                        String day = Global.getCommon().leftPad(Integer.toString(dayOfMonth + 1), 2, '0');

                        txtDate.setText(year + "." + month + "." + day);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    /// 시간선택 클릭시 이벤트
    public void txtTime_onClick(View view) {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        try {
            if (Global.getCommon().isValidTime(txtTime.getText().toString()) == true)  {

                String sTime = txtTime.getText().toString();
                String[] arrTime = sTime.split(":");

                mHour = Integer.parseInt(arrTime[0]);
                mMinute =Integer.parseInt(arrTime[1]);


            }
            else {
                throw new Exception("시간포맷이 아닙니다.");
            }
        }catch(Exception ex) {
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
        }

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        String hour = Global.getCommon().leftPad(Integer.toString(hourOfDay), 2, '0');
                        String min = Global.getCommon().leftPad(Integer.toString(minute), 2, '0');
                        txtTime.setText(hour + ":" + min);
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
    }

    /// 저장버튼클릭시  이벤트
    public void btnSave_onClick(View v) {

       // FtpFIleUploadTask upload = new FtpFIleUploadTask(this);
       // upload.execute(filePath);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
