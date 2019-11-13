package com.altsoft.Adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.altsoft.loggalapp.R;
import com.altsoft.model.T_AD;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class BannerListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    public ArrayList<T_AD> listViewItemList = new ArrayList<T_AD>() ;

    // ListViewAdapter의 생성자
    public BannerListViewAdapter() {

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
        T_AD listViewItem = listViewItemList.get(position);
        Glide.with(context)
                .load(listViewItem.LOGO_URL)
                .apply(new RequestOptions().override(100, 100))
                .into(iconImageView)
        ;

        titleTextView.setText(listViewItem.TITLE);
        descTextView.setText(listViewItem.SUB_TITLE);
        userNameView.setText(listViewItem.COMPANY_NAME);
        if(listViewItem.BOOKMARK_YN) {
            ((ImageView) convertView.findViewById(R.id.btnBookmark)).setVisibility(View.VISIBLE);
            ((ImageView) convertView.findViewById(R.id.btnBookmark)).setImageResource(R.drawable.ic_baseline_bookmark_24px);
        }
        else {
            ((ImageView) convertView.findViewById(R.id.btnBookmark)).setVisibility(View.GONE);
        }

        if(listViewItem.ITEM_TYPE == 1000) convertView.setBackground(ContextCompat.getDrawable(context,R.drawable.itemborder));
        else if(listViewItem.ITEM_TYPE == 10000) convertView.setBackground(ContextCompat.getDrawable(context,R.drawable.itemborder2));
        else if(listViewItem.ITEM_TYPE == 100000) convertView.setBackground(ContextCompat.getDrawable(context,R.drawable.itemborder3));


        if(listViewItem.FAVORITE_YN) {
            ((ImageView) convertView.findViewById(R.id.btnFavorite)).setVisibility(View.VISIBLE);
            ((ImageView) convertView.findViewById(R.id.btnFavorite)).setImageResource(R.drawable.ic_baseline_favorite_24px);
        }
        else {
            ((ImageView) convertView.findViewById(R.id.btnFavorite)).setVisibility(View.GONE);
        }
        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public T_AD getItem(int position) {
        return listViewItemList.get(position) ;
    }

    public void setItem(View convertView, T_AD data)
    {
        if(data.BOOKMARK_YN) {
            ((ImageView) convertView.findViewById(R.id.btnBookmark)).setVisibility(View.VISIBLE);
            ((ImageView) convertView.findViewById(R.id.btnBookmark)).setImageResource(R.drawable.ic_baseline_bookmark_24px);
        }
        else {
            ((ImageView) convertView.findViewById(R.id.btnBookmark)).setVisibility(View.GONE);
        }

        if(data.FAVORITE_YN){
            ((ImageView) convertView.findViewById(R.id.btnFavorite)).setVisibility(View.VISIBLE);
            ((ImageView) convertView.findViewById(R.id.btnFavorite)).setImageResource(R.drawable.ic_baseline_favorite_24px);
        }
        else
        {
            ((ImageView) convertView.findViewById(R.id.btnFavorite)).setVisibility(View.GONE);
        }
    }

    public Boolean SetDataBind(List<T_AD> list) {
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


    public void clearData() {
        // clear the data
        listViewItemList.clear();
    }
}

