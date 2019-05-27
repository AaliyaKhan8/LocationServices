package com.example.aaliyakhan.locationservices;

import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.here.android.mpa.common.GeoPosition;
import com.here.android.mpa.common.OnEngineInitListener;
import com.here.android.mpa.common.PositioningManager;
import com.here.android.mpa.mapping.Map;
import com.here.android.mpa.mapping.MapFragment;
import com.here.android.mpa.search.DiscoveryResult;
import com.here.android.mpa.search.DiscoveryResultPage;
import com.here.android.mpa.search.ErrorCode;
import com.here.android.mpa.search.PlaceLink;
import com.here.android.mpa.search.ResultListener;
import com.here.android.mpa.search.SearchRequest;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class BookTruckFragment extends Fragment {
    double lat,lng;
    Spinner trucktype;
    Button bookbtn;
    ArrayAdapter truckadapter;
    MapFragment mapFragment;
    ListView fromlist,tolist;
    public static List<DiscoveryResult> s_ResultList;
    Map m_map;
    TextInputEditText from,to;
    Boolean paused;
    ArrayList suggestionlist;
    PositioningManager posManager;
    View view;
     public BookTruckFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      view = inflater.inflate(R.layout.fragment_book_truck, container, false);
        init();
        String truckstr[] = {"Semi Trailer Trucks", "Tail Lift Trucks", "Jumbo Trailer Trucks", "Flatbed Trucks", "Straight trucks", "Refrigerated Trucks"};
        truckadapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, truckstr);
        trucktype.setAdapter(truckadapter);

        from.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                SearchRequest searchRequest = new SearchRequest(s.toString());
                searchRequest.setSearchCenter(m_map.getCenter());
                searchRequest.execute(discoveryResultPageListener);
                fromlist.setVisibility(View.VISIBLE);


            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        to.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                SearchRequest searchRequest = new SearchRequest(s.toString());
                searchRequest.setSearchCenter(m_map.getCenter());
                searchRequest.execute(tolocationlistener);
                tolist.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        bookbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getActivity(), "Successfully Booked Truck", Toast.LENGTH_SHORT).show();
            }
        });

      return view;
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
    void init()
    {
        trucktype=view.findViewById(R.id.trucktype);
        from=view.findViewById(R.id.from);
        fromlist=view.findViewById(R.id.fromlist);
        to=view.findViewById(R.id.to);
        tolist=view.findViewById(R.id.tolist);
        bookbtn=view.findViewById(R.id.book);
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

                    posManager.addListener(
                            new WeakReference<PositioningManager.OnPositionChangedListener>(positionListener));


                } else {
                    System.out.println("ERROR: Cannot initialize SupportMapFragment");
                }
            }
        });

    }

    private ResultListener<DiscoveryResultPage> discoveryResultPageListener = new ResultListener<DiscoveryResultPage>() {
        @Override
        public void onCompleted(DiscoveryResultPage discoveryResultPage, ErrorCode errorCode) {
            if (errorCode == ErrorCode.NONE) {

                s_ResultList = discoveryResultPage.getItems();
                suggestionlist=new ArrayList();

                for (DiscoveryResult item : s_ResultList) {

                    suggestionlist.add(item.getTitle());

                    Log.e( "items: ", item.getTitle());


                    Log.e( "onCompleted: ",item.getTitle() );
                    if (item.getResultType() == DiscoveryResult.ResultType.PLACE) {
                        PlaceLink placeLink = (PlaceLink) item;
                        lat=placeLink.getPosition().getLatitude();
                        lng=placeLink.getPosition().getLongitude();

                    }
                    fromlist.setAdapter(new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,suggestionlist));
                    fromlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            from.setText(suggestionlist.get(i).toString());
                            fromlist.setVisibility(View.GONE);
                        }
                    });

                }
            } else {
                Toast.makeText(getActivity(),
                        "ERROR:Discovery search request returned return error code+ " + errorCode,
                        Toast.LENGTH_SHORT).show();
            }
        }




    };
    private ResultListener<DiscoveryResultPage> tolocationlistener = new ResultListener<DiscoveryResultPage>() {
        @Override
        public void onCompleted(DiscoveryResultPage discoveryResultPage, ErrorCode errorCode) {
            if (errorCode == ErrorCode.NONE) {

                s_ResultList = discoveryResultPage.getItems();
                suggestionlist=new ArrayList();
                for (DiscoveryResult item : s_ResultList) {

                    suggestionlist.add(item.getTitle());

                    Log.e( "items: ", item.getTitle());

                    Log.e( "onCompleted: ",item.getTitle() );
                    if (item.getResultType() == DiscoveryResult.ResultType.PLACE) {
                        PlaceLink placeLink = (PlaceLink) item;
                        lat=placeLink.getPosition().getLatitude();
                        lng=placeLink.getPosition().getLongitude();

                    }

                    tolist.setAdapter(new ArrayAdapter(getActivity(),android.R.layout.simple_list_item_1,suggestionlist));
                    tolist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            to.setText(suggestionlist.get(i).toString());
                            tolist.setVisibility(View.GONE);
                        }
                    });
                }
            } else {
                Toast.makeText(getActivity(),
                        "ERROR:Discovery search request returned return error code+ " + errorCode,
                        Toast.LENGTH_SHORT).show();
            }
        }




    };

    @Override
    public void onResume() {
        super.onResume();
        paused = false;
        if (posManager != null) {
            posManager.start(
                    PositioningManager.LocationMethod.GPS_NETWORK);
        }
    }
    @Override
    public void onPause() {
        if (posManager != null) {
            posManager.stop();
        }
        super.onPause();
        paused = true;
    }
    @Override
    public void onDestroy() {
        if (posManager != null) {
            posManager.removeListener(
                    positionListener);
        }
        m_map = null;
        super.onDestroy();
    }
}