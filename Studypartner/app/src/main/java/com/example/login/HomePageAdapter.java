//package com.example.login;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.BaseAdapter;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
//public class HomePageAdapter extends BaseAdapter {
//    private ArrayList<String> mData;
//    ArrayList<String> userName;
//
//    private Context mContext;
//
//
//
//
//    public HomePageAdapter(ArrayList<String> mData, ArrayList<String> userName) {
//
//        this.mData = mData;
//        this.userName = userName;
//
//
//    }
//
//    public int getCount() {
//        return mData == null ? 0 : mData.size();
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return mData.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public View getView(int position, View view, ViewGroup viewGroup) {
//        TextView viewHolder = null;
//
//        if (mContext == null)
//            mContext = new TextView(getActivity());
//        if (view == null) {
//            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.answerlist, null);
//            viewHolder = new AnswerFlashAdapter.ViewHolder();
//            viewHolder.userAnswer = view.findViewById(R.id.userAnswer);
//            viewHolder.userID = view.findViewById(R.id.userID);
//            view.setTag(viewHolder);
//        }
//        //获取viewHolder实例
//        viewHolder = (AnswerFlashAdapter.ViewHolder) view.getTag();
//
//
//        viewHolder.userID.setText(userName.get(position));
//
//        viewHolder.userAnswer.setText(mData.get(position));
//
//
//        return view;
//
//
//    }
//
//
//
//
//    static class ViewHolder {
//        TextView userAnswer;
//        TextView userID;
//    }
//}
