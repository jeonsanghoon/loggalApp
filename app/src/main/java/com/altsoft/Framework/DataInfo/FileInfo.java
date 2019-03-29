package com.altsoft.Framework.DataInfo;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.altsoft.Framework.Global;
import com.altsoft.Interface.AsyncCallbackOnEventListener;
import com.altsoft.Interface.ServiceInfo;
import com.altsoft.asynctask.FtpFileUploadTask;
import com.altsoft.loggalapp.MainActivity;
import com.altsoft.loggalapp.R;
import com.altsoft.model.RTN_SAVE_DATA;
import com.altsoft.model.common.T_FILE;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.developers.imagezipper.ImageZipper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;


import gun0912.tedbottompicker.TedBottomPicker;

import retrofit2.Call;

public class FileInfo {
    ///파일명과 확장자 분리
    public String[] getFileAndExtension(String FilePath) {
        String[] arrStr = new String[2];

        File f = new File(FilePath);
        arrStr[0] = f.getName();
        arrStr[1] =getFileExtension(arrStr[0]);
        return arrStr;
    }

    /// 파일확장자가져오기
    public String getFileExtension(String FileName) {
        int pos = FileName.lastIndexOf( "." );
        return FileName.substring( pos + 1 );
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Context inContext,Uri uri) {
        Cursor cursor = inContext.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public String getImagePathFromInputStreamUri(Context context, Uri uri) {
        InputStream inputStream = null;
        String filePath = null;

        if (uri.getAuthority() != null) {
            try {
                inputStream = context.getContentResolver().openInputStream(uri); // context needed
                File photoFile = createTemporalFileFrom(context,inputStream);

                filePath = photoFile.getAbsolutePath();

            } catch (FileNotFoundException e) {
                // log
            } catch (IOException e) {
                // log
            }finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return filePath;
    }

    public  int getOrientation(Context context, Uri photoUri) {
        /* it's on the external media. */
        Cursor cursor = context.getContentResolver().query(photoUri,
                new String[] { MediaStore.Images.ImageColumns.ORIENTATION }, null, null, null);

        if (cursor.getCount() != 1) {
            return -1;
        }

        cursor.moveToFirst();
        return cursor.getInt(0);
    }

    private  final int ROTATION_DEGREES = 90;
    // This means 512 px
    private  final Integer MAX_IMAGE_DIMENSION = 512;
    public  Bitmap getCorrectlyOrientedImage(Context context, Uri photoUri) throws IOException {
        InputStream is = context.getContentResolver().openInputStream(photoUri);
        BitmapFactory.Options dbo = new BitmapFactory.Options();
        dbo.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(is, null, dbo);
        is.close();

        int rotatedWidth, rotatedHeight;
        int orientation = getOrientation(context, photoUri);

        if (orientation == 90 || orientation == 270) {
            rotatedWidth = dbo.outHeight;
            rotatedHeight = dbo.outWidth;
        } else {
            rotatedWidth = dbo.outWidth;
            rotatedHeight = dbo.outHeight;
        }

        Bitmap srcBitmap;
        is = context.getContentResolver().openInputStream(photoUri);
        if (rotatedWidth > MAX_IMAGE_DIMENSION || rotatedHeight > MAX_IMAGE_DIMENSION) {
            float widthRatio = ((float) rotatedWidth) / ((float) MAX_IMAGE_DIMENSION);
            float heightRatio = ((float) rotatedHeight) / ((float) MAX_IMAGE_DIMENSION);
            float maxRatio = Math.max(widthRatio, heightRatio);

            // Create the bitmap from file
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = (int) maxRatio;
            srcBitmap = BitmapFactory.decodeStream(is, null, options);
        } else {
            srcBitmap = BitmapFactory.decodeStream(is);
        }
        is.close();

        // if the orientation is not 0, we have to do a rotation.
        if (orientation > 0) {
            Matrix matrix = new Matrix();
            matrix.postRotate(orientation);

            srcBitmap = Bitmap.createBitmap(srcBitmap, 0, 0, srcBitmap.getWidth(),
                    srcBitmap.getHeight(), matrix, true);
        }

        return srcBitmap;
    }
    private File createTemporalFileFrom(Context context, InputStream inputStream) throws IOException {
        File targetFile = null;

        if (inputStream != null) {
            int read;
            byte[] buffer = new byte[8 * 1024];

            targetFile = createTemporalFile(context);
            OutputStream outputStream = new FileOutputStream(targetFile);

            while ((read = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, read);
            }
            outputStream.flush();

            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return targetFile;
    }

    public  Bitmap decodeUri( Uri uri, final int requiredSize)
            throws FileNotFoundException {
        Context c = Global.getCurrentActivity();
        BitmapFactory.Options o = new BitmapFactory.Options();

        o.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o);

        int width_tmp = o.outWidth
                , height_tmp = o.outHeight;
        int scale = 1;

        while(true) {
            if(width_tmp / 2 < requiredSize || height_tmp / 2 < requiredSize)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }

        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;

        return  Bitmap.createScaledBitmap(BitmapFactory.decodeStream(c.getContentResolver().openInputStream(uri), null, o2), width_tmp, height_tmp, true);
    }

    private File createTemporalFile(Context context) {
        return new File(context.getExternalCacheDir(), "tempFile.jpg"); // context needed
    }
    public void ImageProfilePic()
    {
        if(Global.getAuthInfo().grantExternalStoragePermission() && Global.getAuthInfo().grantCameraPermission()) {

            TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(Global.getCurrentActivity())
                    .showTitle(false)
                    .showGalleryTile(false)
                    .setPreviewMaxCount(300)
                    .setImageProvider(new TedBottomPicker.ImageProvider() {
                        @Override
                        public void onProvideImage(ImageView imageView, Uri imageUri) {

                            RequestOptions options = new RequestOptions().centerCrop();
                            options.diskCacheStrategy(DiskCacheStrategy.NONE);
                            options.skipMemoryCache(true);
                            Glide.with(Global.getCurrentActivity()).load(imageUri).apply(options).into(imageView);
                            Log.d("Log", "Uri Log : " + imageUri.toString());

                        }
                    })

                    .setOnImageSelectedListener(new TedBottomPicker.OnImageSelectedListener() {



                        @SuppressLint("CheckResult")
                        @Override
                        public void onImageSelected(Uri uri) {
                            if(uri == null) return;

                            File file =new File(uri.getPath());
                            try {
                                File imageZipperFile=new ImageZipper(Global.getCurrentActivity())
                                        .setQuality(100)
                                        .setMaxWidth(400)
                                        .setMaxHeight(400)
                                        .compressToFile(file);
                                 uri = Uri.fromFile(imageZipperFile);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            final String realUrl = Global.getCommon().getRealPath(Global.getCurrentActivity(),uri);
                            final String filename=realUrl.substring(realUrl.lastIndexOf("/")+1);
                            final String extension = filename.substring(filename.lastIndexOf("."));

                            final String dir = new SimpleDateFormat("yyyyMM").format(new Date());
                            final String newFileName =  new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + extension;


                            /**
                             * 파일업로드
                             * @author 전상훈
                             * @version 1.0.0
                             * @since 2019-03-06 오후 6:12
                             **/
                            Global.getCommon().ProgressShow();
                            new FtpFileUploadTask(new AsyncCallbackOnEventListener<String>() {
                                @Override
                                public void onSuccess(String result) {
                                    //Toast.makeText(getApplicationContext(), editMail.getText().toString() + "로 임시 비밀번호가 방송되었습니다.", Toast.LENGTH_LONG).show();
                                    final String linkurl =  Global.getResourceInfo().getFileHost() +  dir + "/" + newFileName;
                                    T_FILE file = new T_FILE("U", Global.getSecurityInfo().EncryptAes("T_MEMBER"),  Global.getSecurityInfo().EncryptAes(Integer.toString(Global.getLoginInfo().MEMBER_CODE))
                                            , 1, 1, filename, extension, Global.getSecurityInfo().EncryptAes(linkurl),"1","회원썸네일" );
                                    Call<RTN_SAVE_DATA> call = Global.getAPIService().FileSave(file);
                                    Global.getCallService().callService(call
                                            , new ServiceInfo.Act<RTN_SAVE_DATA>() {
                                                @Override
                                                public void execute(RTN_SAVE_DATA rtn) {
                                                    if(Global.getValidityCheck().isEmpty(rtn.ERROR_MESSAGE)) {
                                                        Bitmap img = Global.getCommon().getBitmapRotate(realUrl);
                                                        // 이미지 표시
                                                        //Global.getEditInfo().SetCircleImageBmp( (ImageView)findViewById(R.id.img_profile), img);
                                                        Global.getEditInfo().SetCircleImage((ImageView)Global.getCurrentActivity().findViewById(R.id.img_profile),realUrl);
                                                        Global.getLoginInfo().setThumnailPath(linkurl);
                                                    }
                                                    else{
                                                        Toast.makeText(
                                                                Global.getCurrentActivity(),
                                                                rtn.ERROR_MESSAGE,
                                                                Toast.LENGTH_LONG).show();
                                                    }
                                                }
                                            },
                                            new ServiceInfo.Act<Throwable>() {
                                                @Override
                                                public void execute(Throwable data) {
                                                    //TODO: Do something!
                                                }
                                            }
                                    );
                                    Global.getCommon().ProgressHide();
                                }

                                @Override
                                public void onFailure(Exception e) {
                                    Toast.makeText(Global.getCurrentActivity(), "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    Global.getCommon().ProgressHide();
                                }
                            }).execute(realUrl,newFileName,dir);;


                        }
                    }) /*.setOnMultiImageSelectedListener(new TedBottomPicker.OnMultiImageSelectedListener() {
                        @Override
                        public void onImagesSelected(ArrayList<Uri> uriList) {


                        }
                    })*/.setOnErrorListener(new TedBottomPicker.OnErrorListener(){
                        @Override
                        public void onError(String message){

                        }
                    })
                    .create();


            tedBottomPicker.show(Global.getFragmentManager());

        }
        else{
            Toast.makeText(
                    Global.getCurrentActivity(),
                    "스토리지권한이 없습니다.",
                    Toast.LENGTH_LONG).show();
        }
    }

}
