package com.dong.sortviewpagerdemo.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dong.sortviewpagerdemo.R;
import com.dong.sortviewpagerdemo.entitys.IconInfo;

import java.util.ArrayList;
import java.util.List;

public class myGridViewAdapter extends BaseAdapter {
    private List<IconInfo> mData = new ArrayList<IconInfo>();
    private Context mContext;

    public myGridViewAdapter(List<IconInfo> data, Context context) {
        mData = data;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View item, ViewGroup parent) {
        item = LayoutInflater.from(mContext).inflate(R.layout.grideview_item_layout, null);
        ImageView icon = (ImageView) item.findViewById(R.id.image);
        TextView iconName = (TextView) item.findViewById(R.id.sortName);
        icon.setBackgroundResource(mData.get(position).getIconId());
        iconName.setText(mData.get(position).getIconName());
        return item;
    }
}