package com.summons.tourmateapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.summons.tourmateapp.Model.Event;
import com.summons.tourmateapp.Model.Expense;
import com.summons.tourmateapp.R;

import java.util.ArrayList;

/**
 * Created by engrb on 25-Nov-16.
 */

public class ExpenseListAdapter extends BaseAdapter {

    Context context;
    ArrayList<Expense> expenseArrayList;
    LayoutInflater layoutInflater;

    public ExpenseListAdapter(Context context, ArrayList<Expense> expenseArrayList) {
        this.context = context;
        this.expenseArrayList = expenseArrayList;
        layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return expenseArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return expenseArrayList.get(i);
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
            viewRow = layoutInflater.inflate(R.layout.dashboard_item,null);
            viewHolder = new ViewHolder();

            viewHolder.dateTextView = (TextView)viewRow.findViewById(R.id.dateTextView);
            viewHolder.timeTextView = (TextView)viewRow.findViewById(R.id.timeTextView);
            viewHolder.detailsTextView = (TextView)viewRow.findViewById(R.id.detailsTextView);
            viewHolder.amountTextView = (TextView)viewRow.findViewById(R.id.amountTextView);
            viewRow.setTag(viewHolder);

        }
        else {
            viewHolder  =(ViewHolder)view.getTag();
        }
        viewHolder.dateTextView.setText(expenseArrayList.get(i).getDate());
        viewHolder.timeTextView.setText(expenseArrayList.get(i).getTime());
        viewHolder.detailsTextView.setText(expenseArrayList.get(i).getDetails());
        viewHolder.amountTextView.setText(expenseArrayList.get(i).getAmount()+" ৳");
        return viewRow;
    }

    public class ViewHolder{
        private TextView dateTextView;
        private TextView timeTextView;
        private TextView detailsTextView;
        private TextView amountTextView;
    }
}
