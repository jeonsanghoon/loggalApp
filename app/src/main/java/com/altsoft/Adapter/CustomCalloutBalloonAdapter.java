package com.altsoft.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.altsoft.Framework.Global;
import com.altsoft.loggalapp.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;

public class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
    private  View mCalloutBalloon;

    public CustomCalloutBalloonAdapter(String imageUrl, String desc) {
        mCalloutBalloon = Global.getCurrentActivity().getLayoutInflater().inflate(R.layout.custom_callout_balloon, null);
        ImageView iconImageView  = (ImageView) mCalloutBalloon.findViewById(R.id.badge);

        ((TextView) mCalloutBalloon.findViewById(R.id.desc)).setText(desc);
    }

    @Override
    public View getCalloutBalloon(MapPOIItem poiItem) {

        String[] arrData = poiItem.getItemName().split("\\|");
        ((TextView) mCalloutBalloon.findViewById(R.id.title)).setText(arrData[0]);
        ((TextView) mCalloutBalloon.findViewById(R.id.desc)).setText(arrData[1]);
        ImageView iconImageView  = (ImageView) mCalloutBalloon.findViewById(R.id.badge);
        iconImageView.setImageBitmap(Global.getCommon().loadBitmap(arrData[2],200,200));
        return mCalloutBalloon;
    }

    @Override
    public View getPressedCalloutBalloon(MapPOIItem poiItem) {
        return null;
    }
}
