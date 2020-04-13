package com.example.familymap;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.joanzapata.iconify.IconDrawable;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeIcons;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

import java.util.List;

import Model.Event;
import Model.Person;
import Proxy.DataCache;

public class PersonActivity extends AppCompatActivity {
    Person personActivityPerson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        Iconify.with(new FontAwesomeModule());
        View view = getLayoutInflater().inflate(R.layout.activity_person, null);

        DataCache dCache= DataCache.getInstance();
        personActivityPerson = dCache.getActivityPerson();
        TextView firstName = (TextView)findViewById(R.id.personFirstName);
        firstName.setText(personActivityPerson.getFirstName());

        TextView lastName = (TextView)findViewById(R.id.personlastName);
        lastName.setText(personActivityPerson.getLastName());

        TextView gender = (TextView)findViewById(R.id.personGender);
        if(personActivityPerson.getGender().equals("m")) {
            gender.setText("Male");
        }
        if(personActivityPerson.getGender().equals("f")) {
            gender.setText("Female");
        }

        ExpandableListView expandableListView = findViewById(R.id.expandableListView);
        List<Person> personFamMembers = dCache.getDisplayPeople();
        List<Event> userEvents = dCache.getOrderedEvents(personActivityPerson.getPersonID());
        expandableListView.setAdapter(new ExpandableListAdapter(personFamMembers, userEvents));
    }

    private class ExpandableListAdapter extends BaseExpandableListAdapter {

        private static final int EVENT_POSITION = 0;
        private static final int PERSON_POSITION = 1;

        private final List<Event> personEvents;
        private final List<Person> personFam;

        ExpandableListAdapter(List<Person> famMembers, List<Event> lifeEvents) {
            this.personFam = famMembers;
            personEvents = lifeEvents;
        }

        @Override
        public int getGroupCount() {
            return 2;
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            switch (groupPosition) {
                case EVENT_POSITION:
                    return personEvents.size();
                case PERSON_POSITION:
                    return personFam.size();
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getGroup(int groupPosition) {
            switch (groupPosition) {
                case EVENT_POSITION:
                    return "LIFE EVENTS";
                case PERSON_POSITION:
                    return "FAMILY";
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public Object getChild(int groupPosition, int childPosition) {
            switch (groupPosition) {
                case EVENT_POSITION:
                    return personEvents.get(childPosition);
                case PERSON_POSITION:
                    return personFam.get(childPosition);
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }

        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_group, parent, false);
            }

            TextView titleView = convertView.findViewById(R.id.listTitle);

            switch (groupPosition) {
                case EVENT_POSITION:
                    titleView.setText("LIFE EVENTS");
                    break;
                case PERSON_POSITION:
                    titleView.setText("FAMILY");
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            View itemView;

            switch (groupPosition) {
                case EVENT_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.item_event, parent, false);
                    initializeEventView(itemView, childPosition);
                    break;
                case PERSON_POSITION:
                    itemView = getLayoutInflater().inflate(R.layout.item_person, parent, false);
                    initializePersonView(itemView, childPosition);
                    break;
                default:
                    throw new IllegalArgumentException("Unrecognized group position: " + groupPosition);
            }

            return itemView;
        }

        private void initializePersonView(View personView, final int childPosition) {
            TextView resortNameView = personView.findViewById(R.id.personName);
            String personName = personFam.get(childPosition).getFirstName()
                    + " " + personFam.get(childPosition).getLastName();
            resortNameView.setText(personName);

            TextView resortLocationView = personView.findViewById(R.id.familyRole);
            resortLocationView.setText("TODO");

            personView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(this, getString(R.string.skiResortToastText, skiResorts.get(childPosition).getName()), Toast.LENGTH_SHORT).show();
                }
            });

            ImageView genderImageView = (ImageView)personView.findViewById(R.id.genderItemIcon);
            Drawable genderIcon;
            if(personFam.get(childPosition).getGender().equals("m")) {
                genderIcon = new IconDrawable(personView.getContext(), FontAwesomeIcons.fa_male).sizeDp(40);
            } else {
                genderIcon = new IconDrawable(personView.getContext(), FontAwesomeIcons.fa_female).sizeDp(40);
            }
            genderImageView.setImageDrawable(genderIcon);
        }

        private void initializeEventView(View personView, final int childPosition) {
            TextView eventPersonName = personView.findViewById(R.id.eventPersonName);
            String personName = personActivityPerson.getFirstName()
                    + " " + personActivityPerson.getLastName();
            eventPersonName.setText(personName);

            TextView eventInfo = personView.findViewById(R.id.eventInfo);
            String allEventInfo = personEvents.get(childPosition).getEventType()
                    + ": " + personEvents.get(childPosition).getCity()
                    + ", " + personEvents.get(childPosition).getCountry()
                    + "( " + personEvents.get(childPosition).getYear() + ")";
            eventInfo.setText(allEventInfo);

            personView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(this, getString(R.string.skiResortToastText, skiResorts.get(childPosition).getName()), Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP |
                    Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
        return true;
    }

}
