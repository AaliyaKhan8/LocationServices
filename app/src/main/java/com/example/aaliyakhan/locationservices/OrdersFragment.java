package com.example.aaliyakhan.locationservices;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class OrdersFragment extends Fragment implements Listener {
    ListView listView;
    ListAdapter listAdapter;
    public OrdersFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=inflater.inflate(R.layout.fragment_orders, container, false);

        listView = view.findViewById(R.id.listview);
        ConstantsData.list.add(new OrderData("Ramesh","ghansoli","Airoli","001"));
        ConstantsData.list.add(new OrderData("Suresh","ghansoli","Mumbra","002"));
        ConstantsData.list.add(new OrderData("Rajesh","ghansoli","Thane","003"));
        ConstantsData.list.add(new OrderData("Raj","ghansoli","Navi Mumbai","004"));
        ConstantsData.list.add(new OrderData("Kumar","ghansoli","Airoli","005"));
        listAdapter=new ListAdapter(getActivity(),ConstantsData.list);
        listView.setAdapter(listAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // When clicked, show a toast with the TextView text
                Toast.makeText(getActivity(),
                        "position"+position, Toast.LENGTH_SHORT).show();
            }
        });

        listAdapter.setListener(this);
        return view;
    }


    @Override
    public void move(int pos) {
//        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.con)

        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,new TrackFragment()).commit();
        Toast.makeText(getActivity(), "pos"+pos, Toast.LENGTH_SHORT).show();
    }
}
