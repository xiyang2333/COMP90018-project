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


public class PostAdapter extends BaseAdapter implements View.OnClickListener {

    private ArrayList<String> mData;
    ArrayList<Integer> mPostId;
    private Context mContext;
    int userId;


    public PostAdapter(ArrayList<String> mData, int userId, ArrayList<Integer> mPostId) {

        this.mData = mData;
        this.userId = userId;
        this.mPostId = mPostId;
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.searchedpost, null);
            viewHolder = new ViewHolder();
            viewHolder.mTv = view.findViewById(R.id.mTv);
            viewHolder.mBtn = view.findViewById(R.id.mBtn);
            view.setTag(viewHolder);
        }
        //获取viewHolder实例
        viewHolder = (ViewHolder) view.getTag();
        //设置tag标记
        viewHolder.mBtn.setTag(R.id.btn, position);//添加此代码
        viewHolder.mBtn.setText("Show More");
        viewHolder.mBtn.setOnClickListener(this);
        viewHolder.mTv.setText(mData.get(position));
        //设置tag标记
        viewHolder.mTv.setTag(R.id.tv, position);//添加此代码
        viewHolder.mTv.setOnClickListener(this);
        return view;


    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.mBtn:
                int b = (int) view.getTag(R.id.btn);
                int postId = mPostId.get(b);
                Intent intent = new Intent(mContext, PostDetail.class);
                intent.putExtra("postId", postId);

                System.out.println(postId + "在adapter 中的PostId");

                intent.putExtra("userId", userId);
                mContext.startActivity(intent);
                break;
            case R.id.mTv:
                int t = (int) view.getTag(R.id.tv);
                Toast.makeText(mContext, "我是文本" + t, Toast.LENGTH_SHORT).show();
                break;
        }
    }


    static class ViewHolder {
        TextView mTv;
        Button mBtn;
    }
}
