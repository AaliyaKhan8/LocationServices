package com.example.aaliyakhan.locationservices;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.here.android.mpa.common.GeoPosition;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;

import java.lang.ref.WeakReference;


public class MapFragments extends Fragment {
    private PositioningManager posManager;
    boolean paused;
    Map m_map;
    MapFragment mapFragment;

    public MapFragments() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_map, container, false);
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

//                    m_map.setTrafficInfoVisible(true);
                    posManager.addListener(
                            new WeakReference<PositioningManager.OnPositionChangedListener>(positionListener));
                    m_map.getPositionIndicator().setVisible(true);
                } else {
                    System.out.println("ERROR: Cannot initialize SupportMapFragment");
                }
            }
        });
        return  v;
    }

    private PositioningManager.OnPositionChangedListener positionListener = new
            PositioningManager.OnPositionChangedListener() {

                public void onPositionUpdated(PositioningManager.LocationMethod method,
                                              GeoPosition position, boolean isMapMatched) {
                    // set the center only when the app is in the foreground
                    // to reduce CPU consumption
                    if (!paused)
                        m_map.setCenter(position.getCoordinate(),
                                Map.Animation.LINEAR);

                }


                public void onPositionFixChanged(PositioningManager.LocationMethod method,
                                                 PositioningManager.LocationStatus status) {
                }
            };
}
