package com.example.aaliyakhan.locationservices;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    ArrayList list;
    Listener listener;

    public ListAdapter(Context context, ArrayList list) {
        this.context = context;
        this.list = list;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    void setListener(Listener list){
        this.listener=list;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView=inflater.inflate(R.layout.item,parent,false);
        final TextView name,from,to,code;
        Button start;
        OrderData data=(OrderData) list.get(position);
        start=convertView.findViewById(R.id.start);
        from=convertView.findViewById(R.id.from);
        to=convertView.findViewById(R.id.to);
        name=convertView.findViewById(R.id.name);
        code=convertView.findViewById(R.id.code);
        name.setText("Name:"+data.getName());
        code.setText("Code:"+data.getCode());
        from.setText("From:"+data.getFrom());
        to.setText("To:"+data.getTo());

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.move(position);
            }
        });

        return convertView;
    }
}
