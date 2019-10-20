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

public class Adaptertag extends RecyclerView.Adapter<Adaptertag.ViewHolder> {

    private ArrayList<Tagitem> mtaglist;

    private static List<String> mlist =new ArrayList<String>();
    public List<String> getList(){
        return mlist;
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        View mTextView1;
        TextView tagName ;




        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTextView1 = itemView;
            tagName = itemView.findViewById(R.id.textView);

        }

    }

    public Adaptertag(ArrayList<Tagitem> taglist){
        mtaglist=taglist;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tagname,parent,false);

        final ViewHolder holder = new ViewHolder(view);

        holder.mTextView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Tagitem tag = mtaglist.get(position);
                Toast.makeText(view.getContext(),"you clicked" + tag.getText1(),Toast.LENGTH_SHORT).show();
                if (mlist.size()<4){
                    mlist.add(view.getContext().toString().trim());
                }
                else{
                    Toast.makeText(view.getContext(), "maximum num of tag 4 reached!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
        holder.tagName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Tagitem tag = mtaglist.get(position);
                Toast.makeText(view.getContext(),"you clicked " + tag.getText1(),Toast.LENGTH_SHORT).show();
                if (mlist.size()<4){
                    mlist.add(view.getContext().toString().trim());
                }
                else{
                    Toast.makeText(view.getContext(), "maximum num of tag 4 reached!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Tagitem currentItem= mtaglist.get(position);
        holder.tagName.setText(currentItem.getText1());
    }



    @Override
    public int getItemCount() {
        return mtaglist.size();
    }
}
