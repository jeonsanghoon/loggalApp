package com.altsoft.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.altsoft.loggalapp.R;
import com.altsoft.model.search.MOBILE_AD_SEARCH_DATA;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class SearchBannerAdapter  extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<MOBILE_AD_SEARCH_DATA> listViewItemList = new ArrayList<MOBILE_AD_SEARCH_DATA>() ;

    // ListViewAdapter의 생성자
    public SearchBannerAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
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
        MOBILE_AD_SEARCH_DATA listViewItem = listViewItemList.get(position);
        Glide.with(context)
                .load(listViewItem.LOGO_URL)
                .apply(new RequestOptions().override(100, 100))
                .into(iconImageView)
        ;

        titleTextView.setText(listViewItem.TITLE);
        descTextView.setText(listViewItem.SUB_TITLE);
        userNameView.setText(listViewItem.COMPANY_NAME);
        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public MOBILE_AD_SEARCH_DATA getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public Boolean SetDataBind(List<MOBILE_AD_SEARCH_DATA> list) {
        return this.SetDataBind(list, false);
    }
    public Boolean SetDataBind(List<MOBILE_AD_SEARCH_DATA> list, Boolean bFirst) {

        if (listViewItemList.size() == 0  || bFirst) {
            listViewItemList = (ArrayList) list;
            return false;
        }
        else {
            for (int i = 0; i < list.size(); i++) {
                listViewItemList.add(list.get(i));
            }
        }
        this.notifyDataSetChanged();

        return true;
    }
}
