package com.altsoft.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.altsoft.Framework.Global;
import com.altsoft.loggalapp.R;
import com.altsoft.model.signage.MOBILE_SIGNAGE_LIST;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class SignageListViewAdapter extends BaseAdapter {
    private ArrayList<MOBILE_SIGNAGE_LIST> listViewItemList = new ArrayList<MOBILE_SIGNAGE_LIST>() ;
    public ArrayList<MOBILE_SIGNAGE_LIST> getListViewItemList()
    {
        return listViewItemList;
    }
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public MOBILE_SIGNAGE_LIST getItem(int position) {
        return listViewItemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
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
        MOBILE_SIGNAGE_LIST listViewItem = listViewItemList.get(position);


        ((TextView) convertView.findViewById(R.id.txtTitle)).setText(listViewItem.SIGN_NAME);
        ((TextView) convertView.findViewById(R.id.txtDesc)).setText(listViewItem.REMARK);
        ((TextView) convertView.findViewById(R.id.txtUserName)).setText(listViewItem.COMPANY_NAME);
        if(listViewItem.DISTANCE != null) {
            Double dTmp = listViewItem.DISTANCE / 1000.00;
            ((TextView) convertView.findViewById(R.id.txtDistance)).setText((new DecimalFormat("###,##0.000")).format(dTmp) + "km");
        }

        return convertView;
    }

    public Boolean SetDataBind(List<MOBILE_SIGNAGE_LIST> list) {
        if (listViewItemList.size() == 0 ) {
            listViewItemList = (ArrayList) list;
            Global.getData().signagelist = listViewItemList;
            return false;
        }
        for(int i =0; i< list.size(); i++) {
            listViewItemList.add(list.get(i));
        }
        Global.getData().signagelist = listViewItemList;
        this.notifyDataSetChanged();
        return true;

    }
    public void clearData() {
        // clear the data
        listViewItemList.clear();
        Global.getData().signagelist = listViewItemList;
    }
}
