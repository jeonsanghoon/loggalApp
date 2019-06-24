package com.altsoft.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.altsoft.Framework.Global;
import com.altsoft.loggalapp.R;
import com.altsoft.model.T_AD;
import com.altsoft.model.device.AD_DEVICE_MOBILE_LIST;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class LocalBoxListItemAdapter extends BaseAdapter {

    private ArrayList<AD_DEVICE_MOBILE_LIST> listViewItemList = new ArrayList<>() ;
    public LocalBoxListItemAdapter(){
    }
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public AD_DEVICE_MOBILE_LIST getItem(int position) {
        return listViewItemList.get(position) ;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void setItem(View convertView, AD_DEVICE_MOBILE_LIST data)
    {
        this.BookMarkInit(convertView, data);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        TextView titleTextView = (TextView) convertView.findViewById(R.id.textView1) ;
        TextView descTextView = (TextView) convertView.findViewById(R.id.textView2) ;
        TextView userNameView = (TextView) convertView.findViewById(R.id.textView3) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        AD_DEVICE_MOBILE_LIST listViewItem = listViewItemList.get(position);
        Glide.with(context)
                .load(listViewItem.LOGO_URL)
                .apply(new RequestOptions().override(100, 100))
                .into(iconImageView)
        ;

        titleTextView.setText(Global.getValidityCheck().isEmpty(listViewItem.TITLE)? listViewItem.DEVICE_NAME : listViewItem.TITLE);
        descTextView.setText(listViewItem.SUB_TITLE);
        String companyName = "";
        if(!Global.getValidityCheck().isEmpty(listViewItem.COMPANY_NAME))
        {
            String[] arrTmp = listViewItem.COMPANY_NAME.split(">");
            companyName = arrTmp[arrTmp.length-1];
        }

        userNameView.setText(companyName);
        this.BookMarkInit(convertView, listViewItem);
        return convertView;
    }

    private void BookMarkInit(View convertView, AD_DEVICE_MOBILE_LIST data)
    {

        if(data.BANNER_BOOKMARK_YN) {
            ((ImageView) convertView.findViewById(R.id.btnBookmark)).setVisibility(View.VISIBLE);
            ((ImageView) convertView.findViewById(R.id.btnBookmark)).setImageResource(R.drawable.ic_baseline_bookmark_24px);
        }
        else {
            ((ImageView) convertView.findViewById(R.id.btnBookmark)).setVisibility(View.GONE);
        }

        if(data.BANNER_FAVORITE_YN) {
            ((ImageView) convertView.findViewById(R.id.btnFavorite)).setVisibility(View.VISIBLE);
            ((ImageView) convertView.findViewById(R.id.btnFavorite)).setImageResource(R.drawable.ic_baseline_favorite_24px);
        }
        else {
            ((ImageView) convertView.findViewById(R.id.btnFavorite)).setVisibility(View.GONE);
        }
    }

    public Boolean SetDataBind(List<AD_DEVICE_MOBILE_LIST> list) {
        if (listViewItemList.size() == 0) {
            listViewItemList = (ArrayList) list;
            return false;
        }
        for(int i =0; i< list.size(); i++) {
            listViewItemList.add(list.get(i));
        }
        this.notifyDataSetChanged();
        return true;
    }
}
