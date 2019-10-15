package com.example.login;


import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ShowTag extends AppCompatActivity{
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mTextView2;
    private List<String> ourlist;
    private String tags;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showtag);

        ArrayList<Tagitem> tagList= new ArrayList<>();
        tagList.add(new Tagitem("Chemistry"));
        tagList.add(new Tagitem("Python"));
        tagList.add(new Tagitem("Java"));
        tagList.add(new Tagitem("PHP"));
        tagList.add(new Tagitem("AI"));
        tagList.add(new Tagitem("Math"));
        tagList.add(new Tagitem("Physics"));
        tagList.add(new Tagitem("Biology"));
        tagList.add(new Tagitem("English"));
        tagList.add(new Tagitem("Mechanics"));
        tagList.add(new Tagitem("Mobile computing"));
        tagList.add(new Tagitem("C++"));
        tagList.add(new Tagitem("Database"));
        tagList.add(new Tagitem("Algorithm"));

        mTextView2=findViewById(R.id.tagslected);

        mRecyclerView= findViewById(R.id.recyclerView);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new Adaptertag(tagList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        ourlist=new Adaptertag(tagList).getList();
        tags=listToString(ourlist,' ');
        mTextView2.setText(tags);




    }
    public String listToString(List list, char separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1){
                sb.append(list.get(i));}
            else {
                sb.append(list.get(i));
                sb.append(separator);
            }
        }
        return sb.toString();
    }
}