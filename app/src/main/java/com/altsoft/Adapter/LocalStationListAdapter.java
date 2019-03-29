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

import com.altsoft.model.device.T_DEVICE_STATION;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

public class LocalStationListAdapter  extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<T_DEVICE_STATION> listViewItemList = new ArrayList<T_DEVICE_STATION>() ;

    // ListViewAdapter의 생성자
    public LocalStationListAdapter() {

    }
    public ArrayList<T_DEVICE_STATION> getListViewItemList()
    {
        return listViewItemList;
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
            convertView = inflater.inflate(R.layout.listview_item_localbox, parent, false);
        }

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        T_DEVICE_STATION listViewItem = listViewItemList.get(position);

        ImageView iconImageView = (ImageView) convertView.findViewById(R.id.imageView1) ;
        ((TextView) convertView.findViewById(R.id.txtTitle)).setText(listViewItem.STATION_NAME);
        ((TextView) convertView.findViewById(R.id.txtDesc)).setText(listViewItem.STATION_DESC);



        ((TextView) convertView.findViewById(R.id.txtUserName)).setText("기기수 : " + listViewItem.DEVICE_CNT.toString());
        Glide.with(context)
                .load(listViewItem.LOGO_URL)
                .apply(new RequestOptions().override(100, 100))
                .into(iconImageView)
        ;
        if(listViewItem.BOOKMARK_YN) {
            ((ImageView) convertView.findViewById(R.id.btnBookmark)).setVisibility(View.VISIBLE);
            ((ImageView) convertView.findViewById(R.id.btnBookmark)).setImageResource(R.drawable.ic_baseline_bookmark_24px);
        }
        else {
            ((ImageView) convertView.findViewById(R.id.btnBookmark)).setVisibility(View.GONE);
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
    public T_DEVICE_STATION getItem(int position) {
        return listViewItemList.get(position) ;
    }
    public void setItem(View convertView, T_DEVICE_STATION data) {
        if(data.BOOKMARK_YN) {
            ((ImageView) convertView.findViewById(R.id.btnBookmark)).setVisibility(View.VISIBLE);
            ((ImageView) convertView.findViewById(R.id.btnBookmark)).setImageResource(R.drawable.ic_baseline_bookmark_24px);
        }
        else {
            ((ImageView) convertView.findViewById(R.id.btnBookmark)).setVisibility(View.GONE);
        }
    }
    public Boolean SetDataBind(List<T_DEVICE_STATION> list) {
        return this.SetDataBind(list, false);
    }

    public boolean SetDataBind(List<T_DEVICE_STATION> list, Boolean bFirst) {
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
    public void clearData() {
        // clear the data
        listViewItemList.clear();
        Global.getData().stationlist = listViewItemList;
    }
}
