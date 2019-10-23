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


public class AnswerFlashAdapter extends BaseAdapter {

    private ArrayList<String> mData;
    ArrayList<String> userName;

    private Context mContext;




    public AnswerFlashAdapter(ArrayList<String> mData, ArrayList<String> userName) {

        this.mData = mData;
        this.userName = userName;


    }

    public int getCount() {
        return mData == null ? 0 : mData.size();
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
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;

        if (mContext == null)
            mContext = viewGroup.getContext();
        if (view == null) {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.answerlist, null);
            viewHolder = new ViewHolder();
            viewHolder.userAnswer = view.findViewById(R.id.userAnswer);
            viewHolder.userID = view.findViewById(R.id.userID);
            view.setTag(viewHolder);
        }
        //获取viewHolder实例
        viewHolder = (ViewHolder) view.getTag();


        viewHolder.userID.setText(userName.get(position));

        viewHolder.userAnswer.setText(mData.get(position));


        return view;


    }




    static class ViewHolder {
        TextView userAnswer;
        TextView userID;
    }
}
