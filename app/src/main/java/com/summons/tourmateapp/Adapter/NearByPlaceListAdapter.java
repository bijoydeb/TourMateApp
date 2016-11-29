package com.summons.tourmateapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.summons.tourmateapp.Model.Result;
import com.summons.tourmateapp.R;

import java.util.List;

/**
 * Created by ripon on 11/23/2016.
 */

public class NearByPlaceListAdapter extends BaseAdapter {


    private Context context;
    private List<Result> placesArrayList;
    private LayoutInflater layoutInflater;

    public NearByPlaceListAdapter(Context context, List<Result> placesArrayList) {
        this.context = context;
        this.placesArrayList = placesArrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return placesArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return placesArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        View viewRow = view;
        if (view == null) {
            viewRow = layoutInflater.inflate(R.layout.near_by_placeslist, null);
            viewHolder = new ViewHolder();

            viewHolder.nameTextView = (TextView) viewRow.findViewById(R.id.nameTextView);
            viewHolder.addressTextView = (TextView) viewRow.findViewById(R.id.addressTextView);

            viewRow.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String name = placesArrayList.get(i).getName();
        String address = placesArrayList.get(i).getVicinity();

        viewHolder.nameTextView.setText(name);
        viewHolder.addressTextView.setText(address);
        return viewRow;
    }

     public class ViewHolder {
        private TextView nameTextView;
        private TextView addressTextView;

    }
}
