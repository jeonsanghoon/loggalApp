package com.altsoft.Framework.Editor;

import android.graphics.Bitmap;
import android.media.ExifInterface;
import android.util.Log;

import com.altsoft.Framework.Global;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;


public class EditInfo {

    int _radius = 35;
    public void SetCircleImage(android.widget.ImageView view, String src) {
        SetCircleImage(view,  src,  _radius);
    }
    public void SetCircleImage(android.widget.ImageView view, String src,  int radius) {

        if(Global.getValidityCheck().isEmpty(src)) src = "https://altsoft.ze.am/files/common/noimage.gif";
        RequestOptions requestOptions  =  new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .override(100, 100)
               // .circleCropTransform();
                .transform(new MultiTransformation(new CenterCrop(), new CustomRoundedCornersTransformation(Global.getCurrentActivity(), (int)(radius), 0, CustomRoundedCornersTransformation.CornerType.ALL)));
        Glide.with(Global.getCurrentActivity())
                .load(src)
                .apply(requestOptions)
                // .apply(RequestOptions.circleCropTransform())
                .into(view);
    }


    public void SetCircleImageBmp(android.widget.ImageView view, Bitmap src)
    {
        SetCircleImageBmp(view,src,_radius);
    }
    public void SetCircleImageBmp(android.widget.ImageView view, Bitmap src, int radius)
    {
        if(src == null)
        {
            SetCircleImage(view, null);
            return;
        }
      //  Picasso.with(Global.getCurrentActivity()).load(Global.getCommon().getImageUriString(Global.getCurrentActivity(),src)).transform(new CircleTransform()).rotate(0).into(view);
       RequestOptions requestOptions  =  new RequestOptions()
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .skipMemoryCache(true)
                .override(100, 100)
              //  .circleCropTransform();
               .transform(new MultiTransformation(new CenterCrop(), new CustomRoundedCornersTransformation(Global.getCurrentActivity(), (int)(radius), 0, CustomRoundedCornersTransformation.CornerType.LEFT)));
        Glide.with(Global.getCurrentActivity())
                .load(src)
                .apply(requestOptions)
                // .apply(RequestOptions.circleCropTransform())
                .into(view);
    }


}
