package com.altsoft.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.altsoft.Framework.Global;
import com.altsoft.loggalapp.R;
import com.altsoft.loggalapp.SignageControlActivity;
import com.altsoft.loggalapp.WebViewActivity;
import com.altsoft.model.T_AD;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class BannerRecyclerViewAdapter extends RecyclerView.Adapter<BannerRecyclerViewAdapter.RecyclerViewHolder> {
    private Context mContext;
    private ArrayList<T_AD> listViewItemList = new ArrayList<T_AD>() ;

    public BannerRecyclerViewAdapter() {

    }

    public void SetBind(ArrayList<T_AD> list, Integer page)
    {
        if(page == 1)       listViewItemList = list;
        else listViewItemList.addAll(list);
    }

  @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_item, parent, false);
        mContext = parent.getContext();
        RecyclerViewHolder holder = new RecyclerViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, final int position) {
        T_AD listViewItem = listViewItemList.get(position);
        holder.titleTextView.setText(listViewItem.TITLE);
        ImageView iconImageView = (ImageView) holder.iconImageView ;
        Glide.with(Global.getCurrentActivity())
                .load(listViewItem.LOGO_URL)
                .apply(new RequestOptions().override(100, 100))
                .into(iconImageView)
        ;
        holder.descTextView.setText(listViewItem.SUB_TITLE);
        holder.userNameView.setText(listViewItem.COMPANY_NAME);
        //Here it is simply write onItemClick listener here
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Toast.makeText(context, position +"", Toast.LENGTH_LONG).show();
                T_AD adItem = getItem(position);
                //Toast.makeText(getActivity(),adItem.TITLE  + "가 선택되었습니다.", Toast.LENGTH_LONG).show();
                if (adItem.SIGN_CODE == null) {
                    Intent intent = new Intent(Global.getCurrentActivity(), WebViewActivity.class);
                    intent.putExtra("T_AD", adItem);
                    Global.getCurrentActivity().startActivity(intent);
                } else {
                    /// 사이니지제어
                    Toast.makeText(Global.getCurrentActivity(), adItem.TITLE + "가 선택되었습니다.", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Global.getCurrentActivity(), SignageControlActivity.class);
                    intent.putExtra("SIGN_CODE", adItem.SIGN_CODE);
                    Global.getCurrentActivity().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return listViewItemList.size();
    }

    public T_AD getItem(int position) {
        return listViewItemList.get(position);
    }
    public void clear() {
        final int size = listViewItemList.size();
        if (size > 0) {
            for (int i = 0; i < size; i++) {
                listViewItemList.remove(0);
            }

            notifyItemRangeRemoved(0, size);
        }
    }
    public  class RecyclerViewHolder extends RecyclerView.ViewHolder {
        public ImageView iconImageView;
        public TextView titleTextView;
        public TextView descTextView;
        public TextView userNameView;
        public final View mView;
        public RecyclerViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            iconImageView = (ImageView) itemView.findViewById(R.id.imageView1) ;
            titleTextView = (TextView) itemView.findViewById(R.id.textView1) ;
            descTextView = (TextView) itemView.findViewById(R.id.textView2) ;
            userNameView = (TextView) itemView.findViewById(R.id.textView3) ;
        }
    }

}
