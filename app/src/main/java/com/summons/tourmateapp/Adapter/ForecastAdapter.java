
package com.summons.tourmateapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.summons.tourmateapp.R;
import com.summons.tourmateapp.Utils.WeatherSharedPreference;
import com.summons.tourmateapp.WeatherModel.Forecast;

import java.util.ArrayList;

/**
 * Created by engrb on 10-Nov-16.
 */

public class ForecastAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Forecast> forecastArrayList;
    private LayoutInflater layoutInflater;
    private String img = "http://l.yimg.com/a/i/us/we/52/";
    WeatherSharedPreference sharedPreference;

    public ForecastAdapter(Context context, ArrayList<Forecast> forecastArrayList) {
        this.context = context;
        this.forecastArrayList = forecastArrayList;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return forecastArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return forecastArrayList.get(i);
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
            viewRow = layoutInflater.inflate(R.layout.forecast_list, null);
            viewHolder = new ViewHolder();

            viewHolder.dateTextView = (TextView) viewRow.findViewById(R.id.dateTextView);
            viewHolder.conditionImageView = (ImageView) viewRow.findViewById(R.id.conditionImageView);
            viewHolder.highTextView = (TextView) viewRow.findViewById(R.id.tempHighTextView);
            viewHolder.lowTextView = (TextView) viewRow.findViewById(R.id.tempLowTextView);
            viewHolder.conditionTextView = (TextView) viewRow.findViewById(R.id.textTextView);
            viewHolder.dayTextView = (TextView) viewRow.findViewById(R.id.dayTextView);

            viewRow.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
        String code = forecastArrayList.get(i).getCode();
        viewHolder.dateTextView.setText(forecastArrayList.get(i).getDate());

        viewHolder.conditionTextView.setText(forecastArrayList.get(i).getText());
        String day = forecastArrayList.get(i).getDay();

        int hf = Integer.parseInt(forecastArrayList.get(i).getHigh());
        int cHTemp = ((hf - 32) * 5) / 9;
        int lf = Integer.parseInt(forecastArrayList.get(i).getLow());
        int clTemp = ((lf - 32) * 5) / 9;
        viewHolder.highTextView.setText(cHTemp + "" + (char) 0x00B0 + "C");
        viewHolder.lowTextView.setText(clTemp + "" + (char) 0x00B0 + "C");
        
        if (day.equals("Sat")) {
            viewHolder.dayTextView.setText("Saturday");
        } else if (day.equals("Sun")) {
            viewHolder.dayTextView.setText("Sunday");
        } else if (day.equals("Mon")) {
            viewHolder.dayTextView.setText("Monday");
        } else if (day.equals("Tue")) {
            viewHolder.dayTextView.setText("TuesDay");
        } else if (day.equals("Wed")) {
            viewHolder.dayTextView.setText("Wednesday");
        } else if (day.equals("Thu")) {
            viewHolder.dayTextView.setText("Thursday");
        } else {
            viewHolder.dayTextView.setText("Friday");

        }

        String image_link = img + code + ".gif";
        Picasso.with(context).load(image_link).error(R.drawable.clear).into(viewHolder.conditionImageView);

        return viewRow;
    }

    public class ViewHolder {
        private TextView dateTextView;
        private ImageView conditionImageView;
        private TextView highTextView;
        private TextView lowTextView;
        private TextView conditionTextView;
        private TextView dayTextView;

    }
}
