package com.summons.tourmateapp.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.summons.tourmateapp.Model.Moment;
import com.summons.tourmateapp.R;

import java.util.ArrayList;

/**
 * Created by engrb on 26-Nov-16.
 */

public class MomentAdapter extends BaseAdapter {
    Context context;
    ArrayList<Moment> momentArrayList;
    LayoutInflater layoutInflater;

    public MomentAdapter(Context context, ArrayList<Moment> momentArrayList) {
        this.context = context;
        this.momentArrayList = momentArrayList;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return momentArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return momentArrayList.get(i);
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
            viewRow = layoutInflater.inflate(R.layout.thumble_image,null);
            viewHolder = new ViewHolder();

            viewHolder.momentImageView = (ImageView)viewRow.findViewById(R.id.thumbnail);

            viewRow.setTag(viewHolder);

        }
        else {
            viewHolder  =(ViewHolder)view.getTag();
        }

        String imageLink= momentArrayList.get(i).getImagePath();
        if (!imageLink.equals("")) {
            viewHolder.momentImageView.setImageURI(Uri.parse(imageLink));
        }

        return viewRow;
    }

    public class ViewHolder{
        private ImageView momentImageView;
    }
}
