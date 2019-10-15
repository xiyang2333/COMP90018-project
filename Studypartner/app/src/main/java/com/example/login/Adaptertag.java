package com.example.login;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Adaptertag extends RecyclerView.Adapter<Adaptertag.viewHolder> {
    private ArrayList<Tagitem> mtaglist;
    private static List<String> mlist =new ArrayList<String>();
    public List<String> getList(){
        return mlist;
    }
    public static class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView mTextView1;



        public viewHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            mTextView1 = itemView.findViewById(R.id.textView);

        }
        public void onClick(View view){
            if (mlist.size()<4){
                mlist.add(view.getContext().toString().trim());
            }
            else{
                // seems like toast function can't be used in static class
            }
        }

    }

    public Adaptertag(ArrayList<Tagitem> taglist){
        mtaglist=taglist;
    }
    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.tagname,parent,false);
        viewHolder vh = new viewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        Tagitem currentItem= mtaglist.get(position);
        holder.mTextView1.setText(currentItem.getText1());


    }

    @Override
    public int getItemCount() {
        return mtaglist.size();
    }
}
