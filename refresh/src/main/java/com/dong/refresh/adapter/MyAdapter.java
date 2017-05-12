package com.dong.refresh.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dong.refresh.R;

import java.util.List;

/**
 * Created by 川东 on 2016/12/13.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHodler> {

    private List<String> mList;
    private Context mContext;

    public MyAdapter(Context context, List<String> list) {
        this.mContext = context;
        mList = list;
    }

    @Override
    public ViewHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.refresh_item, parent, false);

        return new ViewHodler(view);
    }

    public void clearData(List<String> mList) {
        mList.clear();
        notifyItemRangeRemoved(0, mList.size());
    }

    public void addData(List<String> mList) {
        addData(0,mList );
    }

    public List<String> getData() {
        return mList;
    }

    public void addData(int position, List<String> mList) {

        if(mList!=null&&mList.size()>0){
            mList.addAll(mList);
            notifyItemRangeInserted(position, mList.size());
        }

    }

    @Override
    public void onBindViewHolder(ViewHodler holder, int position) {

        holder.tv_show.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class ViewHodler extends RecyclerView.ViewHolder {
        TextView tv_show;

        public ViewHodler(View itemView) {
            super(itemView);
            tv_show = (TextView) itemView.findViewById(R.id.tv_show);
        }
    }
}
