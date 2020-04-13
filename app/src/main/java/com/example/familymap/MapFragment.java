package com.example.familymap;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.ArrayList;

import Model.Event;
import Model.Person;
import Proxy.DataCache;

//spouse:red
//LifeStory:blue
//family:black
//birth: Blue
//Death:Orange
//Marriage:purple
//else:green

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapLoadedCallback {
    private GoogleMap map;
    private View mapView;
    private ImageView genderImageView;
    private String recentMarker = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Iconify.with(new FontAwesomeModule());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(layoutInflater, container, savedInstanceState);
        View view = layoutInflater.inflate(R.layout.fragment_map, container, false);
        mapView = view;
        genderImageView = (ImageView)mapView.findViewById(R.id.genderIcon);
        genderImageView.setImageDrawable(new IconDrawable(getActivity(), FontAwesomeIcons.fa_android).sizeDp(40));
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        //Map methods are on lecture at 47:50
        //set tag and get tag will show how to get specific event
        //marker options let you specify color, 52:10
        map = googleMap;
        map.setOnMapLoadedCallback(this);
        setMarkers();

        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                // Toast.makeText(getActivity(), markerID, Toast.LENGTH_SHORT).show(); -seems like it's getting it
                DataCache dCache = DataCache.getInstance();
                Event event = dCache.findEvent(marker.getTag().toString());
                Person associatedPerson = dCache.findPerson(event.getPersonID());
                recentMarker = (String) marker.getTag();

                String name =  associatedPerson.getFirstName()
                        + " " + associatedPerson.getLastName() + "\n";
                String eventInfo = event.getEventType().toUpperCase() + ": "
                        + event.getCity() + ", "
                        + event.getCountry() + " ("
                        + event.getYear() + ") ";

                Drawable genderIcon;
                if(associatedPerson.getGender().equals("m")) {
                    genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_male).sizeDp(40);
                } else {
                    genderIcon = new IconDrawable(getActivity(), FontAwesomeIcons.fa_female).sizeDp(40);
                }
                genderImageView.setImageDrawable(genderIcon);

                TextView tvName = (TextView)mapView.findViewById(R.id.markerName);
                tvName.setText(name + eventInfo);

                setLines(marker);
                dCache.setActivityPerson(associatedPerson);
                return false;
            }
        });

        LinearLayout selectedPerson = (LinearLayout) mapView.findViewById(R.id.markerPersonLayout);
        selectedPerson.setOnClickListener(v -> {
            DataCache dCache = DataCache.getInstance();
            if(dCache.getActivityPerson() != null) {
                Intent intent = new Intent(getActivity(), PersonActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        DataCache dCache = DataCache.getInstance();
        if(dCache.isSettingsChange()) {
            setMarkers();
            if(recentMarker != null) {
                for(Marker marker: markers) {
                    if(marker.getTag().equals(recentMarker)) {
                        CameraUpdate update = CameraUpdateFactory.newLatLng(marker.getPosition());
                        map.animateCamera(update);
                        setLines(marker);
                    }
                }
                //map.animateCamera(recentMarker);
            }
            dCache.setSettingsChange(false);
        }

    }

    @Override
    public void onMapLoaded() {
        // You probably don't need this callback. It occurs after onMapReady and I have seen
        // cases where you get an error when adding markers or otherwise interacting with the map in
        // onMapReady(...) because the map isn't really all the way ready. If you see that, just
        // move all code where you interact with the map (everything after
        // map.setOnMapLoadedCallback(...) above) to here.
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        Intent intent;
        switch(menu.getItemId()) {
            case R.id.searchMenuItem:
                intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
                return true;

            case R.id.settingsMenuItem:
                intent = new Intent(getActivity(), SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(menu);
        }
        //need to use a recycler view for search
        //use search view @ Views - 19:40 More detail at 41:20
        //Settings should use a gridlayout or constraint layout
    }

    private ArrayList<Marker> markers = new ArrayList<>();
    private void setMarkers() {
        DataCache dCache = DataCache.getInstance();
        ArrayList<Event> allEvent = dCache.getAllEvent();

        if(markers.size() > 0) {
            for(Marker marker : markers)
            {
                marker.remove();
            }
            markers.clear();
        }


        for(Event event: allEvent) {

            Marker marker = map.addMarker(new MarkerOptions()
                    .position(new LatLng(event.getLatitude(), event.getLongitude())));
            marker.setTag(event.getEventID());

            switch (event.getEventType()) {
                case "birth":
                    marker.setIcon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
                    break;
                case "death":
                    marker.setIcon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_ORANGE));
                    break;
                case "marriage":
                    marker.setIcon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA));
                    break;
                default:  //unknown event type
                    marker.setIcon(BitmapDescriptorFactory
                            .defaultMarker(BitmapDescriptorFactory.HUE_GREEN));
                    break;
            }
            markers.add(marker);
        }
        dCache.getPersonID();
    }

    private ArrayList<Polyline> polylines = new ArrayList<>();
    public void setLines(Marker marker) {

        DataCache dCache = DataCache.getInstance();
        ArrayList<Event> allEvent = dCache.getAllEvent();

        if(polylines.size() > 0) {
            for(Polyline line : polylines)
            {
                line.remove();
            }
            polylines.clear();
        }

        if(dCache.isLifeStorySwitch()) {
            drawLifeStoryLines(marker);
        }
        if(dCache.isFamilyTreeSwitch()) {
            drawFamilyTreeLines(marker);
        }
        if(dCache.isSpouseSwitch()) {
            drawSpouseLines(marker);
        }
    }

    private void drawSpouseLines(Marker marker) {
        DataCache dCache = DataCache.getInstance();
        Event currentEvent = dCache.findEvent(marker.getTag().toString());
        Person currentPerson = dCache.findPerson(currentEvent.getPersonID());
        if(currentPerson.getSpouseID() == null
                || dCache.isFemaleSwitch() != dCache.isMaleSwitch()) {
            return;
        }

        Event firstSpouseEvent = dCache.getFirstSpouseEvent(currentPerson.getSpouseID());

        polylines.add(map.addPolyline(new PolylineOptions()
                .add(marker.getPosition(), new LatLng(firstSpouseEvent.getLatitude()
                        ,firstSpouseEvent.getLongitude()))
                .color(Color.RED)));
    }

    private void drawLifeStoryLines(Marker marker) {
        DataCache dCache = DataCache.getInstance();
        Event currentEvent = dCache.findEvent(marker.getTag().toString());

        if(!dCache.findCurrentEvent(currentEvent.getEventID())) {
            return;
        }

        ArrayList<Event> orderedEvents = dCache.getOrderedEvents(currentEvent.getPersonID());
        for(int i = 0; i < orderedEvents.size() - 1; ++i) {
            polylines.add(map.addPolyline(new PolylineOptions()
                    .add(new LatLng(orderedEvents.get(i).getLatitude()
                                    , orderedEvents.get(i).getLongitude())
                            , new LatLng(orderedEvents.get(i + 1).getLatitude()
                                    , orderedEvents.get(i + 1).getLongitude()))
                    .color(Color.BLUE)));
        }
    }

    private void drawFamilyTreeLines(Marker marker) {
        //recursive loop through father and mother side adding the poly lines along the way
        DataCache dCache = DataCache.getInstance();
        Event currentEvent = dCache.findEvent(marker.getTag().toString());
        Person currentPerson = dCache.findPerson(currentEvent.getPersonID());

        drawParentSide(currentEvent, currentPerson.getFatherID(), 16);
        drawParentSide(currentEvent, currentPerson.getMotherID(), 16);
    }

    private void drawParentSide(Event childEvent, String parentID, int width) {
        if(parentID == null) {
            return;
        }
        DataCache dCache = DataCache.getInstance();
        Person currentPerson = dCache.findPerson(childEvent.getPersonID());

        Event parentEvent = dCache.getFirstPersonEvent(parentID);
        if(parentEvent == null || !dCache.findCurrentEvent(parentEvent.getEventID())) {
            return;
        }

        polylines.add(map.addPolyline(new PolylineOptions()
                .add(new LatLng(childEvent.getLatitude()
                                , childEvent.getLongitude())
                        , new LatLng(parentEvent.getLatitude()
                                , parentEvent.getLongitude()))
                .width(width)));

        if(width > 4) width -= 4;

        drawParentSide(parentEvent, currentPerson.getFatherID(), width);
        drawParentSide(parentEvent, currentPerson.getMotherID(), width);
    }
}
