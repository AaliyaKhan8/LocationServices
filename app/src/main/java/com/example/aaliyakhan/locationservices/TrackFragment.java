package com.example.aaliyakhan.locationservices;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.here.android.mpa.common.GeoCoordinate;
import com.here.android.mpa.common.GeoPosition;
import com.here.android.mpa.common.Image;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.mapping.MapMarker;
import com.here.android.mpa.mapping.MapObject;
import com.here.android.mpa.search.DiscoveryResult;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class TrackFragment extends Fragment {

    MapFragment mapFragment;
    Map m_map;
    DatabaseReference myRef;
    PositioningManager posManager;
    HashMap<String,String>map=new HashMap<String,String>();
    List<MapObject> m_mapObjectList=new ArrayList<>();
    public TrackFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_track, container, false);

        mapFragment = (MapFragment) getActivity().getFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.init(new OnEngineInitListener() {
            @Override
            public void onEngineInitializationCompleted(
                    OnEngineInitListener.Error error) {
                if (error == OnEngineInitListener.Error.NONE) {
                    posManager = PositioningManager.getInstance();
                    if (posManager != null) {
                        posManager.start(
                                PositioningManager.LocationMethod.GPS_NETWORK);
                    }
                    m_map = mapFragment.getMap();
                    addLocationListener();
                    posManager.addListener(new WeakReference<PositioningManager.OnPositionChangedListener>(positionListener));
                    m_map.getPositionIndicator().setVisible(true);
                } else {
                    System.out.println("ERROR: Cannot initialize SupportMapFragment");
                }
            }
        });

        return  view;
    }
    private PositioningManager.OnPositionChangedListener positionListener = new
            PositioningManager.OnPositionChangedListener() {

                public void onPositionUpdated(PositioningManager.LocationMethod method,
                                              GeoPosition position, boolean isMapMatched) {
                    m_map.setCenter(position.getCoordinate(),
                            Map.Animation.LINEAR);

                }


                public void onPositionFixChanged(PositioningManager.LocationMethod method,
                                                 PositioningManager.LocationStatus status) {
                }
            };



    public void addLocationListener(){
        myRef= FirebaseDatabase.getInstance().getReference("message");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Log.d("changed", "Value is: " + dataSnapshot.getValue());
                for (DataSnapshot a:dataSnapshot.getChildren()){
                    System.out.println("for"+a.getValue());

                    map.put(a.getKey(),a.getValue()+"");
                    map.put(a.getKey(),a.getValue()+"");
                }



                GeoCoordinate geoCoordinate=new GeoCoordinate(Double.parseDouble(map.get("latitude")),Double.parseDouble(map.get("longitude")));


                Image img = new Image();

                try {
                    img.setImageResource(R.drawable.ma);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                MapMarker mapMarker = new MapMarker();
                mapMarker.setIcon(img);

                mapMarker.setCoordinate(geoCoordinate);
                m_map.addMapObject(mapMarker);
                m_mapObjectList.add(mapMarker);

                m_map.setCenter(geoCoordinate, Map.Animation.NONE);
                m_map.getPositionIndicator();

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("changed", "Failed to read value.", error.toException());
            }
        });


    }
}
