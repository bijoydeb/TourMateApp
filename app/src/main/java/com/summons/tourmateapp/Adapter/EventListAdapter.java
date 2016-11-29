package com.summons.tourmateapp.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.summons.tourmateapp.Model.Event;
import com.summons.tourmateapp.R;

import java.util.ArrayList;

/**
 * Created by engrb on 18-Nov-16.
 */

public class EventListAdapter extends BaseAdapter {
    Context context;
    ArrayList<Event> eventArrayList;
    LayoutInflater layoutInflater;

    public EventListAdapter(Context context, ArrayList<Event> eventArrayList) {
        this.context = context;
        this.eventArrayList = eventArrayList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return eventArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return eventArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewRow = view;
        ViewHolder viewHolder;
        if (view==null){
            viewRow = layoutInflater.inflate(R.layout.event_list,null);
            viewHolder = new ViewHolder();

            viewHolder.destinationTV = (TextView)viewRow.findViewById(R.id.destinationTV);
            viewHolder.budgetTV = (TextView)viewRow.findViewById(R.id.budgetTV);
            viewHolder.fromDateTV = (TextView)viewRow.findViewById(R.id.fromDateTV);
            viewHolder.toDateTV = (TextView)viewRow.findViewById(R.id.toDateTV);
            viewRow.setTag(viewHolder);

        }
        else {
            viewHolder  =(ViewHolder)view.getTag();
        }
        viewHolder.destinationTV.setText(eventArrayList.get(i).getDestination());
        viewHolder.budgetTV.setText("Est Budget : "+eventArrayList.get(i).getBudget()+" ৳");
        viewHolder.fromDateTV.setText(eventArrayList.get(i).getFromDate());
        viewHolder.toDateTV.setText(eventArrayList.get(i).getToDate());
        return viewRow;
    }

    public class ViewHolder{
        private TextView destinationTV;
        private TextView budgetTV;
        private TextView fromDateTV;
        private TextView toDateTV;
    }
}
