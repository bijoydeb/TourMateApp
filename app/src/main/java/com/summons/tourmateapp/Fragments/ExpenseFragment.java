package com.summons.tourmateapp.Fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.summons.tourmateapp.Adapter.EventListAdapter;
import com.summons.tourmateapp.Adapter.ExpenseListAdapter;
import com.summons.tourmateapp.Database.EventManager;
import com.summons.tourmateapp.Database.ExpenseManager;
import com.summons.tourmateapp.Model.Event;
import com.summons.tourmateapp.Model.Expense;
import com.summons.tourmateapp.R;
import com.summons.tourmateapp.Utils.TourMateSharedPreference;

import java.util.ArrayList;
import java.util.Calendar;

public class ExpenseFragment extends Fragment {
    Context context;
    ListView expenseListView;
    EditText expenseDetailsEditText;
    EditText amountEditText;
    EditText dateEditText;
    EditText timeEditText;
    TextView tourTextView;

    Button submitButton;
    Button cancelButton;

    Spinner eventSpinner;
    Spinner eventListSpinner;

    TourMateSharedPreference sharedPreference;
    String eventName;
    String event_name;
    EventManager eventManager;
    ExpenseManager expenseManager;
    String userId;
    String eventId = "1";
    Expense expense;
    ArrayList<Event> eventArrayList;
    ArrayList<Event> eventNameArrayList;
    ArrayList<String> eventNameList;
    ArrayList<Expense> expenseArrayList;
    ExpenseListAdapter expenseListAdapter;

    public ExpenseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_expense, container, false);
        this.context = getActivity().getApplicationContext();

        expenseListView = (ListView) view.findViewById(R.id.expenseListView);
        eventListSpinner = (Spinner) view.findViewById(R.id.eventListSpinner);
        tourTextView = (TextView)view.findViewById(R.id.tourTextView);

        eventNameArrayList = new ArrayList<>();

        eventNameList = new ArrayList<>();
        eventNameList.add("Select Event");

        eventManager = new EventManager(getActivity());
        expenseManager = new ExpenseManager(getActivity());
        eventNameArrayList = eventManager.getEventName();

        for (Event event : eventNameArrayList) {
            eventNameList.add(event.getDestination());
        }

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, eventNameList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventListSpinner.setAdapter(arrayAdapter);

        eventListSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String eventName= (String) adapterView.getItemAtPosition(i);

               if (i!=0){
                   eventManager = new EventManager(context);
                   expenseArrayList = new ArrayList<>();
                   expenseArrayList = new ArrayList<>();
                   expenseManager = new ExpenseManager(context);
                   sharedPreference = new TourMateSharedPreference(context);

                   userId = sharedPreference.getUserId();
                   String eventId= eventManager.getEventId(eventName);
                   tourTextView.setText(eventName+" Tour Expense List");
                   expenseArrayList = expenseManager.getAllExpense(userId,eventId);

                   expenseListAdapter = new ExpenseListAdapter(context, expenseArrayList);
                   expenseListView.setAdapter(expenseListAdapter);

               }else {
                   Toast.makeText(getActivity(), "Select a Event", Toast.LENGTH_SHORT).show();
               }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return view;

    }


    public void refreshPage() {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.detach(this).attach(this).commit();
    }

    @SuppressLint("ValidFragment")
    private class ExpenseDialog extends DialogFragment {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
            LayoutInflater inflater = getActivity().getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.expence_dialoge, null);
            dialogBuilder.setView(dialogView);

            expenseDetailsEditText = (EditText) dialogView.findViewById(R.id.expenseDetailsEditText);
            amountEditText = (EditText) dialogView.findViewById(R.id.amountEditText);
            dateEditText = (EditText) dialogView.findViewById(R.id.dateEditText);
            timeEditText = (EditText) dialogView.findViewById(R.id.timeEditText);
            submitButton = (Button) dialogView.findViewById(R.id.submitButton);
            cancelButton = (Button) dialogView.findViewById(R.id.cancelButton);

            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, eventNameList);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            eventSpinner.setAdapter(arrayAdapter);


            eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0) {
                        eventName = "";
                    } else {
                        eventName = (String) eventSpinner.getItemAtPosition(i);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            dateEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                            dateEditText.setText(selectedDay + "/" + selectedMonth + "/" + selectedYear);
                        }
                    }, year, month, day);
                    datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                    datePickerDialog.show();
                }
            });

            timeEditText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Calendar calendar = Calendar.getInstance();
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH);
                    int day = calendar.get(Calendar.DAY_OF_MONTH);

                    DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {
                            timeEditText.setText(selectedDay + "/" + selectedMonth + "/" + selectedYear);

                        }
                    }, year, month, day);
                    datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis());
                    datePickerDialog.show();
                }
            });

//            dialogBuilder.setTitle("Create Event");
            submitButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String expense_details = expenseDetailsEditText.getText().toString().trim();
                    String amount = amountEditText.getText().toString().trim();
                    String date = dateEditText.getText().toString().trim();
                    String time = timeEditText.getText().toString().trim();

                    if (eventName.equals("")) {
                        Toast.makeText(getActivity(), "select event!", Toast.LENGTH_SHORT).show();
                    } else if (expense_details.equals("")) {
                        Toast.makeText(getActivity(), "Enter Expense Details!", Toast.LENGTH_SHORT).show();
                    } else if (amount.equals("")) {
                        Toast.makeText(getActivity(), "Enter Amount!", Toast.LENGTH_SHORT).show();
                    } else if (date.equals("")) {
                        Toast.makeText(getActivity(), "Enter To Date!", Toast.LENGTH_SHORT).show();
                    } else {

                        String event_id = eventManager.getEventId(eventName);
                        Log.e("EF", "a : " + event_id + expense_details + amount + date + time);
                        expense = new Expense(event_id, expense_details, amount, date, time);
                        long success = expenseManager.addExpense(expense);
                        if (success > 0) {
                            refreshPage();
                            getDialog().dismiss();
                            Toast.makeText(getActivity(), "Expense Add Successful", Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(getActivity(), "Expense Add Failed !", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
            cancelButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getDialog().dismiss();
                }
            });

            AlertDialog alertDialog = dialogBuilder.create();

            return alertDialog;

        }

    }
}
