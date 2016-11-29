package com.summons.tourmateapp.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.summons.tourmateapp.Adapter.MomentAdapter;
import com.summons.tourmateapp.Database.EventManager;
import com.summons.tourmateapp.Database.MomentManager;
import com.summons.tourmateapp.DialogFragments.SlideshowDialogFragment;
import com.summons.tourmateapp.Model.Event;
import com.summons.tourmateapp.Model.Moment;
import com.summons.tourmateapp.R;
import com.summons.tourmateapp.Utils.TourMateSharedPreference;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class MomentFragment extends Fragment {

    Spinner momentSpinner;
    GridView momentGridView;
    TextView tourTextView;

    ArrayList<Event> eventNameArrayList;
    ArrayList<String> eventNameList;
    ArrayList<Moment> momentArrayList;

    EventManager eventManager;
    MomentManager momentManager;

    MomentAdapter momentAdapter;

    TourMateSharedPreference sharedPreference;

    String userId;
    String eventId;

    public MomentFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_moment, container, false);

        momentSpinner = (Spinner) view.findViewById(R.id.momentListSpinner);
        momentGridView = (GridView) view.findViewById(R.id.momentGridView);
        tourTextView = (TextView) view.findViewById(R.id.tourTextView);

        sharedPreference = new TourMateSharedPreference(getActivity());
        userId = sharedPreference.getUserId();

        eventNameArrayList = new ArrayList<>();
        eventNameList = new ArrayList<>();
        eventNameList.add("Select Event");
        momentArrayList = new ArrayList<>();


        eventManager = new EventManager(getActivity());
        momentManager = new MomentManager(getActivity());

        eventNameArrayList = eventManager.getEventName();

        for (Event event : eventNameArrayList) {
            eventNameList.add(event.getDestination());
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, eventNameList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        momentSpinner.setAdapter(arrayAdapter);

        momentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String eventName = (String) adapterView.getItemAtPosition(i);
                if (i != 0) {
                    eventId = eventManager.getEventId(eventName);
                    momentArrayList = momentManager.getAllMoment(userId, eventId);
                    tourTextView.setText(eventName + " Tour Moment List");
                    momentAdapter = new MomentAdapter(getActivity(), momentArrayList);
                    momentGridView.setAdapter(momentAdapter);

                } else {
                    Toast.makeText(getActivity(), "Select Event", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        momentGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("images", momentArrayList);
                bundle.putInt("position", i);
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                SlideshowDialogFragment newFragment = SlideshowDialogFragment.newInstance();
                newFragment.setArguments(bundle);
                newFragment.show(ft, "slideshow");
            }
        });

        return view;
    }

}
