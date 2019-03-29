package com.altsoft.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.altsoft.loggalapp.R;
import com.altsoft.model.keyword.CODE_DATA;

import java.util.ArrayList;
import java.util.List;

public class SearchAdapter extends ArrayAdapter {
    private ArrayList<CODE_DATA> listViewItemList = new ArrayList<CODE_DATA>() ;
    private int itemLayout;


    public SearchAdapter(Context context, int resource, List<CODE_DATA> list) {
        super(context, resource, list);
        itemLayout = resource;
        listViewItemList = (ArrayList<CODE_DATA>) list;
    }
    @Override
    public int getCount() {
        return listViewItemList.size();
    }

    @Override
    public String getItem(int position) {
        return listViewItemList.get(position).NAME;
    }
    public CODE_DATA getObject(int position) {
        return listViewItemList.get(position);
    }
    private CODE_DATA selectedItem;
    public CODE_DATA getSelectedItem() {
        selectedItem = selectedItem == null ? new CODE_DATA() : selectedItem;
        selectedItem.NAME = "";
        return selectedItem;
    }

    public void setSelectedItem(CODE_DATA selectedItem) {
        this.selectedItem = selectedItem;
    }

    @Override
    public long getItemId(int position) {
        return listViewItemList.get(position).CODE;
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

    public Boolean SetDataBind(List<CODE_DATA> list) {
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
