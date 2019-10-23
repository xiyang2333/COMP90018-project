package com.example.login;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class ActivityAdapter extends BaseAdapter implements View.OnClickListener{

    private Context context;
    private ArrayList<MyActivity> data;
    private int userId;
    public ActivityAdapter(ArrayList<MyActivity> data, int userId){
        this.data = data;
        this.userId = userId;
    }
    @Override
    public int getCount() {
        return data == null ? 0 : data.size();
    }
    @Override
    public Object getItem(int i) {
        return data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if(context == null)
            context = viewGroup.getContext();
        if(view == null){
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.join_activity_item,null);
            viewHolder = new ViewHolder();
            viewHolder.mTvtitle = view.findViewById(R.id.item_join_activity_title);
            viewHolder.mTvDes = view.findViewById(R.id.item_join_activity_des);
            viewHolder.mBtn = view.findViewById(R.id.btn_join_activity);
            view.setTag(viewHolder);
        }
        viewHolder = (ViewHolder)view.getTag();
        //设置tag标记
        viewHolder.mBtn.setTag(R.id.btn_2,i);
        viewHolder.mBtn.setOnClickListener(this);
        viewHolder.mTvtitle.setText(data.get(i).getTitle());
        viewHolder.mTvDes.setText(data.get(i).getSnippet());
        return view;
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_join_activity:
                int b = (int) view.getTag(R.id.btn_2);
                Toast.makeText(context, data.get(b).getTitle(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context,JoinSelectedActivity.class);
                intent.putExtra("userId",userId);
                intent.putExtra("activityId", data.get(b).getActivityId());
                context.startActivity(intent);
                break;
        }
    }
    static class ViewHolder{
        TextView mTvtitle;
        TextView mTvDes;
        Button mBtn;
    }
}