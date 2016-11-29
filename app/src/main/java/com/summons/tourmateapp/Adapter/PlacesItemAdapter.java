package com.summons.tourmateapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.summons.tourmateapp.R;

/**
 * Created by engrb on 07-Apr-16.
 */
public class PlacesItemAdapter extends BaseAdapter {

    private String[] result;
    private Context context;
    private int[] imageId;


    private LayoutInflater layoutInflater = null;

    public PlacesItemAdapter(Context mainActivity, String[] optionList, int[] optionImages) {
        result = optionList;
        context = mainActivity;
        imageId = optionImages;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {


        final ViewHolder viewHolder = new ViewHolder();
        final View itemView;
        itemView = layoutInflater.inflate(R.layout.option_list, null);

        viewHolder.imageView = (ImageView) itemView.findViewById(R.id.itemImageView);
        viewHolder.textView = (TextView) itemView.findViewById(R.id.itemTextView);

        viewHolder.imageView.setImageResource(imageId[position]);
        viewHolder.textView.setText(result[position]);


        return itemView;
    }

    public class ViewHolder {

        private ImageView imageView;
        private TextView textView;
    }
}
