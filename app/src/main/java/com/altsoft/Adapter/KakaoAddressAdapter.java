package com.altsoft.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.altsoft.loggalapp.R;

import com.altsoft.model.daummap.DAUM_ADDRESS;


import java.util.ArrayList;
import java.util.List;

public class KakaoAddressAdapter extends ArrayAdapter {
    private ArrayList<DAUM_ADDRESS> listViewItemList = new ArrayList<DAUM_ADDRESS>() ;
    private int itemLayout;

    public KakaoAddressAdapter(Context context, int resource, List<DAUM_ADDRESS> list) {
        super(context, resource, list);
        itemLayout = resource;
        listViewItemList = (ArrayList<DAUM_ADDRESS>) list;
    }

    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public String getItem(int position) {
        return listViewItemList.get(position).address_name;
    }
    public DAUM_ADDRESS getObject(int position) {
        return listViewItemList.get(position);
    }
    private DAUM_ADDRESS selectedItem;
    public DAUM_ADDRESS getSelectedItem() {
        selectedItem = selectedItem == null ? new DAUM_ADDRESS() : selectedItem;
        return selectedItem;
    }

    public void setSelectedItem(DAUM_ADDRESS selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext())
                    .inflate(itemLayout, parent, false);
        }

        TextView strName = (TextView) convertView.findViewById(R.id.txtAutoCompleate);
        strName.setText(getItem(position));
        return convertView;
    }

    public Boolean SetDataBind(List<DAUM_ADDRESS> list) {
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