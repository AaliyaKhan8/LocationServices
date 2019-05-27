package com.example.aaliyakhan.locationservices;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class BookRawMaterialsFragment extends Fragment {
    View v;
    Spinner rawmaterialspinner;
    ArrayAdapter adapter;
    Button bookraw;
    EditText raw1,raw2,raw3,raw4;
    public BookRawMaterialsFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v= inflater.inflate(R.layout.fragment_book_raw_materials, container, false);
        init();
        String[] rawmaterialplace={"Thambaram","CMBT","T.Nagar","Adyar"};
        adapter=new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,rawmaterialplace);
        rawmaterialspinner.setAdapter(adapter);
        bookraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                raw1.setText("");
                raw2.setText("");
                raw3.setText("");
                raw4.setText("");
                Toast.makeText(getActivity(), "Raw Materials Booked", Toast.LENGTH_SHORT).show();
            }
        });
        return  v;
    }
    void init()
    {
        rawmaterialspinner=v.findViewById(R.id.rawmaterialplace);
        bookraw=v.findViewById(R.id.bookraw);
        raw1=v.findViewById(R.id.raw1);
        raw2=v.findViewById(R.id.raw2);
        raw3=v.findViewById(R.id.raw3);
        raw4=v.findViewById(R.id.raw4);
    }
    }


